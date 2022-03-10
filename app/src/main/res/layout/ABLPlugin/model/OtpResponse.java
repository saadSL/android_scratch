package com.unikrew.faceoff.ABLPlugin.model;

import java.io.Serializable;

public class OtpResponse implements Serializable {

    private OtpResponseData data = new OtpResponseData();
    private OtpResponseMessage message = new OtpResponseMessage();

    public OtpResponseData getData() {
        return data;
    }

    public void setData(OtpResponseData data) {
        this.data = data;
    }

    public OtpResponseMessage getMessage() {
        return message;
    }

    public void setMessage(OtpResponseMessage message) {
        this.message = message;
    }

    @Override
    public String toString(){
        return
        "OtpResponse{" +
            "data = '" + data + '\'' +
            ",message = '" + message + '\'' +
        "}";
    }
}
