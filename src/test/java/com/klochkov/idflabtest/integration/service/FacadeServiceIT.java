package com.klochkov.idflabtest.integration.service;

import com.klochkov.idflabtest.entity.Transaction;
import com.klochkov.idflabtest.enumeration.Category;
import com.klochkov.idflabtest.service.ExchangeRateService;
import com.klochkov.idflabtest.service.TransactionService;
import com.klochkov.idflabtest.service.LimitAccountService;
import com.klochkov.idflabtest.service.BalanceService;
import com.klochkov.idflabtest.service.FacadeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacadeServiceIT extends AbstractTestIT {
    private final ExchangeRateService exchangeRateService;
    private final TransactionService transactionService;
    private final LimitAccountService limitAccountService;
    private final BalanceService balanceService;
    private final FacadeService facadeService;
    private final Long account1 = 1111111L;
    private final Long account2 = 2222222L;

    @Autowired
    public FacadeServiceIT(ExchangeRateService exchangeRateService, TransactionService transactionService,
                           LimitAccountService limitAccountService, BalanceService balanceService,
                           FacadeService facadeService) {
        this.exchangeRateService = exchangeRateService;
        this.transactionService = transactionService;
        this.limitAccountService = limitAccountService;
        this.balanceService = balanceService;
        this.facadeService = facadeService;
    }

    @Test
    @Transactional
    @DisplayName("save transaction")
    void whenSaveTransaction() {
        Transaction transaction = new Transaction(null, account1, account2, "EUR",
                new BigDecimal("500.00"), Category.PRODUCT, OffsetDateTime
                .parse("2024-12-23T12:00+03:00"), null);
        Transaction transactionAfterSaving = facadeService.saveTransactionAndChangeBalance(transaction);
        Assertions.assertNotNull(transactionAfterSaving);
        Assertions.assertEquals(new BigDecimal("1200.00"), transactionAfterSaving.getLimitAccount().getLimitAmount());
    }
}
