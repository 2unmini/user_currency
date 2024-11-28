package com.sparta.currency_user.exception;

import lombok.Getter;

@Getter
public class ErrorResponse {
    private String errorCode; // 오류 코드
    private String errorMessage; // 오류 메시지

    public ErrorResponse(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
