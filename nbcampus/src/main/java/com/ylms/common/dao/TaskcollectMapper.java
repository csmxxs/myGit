package com.ylms.common.dao;

import java.util.List;
import java.util.Map;

import com.ylms.common.entity.Student2Task;

public interface TaskcollectMapper {
	public int addTaskcollect(Student2Task st);

	public int deleteById(Long id);

	public int update(Student2Task obj);

	public List<Student2Task> list(Map<String, Object> param);

	public List<Student2Task> listByTel(String studentTel);
	
	public Student2Task findByPrefectureIdTaskIdStudentTel(Map<String, Object> params);
 
	public int deleteTaskcollect(Map<String, Object> params) ;
}
