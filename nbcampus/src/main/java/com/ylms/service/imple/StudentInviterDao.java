package com.ylms.service.imple;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ylms.common.dao.StudentInviterMapper;
import com.ylms.common.entity.StudentInviter;
import com.ylms.core.mybatis.BaseMybatisDao;
import com.ylms.service.impl.IStudentInviterDao;
@Service
public class StudentInviterDao extends BaseMybatisDao<StudentInviterMapper> implements IStudentInviterDao{
    @Autowired
    private StudentInviterMapper mapper;

	@Override
	public int updateStudentInviterById(Long id) {
		if(id==null){
			return 0;
		}
		return mapper.updateStudentInviterById(id);
	}

	@Override
	public int addStudentInviter(StudentInviter studentinviter) {
		if(studentinviter==null){
			return 0;
		}
		return mapper.addStudentInviter(studentinviter);
	}

	@Override
	public List<StudentInviter> listById(String tel) {
		if(tel==null||tel.equals("")){
			return null;
		}
		return mapper.listById(tel);
	}

   
}
