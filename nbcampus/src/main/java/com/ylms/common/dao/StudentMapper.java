package com.ylms.common.dao;

import java.util.List;
import java.util.Map;

import com.ylms.common.entity.Student;

/**
 * student的映射文件的代理接口!(代理StudentMapper.xml)
 * 
 * */
public interface StudentMapper {
	List<Student> list(Map<String, Object> param);

	int deleteById(Long id);

	// 根据电话号码查询
	Student findByTel(String tel);

	int update(Student obj);

	int add(Student entity);

	// 默认为验证成功的用户注册一个空账号
	int addTel(Student entity);

	Student findById(Long id);

	int check1ById(Long id);

	int check2ById(Long id);

	List<Student> findStudentInviter(String tel);
}
