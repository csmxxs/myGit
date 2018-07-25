package com.ylms.service.impl;

import java.util.List;
import java.util.Map;
import com.ylms.common.entity.TaskCount;

public interface ITaskCountDao {
	Map<String, Object> prefectureCount(String id);

	List<TaskCount> findById(Long id);

	TaskCount findTask(Map<String, Object> params);

	void addDayCount(TaskCount t);

	void updateTaskSsscnumber(TaskCount t);

	void updateDayCountAndTaskCount(TaskCount t);

}
