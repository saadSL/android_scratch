package com.unikrew.faceoff.ABLPlugin.model;

import java.io.Serializable;

public class OtpResponseData implements Serializable {
    private int rdaCustomerProfileId=0;
    private Object rdaCustomerAccInfoId=null;
    private String otp="";
    private Object customerTypeId=null;
    private String message="";

    public int getRdaCustomerProfileId() {
        return rdaCustomerProfileId;
    }

    public void setRdaCustomerProfileId(int rdaCustomerProfileId) {
        this.rdaCustomerProfileId = rdaCustomerProfileId;
    }

    public Object getRdaCustomerAccInfoId() {
        return rdaCustomerAccInfoId;
    }

    public void setRdaCustomerAccInfoId(Object rdaCustomerAccInfoId) {
        this.rdaCustomerAccInfoId = rdaCustomerAccInfoId;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public Object getCustomerTypeId() {
        return customerTypeId;
    }

    public void setCustomerTypeId(Object customerTypeId) {
        this.customerTypeId = customerTypeId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString(){
        return
        "OtpResponseData{" +
            "rdaCustomerProfileId = '" + rdaCustomerProfileId + '\'' +
            ",rdaCustomerAccInfoId = '" + rdaCustomerAccInfoId + '\'' +
            ",otp = '" + otp + '\'' +
            ",customerTypeId = '" + customerTypeId + '\'' +
            ",message = '" + message + '\'' +
        "}";
    }
}
