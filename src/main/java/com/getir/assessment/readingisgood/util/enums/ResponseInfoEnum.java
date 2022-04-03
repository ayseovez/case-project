package com.getir.assessment.readingisgood.util.enums;

public enum ResponseInfoEnum {

    FAIL(0, "An error has occurred in the system"){
        @Override
        public Integer getStatus() {
            return FAIL.status;
        }

        @Override
        public String getMessage() {
            return FAIL.message;
        }
    },
    SUCCESS(200, "Success"){
        @Override
        public Integer getStatus() {
            return SUCCESS.status;
        }

        @Override
        public String getMessage() {
            return SUCCESS.message;
        }
    };


    private final Integer status;
    private final String message;

    ResponseInfoEnum(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public abstract Integer getStatus();
    public abstract String getMessage();
}
