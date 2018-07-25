package com.ylms.service.imple;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ylms.common.dao.TaskCountMapper;
import com.ylms.common.entity.TaskCount;
import com.ylms.service.impl.ITaskCountDao;

@Service
public class TaskCountDao implements ITaskCountDao {

	@Autowired
	private TaskCountMapper mapper;
    /**
     * 
     * 任务专区汇总
     * 
     * */
	@Override
	public Map<String, Object> prefectureCount(String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("p1", id);
		mapper.prefectureCount(map);
		return map;
	}

	@Override
	public void updateTaskSsscnumber(TaskCount t) {
		if(t==null){
			return ;
		}
		mapper.updateTaskSsscnumber(t);
	}

	@Override
	public List<TaskCount> findById(Long id) {
		return mapper.findById(id);
	}

	/**
	 * 
	 * 每20分钟调用一次 添加日汇总同时修改任务汇总列表对应的未完成数(即点击任务开始时)
	 * 
	 * */
	@Override
	public void updateDayCountAndTaskCount(TaskCount t) {
		if (t == null) {
			return;
		}
		mapper.updateDayCountAndTaskCount(t);
	}

	/**
	 * 
	 * 每天凌晨1点调用一次 初始化在线任务列表的日汇总
	 * 
	 * **/
	@Override
	public void addDayCount(TaskCount t) {
		 if(t==null){
			 return ;
		 }
		mapper.addDayCount(t);
	}

	@Override
	public TaskCount findTask(Map<String, Object> params) {
		if(params==null){
			return null;
		}
		return mapper.findTask(params);
	}
}
