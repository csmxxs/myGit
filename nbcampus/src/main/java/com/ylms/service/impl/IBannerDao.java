package com.ylms.service.impl;

import java.util.List;
import java.util.Map;

import com.ylms.common.entity.Banner;

public interface IBannerDao extends IBaseDao<Banner> {
	Map<String, Object> checkById(String ids, Integer nopass);

	Map<String, Object> online_or_offline_ById(String id, Integer state);

	List<Banner> findAllByOnlie();
}
