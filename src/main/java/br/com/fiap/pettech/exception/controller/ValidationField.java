package br.com.fiap.pettech.exception.controller;

public class ValidationField {

    private String field;
    private String message;

    public ValidationField() {
    }

    public ValidationField(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
