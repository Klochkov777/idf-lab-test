package com.klochkov.idflabtest.service;

import com.klochkov.idflabtest.dto.LimitBalanceDto;
import com.klochkov.idflabtest.entity.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TransactionService {
    /**
     * Method for getting LimitBalanceDto Transaction.
     *
     * @param transaction - transaction
     */
    LimitBalanceDto getCharacteristicsOfLimitAndBalanceForTransactions(Transaction transaction);
    /**
     * Method for saving Transaction.
     *
     * @param transaction - transaction for saving
     */
    Transaction saveTransaction(Transaction transaction);
    /**
     * Method for getting all transactions which have limit with limit_exceeded true.
     */
    List<Transaction> getAllTransactionsWithLimitExceeded();
}
