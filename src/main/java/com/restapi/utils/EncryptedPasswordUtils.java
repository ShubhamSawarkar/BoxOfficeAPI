package com.restapi.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; 

public class EncryptedPasswordUtils {

	public static String encryptPassword(String password)
	{
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(password);
	}
	
	public static void main(String args[])
	{
		System.out.println(encryptPassword("password@001"));
	}
	
}
