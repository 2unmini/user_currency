package com.sparta.currency_user.dto.currency;

import lombok.Getter;

@Getter
public class TotalUserCurrencyResponseDto {
    private Long count; // 요청한 접수 수(취소 된 건 제외)
    private Long totalAmountInKrw; //환전 요청 총 금액


    public TotalUserCurrencyResponseDto(Long count, Long totalAmountInKrw) {
        this.count = count;
        this.totalAmountInKrw = totalAmountInKrw;
    }

}
