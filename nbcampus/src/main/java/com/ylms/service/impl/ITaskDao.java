package com.ylms.service.impl;

import java.util.List;
import java.util.Map;

import com.ylms.common.entity.Task;
import com.ylms.core.mybatis.page.Pagination;

public interface ITaskDao extends IBaseDao<Task> {
	Map<String, Object> checkById(String ids, Integer nopass);

	Map<String, Object> online_or_offline_ById(String ids, Integer state);

	List<Task> list(String ids);

	List<Task> listById(String id);

	List<Task> list(Long id);

	Pagination<Task> listTaskCount(String sqlId, Map<String, Object> param,
			Integer pageNo, Integer pageSize);

	List<Task> findAllByProvinceId(Map<String, Object> param);

	List<Task> getTaskcollectByStudentTel(String studentTel);
	
	List<Task> getCommonStudentTask(String studentTel);
	
}
