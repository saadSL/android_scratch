package com.unikrew.faceoff.ABLPlugin.model;

import java.io.Serializable;

public class UpdateBioMetricStatusPostParams implements Serializable {
    private UpdateBioMetricStatusPostData data = new UpdateBioMetricStatusPostData();

    public UpdateBioMetricStatusPostData getData() {
        return data;
    }

    public void setData(UpdateBioMetricStatusPostData data) {
        this.data = data;
    }

    @Override
    public String toString(){
        return
            "UpdateBioMetricStatusPostParams{" +
                "data = '" + data + '\'' +
            "}";
    }
}
