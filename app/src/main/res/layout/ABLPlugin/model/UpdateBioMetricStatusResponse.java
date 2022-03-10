package com.unikrew.faceoff.ABLPlugin.model;

import java.io.Serializable;

public class UpdateBioMetricStatusResponse implements Serializable {
    private UpdateBioMetricStatusResponseData data;
    private UpdateBioMetricStatusResponseMessage message;

    public UpdateBioMetricStatusResponseData getData() {
        return data;
    }

    public void setData(UpdateBioMetricStatusResponseData data) {
        this.data = data;
    }

    public UpdateBioMetricStatusResponseMessage getMessage() {
        return message;
    }

    public void setMessage(UpdateBioMetricStatusResponseMessage message) {
        this.message = message;
    }


    @Override
    public String toString(){
        return
            "ResponseDTO{" +
                "data = '" + data + '\'' +
                ",message = '" + message + '\'' +
            "}";
    }
}
