package com.example.demo.enums;

import lombok.Getter;

@Getter
public enum StatusCode {

    SUCCESS("200", "SUCCESS"),
    FEATURE_NOT_SUPPORTED("501", "FEATURE_NOT_SUPPORTED"),
    UNHANDLED("501", "UNHANDLED"),
    INTERNAL_SERVER_ERROR("500", "INTERNAL_SERVER_ERROR"),
    FORBIDDEN("403", "FORBIDDEN"),
    REQUEST_NOT_FOUND("404", "REQUEST_NOT_FOUND"),
    BAD_REQUEST("400", "BAD_REQUEST"),
    CONNECTION_ERROR("502", "CONNECTION_ERROR");

    String code;
    String message;

    StatusCode(String code, String msg) {
        this.code=code;
        this.message=msg;
    }
}
