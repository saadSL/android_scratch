package com.unikrew.faceoff.ABLPlugin.model;

import java.io.Serializable;

public class DataDTO implements Serializable {
	private Object userId=null;
	private int entityId=0;
	private String fullName="";
	private Object profilePicture=null;
	private String username="";
	private int userTypeId=0;
	private Object helpDeskUserTypeId=0;
	private Object statusId=null;
	private String accessToken="";
	private boolean existingCustomer=false;
	private boolean guestConsumer=false;
	private int sessionTimeout=0;
	private Object menus=null;
	private String mobileNo ="";

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public void setUserId(Object userId){
		this.userId = userId;
	}

	public Object getUserId(){
		return userId;
	}

	public void setEntityId(int entityId){
		this.entityId = entityId;
	}

	public int getEntityId(){
		return entityId;
	}

	public void setFullName(String fullName){
		this.fullName = fullName;
	}

	public String getFullName(){
		return fullName;
	}

	public void setProfilePicture(Object profilePicture){
		this.profilePicture = profilePicture;
	}

	public Object getProfilePicture(){
		return profilePicture;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return username;
	}

	public void setUserTypeId(int userTypeId){
		this.userTypeId = userTypeId;
	}

	public int getUserTypeId(){
		return userTypeId;
	}

	public void setHelpDeskUserTypeId(Object helpDeskUserTypeId){
		this.helpDeskUserTypeId = helpDeskUserTypeId;
	}

	public Object getHelpDeskUserTypeId(){
		return helpDeskUserTypeId;
	}

	public void setStatusId(Object statusId){
		this.statusId = statusId;
	}

	public Object getStatusId(){
		return statusId;
	}

	public void setAccessToken(String accessToken){
		this.accessToken = accessToken;
	}

	public String getAccessToken(){
		return accessToken;
	}

	public void setExistingCustomer(boolean existingCustomer){
		this.existingCustomer = existingCustomer;
	}

	public boolean isExistingCustomer(){
		return existingCustomer;
	}

	public void setGuestConsumer(boolean guestConsumer){
		this.guestConsumer = guestConsumer;
	}

	public boolean isGuestConsumer(){
		return guestConsumer;
	}

	public void setSessionTimeout(int sessionTimeout){
		this.sessionTimeout = sessionTimeout;
	}

	public int getSessionTimeout(){
		return sessionTimeout;
	}

	public void setMenus(Object menus){
		this.menus = menus;
	}

	public Object getMenus(){
		return menus;
	}

	@Override
 	public String toString(){
		return 
			"DataDTO{" + 
			"userId = '" + userId + '\'' + 
			",entityId = '" + entityId + '\'' + 
			",fullName = '" + fullName + '\'' + 
			",profilePicture = '" + profilePicture + '\'' + 
			",username = '" + username + '\'' + 
			",userTypeId = '" + userTypeId + '\'' + 
			",helpDeskUserTypeId = '" + helpDeskUserTypeId + '\'' + 
			",statusId = '" + statusId + '\'' + 
			",accessToken = '" + accessToken + '\'' + 
			",existingCustomer = '" + existingCustomer + '\'' + 
			",guestConsumer = '" + guestConsumer + '\'' + 
			",sessionTimeout = '" + sessionTimeout + '\'' + 
			",menus = '" + menus + '\'' +
			",contact = '" + mobileNo + '\'' +
			"}";
		}
}