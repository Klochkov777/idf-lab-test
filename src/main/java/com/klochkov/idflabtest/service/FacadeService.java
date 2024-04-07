package com.klochkov.idflabtest.service;

import com.klochkov.idflabtest.entity.Transaction;
import org.springframework.stereotype.Service;

@Service
public interface FacadeService {
    /**
     * Method for saving transaction and change balance and limit as needed.
     *
     * @param transaction - transaction for saving
     */
    Transaction saveTransactionAndChangeBalance(Transaction transaction);
}
