package br.com.fiap.pettech.exception.controller;

import br.com.fiap.pettech.exception.service.DefaultError;

import java.util.ArrayList;
import java.util.List;

public class FormValidation extends DefaultError {

    private List<ValidationField> messages = new ArrayList<>();

    public List<ValidationField> getMessages() {
        return this.messages;
    }

    public void addMessages(String field, String message) {
        this.messages.add(new ValidationField(field, message));
    }
}
