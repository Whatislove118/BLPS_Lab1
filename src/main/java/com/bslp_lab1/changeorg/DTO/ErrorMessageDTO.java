package com.bslp_lab1.changeorg.DTO;

import java.io.Serializable;

public class ErrorMessageDTO implements MessageDTO, Serializable {
    private String error;
    private String description;
    private String reasons;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReasons() {
        return reasons;
    }

    public void setReasons(String reasons) {
        this.reasons = reasons;
    }
}
