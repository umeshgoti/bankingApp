package com.bank.atm.Exception;

public class ThirdPartyExceptions extends BaseException {

    private static final long serialVersionUID = -3443176925376713638L;

    public ThirdPartyExceptions(int httpStatus, String errorCode, String errorMessage, String developerMessage) {
        super(httpStatus, errorCode, errorMessage, developerMessage);
    }

    public ThirdPartyExceptions(int httpStatus, String developerMessage) {
        super(httpStatus, String.valueOf(httpStatus), developerMessage, developerMessage);
    }

}
