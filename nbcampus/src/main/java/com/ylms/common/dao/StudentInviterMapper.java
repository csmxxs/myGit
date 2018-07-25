package com.ylms.common.dao;

import java.util.List;

import com.ylms.common.entity.StudentInviter;

public interface StudentInviterMapper {
	int updateStudentInviterById(Long id);

	int addStudentInviter(StudentInviter studentinviter);

	List<StudentInviter> listById(String inviteeTel);

}
