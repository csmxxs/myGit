package com.ylms.service.imple;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ylms.common.dao.PrefectureMapper;
import com.ylms.common.dao.TaskCountMapper;
import com.ylms.common.entity.Prefecture;
import com.ylms.common.utils.LoggerUtils;
import com.ylms.core.mybatis.BaseMybatisDao;
import com.ylms.core.mybatis.page.Pagination;
import com.ylms.service.impl.IPrefecture2taskDao;
import com.ylms.service.impl.IPrefectureDao;
import com.ylms.service.impl.ITaskDao;

/**
 * 
 * 任务专区服务
 * 
 * */
@Service
public class PrefectureDao extends BaseMybatisDao<PrefectureMapper> implements
		IPrefectureDao {

	@Autowired
	private PrefectureMapper mapper;
	@Autowired
	private IPrefecture2taskDao p2t;
	@Autowired
	private ITaskDao td;
	@Autowired
	private TaskCountMapper taskcountmapper;

	@Override
	public List<Prefecture> list(Map<String, Object> param) {
		return mapper.findList(param);
	}

	/**
	 * 
	 * 更新任务专区和新的任务列表
	 * 
	 * 
	 * */
	@Override
	public int update(Prefecture prefecture, String ids) {
		int i=1;
		if (prefecture == null || ids == null) {
			i=0;
			return i;
		}
			this.update(prefecture);
			if (ids.equals("0")) {
				// 清空该专区下的任务列表
				p2t.deleteByPrefectureId(prefecture.getId());
			}
			if (ids != null && !ids.equals("0") && !ids.equals("")) {
				// 该专区有任务列表要更新
				p2t.addTask2Prefecture(prefecture.getId(), ids);
			}
		return i;
	}

	@Override
	public int add(Prefecture prefecture) {
		return mapper.add(prefecture);
	}

	@Override
	public int deleteById(Long id) {
		return mapper.deleteById(String.valueOf(id));
	}

	@Override
	public Map<String, Object> deleteById(String ids) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (ids == null) {
			resultMap.put("status", 400);
			resultMap.put("message", "参数错误!");
			return resultMap;
		}
		try {
			String resultMsg = "删除成功。";
			String[] idArray = new String[] {};
			if (StringUtils.contains(ids, ",")) {
				idArray = ids.split(",");
			} else {
				idArray = new String[] { ids };
			}

			for (String idx : idArray) {
				// 找到对应的任务列表ID
				Prefecture prefecture = this.findByPrefectureId(idx);
				// 1:上线2:下线
				if (prefecture != null) {
					if (prefecture.getState() != 1) {// 下线状态才操作
						this.deleteById(new Long(idx));
						// 删除对应的任务列表
						p2t.deleteByPrefectureId(idx);
					} else {
						resultMsg = "该专区已上线,不能删除!";
					}
				}
			}
			resultMap.put("status", 200);
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
	public Pagination<Prefecture> findByPage(Map<String, Object> resultMap,
			Integer pageNo, Integer pageSize) {
		return super.findPage(resultMap, pageNo, pageSize);
	}

	/**
	 * 
	 * 查询在线的任务专区
	 * 
	 * 
	 * */
	@Override
	public List<Prefecture> findAllByOnlie() {
		return mapper.findAllByOnlie();
	}

	@Override
	public Prefecture findByPrefectureId(String id) {
		if (id.equals("") || id == null) {
			return null;
		}
		return mapper.findByPrefectureId(id);
	}

	@Override
	public Prefecture findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(Prefecture obj) {
		return mapper.update(obj);
	}

}
