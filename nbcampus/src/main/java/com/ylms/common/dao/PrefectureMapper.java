package com.ylms.common.dao;

import java.util.List;
import java.util.Map;

import com.ylms.common.entity.Prefecture;

/**
 * 
 * 任务专区代理接口(prefecture.xml文件代理)
 * 
 * */
public interface PrefectureMapper {
	List<Prefecture> findList(Map<String, Object> param);

	int deleteById(String id);

	int update(Prefecture prefecture);

	int add(Prefecture prefecture);

	Prefecture findByPrefectureId(String id);

	List<Prefecture> findAllByOnlie();

}
