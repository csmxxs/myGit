package com.ylms.service.imple;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ylms.common.dao.ShortcutMapper;
import com.ylms.common.entity.Shortcut;
import com.ylms.common.utils.LoggerUtils;
import com.ylms.core.mybatis.BaseMybatisDao;
import com.ylms.core.mybatis.page.Pagination;
import com.ylms.service.impl.IShortcutDao;

/**
 * 
 * 快捷功能入口服务
 * 
 * */
@Service
public class ShortcutDao extends BaseMybatisDao<ShortcutMapper> implements IShortcutDao{
     @Autowired
     private ShortcutMapper mapper;
	@Override
	public List<Shortcut> list(Map<String, Object> param) {
		return mapper.findList(param);
	}

	@Override
	public int deleteById(Long id) {
		return mapper.deleteById(id);
	}

	@Override
	public int update(Shortcut shortcut) {
		return mapper.update(shortcut);
	}

	@Override
	public int add(Shortcut shortcut) {
		return mapper.add(shortcut);
	}

	@Override
	public Shortcut findById(Long id) {
		return mapper.findById(id);
	}

	@Override
	public Map<String, Object> deleteById(String ids) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(ids==null){
			return resultMap;
		}
		try {
			int count = 0;
			String resultMsg = "删除成功。";
			String[] idArray = new String[] {};
			if (StringUtils.contains(ids, ",")) {
				idArray = ids.split(",");
			} else {
				idArray = new String[] { ids };
			}

			for (String idx : idArray) {
				Long id = new Long(idx);
				Shortcut shortcut = this.findById(id);
				// 1:上线2:下线
				if (shortcut.getState() != 1) {//下线状态才操作
					this.deleteById(id);
					count++;
				} else {
					resultMsg = "功能入口已上线,不能删除!";
				}
			}
			resultMap.put("status", 200);
			resultMap.put("count", count);
			resultMap.put("message", resultMsg);
		} catch (Exception e) {
			LoggerUtils.fmtError(getClass(), e, "根据IDS删除出现错误，ids[%s]", ids);
			resultMap.put("status", 500);
			resultMap.put("message", "删除出现错误，请刷新后再试！");
		}
		return resultMap;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Pagination<Shortcut> findByPage(
			Map<String, Object> resultMap, Integer pageNo, Integer pageSize) {
		return super.findPage(resultMap, pageNo, pageSize);
	}

	@Override
	public List<Shortcut> findAllByOnlie() {
		return mapper.findAllByOnlie();
	}

}
