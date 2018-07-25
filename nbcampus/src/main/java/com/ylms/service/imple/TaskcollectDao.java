package com.ylms.service.imple;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ylms.common.dao.TaskcollectMapper;
import com.ylms.common.entity.Student2Task;
import com.ylms.core.mybatis.BaseMybatisDao;
import com.ylms.core.mybatis.page.Pagination;
import com.ylms.service.impl.ITaskcollectDao;

@Service
public class TaskcollectDao extends BaseMybatisDao<TaskcollectMapper> implements
		ITaskcollectDao {
	@Autowired
	private TaskcollectMapper mapper;

	@Override
	public int addTaskcollect(Student2Task st) {
		if (st == null) {
			return 0;
		}
		return mapper.addTaskcollect(st);
	}

	@Override
	public List<Student2Task> list(Map<String, Object> param) {
		if (param == null) {
			return null;
		}
		return mapper.list(param);
	}

	@Override
	public int deleteById(Long id) {
		if (id == null) {
			return 0;
		}
		return mapper.deleteById(id);
	}

	@Override
	public int update(Student2Task obj) {
		if (obj == null) {
			return 0;
		}
		return mapper.update(obj);
	}

	@Override
	public int add(Student2Task entity) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Student2Task findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> deleteById(String ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pagination<Student2Task> findByPage(Map<String, Object> resultMap,
			Integer pageNo, Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Student2Task> listByTel(String studentTel) {

		if (studentTel == null || studentTel.equals("")) {
			return null;
		}
		return mapper.listByTel(studentTel);
	}

	@Override
	public int deleteTaskcollect(Map<String, Object> params) {
		if(params==null){
			return 0;
		}
		return mapper.deleteTaskcollect(params);
	}

	@Override
	public Student2Task findByPrefectureIdTaskIdStudentTel(
			Map<String, Object> params) {
        if(params==null){
        	return null;
        }
		return mapper.findByPrefectureIdTaskIdStudentTel(params);
	}


}
