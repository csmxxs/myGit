package com.ylms.common.utils;


/**
 * 
 * 用户连接生成的访问地址
 * 
 * */
public class InviterUrl {
	// tomcat服务地址
	public String httpUrl;
	// web Index.html服务地址
	public String webHttpUrl;
	//是否是移动端
	public String[] agent;

	public String[] getAgent() {
		return agent;
	}

	public void setAgent(String[] agent) {
		this.agent = agent;
	}

	public String getWebHttpUrl() {
		return webHttpUrl;
	}

	public void setWebHttpUrl(String webHttpUrl) {
		this.webHttpUrl = webHttpUrl;
	}

	public String getHttpUrl() {
		return httpUrl;
	}

	public void setHttpUrl(String httpUrl) {
		this.httpUrl = httpUrl;
	}

}
