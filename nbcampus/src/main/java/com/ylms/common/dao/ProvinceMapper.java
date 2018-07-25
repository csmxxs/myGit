package com.ylms.common.dao;

import java.util.List;
import java.util.Map;

import com.ylms.common.entity.Province;

public interface ProvinceMapper {
	List<Province> list(Map<String, Object> param);

	int deleteById(Long id);

	int update(Province obj);

	int add(Province entity);

	Province findById(Long id);
}
