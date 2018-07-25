package com.ylms.common.dao;

import java.util.List;
import java.util.Map;

import com.ylms.common.entity.UUser;
import com.ylms.permission.bo.URoleBo;

public interface UUserMapper {
	
	int deleteByPrimaryKey(Long id);
	int insert(UUser record);

    int insertSelective(UUser record);

    UUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UUser record);

    int updateByPrimaryKey(UUser record);

	UUser login(Map<String, Object> map);

	UUser findUserByEmail(String email);

	List<URoleBo> selectRoleByUserId(Long id);

}