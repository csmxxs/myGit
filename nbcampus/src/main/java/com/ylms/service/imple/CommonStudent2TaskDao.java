package com.ylms.service.imple;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ylms.common.dao.CommonStudent2taskMapper;
import com.ylms.common.entity.CommonStudent2task;
import com.ylms.common.entity.Student2Task;
import com.ylms.common.entity.Task;
import com.ylms.core.mybatis.BaseMybatisDao;
import com.ylms.core.mybatis.page.Pagination;
import com.ylms.liuliangbao.config.GrantCoin;
import com.ylms.service.impl.ICommonStudent2TaskDao;
import com.ylms.service.impl.ITaskCountDao;
import com.ylms.service.impl.ITaskDao;
import com.ylms.service.impl.ITaskcollectDao;

@Service
public class CommonStudent2TaskDao extends
		BaseMybatisDao<CommonStudent2taskMapper> implements
		ICommonStudent2TaskDao {
	@Autowired
	private CommonStudent2taskMapper mapper;
	@Autowired
	private ITaskDao task;
	@Autowired
	private ITaskCountDao tc;
	@Autowired
	ITaskcollectDao collect;

	@Override
	public int addCommonStudent2Task(CommonStudent2task st) {
		if (st == null) {
			return 0;
		}
		return mapper.addCommonStudent2Task(st);
	}

	@Override
	public List<CommonStudent2task> list(Map<String, Object> param) {
		if (param == null) {
			return null;
		}
		return mapper.list(param);
	}

	@Override
	public int deleteById(Long id) {
		if (id == null) {
			return 0;
		}
		return mapper.deleteById(id);
	}

	@Override
	public int update(CommonStudent2task obj) {
		if (obj == null) {
			return 0;
		}
		return mapper.update(obj);
	}

	@Override
	public int add(CommonStudent2task entity) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public CommonStudent2task findById(Long id) {
		if (id == null) {
			return null;
		}
		return mapper.findById(id);
	}

	@Override
	public Map<String, Object> deleteById(String ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Pagination<CommonStudent2task> findByPage(
			Map<String, Object> params, Integer pageNo, Integer pageSize) {
		return super.findPage(params, pageNo, pageSize);
	}

	@Override
	public List<CommonStudent2task> listByTel(String studentTel) {

		if (studentTel == null || studentTel.equals("")) {
			return null;
		}
		return mapper.listByTel(studentTel);
	}

	@Override
	public int deleteCommonTask(Map<String, Object> params) {
		if (params == null) {
			return 0;
		}
		return mapper.deleteCommonStudent2Task(params);
	}

	@Override
	public CommonStudent2task findByPrefectureIdTaskIdStudentTel(
			Map<String, Object> params) {
		if (params == null) {
			return null;
		}
		return mapper.findByPrefectureIdTaskIdStudentTel(params);
	}
   /**
    * 
    * 牛币派发操作
    * 
    * */
	@Override
	public int update(String ids) {
		int count = 0;
		if (ids.equals("") || ids == null) {
			return 0;
		}
		String[] idArray = new String[] {};
		if (StringUtils.contains(ids, ",")) {
			idArray = ids.split(",");
		} else {
			idArray = new String[] { ids };
		}

		for (String idx : idArray) {
			Long id = new Long(idx);
			CommonStudent2task ctask = this.findById(id);
			Task t = task.findById(ctask.getCommonTaskId());
			// 开始派发nb
			Map<String, Object> map = GrantCoin.getGrantCoin(
					ctask.getStudentTel(), (int) t.getNbStimulate(),
					Integer.valueOf(t.getTaskId()));
			if (map != null && map.containsKey("result")) {
				String orderNo = "";
				String requestNo = "";
				Integer availableNum = 0;
				for (Map.Entry<String, Object> m : map.entrySet()) {
					if (m.getKey().equals("orderNo")) {
						orderNo = (String) m.getValue();
					}
					if (m.getKey().equals("requestNo")) {
						requestNo = (String) m.getValue();
					}
					if (m.getKey().equals("availableNum")) {
						availableNum = (Integer) m.getValue();
					}
				}
				for (Map.Entry<String, Object> val : map.entrySet()) {
					if (val.getKey().equals("result")
							&& (Integer) val.getValue() == 221310000) {
						// 修改公共列表状态
						ctask.setAvailableNum(availableNum);
						ctask.setCommonState(2);
						ctask.setOrderNo(orderNo);
						ctask.setRequestNo(requestNo);
						ctask.setCommonTaskState(1);
						this.update(ctask);
						//修改我的收藏
						Map<String, Object> m=new HashMap<String, Object>();
						m.put("prefectureId", ctask.getCommonPrefectureId());
						m.put("taskId", ctask.getCommonTaskId());
						m.put("studentTel", ctask.getStudentTel());
						Student2Task tc=collect.findByPrefectureIdTaskIdStudentTel(m);
						tc.setTaskState(1);
						collect.update(tc);
						
						// 修改专区统计列表
						// 修改日汇总表

						count++;
						break;
					}

				}
			}

		}
		return count;
	}

}
