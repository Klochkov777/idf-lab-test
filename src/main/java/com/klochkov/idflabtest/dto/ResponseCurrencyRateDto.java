package com.klochkov.idflabtest.dto;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseCurrencyRateDto {
    private String symbol;
    private String exchange;
    private LocalDate datetime;
    private Long timestamp;
    private BigDecimal close;
}
