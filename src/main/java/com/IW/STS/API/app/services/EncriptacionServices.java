package com.IW.STS.API.app.services;

public interface EncriptacionServices {	
	String encryptPassowrd(String password);	
	boolean verifyPassword(String originalPassword, String hashaPasssword);
}
