package com.sparta.currency_user.dto.currency;

import com.sparta.currency_user.entity.CurrencyExchange;
import lombok.Getter;

import java.math.BigDecimal;
@Getter
public class TotalUserCurrencyResponseDto {
    private Long count;
    private Long totalAmountInKrw;



    public TotalUserCurrencyResponseDto(Long count, Long totalAmountInKrw) {
        this.count = count;
        this.totalAmountInKrw = totalAmountInKrw;
    }

}
