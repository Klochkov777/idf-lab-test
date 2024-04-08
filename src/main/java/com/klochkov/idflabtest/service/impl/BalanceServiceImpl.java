package com.klochkov.idflabtest.service.impl;

import com.klochkov.idflabtest.dto.LimitBalanceDto;
import com.klochkov.idflabtest.entity.Balance;
import com.klochkov.idflabtest.entity.LimitAccount;
import com.klochkov.idflabtest.entity.Transaction;
import com.klochkov.idflabtest.enumeration.Category;
import com.klochkov.idflabtest.exception.ResourceNotFoundException;
import com.klochkov.idflabtest.repository.BalanceRepository;
import com.klochkov.idflabtest.service.BalanceService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BalanceServiceImpl implements BalanceService {
    private final BalanceRepository balanceRepository;
    /**
     * Method for add new balance because old balance will not need in future. Possible way it'll need for history.
     *
     * @param transaction - transaction
     * @param dataAboutOldBalance - info about old balance for adding to new balance
     * @param sumUSD - sum for subtracting
     * @param limitAccount - info about limit
     */
    @Transactional
    public Balance addBalance(Transaction transaction, LimitBalanceDto dataAboutOldBalance,
                              BigDecimal sumUSD, LimitAccount limitAccount) {
        Balance oldBalance = getBalanceById(dataAboutOldBalance.getBalanceId());
        Balance balance = Balance.builder()
                .balanceAmount(dataAboutOldBalance.getAmount().subtract(sumUSD))
                .date(transaction.getDateTime())
                .account(transaction.getAccountFrom())
                .category(transaction.getExpenseCategory())
                .limitAccount(limitAccount)
                .isLatest(true)
                .build();
        oldBalance.setIsLatest(false);
        balanceRepository.save(oldBalance);
        return balanceRepository.save(balance);
    }
    /**
     * Method for searching balance by id.
     *
     * @param id - id for searching
     */
    @Transactional
    public Balance getBalanceById(UUID id) {
        return balanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found balance with id: " + id));
    }
    /**
     * Method for searching latest balance by id of limit.
     *
     * @param limitAccountId - id for searching
     */
    @Transactional
    public Balance getBalanceByLimitIdLatest(UUID limitAccountId) {
        return balanceRepository.findBalanceByLimitAccountIdAndIsLatest(limitAccountId)
                .orElseThrow(() -> new ResourceNotFoundException("Balance for limit with id: " + limitAccountId
                + "was not found"));
    }
    /**
     * Method for saving balance.
     *
     * @param balance - balance
     * @return  balance
     */
    @Transactional
    public Balance saveBalance(Balance balance) {
        return balanceRepository.save(balance);
    }
    /**
     * Method for searching balance.
     *
     * @param category - category
     * @param account - account
     * @return  balance
     */
    @Transactional
    public Balance getBalanceByCategoryAndAccountLatest(Category category, Long account) {
        return balanceRepository.findBalanceByAccountAndCategoryAndIsLatestTrue(account, category);
    }
}
