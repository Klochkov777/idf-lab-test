package com.klochkov.idflabtest.dto.transaction;

import com.klochkov.idflabtest.enumeration.Category;

import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ResponseTransactionDto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private Long accountFrom;
    private Long accountTo;
    private String currencyShortname;
    private BigDecimal sum;
    private Category expenseCategory;
    private OffsetDateTime dateTime;
    private BigDecimal limitSum;
    private OffsetDateTime limitDateTime;
    private String currency;
    private Boolean limitExceeded;
}
