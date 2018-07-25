package com.ylms.common.dao;

import java.util.List;
import java.util.Map;

import com.ylms.common.entity.Shortcut;

/**
 * 
 * 快捷功能入口的映射文件的代理接口!(ShortcutMapper.xml)
 * 
 * */
public interface ShortcutMapper {
	List<Shortcut> findList(Map<String, Object> param);

	int deleteById(Long id);

	int update(Shortcut shortcut);

	int add(Shortcut shortcut);

	Shortcut findById(Long id);

	List<Shortcut> findAllByOnlie();
}
