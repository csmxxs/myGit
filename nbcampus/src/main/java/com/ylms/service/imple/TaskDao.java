package com.ylms.service.imple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ylms.common.dao.Prefecture2taskMapper;
import com.ylms.common.dao.TaskMapper;
import com.ylms.common.entity.Task;
import com.ylms.common.utils.LoggerUtils;
import com.ylms.core.mybatis.BaseMybatisDao;
import com.ylms.core.mybatis.page.Pagination;
import com.ylms.service.impl.ITaskDao;

@Service
public class TaskDao extends BaseMybatisDao<TaskMapper> implements ITaskDao {
	@Autowired
	private TaskMapper mapper;
	@Autowired
	private Prefecture2taskMapper p2t;
	@Override
	public List<Task> list(Map<String, Object> param) {
		return mapper.list(param);
	}

	@Override
	public int deleteById(Long id) {
		return mapper.deleteById(id);
	}

	@Override
	public int update(Task obj) {
		return mapper.update(obj);
	}

	@Override
	public int add(Task entity) {
		return mapper.add(entity);
	}

	@Override
	public Task findById(Long id) {
		return mapper.findById(id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Pagination<Task> findByPage(Map<String, Object> resultMap,
			Integer pageNo, Integer pageSize) {
		return super.findPage(resultMap, pageNo, pageSize);
	}

	/**
	 * 
	 * 批量删除操作
	 * */
	@Override
	public Map<String, Object> deleteById(String ids) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
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
				Task task = this.findById(id);
				// 对下线的任务列表进行删除
				if (task.getState() != 1) {
					if (this.deleteById(id) > 0) {
						//需根据下线任务列表ID去删除中间表(prefecture2task)
						resultMap.put("taskId", ids);
						p2t.deleteById(resultMap);
						count++;
					}
				} else {
					resultMsg = "在线列表不能删除。";
				}
			}
			resultMap.put("status", 200);
			resultMap.put("count", count);
			resultMap.put("message", resultMsg);
		} catch (Exception e) {
			LoggerUtils.fmtError(getClass(), e, "根据IDS删除用户出现错误，ids[%s]", ids);
			resultMap.put("status", 500);
			resultMap.put("message", "删除出现错误，请刷新后再试！");
		}
		return resultMap;
	}

	/**
	 * 批量上线和下线操作
	 * 
	 * */
	@Override
	public Map<String, Object> online_or_offline_ById(String id, Integer state) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			int count = 0;
			String resultMsg = "操作成功。";
			Long ids = new Long(id);
			Task task = this.findById(ids);
			if (task != null) {
				if (state == 2 && task.getNoPass() == 1 && task.getState() == 1) {
					// 对任务列表进行下线
					if (mapper.offlineById(ids) > 0) {
						count++;
						//需根据下线任务列表ID去删除中间表(prefecture2task)
						resultMap.put("taskId", ids);
						p2t.deleteById(resultMap);
						resultMsg = "下线成功!";
						
					}
				} else if (state == 1 && task.getNoPass() == 1) {
					// 对任务列表进行上线
					if (mapper.onlineById(ids) > 0) {
						count++;
						resultMsg = "上线成功!";
					}
				} else if (task.getNoPass() != 1) {
					resultMsg = "请先审核列表再操作!";
				}
			}

			resultMap.put("status", 200);
			resultMap.put("count", count);
			resultMap.put("message", resultMsg);
		} catch (Exception e) {
			LoggerUtils.fmtError(getClass(), e, "根据ID上线或者下线出现错误，ids[%s]", id);
			resultMap.put("status", 500);
			resultMap.put("message", "上线或者下线操作出现错误，请刷新后再试！");
		}
		return resultMap;
	}

	/**
	 * 
	 * task列表批量审核
	 * 
	 * 
	 * **/
	@Override
	public Map<String, Object> checkById(String ids, Integer nopass) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			int count = 0;
			String resultMsg = "审核完成。";
			String[] idArray = new String[] {};
			if (StringUtils.contains(ids, ",")) {
				idArray = ids.split(",");
			} else {
				idArray = new String[] { ids };
			}

			for (String idx : idArray) {
				Long id = new Long(idx);
				Task task = this.findById(id);
				if (task != null) {
					// 没有审核过才进行审核
					if (task.getNoPass() == 0) {
						if (nopass == 1 && task.getNoPass() == 0) {
							mapper.check1ById(id);
							count++;
						}
						if (nopass == 2 && task.getNoPass() == 0) {
							mapper.check2ById(id);
							count++;
						}
					} else {
						resultMsg = "列表已审核!";
					}
				}
			}
			resultMap.put("status", 200);
			resultMap.put("count", count);
			resultMap.put("message", resultMsg);
		} catch (Exception e) {
			LoggerUtils.fmtError(getClass(), e, "根据IDS审核列表出现错误，ids[%s]", ids);
			resultMap.put("status", 500);
			resultMap.put("message", "审核列表出现错误，请刷新后再试！");
		}
		return resultMap;
	}

	/**
	 * 当添加任务专区时 根据传过来的ids查询 任务管理专区添加的任务列表,回传页面
	 * 
	 * **/
	@Override
	public List<Task> list(String ids) {
		if (ids == null) {
			return null;
		}
		List<Task> list = new ArrayList<Task>();
		try {
			String[] idArray = new String[] {};
			if (StringUtils.contains(ids, ",")) {
				idArray = ids.split(",");
			} else {
				idArray = new String[] { ids };
			}

			for (String idx : idArray) {
				Long id = new Long(idx);
				Task task = this.findById(id);
				if (task != null) {
					list.add(task);
				}
			}
		} catch (Exception e) {

		}
		return list;
	}

	/**
	 * 
	 * 查询任务专区所属的任务列表
	 * */
	@Override
	public List<Task> listById(String id) {
		if (id == null) {
			return null;
		}
		return mapper.listById(id);
	}

	/**
	 * 
	 * 为页面查询所有在线的任务专区和对应的任务列表
	 * 
	 * */
	@Override
	public List<Task> list(Long id) {
		return mapper.list(id);
	}

	/**
	 * 
	 * 列表效果分析日汇总
	 * 
	 * */
	@SuppressWarnings("unchecked")
	@Override
	public Pagination<Task> listTaskCount(String sqlId,
			Map<String, Object> param, Integer pageNo, Integer pageSize) {
		return super.findPage(sqlId, param, pageNo, pageSize);
	}
	
    /**
     * 
     * 根据省份查询任务列表
     * 
     * */
	@Override
	public List<Task> findAllByProvinceId(Map<String, Object> param) {
		if (param == null) {
			return null;
		}
		return mapper.findAllByProvinceId(param);
	}

	/**
	 * 
	 * 根据学生电话查询任务列表
	 * 
	 * */
	@Override
	public List<Task> getTaskcollectByStudentTel(String studentTel) {
		if (studentTel.equals("")|| studentTel == null) {
			return null;
		}
		return mapper.getTaskcollectByStudentTel(studentTel);
	}

	@Override
	public List<Task> getCommonStudentTask(String studentTel) {
		if (studentTel.equals("")|| studentTel == null) {
			return null;
		}
		return mapper.getCommonStudentTask(studentTel);
	}

}
