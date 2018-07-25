package com.ylms.core.shiro.listenter;


import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

import com.ylms.core.shiro.session.ShiroSessionRepository;
/**
 * 
 * 开发公司：ylms在线工具 <p>
 * 版权所有：© www.ylms.com<p>
 * 博客地址：  <p>
 * <p>
 * 
 * shiro 回话 监听
 * 
 * <p>
 * 
 * 区分　责任人　日期　　　　说明<br/>
 * 创建　谢雄世　2018年4月1日 　<br/>
 *
 * @author xie-xiongshi
 * @email  so@ylms.com
 * @version 1.0,2018年4月1日 <br/>
 * 
 */
public class CustomSessionListener implements SessionListener {

    private ShiroSessionRepository shiroSessionRepository;

    /**
     * 一个回话的生命周期开始
     */
    @Override
    public void onStart(Session session) {
        //TODO
        System.out.println("on start");
    }
    /**
     * 一个回话的生命周期结束
     */
    @Override
    public void onStop(Session session) {
        //TODO
        System.out.println("on stop");
    }

    @Override
    public void onExpiration(Session session) {
        shiroSessionRepository.deleteSession(session.getId());
    }

    public ShiroSessionRepository getShiroSessionRepository() {
        return shiroSessionRepository;
    }

    public void setShiroSessionRepository(ShiroSessionRepository shiroSessionRepository) {
        this.shiroSessionRepository = shiroSessionRepository;
    }

}

