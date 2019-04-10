package com.mdem.cms.model.common;

import java.util.Map;

public class ValidationError {
    private Map<String, String> validationErrors;

    public ValidationError(Map<String, String> validationErrors) {
        this.validationErrors = validationErrors;
    }

    public Map<String, String> getValidationErrors() {
        return validationErrors;
    }

    public void setValidationErrors(Map<String, String> validationErrors) {
        this.validationErrors = validationErrors;
    }
}
