package com.klochkov.idflabtest.dto;

import com.klochkov.idflabtest.enumeration.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
@Data
@ToString
@AllArgsConstructor
public class LimitBalanceDto {
    private UUID limitId;
    private UUID balanceId;
    private Category category;
    private Long account;
    private BigDecimal amount;
}
