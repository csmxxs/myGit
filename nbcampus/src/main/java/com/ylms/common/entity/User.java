package com.ylms.common.entity;

import java.io.Serializable;

/**
 * 存在会话里的临时对象
 * 
 * */
public class User implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String token;
    private String mobile;
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
    
}
