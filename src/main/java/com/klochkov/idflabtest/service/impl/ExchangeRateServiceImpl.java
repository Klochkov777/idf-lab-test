package com.klochkov.idflabtest.service.impl;

import com.klochkov.idflabtest.dto.ResponseCurrencyRateDto;
import com.klochkov.idflabtest.entity.ExchangeRate;
import com.klochkov.idflabtest.repository.ExchangeRateRepository;
import com.klochkov.idflabtest.service.ExchangeRateService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExchangeRateServiceImpl implements ExchangeRateService {
    private final ExchangeRateRepository exchangeRateRepository;
    private final WebClient webClient = WebClient.builder()
            .baseUrl("https://api.twelvedata.com").build();
    private final List<String> symbols;
    private final String apiKeyTwelwedata;

    /**
     * Method for uploading exchange rate for list of currencies.
     */
    @Transactional
    @Scheduled(cron = "0 0 0 * * *")
    public void uploadExchangeRate() {
        symbols.stream().forEach(symbol -> {
            Mono<ResponseCurrencyRateDto> responseCurrencyRateDtoMono = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/eod")
                            .queryParam("symbol", symbol + "/USD")
                            .queryParam("apikey", apiKeyTwelwedata)
                            .build())
                    .retrieve().bodyToMono(ResponseCurrencyRateDto.class);
            responseCurrencyRateDtoMono.subscribe(responseCurrencyRateDto -> {
                        ExchangeRate exchangeRate = ExchangeRate.builder()
                                .rate(responseCurrencyRateDto.getClose())
                                .exchangeDate(responseCurrencyRateDto.getDatetime())
                                .currency(symbol)
                                .build();
                        System.out.println(exchangeRate);
                        exchangeRateRepository.save(exchangeRate);
                    },
                    error -> {
                        log.error(error.getMessage());
                    });
        });
    }
    /**
     * Method for getting ExchangeRate by currency for last date.
     *
     * @param currency - currency
     */
    public ExchangeRate getExchangeRateByCurrencyForLastDate(String currency) {
        Optional<ExchangeRate> optionalExchangeRate = exchangeRateRepository
                .findFirstByCurrencyOrderByExchangeDateDesc(currency);
        return optionalExchangeRate
                .orElseThrow(() -> new RuntimeException("Not found exchangeRate for currency "
                        + currency));
    }
    /**
     * Method for converting different currency sum to USD.
     *
     * @param currency - currency
     * @param sum - sum for converting
     */
    @Transactional
    public BigDecimal convertToUSD(String currency, BigDecimal sum) {
        ExchangeRate exchangeRate = getExchangeRateByCurrencyForLastDate(currency);
        return exchangeRate.getRate().multiply(sum);
    }
}
