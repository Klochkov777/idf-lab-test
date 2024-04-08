package com.klochkov.idflabtest.service.impl;


import com.klochkov.idflabtest.dto.LimitBalanceDto;
import com.klochkov.idflabtest.entity.Transaction;
import com.klochkov.idflabtest.exception.ResourceNotFoundException;
import com.klochkov.idflabtest.repository.TransactionRepository;
import com.klochkov.idflabtest.service.TransactionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;


@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    /**
     * Method for getting LimitBalanceDto Transaction.
     *
     * @param transaction - transaction
     */
    @Transactional
    public LimitBalanceDto getCharacteristicsOfLimitAndBalanceForTransactions(Transaction transaction) {
        Optional<LimitBalanceDto> optionalLimitBalanceDto = transactionRepository
                .getLimitBalanceDto(transaction.getExpenseCategory(), transaction.getAccountFrom());
        return optionalLimitBalanceDto
                .orElseThrow(() -> new ResourceNotFoundException("Not found characteristics of Limit and Balance for "
                        + transaction));
    }
    /**
     * Method for saving Transaction.
     *
     * @param transaction - transaction for saving
     */
    public Transaction saveTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }
    /**
     * Method for getting all transactions which have limit with limit_exceeded true.
     */
    public List<Transaction> getAllTransactionsWithLimitExceeded() {
       return transactionRepository.findAllWhereLimitExceeded().orElseThrow(() -> new RuntimeException(""));
    }
}
