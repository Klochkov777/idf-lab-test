package com.klochkov.idflabtest.repository;

import com.klochkov.idflabtest.entity.Balance;
import com.klochkov.idflabtest.enumeration.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BalanceRepository extends JpaRepository<Balance, UUID> {
    /**
     * Method for searching balance.
     *
     * @param limitAccountId - limitAccountId
     * @return  balance
     */
    @Query(value = "FROM Balance b WHERE b.limitAccount.id = :id AND b.isLatest = true")
    Optional<Balance> findBalanceByLimitAccountIdAndIsLatest(@Param("id") UUID limitAccountId);
    /**
     * Method for searching balance.
     *
     * @param category - category
     * @param account - account
     * @return  balance
     */
    @Query(value = "FROM Balance b WHERE b.category = :category AND b.account = :account AND b.isLatest = true ")
    Balance findBalanceByAccountAndCategoryAndIsLatestTrue(Long account, Category category);
}
