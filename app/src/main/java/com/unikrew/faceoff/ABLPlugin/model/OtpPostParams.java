package com.unikrew.faceoff.ABLPlugin.model;

import java.io.Serializable;

public class OtpPostParams implements Serializable {
    private OtpPostData data = new OtpPostData();

    public OtpPostData getData() {
        return data;
    }

    public void setData(OtpPostData data) {
        this.data = data;
    }

    @Override
    public String toString(){
        return
            "OtpPostParams{" +
                    "data = '" + data + '\'' +
                    "}";
    }
}
