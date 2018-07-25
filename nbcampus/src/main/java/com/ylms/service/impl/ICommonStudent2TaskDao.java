package com.ylms.service.impl;

import java.util.List;
import java.util.Map;

import com.ylms.common.entity.CommonStudent2task;

public interface ICommonStudent2TaskDao extends IBaseDao<CommonStudent2task> {
	public int addCommonStudent2Task(CommonStudent2task st);

	public List<CommonStudent2task> listByTel(String studentTel);

	public int deleteCommonTask(Map<String, Object> params);

	public int update(String ids);

	public CommonStudent2task findByPrefectureIdTaskIdStudentTel(
			Map<String, Object> params);

}
