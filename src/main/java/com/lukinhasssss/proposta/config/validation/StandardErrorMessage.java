package com.lukinhasssss.proposta.config.validation;

public class StandardErrorMessage {

    private final String field;
    private final String error;

    public StandardErrorMessage(String field, String error) {
        this.field = field;
        this.error = error;
    }

    public String getField() {
        return field;
    }

    public String getError() {
        return error;
    }

}
