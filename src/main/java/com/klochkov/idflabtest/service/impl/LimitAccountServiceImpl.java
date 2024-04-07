package com.klochkov.idflabtest.service.impl;

import com.klochkov.idflabtest.entity.Balance;
import com.klochkov.idflabtest.entity.LimitAccount;
import com.klochkov.idflabtest.exception.ResourceNotFoundException;
import com.klochkov.idflabtest.repository.LimitAccountRepository;
import com.klochkov.idflabtest.service.LimitAccountService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LimitAccountServiceImpl implements LimitAccountService {
    private final LimitAccountRepository limitAccountRepository;
    /**
     * Method for getting limit by id.
     *
     * @param uuid - uuid of limitAccount
     */
    @Transactional
    public LimitAccount getLimitAccountById(UUID uuid) {
        return limitAccountRepository.findById(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("not found limit with id: " + uuid));
    }
    /**
     * Method for changing limit_exceeded if balance less zero.
     */
    @Transactional
    public void changeLimitExceededAsNeeded(Balance balance, LimitAccount limitAccount) {
        if (balance.getBalanceAmount().compareTo(BigDecimal.ZERO) <= 0) {
            limitAccount.setLimitExceeded(true);
            limitAccountRepository.save(limitAccount);
        }
    }
}
