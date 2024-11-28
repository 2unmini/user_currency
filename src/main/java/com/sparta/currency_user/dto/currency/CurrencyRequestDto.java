package com.sparta.currency_user.dto.currency;

import com.sparta.currency_user.entity.Currency;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class CurrencyRequestDto {
    @NotBlank(message = "통화 이름을 입력해주세요")
    private String currencyName;
    private BigDecimal exchangeRate;
    @NotBlank(message = "통화 기호를 입력해주세요")
    private String symbol;

    public Currency toEntity() {
        return new Currency(
                this.currencyName,
                this.exchangeRate,
                this.symbol
        );
    }
}
