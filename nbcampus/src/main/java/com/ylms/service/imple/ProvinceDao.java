package com.ylms.service.imple;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ylms.common.dao.ProvinceMapper;
import com.ylms.common.entity.Province;
import com.ylms.core.mybatis.BaseMybatisDao;
import com.ylms.core.mybatis.page.Pagination;
import com.ylms.service.impl.IProvinceDao;

@Service
public class ProvinceDao extends BaseMybatisDao<ProvinceMapper> implements IProvinceDao {
	@Autowired
	private ProvinceMapper mapper;

	@Override
	public List<Province> list(Map<String, Object> param) {
		if(param==null){
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
	public int update(Province obj) {

		if(obj==null){
			return 0;
		}
		return mapper.update(obj);
	}

	@Override
	public int add(Province entity) {
        if(entity==null){
        	return 0;
        }
		return mapper.add(entity);
	}

	@Override
	public Province findById(Long id) {
        if(id==null){
        	return null;
        }
		return mapper.findById(id);
	}

	@Override
	public Map<String, Object> deleteById(String ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Pagination<Province> findByPage(Map<String, Object> resultMap,
			Integer pageNo, Integer pageSize) {
		    return super.findPage(resultMap, pageNo, pageSize);
	}

}
