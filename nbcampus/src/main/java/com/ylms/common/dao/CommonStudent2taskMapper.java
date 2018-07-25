package com.ylms.common.dao;

import java.util.List;
import java.util.Map;

import com.ylms.common.entity.CommonStudent2task;

public interface CommonStudent2taskMapper {

	public int addCommonStudent2Task(CommonStudent2task st);

	public int deleteById(Long id);

	public CommonStudent2task findById(Long id);

	public int update(CommonStudent2task obj);

	public List<CommonStudent2task> list(Map<String, Object> param);

	public List<CommonStudent2task> listByTel(String studentTel);

	public int deleteCommonStudent2Task(Map<String, Object> params);

	public CommonStudent2task findByPrefectureIdTaskIdStudentTel(
			Map<String, Object> params);

}
