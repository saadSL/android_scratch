package com.unikrew.faceoff.ABLPlugin.model;

import java.io.Serializable;

public class CnicPostData implements Serializable {
	private String cnic;
	private String accountNo;

	public void setCnic(String cnic){
		this.cnic = cnic;
	}

	public String getCnic(){
		return cnic;
	}

	public void setAccountNo(String accountNo){
		this.accountNo = accountNo;
	}

	public String getAccountNo(){
		return accountNo;
	}

	@Override
 	public String toString(){
		return 
			"CnicPostData{" +
			"cnic = '" + cnic + '\'' + 
			",accountNo = '" + accountNo + '\'' + 
			"}";
	}
}