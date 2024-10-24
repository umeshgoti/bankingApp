package com.bank.atm.Exception;

import lombok.Data;

@Data
public class BaseException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final int status;
    private final String errorCode;
    private final String errorMessage;
    private final String developerMessage;

    public BaseException(int httpStatus, String errorCode,
                         String errorMessage, String developerMessage) {
        super(developerMessage);
        this.status = httpStatus;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.developerMessage = developerMessage;
    }
}
