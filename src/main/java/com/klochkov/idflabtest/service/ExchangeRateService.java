package com.klochkov.idflabtest.service;

import com.klochkov.idflabtest.entity.ExchangeRate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public interface ExchangeRateService {
    /**
     * Method for getting ExchangeRate by currency for last date.
     *
     * @param currency - currency
     */
    ExchangeRate getExchangeRateByCurrencyForLastDate(String currency);
    /**
     * Method for uploading exchange rate for list of currencies.
     */
    void uploadExchangeRate();
    /**
     * Method for converting different currency sum to USD.
     *
     * @param currency - currency
     * @param sum - sum for converting
     */
    BigDecimal convertToUSD(String currency, BigDecimal sum);
}
