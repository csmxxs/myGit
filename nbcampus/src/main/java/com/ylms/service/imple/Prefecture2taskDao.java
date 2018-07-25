package com.ylms.service.imple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ylms.common.dao.Prefecture2taskMapper;
import com.ylms.common.dao.TaskCountMapper;
import com.ylms.common.entity.Prefecture2task;
import com.ylms.common.entity.TaskCount;
import com.ylms.common.utils.DateUtil;
import com.ylms.service.impl.IPrefecture2taskDao;
import com.ylms.service.impl.ITaskDao;

@Service
public class Prefecture2taskDao implements IPrefecture2taskDao {
	@Autowired
	private Prefecture2taskMapper mapper;
	@Autowired
	private ITaskDao td;
	@Autowired
	private TaskCountMapper tcm;

	/**
	 * @param id
	 *            任务专区ID
	 * @param ids
	 *            任务列表ID数组
	 * 
	 * */
	@Override
	public Map<String, Object> addTask2Prefecture(String id, String ids) {
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 用来临时存放任务专区原先拥有的任务列表
		List<String> list = new ArrayList<String>();
        
		if (id == null || id.equals("")) {
			resultMap.put("status", 300);
			resultMap.put("message", "参数有误");
			return resultMap;
		}
		try {
			
			// 先找到专区原先拥有任务列表
			param.put("prefectureId", id);
			List<Prefecture2task> p = this.findList(param);
			for (Prefecture2task prefecture2task : p) {
				list.add(prefecture2task.getTaskId() + "");
			}

			for (String taskId : ids.split(",")) {
				if (!"".equals(taskId) && !list.contains(taskId)) {
					// 添加到中间表
					result.put("id", id);
					result.put("taskId", new Long(taskId));
					resultMap.put("taskId", taskId);
					resultMap.put("prefectureId", id);
					resultMap.put("createTime", DateUtil.dateToString(new Date()));
					// 将任务列表上线！
					td.online_or_offline_ById(taskId, 1);
					// 当天更新的任务专区里的任务列表在taskCount和dayCount里没有时需添加进去这两个表
					TaskCount tc = tcm.findTask(resultMap);
					if (tc == null) {
                      //添加新的任务列表到统计表里taskCount和dayCount
					 tcm.addTaskCount(new TaskCount(id, new Long(taskId)));
					}
					this.addTaskById(result);
				}
			}
			// 删除的任务列表选项
			List<String> taskid = Arrays.asList(ids.split(","));
			for (String taskId : list) {
				if (!taskid.contains(taskId)) {
					result.clear();
					result.put("id", id);
					result.put("taskId", taskId);
					this.deleteById(result);
				}
			}
			resultMap.put("status", 200);
			resultMap.put("message", "更新成功!");
		} catch (Exception e) {
			resultMap.put("status", 500);
			resultMap.put("message", "更新失败!");
		}
		    return resultMap;
	}

	@Override
	public List<Prefecture2task> findList(Map<String, Object> param) {
		if (param == null) {
			return null;
		}
		return mapper.findList(param);
	}

	/**
	 * 
	 * 根据任务列表ID删除
	 * 
	 * */
	@Override
	public int deleteById(Map<String, Object> params) {
		if (params == null) {
			return 0;
		}
		return mapper.deleteById(params);
	}

	/**
	 * 
	 * 删除任务专区时同时删除掉包含的任务列表 根据任务专区列表ID删除
	 * */
	@Override
	public int deleteByPrefectureId(String id) {
		return mapper.deleteByPrefectureId(id);
	}

	/**
	 * 
	 * 为添加相应的任务列表
	 * 
	 * 
	 * */
	@Override
	public int addTaskById(Map<String, Object> params) {
		return mapper.addTaskById(params);
	}

	@Override
	public int update(Map<String, Object> params) {
		if (params == null) {
			return 0;
		}
		return mapper.update(params);
	}

}
