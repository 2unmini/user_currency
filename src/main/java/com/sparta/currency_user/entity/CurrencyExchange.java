package com.sparta.currency_user.entity;

import com.sparta.currency_user.dto.currency.ChangeCurrencyRequestDto;
import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;
@Getter
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

    private Long preExchangeAmount; //환전 전 금액

    private BigDecimal postExchangeAmount; //환전 후 금액
    @Enumerated(EnumType.STRING)
    private Status status;

    public CurrencyExchange() {
    }

    public CurrencyExchange(User user, Currency currency, ChangeCurrencyRequestDto currencyRequestDto) {
        this.user=user;
        this.currency=currency;
        this.preExchangeAmount=currencyRequestDto.getPreExchangeAmount();
        this.postExchangeAmount = BigDecimal.valueOf(preExchangeAmount).divide(currency.getExchangeRate(),currency.getCurrencyName().getDigits(), RoundingMode.HALF_UP);
        this.status=Status.NOMAL;
    }
    public void cancel(){
        this.status=Status.CANCALLED;
    }
}
