package com.ylms.service.impl;

import java.util.List;

import com.ylms.common.entity.Shortcut;

public interface IShortcutDao extends IBaseDao<Shortcut> {
	List<Shortcut> findAllByOnlie();
}
