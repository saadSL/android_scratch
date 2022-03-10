package com.unikrew.faceoff.ABLPlugin.model;

import java.io.Serializable;

public class UpdateBioMetricStatusPostData implements Serializable {
    private String rdaCustomerProfileId="";
    private String rdaCustomerAccountInfoId="";
    private String bioMetricVerificationNadraStatus="";
    private String nadraSessionId="";

    public String getRdaCustomerProfileId() {
        return rdaCustomerProfileId;
    }

    public void setRdaCustomerProfileId(String rdaCustomerProfileId) {
        this.rdaCustomerProfileId = rdaCustomerProfileId;
    }

    public String getRdaCustomerAccountInfoId() {
        return rdaCustomerAccountInfoId;
    }

    public void setRdaCustomerAccountInfoId(String rdaCustomerAccountInfoId) {
        this.rdaCustomerAccountInfoId = rdaCustomerAccountInfoId;
    }

    public String getBioMetricVerificationNadraStatus() {
        return bioMetricVerificationNadraStatus;
    }

    public void setBioMetricVerificationNadraStatus(String bioMetricVerificationNadraStatus) {
        this.bioMetricVerificationNadraStatus = bioMetricVerificationNadraStatus;
    }

    public String getNadraSessionId() {
        return nadraSessionId;
    }

    public void setNadraSessionId(String nadraSessionId) {
        this.nadraSessionId = nadraSessionId;
    }

    @Override
    public String toString(){
        return
                "UpdateBioMetricStatusPostData{" +
                        "rdaCustomerProfileId = '" + rdaCustomerProfileId + '\'' +
                        ",rdaCustomerAccountInfoId = '" + rdaCustomerAccountInfoId + '\'' +
                        ",bioMetricVerificationNadraStatus = '" + bioMetricVerificationNadraStatus + '\'' +
                        ",nadraSessionId = '" + nadraSessionId + '\'' +
                        "}";
    }
}
