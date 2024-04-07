package com.klochkov.idflabtest.repository;

import com.klochkov.idflabtest.entity.LimitAccount;
import com.klochkov.idflabtest.enumeration.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LimitAccountRepository extends JpaRepository<LimitAccount, UUID> {
    /**
     * Method for searching limit.
     *
     * @param id - parameter for searching limit.
     */
    Optional<LimitAccount> findById(UUID id);
    /**
     * Method for searching limit for account and category with last date.
     *
     * @param account - account number.
     * @param category - product or service.
     */
    LimitAccount findFirstByAccountAndCategoryOrderByDateDesc(Long account, Category category);
}
