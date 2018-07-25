package com.ylms.service.imple;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ylms.common.dao.SchoolMapper;
import com.ylms.common.entity.School;
import com.ylms.core.mybatis.BaseMybatisDao;
import com.ylms.core.mybatis.page.Pagination;
import com.ylms.service.impl.ISchoolDao;
@Service
public class SchoolDao extends BaseMybatisDao<SchoolMapper> implements ISchoolDao{
    
	@Autowired
	private SchoolMapper mapper;
	
	@Override
	public List<School> list(Map<String, Object> param) {
		if(param ==null){
			return null;
		}
		return mapper.list(param);
	}

	@Override
	public int deleteById(Long id) {
        if(id==null){
        	 return 0;
         }
		return mapper.deleteById(id);
	}

	@Override
	public int update(School obj) {

		  if(obj==null){
			  return 0;
		  }
		return mapper.update(obj);
	}

	@Override
	public int add(School entity) {
		if(entity==null){
			return 0;
		}
		return mapper.add(entity);
	}

	@Override
	public School findById(Long id) {

		 if(id==null){
			 return null;
		 }
		return mapper.findById(id);
	}

	@Override
	public Map<String, Object> deleteById(String ids) {
		if(ids.equals("")||ids==null){
			return null;
		}
		return mapper.deleteById(ids);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Pagination<School> findByPage(Map<String, Object> resultMap,
			Integer pageNo, Integer pageSize) {
		 return super.findPage(resultMap, pageNo, pageSize);
	}

}
