package com.elearn.app.start_learn_back.dtos;

import lombok.Data;

@Data
public class CustomMessage {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    private boolean success;
}