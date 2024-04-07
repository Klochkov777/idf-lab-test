package com.klochkov.idflabtest.entity;

import com.klochkov.idflabtest.enumeration.Category;
import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import lombok.Builder;


import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "balances")
@Entity
@ToString
@Builder
public class Balance {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private Long account;
    @Enumerated(EnumType.STRING)
    private Category category;
    private BigDecimal balanceAmount;
    @ManyToOne
    @JoinColumn(name = "limit_id")
    private LimitAccount limitAccount;
    private OffsetDateTime date;
}
