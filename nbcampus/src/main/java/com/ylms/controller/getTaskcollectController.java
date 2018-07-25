package com.ylms.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ylms.common.controller.BaseController;
import com.ylms.common.entity.CommonStudent2task;
import com.ylms.common.entity.Student2Task;
import com.ylms.common.entity.User;
import com.ylms.common.utils.IntegerUtlis;
import com.ylms.service.impl.ICommonStudent2TaskDao;
import com.ylms.service.impl.ITaskcollectDao;

/**
 * 
 * 任务收藏
 * 
 */

@Controller
@Scope(value = "prototype")
@RequestMapping("taskcollect")
public class getTaskcollectController extends BaseController {
	@Autowired
	private ITaskcollectDao td;
	@Autowired
	private ICommonStudent2TaskDao si2c;
	private IntegerUtlis u;

	/**
	 * 
	 * 
	 * 添加任务收藏
	 * 
	 * */

	@SuppressWarnings("static-access")
	@RequestMapping("addTaskcollect")
	@ResponseBody
	public Map<String, Object> addTaskcollect(HttpServletRequest req,
			HttpServletResponse resp, String prefectureId, String taskId) {

		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");
		if (user == null) {
			resultMap.put("status", 100);
			resultMap.put("message", "该用户没有登录，请先登录!");
			return resultMap;
		}
		if (!u.RegexString(prefectureId) || !u.RegexString(taskId)
				|| taskId.length() > 20 || prefectureId.length() > 20
				|| taskId == null || prefectureId == null
				|| prefectureId.equals("") || taskId.equals("")
				|| prefectureId.length() > 20 || taskId.length() > 20) {
			resultMap.put("status", 300);
			resultMap.put("message", "参数错误!");
			return resultMap;
		}

		// 查询用户收藏的任务列表是否存在该任务
		// resultMap.put("prefectureId", prefectureId);
		resultMap.put("taskId", taskId);
		resultMap.put("studentTel", user.getMobile());
		Student2Task stu = td.findByPrefectureIdTaskIdStudentTel(resultMap);
		if (stu != null) {
			resultMap.clear();
			resultMap.put("status", 400);
			resultMap.put("message", "任务收藏过了!");
		} else {
			// 查询用户的公共任务列表是否存在该任务
			// 先查询是否是已经进行的任务
			CommonStudent2task commonstu = si2c
					.findByPrefectureIdTaskIdStudentTel(resultMap);
			resultMap.clear();
			if (commonstu != null) {

				try {
					// 添加到任务收藏
					Student2Task st = new Student2Task();
					st.setPrefectureId(prefectureId);
					st.setStudentTel(user.getMobile());
					st.setTaskId(Long.valueOf(taskId));
					st.setTaskState(commonstu.getCommonTaskState());
					st.setCollect(0);
					st.setSsscImagesBoolean(1);
					// 修改这个公共任务的收藏状态为已收藏
					commonstu.setCommonCollect(0);
					si2c.update(commonstu);
					td.addTaskcollect(st);
					resultMap.put("status", 200);
					resultMap.put("message", "收藏成功!");

				} catch (Exception e) {
					resultMap.put("status", 500);
					resultMap.put("message", "收藏失败!");
				}

			} else {
				// 如果公共任务列表没有，则没有参与该任务
				Student2Task st = new Student2Task();
				st.setPrefectureId(prefectureId);
				st.setStudentTel(user.getMobile());
				st.setTaskId(Long.valueOf(taskId));
				st.setTaskState(0);
				st.setCollect(0);
				st.setSsscImagesBoolean(1);
				if (td.addTaskcollect(st) > 0) {
					resultMap.put("status", 200);
					resultMap.put("message", "收藏成功!");
				} else {
					resultMap.put("status", 500);
					resultMap.put("message", "收藏失败!");
				}
			}

		}
		return resultMap;
	}

	/**
	 * 
	 * 取消任务收藏
	 * 
	 * 
	 * */
	@SuppressWarnings("static-access")
	@RequestMapping("deleteCollectTask")
	@ResponseBody
	public Map<String, Object> deleteCollectTask(HttpServletRequest req,
			HttpServletResponse resp, String prefectureId, String taskId) {

		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");
		if (user == null) {
			resultMap.put("status", 100);
			resultMap.put("message", "该用户没有登录，请先登录!");
			return resultMap;
		}
		if (!u.RegexString(prefectureId) || !u.RegexString(taskId)
				|| taskId.length() > 20 || prefectureId.length() > 20
				|| taskId == null || prefectureId == null
				|| prefectureId.equals("") || taskId.equals("")
				|| prefectureId.length() > 20 || taskId.length() > 20) {
			resultMap.put("status", 300);
			resultMap.put("message", "参数错误!");
			return resultMap;
		}

		try {
			// 查询用户收藏的任务列表是否存在该任务
			// resultMap.put("prefectureId", prefectureId);
			resultMap.put("taskId", taskId);
			resultMap.put("studentTel", user.getMobile());
			Student2Task stu = td.findByPrefectureIdTaskIdStudentTel(resultMap);
			if (stu != null) {
				// 删除任务收藏列表
				td.deleteTaskcollect(resultMap);
				if (stu.getTaskState() != 0) {
					// 修改公共任务列表
					CommonStudent2task commonstu = si2c
							.findByPrefectureIdTaskIdStudentTel(resultMap);
					// 改变收藏状态
					commonstu.setCommonCollect(1);
					si2c.update(commonstu);
				}

			}
			resultMap.clear();
			resultMap.put("status", 200);
			resultMap.put("message", "任务取消成功!");

		} catch (Exception e) {
			resultMap.clear();
			resultMap.put("status", 500);
			resultMap.put("message", "任务取消失败!");
		}

		return resultMap;
	}
}
