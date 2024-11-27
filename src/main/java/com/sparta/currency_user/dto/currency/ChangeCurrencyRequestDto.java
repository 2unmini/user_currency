package com.sparta.currency_user.dto.currency;

import jakarta.validation.constraints.Min;
import lombok.Getter;

@Getter
public class ChangeCurrencyRequestDto {
    @Min(value = 100,message = "100원 이상 입력해주세요")
    private Long preExchangeAmount;
}
