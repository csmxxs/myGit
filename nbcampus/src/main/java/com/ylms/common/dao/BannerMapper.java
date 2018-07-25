package com.ylms.common.dao;

import java.util.List;
import java.util.Map;

import com.ylms.common.entity.Banner;

/**
 * banner的映射文件的代理接口!(代理BannerMapper.xml)
 * 
 * */
public interface BannerMapper {
	List<Banner> list(Map<String, Object> param);

	int deleteById(Long id);

	int update(Banner obj);

	int add(Banner entity);

	Banner findById(Long id);

	int onlineById(Long id);

	int offlineById(Long id);

	int check1ById(Long id);

	int check2ById(Long id);
	
	List<Banner> findAllByOnlie();
}
