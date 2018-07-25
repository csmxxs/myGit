package com.ylms.core.shiro.session;

import org.apache.shiro.session.Session;

import java.io.Serializable;
import java.util.Collection;

/**
 * 
 * 开发公司：ylms在线工具 <p>
 * 版权所有：© www.ylms.com<p>
 * 博客地址： <p>
 * <p>
 * 
 * Session操作
 * 
 * <p>
 * 
 * 区分　责任人　日期　　　　说明<br/>
 * 创建　谢雄世　2018年4月2日 　<br/>
 *
 * @author xie-xiongshi
 * @email  so@ylms.com
 * @version 1.0,2016年6月2日 <br/>
 * 
 */
public interface ShiroSessionRepository {

	/**
	 * 存储Session
	 * @param session
	 */
    void saveSession(Session session);
    /**
     * 删除session
     * @param sessionId
     */
    void deleteSession(Serializable sessionId);
    /**
     * 获取session
     * @param sessionId
     * @return
     */
    Session getSession(Serializable sessionId);
    /**
     * 获取所有sessoin
     * @return
     */
    Collection<Session> getAllSessions();
}
