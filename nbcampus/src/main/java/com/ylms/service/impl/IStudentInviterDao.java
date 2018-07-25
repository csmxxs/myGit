package com.ylms.service.impl;

import java.util.List;

import com.ylms.common.entity.StudentInviter;

public interface IStudentInviterDao {
	int updateStudentInviterById(Long id);

	int addStudentInviter(StudentInviter studentinviter);

	List<StudentInviter> listById(String tel);

}
