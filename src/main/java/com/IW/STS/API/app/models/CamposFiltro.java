package com.IW.STS.API.app.models;

import org.springframework.stereotype.Component;

@Component("camposFiltro")
public class CamposFiltro {
	
	private String keyContains;
	private String key;
	private String value;
	
	public String getKeyContains() {
		return keyContains;
	}
	public void setKeyContains(String keyContains) {
		this.keyContains = keyContains;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	

}
