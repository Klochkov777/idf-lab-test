package com.klochkov.idflabtest.service;

import com.klochkov.idflabtest.dto.LimitBalanceDto;
import com.klochkov.idflabtest.entity.Balance;
import com.klochkov.idflabtest.entity.LimitAccount;
import com.klochkov.idflabtest.entity.Transaction;
import com.klochkov.idflabtest.enumeration.Category;

import java.math.BigDecimal;
import java.util.UUID;

public interface BalanceService {

    /**
     * Method for add new balance because old balance will not need in future. Possible way it'll need for history.
     *
     * @param transaction         - transaction
     * @param dataAboutOldBalance - info about old balance for adding to new balance
     * @param sumUSD              - sum for subtracting
     * @param limitAccount        - info about limit
     */
    Balance addBalance(Transaction transaction, LimitBalanceDto dataAboutOldBalance,
                       BigDecimal sumUSD, LimitAccount limitAccount);

    /**
     * Method for searching latest balance by id of limit.
     *
     * @param limitAccountId - id for searching
     */
    Balance getBalanceByLimitIdLatest(UUID limitAccountId);

    /**
     * Method for saving balance.
     *
     * @param balance - for saving
     */
    Balance saveBalance(Balance balance);
    /**
     * Method for searching latest balance by category and account.
     *
     * @param category - category for searching
     * @param account - category for searching
     */
    Balance getBalanceByCategoryAndAccountLatest(Category category, Long account);
}
