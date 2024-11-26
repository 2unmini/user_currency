package com.sparta.currency_user.entity;

import jakarta.persistence.*;

@Entity
public class CurrencyExchange {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne()
    @JoinColumn(name = "currency_id")
    private Currency currency;

    private Integer preExchangeAmount;

    private Double postExchangeAmount;

    private Status Status;
}
