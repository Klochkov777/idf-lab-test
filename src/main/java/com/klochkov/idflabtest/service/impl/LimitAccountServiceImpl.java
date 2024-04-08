package com.klochkov.idflabtest.service.impl;

import com.klochkov.idflabtest.entity.Balance;
import com.klochkov.idflabtest.entity.LimitAccount;
import com.klochkov.idflabtest.enumeration.Category;
import com.klochkov.idflabtest.exception.ResourceNotFoundException;
import com.klochkov.idflabtest.repository.LimitAccountRepository;
import com.klochkov.idflabtest.service.BalanceService;
import com.klochkov.idflabtest.service.LimitAccountService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LimitAccountServiceImpl implements LimitAccountService {
    private final LimitAccountRepository limitAccountRepository;
    private final BalanceService balanceService;
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
    public LimitAccount changeLimitExceededAsNeeded(Balance balance, LimitAccount limitAccount) {
        if (balance.getBalanceAmount().compareTo(BigDecimal.ZERO) <= 0) {
            limitAccount.setLimitExceeded(true);
        }
        return limitAccountRepository.save(limitAccount);
    }
    /**
     * Method for setting new limit.
     *
     * @param id - uuid of limitAccount.
     * @return new limitAccount
     */
    public LimitAccount setLimitByLatestLimitId(UUID id, BigDecimal limit) {
        LimitAccount limitAccountOld = getLimitAccountById(id);
        Balance balanceOld = balanceService.getBalanceByLimitIdLatest(id);
        return setLimitAccountAndChangeBalance(limitAccountOld, balanceOld, limit);
    }

    private LimitAccount setLimitAccountAndChangeBalance(LimitAccount limitAccountOld, Balance balanceOld,
                                                         BigDecimal limit) {
        BigDecimal amountBalanceNew = getNewBalanceAmount(limit, limitAccountOld
                .getLimitAmount(), balanceOld.getBalanceAmount());
        LimitAccount limitAccountNew = buildNewLimitAccount(limitAccountOld, limit);
        Balance balanceNew = buildNewBalance(amountBalanceNew, limitAccountNew, balanceOld);
        limitAccountNew = changeLimitExceededAsNeeded(balanceNew, limitAccountNew);
        limitAccountOld.setIsLatest(false);
        limitAccountRepository.save(limitAccountOld);
        balanceOld.setIsLatest(false);
        balanceService.saveBalance(balanceOld);
        balanceService.saveBalance(balanceNew);
        return limitAccountNew;
    }
    /**
     * Method for calculate new balanceAmount.
     *
     * @param limitNew - limitNew.
     * @param limitOld - limitOld.
     * @param balanceAmountNow - balanceAmountNow.
     * @return new balance amount
     */
    private BigDecimal getNewBalanceAmount(BigDecimal limitNew, BigDecimal limitOld, BigDecimal balanceAmountNow) {
        return limitNew.subtract(limitOld).add(balanceAmountNow);
    }
    /**
     * Method for building new limit.
     *
     * @param limitAccountOld - limitNew.
     * @param limit - limit amount new.
     * @return limitAccount
     */
    private LimitAccount buildNewLimitAccount(LimitAccount limitAccountOld, BigDecimal limit) {
        return LimitAccount.builder()
                .account(limitAccountOld.getAccount())
                .limitAmount(limit)
                .limitExceeded(false)
                .currency(limitAccountOld.getCurrency())
                .category(limitAccountOld.getCategory())
                .isLatest(true)
                .date(OffsetDateTime.now())
                .build();
    }

    /**
     * Method for building new limit.
     *
     * @param amountBalanceNew - amountBalanceNew.
     * @param limitAccountNew - limitAccountNew.
     * @param balanceOld - balanceOld.
     * @return balance new
     */
    private Balance buildNewBalance(BigDecimal amountBalanceNew, LimitAccount limitAccountNew, Balance balanceOld) {
        return  Balance.builder()
                .balanceAmount(amountBalanceNew)
                .isLatest(true)
                .limitAccount(limitAccountNew)
                .category(balanceOld.getCategory())
                .account(balanceOld.getAccount())
                .date(OffsetDateTime.now())
                .build();
    }

    /**
     * Method for setting new limit.
     *
     * @param category - category.
     * @param account - account.
     * @return new limitAccount
     */
    public LimitAccount setLimitByCategoryAndAccount(Category category, Long account, BigDecimal limit) {
        LimitAccount limitAccountOld = getLimitAccountByCategoryAndAccountLatest(category, account);
        Balance balanceOld = balanceService.getBalanceByCategoryAndAccountLatest(category, account);
        return setLimitAccountAndChangeBalance(limitAccountOld, balanceOld, limit);
    }
    /**
     * Method for searching limit.
     *
     * @param category - category.
     * @param account - account.
     * @return new limitAccount
     */
    public LimitAccount getLimitAccountByCategoryAndAccountLatest(Category category, Long account) {
        return limitAccountRepository
                .findLimitAccountByCategoryAndAccountAndIsLatestTrue(account, category);
    }
}
