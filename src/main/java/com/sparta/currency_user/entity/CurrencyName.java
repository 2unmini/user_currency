package com.sparta.currency_user.entity;

import lombok.Getter;

@Getter
public enum CurrencyName {
    USD(2), JPY(0); // 달러, 엔화

    private final int digits;

    CurrencyName(int digits) {
        this.digits = digits;
    }

    public static CurrencyName toCurrencyName(String currencyName) {
        return CurrencyName.valueOf(currencyName);
    }
}
