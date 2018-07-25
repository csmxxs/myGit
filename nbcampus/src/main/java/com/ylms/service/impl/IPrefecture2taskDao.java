package com.ylms.service.impl;

import java.util.List;
import java.util.Map;
import com.ylms.common.entity.Prefecture2task;

public interface IPrefecture2taskDao {

	int update(Map<String, Object> param);

	Map<String, Object> addTask2Prefecture(String id, String ids);

	int deleteByPrefectureId(String id);

	int addTaskById(Map<String, Object> param);

	int deleteById(Map<String, Object> params);

	List<Prefecture2task> findList(Map<String, Object> param);
}
