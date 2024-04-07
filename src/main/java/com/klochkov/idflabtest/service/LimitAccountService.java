package com.klochkov.idflabtest.service;

import com.klochkov.idflabtest.entity.Balance;
import com.klochkov.idflabtest.entity.LimitAccount;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface LimitAccountService {
    /**
     * Method for getting limit by id.
     *
     * @param uuid - uuid of limitAccount.
     */
    LimitAccount getLimitAccountById(UUID uuid);
    /**
     * Method for changing limit_exceeded if balance less zero.
     */
    void changeLimitExceededAsNeeded(Balance balance, LimitAccount limitAccount);
}
