package com.ylms.common.dao;

import java.util.List;
import java.util.Map;

import com.ylms.common.entity.School;

public interface SchoolMapper {
	List<School> list(Map<String, Object> param);

	int deleteById(Long id);

	int update(School obj);

	int add(School entity);

	School findById(Long id);

	Map<String, Object> deleteById(String ids);
}
