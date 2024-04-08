package com.klochkov.idflabtest.dto.limit;

import com.klochkov.idflabtest.enumeration.Category;
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
public class ResponseLimitAccountDto {
    private UUID id;
    private Long account;
    private Category category;
    private BigDecimal limitAmount;
    private Boolean limitExceeded;
    private String currency;
    private OffsetDateTime date;
}
