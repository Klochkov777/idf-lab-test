package com.klochkov.idflabtest.service;

import com.klochkov.idflabtest.dto.LimitBalanceDto;
import com.klochkov.idflabtest.entity.Balance;
import com.klochkov.idflabtest.entity.LimitAccount;
import com.klochkov.idflabtest.entity.Transaction;

import java.math.BigDecimal;

public interface BalanceService {
    /**
     * Method for add new balance because old balance will not need in future. Possible way it'll need for history.
     *
     * @param transaction - transaction
     * @param dataAboutOldBalance - info about old balance for adding to new balance
     * @param sumUSD - sum for subtracting
     * @param limitAccount - info about limit
     */
    Balance addBalance(Transaction transaction, LimitBalanceDto dataAboutOldBalance,
                       BigDecimal sumUSD, LimitAccount limitAccount);
}
