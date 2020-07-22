package com.restapi.model;

public class ApiUser {

	private long userId;
	private String userName;
	private String encryptedPasword;
	
	public ApiUser()
	{
		
	}
	
	public ApiUser(long userId, String userName, String encryptedPasword) {
		this.userId = userId;
		this.userName = userName;
		this.encryptedPasword = encryptedPasword;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEncryptedPasword() {
		return encryptedPasword;
	}

	public void setEncryptedPasword(String encryptedPasword) {
		this.encryptedPasword = encryptedPasword;
	}
	
	@Override
	public String toString()
	{
		return "{User} \n" + " User Name : " + this.userName + "\n User ID : " + this.userId + "\n Encypted Password : " + this.encryptedPasword;
	}
	
}
