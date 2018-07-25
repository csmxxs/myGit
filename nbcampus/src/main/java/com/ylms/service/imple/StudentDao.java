package com.ylms.service.imple;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ylms.common.dao.StudentMapper;
import com.ylms.common.entity.Student;
import com.ylms.common.utils.LoggerUtils;
import com.ylms.core.mybatis.BaseMybatisDao;
import com.ylms.core.mybatis.page.Pagination;
import com.ylms.service.impl.IStudentDao;
import com.ylms.service.impl.IStudentInviterDao;

/**
 * 
 * 学生信息增删改查
 * 
 * */
@Service
public class StudentDao extends BaseMybatisDao<StudentMapper> implements
		IStudentDao {
	@Autowired
	private StudentMapper mapper;
	@Autowired
	private IStudentInviterDao isd;
	@Override
	public List<Student> list(Map<String, Object> param) {
		return mapper.list(param);
	}

	@Override
	public int deleteById(Long id) {
		return mapper.deleteById(id);
	}

	@Override
	public int update(Student student) {
		return mapper.update(student);
	}

	@Override
	public int add(Student entity) {
		return mapper.add(entity);
	}

	@Override
	public Student findById(Long id) {
		return mapper.findById(id);
	}

	/***
	 * 
	 * 通用查询
	 * 
	 * */
	@SuppressWarnings("unchecked")
	@Override
	public Pagination<Student> findByPage(Map<String, Object> resultMap,
			Integer pageNo, Integer pageSize) {
		return super.findPage(resultMap, pageNo, pageSize);
	}

	/**
	 * 
	 * 批量删除 只有没通过审核且审核数>=3的信息才能删除
	 * */
	@Override
	public Map<String, Object> deleteById(String ids) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(ids==null){
			return resultMap;
		}
		try {
			int count = 0;
			String resultMsg = "删除成功。";
			String[] idArray = new String[] {};
			if (StringUtils.contains(ids, ",")) {
				idArray = ids.split(",");
			} else {
				idArray = new String[] { ids };
			}

			for (String idx : idArray) {
				Long id = new Long(idx);
				Student student = this.findById(id);
				// 0:待审1:通过2:没通过3.待完善信息
				if (student.getMyCheck() != 1) {
					this.deleteById(id);
					count++;
				} else {
					resultMsg = "审核已通过,不能删除!";
				}
			}
			resultMap.put("status", 200);
			resultMap.put("count", count);
			resultMap.put("message", resultMsg);
		} catch (Exception e) {
			LoggerUtils.fmtError(getClass(), e, "根据IDS删除学生列表出现错误，ids[%s]", ids);
			resultMap.put("status", 500);
			resultMap.put("message", "删除出现错误，请刷新后再试！");
		}
		return resultMap;
	}

	@Override
	public Map<String, Object> checkById(String ids, Integer myCheck) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			int count = 0;
			String resultMsg = "审核完成。";
			String[] idArray = new String[] {};
			if (StringUtils.contains(ids, ",")) {
				idArray = ids.split(",");
			} else {
				idArray = new String[] { ids };
			}

			for (String idx : idArray) {
				Long id = new Long(idx);
				Student student = this.findById(id);
				// 通过审核
				if (student.getMyCheck() == 0 && myCheck == 1) {
					mapper.check1ById(id);
					//isd.addStudentInviterById(id);
					count++;
				}
				// 不通过审核
				if (student.getMyCheck() == 0 && myCheck == 2) {
					mapper.check2ById(id);
					count++;
				}

			}
			resultMap.put("status", 200);
			resultMap.put("count", count);
			resultMap.put("message", resultMsg);
		} catch (Exception e) {
			LoggerUtils.fmtError(getClass(), e, "根据IDS审核学生列表出现错误，ids[%s]", ids);
			resultMap.put("status", 500);
			resultMap.put("message", "审核出现错误，请刷新后再试！");
		}
		return resultMap;
	}
    /**
     * 根据电话号码查询
     * 
     * */
	@Override
	public Student findByTel(String tel) {
		if(tel==null){
			return null;
		}
		return mapper.findByTel(tel);
	}
    /**
     * 
     * 默认为短信验证成功的用户注册一个空的账号
     * 
     * */
	@Override
	public int addTel(Student entity) {
		if(entity==null){
			return 0;
		}
		return mapper.addTel(entity);
	}

	
	@Override
	public List<Student> findStudentInviter(String tel) {
        if(tel.equals("")||tel==null){
        	return null;
        }
		return mapper.findStudentInviter(tel);
	}
}
