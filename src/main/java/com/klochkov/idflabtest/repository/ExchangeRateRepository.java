package com.klochkov.idflabtest.repository;

import com.klochkov.idflabtest.entity.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {
    /**
     * Method for searching exchange rate.
     *
     * @param currency - parameter for searching last mean.
     */
    Optional<ExchangeRate> findFirstByCurrencyOrderByExchangeDateDesc(String currency);
}
