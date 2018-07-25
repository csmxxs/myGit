package com.ylms.service.impl;

import java.util.List;

import com.ylms.common.entity.Prefecture;

public interface IPrefectureDao extends IBaseDao<Prefecture> {

	int update(Prefecture p, String ids);
	
	Prefecture findByPrefectureId(String id);

	List<Prefecture> findAllByOnlie();
}
