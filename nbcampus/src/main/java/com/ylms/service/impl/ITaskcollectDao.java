package com.ylms.service.impl;

import java.util.List;
import java.util.Map;

import com.ylms.common.entity.Student2Task;

public interface ITaskcollectDao extends IBaseDao<Student2Task> {
	public int addTaskcollect(Student2Task st);

	public List<Student2Task> listByTel(String studentTel);

	public int deleteTaskcollect(Map<String, Object> params);

	public Student2Task findByPrefectureIdTaskIdStudentTel(Map<String, Object> params);
}
