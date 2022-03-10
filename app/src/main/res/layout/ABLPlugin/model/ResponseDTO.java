package com.unikrew.faceoff.ABLPlugin.model;

import java.io.Serializable;

public class ResponseDTO implements Serializable {
	private DataDTO data;
	private MessageDTO message;

	public void setData(DataDTO data){
		this.data = data;
	}

	public DataDTO getData(){
		return data;
	}

	public void setMessage(MessageDTO message){
		this.message = message;
	}

	public MessageDTO getMessage(){
		return this.message;
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