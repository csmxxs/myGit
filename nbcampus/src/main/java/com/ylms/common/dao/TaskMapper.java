package com.ylms.common.dao;

import java.util.List;
import java.util.Map;

import com.ylms.common.entity.Task;

/**
 * 
 * TaskMapper.xml文件的代理接口
 * */
public interface TaskMapper {
	List<Task> list(Map<String, Object> param);

	int deleteById(Long id);

	int update(Task obj);

	int add(Task entity);

	Task findById(Long id);

	int onlineById(Long id);

	int offlineById(Long id);

	int check1ById(Long id);

	int check2ById(Long id);

	List<Task> listById(String id);

	List<Task> list(Long id);

	List<Task> findAllByProvinceId(Map<String, Object> param);

	List<Task> getTaskcollectByStudentTel(String studentTel);
	
	List<Task> getCommonStudentTask(String studentTel);
}
