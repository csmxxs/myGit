package com.ylms.permission.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ylms.common.entity.URole;
import com.ylms.core.mybatis.page.Pagination;
import com.ylms.permission.bo.RolePermissionAllocationBo;

public interface RoleService {

	int deleteByPrimaryKey(Long id);

    int insert(URole record);

    int insertSelective(URole record);

    URole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(URole record);

    int updateByPrimaryKey(URole record);

	Pagination<URole> findPage(Map<String, Object> resultMap, Integer pageNo,
			Integer pageSize);

	Map<String, Object> deleteRoleById(String ids);

	Pagination<RolePermissionAllocationBo> findRoleAndPermissionPage(
			Map<String, Object> resultMap, Integer pageNo, Integer pageSize);
	//根据用户ID查询角色（role），放入到Authorization里。
	Set<String> findRoleByUserId(Long userId);

	List<URole> findNowAllPermission();
}
