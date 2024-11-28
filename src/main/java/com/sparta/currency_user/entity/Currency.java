package com.sparta.currency_user.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Currency extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 통화 고유 식별자
    private CurrencyName currencyName; // 통화 이름
    private BigDecimal exchangeRate; // 환율
    private String symbol; // 표시
    @OneToMany(mappedBy = "currency")
    private List<CurrencyExchange> currencyExchangeList = new ArrayList<>();


    public Currency(String currencyName, BigDecimal exchangeRate, String symbol) {
        this.currencyName = CurrencyName.toCurrencyName(currencyName);
        this.exchangeRate = exchangeRate;
        this.symbol = symbol;
    }

    public Currency() {
    }
}
