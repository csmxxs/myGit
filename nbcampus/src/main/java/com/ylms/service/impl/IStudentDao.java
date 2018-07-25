package com.ylms.service.impl;

import java.util.List;
import java.util.Map;

import com.ylms.common.entity.Student;

public interface IStudentDao extends IBaseDao<Student> {
	Map<String, Object> checkById(String ids, Integer myCheck);

	Student findByTel(String tel);

	int addTel(Student entity);
	
	List<Student> findStudentInviter(String tel);
}
