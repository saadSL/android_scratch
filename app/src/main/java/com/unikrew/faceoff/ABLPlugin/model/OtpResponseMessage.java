package com.unikrew.faceoff.ABLPlugin.model;

import java.io.Serializable;

public class OtpResponseMessage implements Serializable {
    private String status;
    private String description;
    private String errorDetail;

    public String getErrorDetail() {
        return errorDetail;
    }

    public void setErrorDetail(String errorDetail) {
        this.errorDetail = errorDetail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString(){
        return
            "OtpResponseMessage{" +
                "status = '" + status + '\'' +
                ",description = '" + description + '\'' +
                ",errorDetail = '" + errorDetail + '\'' +
            "}";
    }

}
