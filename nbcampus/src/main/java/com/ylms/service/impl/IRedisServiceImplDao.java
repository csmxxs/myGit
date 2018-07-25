package com.ylms.service.impl;

public interface IRedisServiceImplDao {

	public void setClickNumber(String prefectureId, String taskId,
			String clicknumber);

	public void setSsscImages(String prefectureId, String taskId);

	public void setComplete(String prefectureId, String taskId);
}
