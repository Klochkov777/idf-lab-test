package com.klochkov.idflabtest.service.impl;

import com.klochkov.idflabtest.dto.LimitBalanceDto;
import com.klochkov.idflabtest.entity.Balance;
import com.klochkov.idflabtest.entity.LimitAccount;
import com.klochkov.idflabtest.entity.Transaction;
import com.klochkov.idflabtest.repository.BalanceRepository;
import com.klochkov.idflabtest.service.BalanceService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

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
        Balance balance = Balance.builder()
                .balanceAmount(dataAboutOldBalance.getAmount().subtract(sumUSD))
                .date(transaction.getDateTime())
                .account(transaction.getAccountFrom())
                .category(transaction.getExpenseCategory())
                .limitAccount(limitAccount)
                .build();
        return balanceRepository.save(balance);
    }
}
