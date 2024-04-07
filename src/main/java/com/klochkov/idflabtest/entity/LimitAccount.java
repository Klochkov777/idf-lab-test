package com.klochkov.idflabtest.entity;

import com.klochkov.idflabtest.enumeration.Category;
import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "limits")
@Entity
@ToString
public class LimitAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private Long account;
    @Enumerated(EnumType.STRING)
    private Category category;
    private BigDecimal limitAmount;
    private Boolean limitExceeded;
    private String currency;
    private OffsetDateTime date;
    private Boolean isLatest;
}
