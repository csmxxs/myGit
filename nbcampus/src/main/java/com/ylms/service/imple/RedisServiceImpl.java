package com.ylms.service.imple;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Service;

import com.ylms.mysqlcall.RedisDataUtils;
import com.ylms.common.entity.TaskCount;
import com.ylms.common.utils.DataToRedis;
import com.ylms.service.impl.IRedisServiceImplDao;
@Service
public class RedisServiceImpl implements IRedisServiceImplDao {
	@Autowired
	private RedisAtomicLong redisAtomicLong;
	@Autowired
	private DataToRedis dataToRedis;
	/** set Object */
	public void setClickNumber(String prefectureId, String taskId,
			String clickNumber) {
		RedisDataUtils.setData(RedisDataUtils.getRedisIdKey(redisAtomicLong), new TaskCount(prefectureId, new Long(taskId)),dataToRedis.dbClick);
	}
	/** set Object */
	public void setSsscImages(String prefectureId, String taskId) {
		RedisDataUtils.setData(RedisDataUtils.getRedisIdKey(redisAtomicLong), new TaskCount(prefectureId, new Long(taskId)),dataToRedis.getDbSsscImages());
	}
	@Override
	public void setComplete(String prefectureId, String taskId) {
		RedisDataUtils.setData(RedisDataUtils.getRedisIdKey(redisAtomicLong), new TaskCount(prefectureId, new Long(taskId)),dataToRedis.dbComplete);
	}
}
