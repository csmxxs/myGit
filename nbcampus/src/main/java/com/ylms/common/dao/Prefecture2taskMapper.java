package com.ylms.common.dao;

import java.util.List;
import java.util.Map;

import com.ylms.common.entity.Prefecture2task;

public interface Prefecture2taskMapper {

	List<Prefecture2task> findList(Map<String, Object> param);

	int deleteById(Map<String, Object> param);

	int addTaskById(Map<String, Object> param);

	int deleteByPrefectureId(String id);

	int update(Map<String, Object> param);
}
