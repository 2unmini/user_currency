package com.sparta.currency_user.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public class ErrorResponse {
    private int errorCode; // 오류 코드
    private String errorMessage;

    public ErrorResponse(HttpStatusCode errorCode, String errorMessage) {
        this.errorCode = errorCode.value();
        this.errorMessage = errorMessage;
    }
}
