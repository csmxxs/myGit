package com.ylms.core.shiro.session;

import java.io.Serializable;

/**
 * 
 * Session 状态 VO
 * 
 * 区分　责任人　日期　　　　说明<br/>
 * 创建　谢雄世　2018年4月2日 　<br/>
 *
 * 
 */
public class SessionStatus implements Serializable {
	private static final long serialVersionUID = 1L;

	//是否踢出 true:有效，false：踢出。
	private Boolean onlineStatus = Boolean.TRUE;

	
	public Boolean isOnlineStatus(){
		return onlineStatus;
	}

	public Boolean getOnlineStatus() {
		return onlineStatus;
	}
	public void setOnlineStatus(Boolean onlineStatus) {
		this.onlineStatus = onlineStatus;
	}
	
	
}
