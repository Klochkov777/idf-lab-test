package com.klochkov.idflabtest.repository;

import com.klochkov.idflabtest.dto.LimitBalanceDto;
import com.klochkov.idflabtest.entity.Transaction;
import com.klochkov.idflabtest.enumeration.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    /**
     * Method for getting transactions with limit_exceeded true.
     */
    @Query(value = "FROM Transaction t WHERE t.limitAccount.limitExceeded = true")
    Optional<List<Transaction>> findAllWhereLimitExceeded();

    /**
     * Method for searching data about limit and balance and using it in next.
     *
     * @param account - account number.
     * @param category - product or service.
     */
    @Query(value = "SELECT new com.klochkov.idflabtest.dto.LimitBalanceDto(l.id, b.id, b.category, "
            + "b.account, b.balanceAmount) FROM LimitAccount l "
            + "JOIN Balance b ON b.limitAccount.id = l.id "
            + "WHERE l.category = :category AND l.account = :account "
            + "AND l.isLatest = true AND b.isLatest = true AND b.id IN ("
            + "SELECT b2.id FROM Balance b2 "
            + "WHERE b2.account = :account AND b2.category = :category)")
    Optional<LimitBalanceDto> getLimitBalanceDto(@Param("category") Category category, @Param("account") Long account);
}

