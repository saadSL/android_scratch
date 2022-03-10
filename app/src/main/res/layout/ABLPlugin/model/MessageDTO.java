package com.unikrew.faceoff.ABLPlugin.model;

import java.io.Serializable;

public class MessageDTO implements Serializable {
	private String status="";
	private String description="";
	private String errorDetail="";

	public String getErrorDetail() {
		return errorDetail;
	}

	public void setErrorDetail(String errorDetail) {
		this.errorDetail = errorDetail;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	@Override
 	public String toString(){
		return 
			"MessageDTO{" + 
			"status = '" + status + '\'' + 
			",description = '" + description + '\'' + 
			"}";
		}
}