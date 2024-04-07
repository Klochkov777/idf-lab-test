package com.klochkov.idflabtest.service;

import com.klochkov.idflabtest.entity.Balance;
import com.klochkov.idflabtest.entity.LimitAccount;
import org.springframework.stereotype.Service;
import com.klochkov.idflabtest.enumeration.Category;

import java.math.BigDecimal;
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
    LimitAccount changeLimitExceededAsNeeded(Balance balance, LimitAccount limitAccount);

    /**
     * Method for setting new limit.
     *
     * @param id - uuid of limitAccount.
     * @return new limitAccount
     */
    LimitAccount setLimitByLatestLimitId(UUID id, BigDecimal limit);
    /**
     * Method for setting new limit.
     *
     * @param category - category.
     * @param account - account.
     * @return new limitAccount
     */
    LimitAccount setLimitByCategoryAndAccount(Category category, Long account, BigDecimal limit);
}
