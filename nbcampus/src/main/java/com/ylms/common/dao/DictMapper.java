package com.ylms.common.dao;

import java.util.List;
import java.util.Map;
import com.ylms.common.entity.Dict;
/**
 * dict的映射文件的代理接口!(代理DictMapper.xml)
 * 
 * */
public interface DictMapper{
	List<Dict> findList(Map<String, Object> param);

	int deleteById(Long id);

	int update(Dict obj);

	int add(Dict entity);

	Dict findById(Long id);
    
}
