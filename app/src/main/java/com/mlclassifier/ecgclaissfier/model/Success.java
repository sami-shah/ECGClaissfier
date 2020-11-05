package com.mlclassifier.ecgclaissfier.model;

import java.io.Serializable;

public class Success implements Serializable {
    private String success;

    private String error;

    public Success(String success, String error) {
        this.success = success;
        this.error = error;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
