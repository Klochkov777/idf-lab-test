package com.klochkov.idflabtest.entity;

import com.klochkov.idflabtest.enumeration.Category;
import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;


@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "transactions")
@ToString
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private Long accountFrom;
    private Long accountTo;
    private String currencyShortname;
    private BigDecimal sum;
    @Enumerated(EnumType.STRING)
    private Category expenseCategory;
    @Column(name = "datetime")
    private OffsetDateTime dateTime;
    @ManyToOne
    @JoinColumn(name = "limit_id")
    private LimitAccount limitAccount;
}
