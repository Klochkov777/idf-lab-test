package com.klochkov.idflabtest.service.impl;

import com.klochkov.idflabtest.dto.LimitBalanceDto;
import com.klochkov.idflabtest.entity.Balance;
import com.klochkov.idflabtest.entity.LimitAccount;
import com.klochkov.idflabtest.entity.Transaction;
import com.klochkov.idflabtest.service.TransactionService;
import com.klochkov.idflabtest.service.FacadeService;
import com.klochkov.idflabtest.service.ExchangeRateService;
import com.klochkov.idflabtest.service.LimitAccountService;
import com.klochkov.idflabtest.service.BalanceService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class FacadeServiceImpl implements FacadeService {
    private final ExchangeRateService exchangeRateService;
    private final TransactionService transactionService;
    private final LimitAccountService limitAccountService;
    private final BalanceService balanceService;
    /**
     * Method for saving transaction and change balance and limit as needed.
     *
     * @param transaction - transaction for saving
     */
    @Transactional
    public Transaction saveTransactionAndChangeBalance(Transaction transaction) {
        LimitBalanceDto limitBalanceDto = transactionService
                .getCharacteristicsOfLimitAndBalanceForTransactions(transaction);
        BigDecimal sumUSD = exchangeRateService.convertToUSD(transaction.getCurrencyShortname(), transaction.getSum());
        LimitAccount limitAccount = limitAccountService.getLimitAccountById(limitBalanceDto.getLimitId());
        transaction.setLimitAccount(limitAccount);
        Balance balance = balanceService.addBalance(transaction, limitBalanceDto, sumUSD, limitAccount);
        limitAccountService.changeLimitExceededAsNeeded(balance, limitAccount);
        return transactionService.saveTransaction(transaction);
    }
}
