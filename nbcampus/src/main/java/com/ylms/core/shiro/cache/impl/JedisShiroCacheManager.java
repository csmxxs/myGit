package com.ylms.core.shiro.cache.impl;

import org.apache.shiro.cache.Cache;

import com.ylms.core.shiro.cache.JedisManager;
import com.ylms.core.shiro.cache.JedisShiroCache;
import com.ylms.core.shiro.cache.ShiroCacheManager;

/**
 * 
 * 
 * JRedis管理
 * 
 * <p>
 * 
 * 区分　责任人　日期　　　　说明<br/>
 * 创建　谢雄世　2018年3月28日 　<br/>
 *
 * @version 1.0,2018年3月28日 <br/>
 * 
 */
public class JedisShiroCacheManager implements ShiroCacheManager {

    private JedisManager jedisManager;

    @Override
    public <K, V> Cache<K, V> getCache(String name) {
        return new JedisShiroCache<K, V>(name, getJedisManager());
    }

    @Override
    public void destroy() {
    	//如果和其他系统，或者应用在一起就不能关闭
    	//getJedisManager().getJedis().shutdown();
    }

    public JedisManager getJedisManager() {
        return jedisManager;
    }

    public void setJedisManager(JedisManager jedisManager) {
        this.jedisManager = jedisManager;
    }
}
