package com.unikrew.faceoff.ABLPlugin.model;

import java.io.Serializable;

public class CnicPostParams implements Serializable {

	private CnicPostData data = new CnicPostData();

	public void setData(CnicPostData data){
		this.data = data;
	}

	public CnicPostData getData(){
		return data;
	}

	@Override
 	public String toString(){
		return 
			"CnicPostParams{" +
			"data = '" + data + '\'' + 
			"}";
		}
}