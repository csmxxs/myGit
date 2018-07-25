package com.ylms.common.dao;

import java.util.List;
import java.util.Map;

import com.ylms.common.entity.TaskCount;

public interface TaskCountMapper {

	void prefectureCount(Map<String, Object> params);

	void addTaskCount(TaskCount t);
	// 每天凌晨1点调用一次
	void addDayCount(TaskCount t);

	void updateTaskSsscnumber(TaskCount t);

	List<TaskCount> findById(Long id);
	// 每隔20分钟调用一次
	void updateDayCountAndTaskCount(TaskCount t);
	
	TaskCount findTask(Map<String, Object> params);
}
