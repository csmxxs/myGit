package com.ylms.service.impl;

import java.util.List;
import java.util.Map;

import com.ylms.core.mybatis.page.Pagination;

/**
 * 
 * 增删改查和分页公共接口
 * 
 * */
public interface IBaseDao<T> {
	List<T> list(Map<String, Object> param);

	int deleteById(Long id);

	int update(T obj);

	int add(T entity);

	T findById(Long id);

	Map<String, Object> deleteById(String ids);

	Pagination<T> findByPage(Map<String, Object> resultMap, Integer pageNo,
			Integer pageSize);

	
}
