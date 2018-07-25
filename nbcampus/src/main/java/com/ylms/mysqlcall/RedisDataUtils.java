package com.ylms.mysqlcall;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;

import redis.clients.jedis.Jedis;

import com.ylms.common.utils.SerializeUtil;
import com.ylms.common.utils.SpringContextUtil;
import com.ylms.core.shiro.cache.JedisManager;

/**
 * 
 * 
 * 
 * <p>
 * 
 * 区分　责任人　日期　　　　说明<br/>
 * 创建　谢雄世　2018年4月7日 　<br/>
 * 
 * @author xie-xiongshi
 * @email so@ylms.com
 * @version 1.0,2018年4月7日 <br/>
 * 
 */
public class RedisDataUtils {
	private static final Logger log = LoggerFactory
			.getLogger(RedisDataUtils.class);
	final static JedisManager J = SpringContextUtil.getBean("jedisManager",
			JedisManager.class);

	public synchronized static void setData(String keys, Object obj,Integer a) {

		Jedis jds = null;
		if(obj==null||a==null||keys==null){
			return ;
		}
		try {
			jds = J.getJedis();
			jds.select(a);
			jds.set(keys.getBytes(), SerializeUtil.serialize(obj));
			log.info("Redis添加了数据=====> id:" + keys + "数据类型====>" + obj);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 还原到连接池
			returnResource(jds);
		}
	}

	public static JedisManager getJm() {
		return RedisDataUtils.J;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public synchronized static List<Map<String, Object>> getList(Integer a) {
		Jedis jds = null;
		List list = new ArrayList();
		if(a==null){
			return list;
		}
		try {
			jds = J.getJedis();
			jds.select(a);
			// 查找所有的key值
			Set<String> keys = jds.keys("*");
			if (keys.size() > 0) {
				for (String id : keys) {
					Map<String, Object> v = new HashMap<String, Object>();
					byte[] ob = jds.get(id.getBytes());
					// 反序列化
					Object obj = SerializeUtil.deserialize(ob);
					v.put(id, obj);
					log.info("redis的"+a+"号数据库得到的数据类型============>:" + obj);
					list.add(v);
					//别人也有可能操作该库
					if (a != 5) {
						jds.del(id);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 还原到连接池
			returnResource(jds);
		}

		return list;
	}
    /**
     * 
     * 生成数据库ID
     * 
     * 
     * */
	public static synchronized String getRedisIdKey(RedisAtomicLong ra) {
		String nowId = null;
		// 第一次，设置初始值
		long original = 0L;
		// 获取 code 值
		original = ra.get();
		// 第一次，设置初始值
		if (original == 0L) {
			ra.set(1L);
		}
		// 获得加1后的值
		nowId = ra.incrementAndGet() + "";
		return nowId;
	}
	/**
	 * 
	 * 根据ID删除
	 * 
	 * 
	 * */
	public static void deleteKey(Integer i,String id) {
		Jedis jds = null;
		if(i==null||id==null){
			return ;
		}
		try {
			jds = J.getJedis();
			jds.select(i);
			jds.del(id);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			returnResource(jds);
		}

	}

	/**
	 * 释放
	 * 
	 * @param jedis
	 * @param isBroken
	 */
	public static void returnResource(Jedis jedis) {
		if (jedis == null) {
			return;
		}
		jedis.close();
	}

}
