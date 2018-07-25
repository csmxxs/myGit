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
import com.ylms.common.entity.Student;
import com.ylms.common.entity.Student2Task;
import com.ylms.common.entity.TaskCount;
import com.ylms.common.entity.User;
import com.ylms.common.utils.IntegerUtlis;
import com.ylms.common.utils.vcode.CommonImagesUrl;
import com.ylms.service.impl.ICommonStudent2TaskDao;
import com.ylms.service.impl.IRedisServiceImplDao;
import com.ylms.service.impl.IStudentDao;
import com.ylms.service.impl.ITaskCountDao;
import com.ylms.service.impl.ITaskcollectDao;

/**
 * 
 * 任务开始点击数的公共接口 (同时处理用户点击任务开始时是点击了任务收藏的任务列表还是公共的任务列表)
 * */
@Controller
@Scope(value = "prototype")
@RequestMapping("submit")
public class getClickNumberController extends BaseController {
	@Autowired
	private IRedisServiceImplDao rs;
	@Autowired
	private ITaskCountDao icd;
	@Autowired
	private CommonImagesUrl imagesURL;
	@Autowired
	private IStudentDao sd;
	@Autowired
	private ITaskcollectDao td;
	@Autowired
	private ICommonStudent2TaskDao si2c;

	/**
	 * 提交点击数（点击开始任务时
	 * 
	 * @param prefectureId
	 *            专区id
	 * @param taskId
	 *            任务id
	 * @param clicknumber
	 *            点击数
	 * 
	 * */
	@RequestMapping(value = "clickNumber")
	@ResponseBody
	public Map<String, Object> clickNumber(HttpServletRequest req,
			HttpServletResponse resp, String prefectureId, String taskId) {
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");
		if (user == null) {
			resultMap.put("status", 100);
			resultMap.put("message", "该用户没有登录，请先登录!");
			return resultMap;
		}
		Student s = sd.findByTel(user.getMobile());
		if (s != null) {
			if (s.getMyCheck() != 1) {
				resultMap.put("status", 500);
				resultMap.put("message", "你的信息未审核,请先审核.");
				return resultMap;
			} else {
				// 查询用户收藏的列表是否存在该任务
				resultMap.clear();
				resultMap.put("prefectureId", prefectureId);
				resultMap.put("taskId", taskId);
				resultMap.put("studentTel", user.getMobile());
				Student2Task stu = td
						.findByPrefectureIdTaskIdStudentTel(resultMap);
				if (stu != null) {
					// 修改用户收藏任务列表的任务状态改变为未完成
					// 把该任务添加到用户的公共任务列表
					// 未完成
					stu.setTaskState(2);
					// 添加任务列表到用户的公共任务列表
					CommonStudent2task commontask = new CommonStudent2task();
					commontask.setCommonPrefectureId(prefectureId);
					commontask.setCommonTaskId(new Long(taskId));
					commontask.setStudentTel(user.getMobile());
					// 已收藏
					commontask.setCommonCollect(0);
					// 未完成
					commontask.setCommonTaskState(2);
					// 未截图
					commontask.setSsscImagesBoolean(1);
					if (td.update(stu) > 0
							&& si2c.addCommonStudent2Task(commontask) > 0) {
						// 调用存储过程
						icd.updateDayCountAndTaskCount(new TaskCount(
								prefectureId, new Long(taskId)));
						resultMap.put("status", 200);
						resultMap.put("message", "任务进行成功!");
						return resultMap;
					} else {
						resultMap.put("status", 500);
						resultMap.put("message", "服务出错,任务进行失败");
						return resultMap;
					}
				} else {
					// 查询用户的公共任务列表是否存在该任务
					resultMap.clear();
					resultMap.put("prefectureId", prefectureId);
					resultMap.put("taskId", taskId);
					resultMap.put("studentTel", user.getMobile());
					CommonStudent2task commonstu = si2c
							.findByPrefectureIdTaskIdStudentTel(resultMap);
					if (commonstu == null) {
						// 添加任务列表到用户的公共任务列表
						CommonStudent2task commontask = new CommonStudent2task();
						commontask.setCommonPrefectureId(prefectureId);
						commontask.setCommonTaskId(new Long(taskId));
						commontask.setStudentTel(user.getMobile());
						// 未收藏
						commontask.setCommonCollect(1);
						// 未完成
						commontask.setCommonTaskState(2);
						// 未截图
						commontask.setSsscImagesBoolean(1);
						if (si2c.addCommonStudent2Task(commontask) > 0) {
							// 调用存储过程
							icd.updateDayCountAndTaskCount(new TaskCount(
									prefectureId, new Long(taskId)));
							resultMap.put("status", 200);
							resultMap.put("message", "任务进行成功!");
							return resultMap;
						} else {
							resultMap.put("status", 500);
							resultMap.put("message", "服务出错,任务进行失败");
							return resultMap;
						}
					}
				}

			}

		} else {
			resultMap.put("status", 400);
			resultMap.put("message", "手机号不存在");
			return resultMap;
		}
		if (prefectureId != null && taskId != null
				&& IntegerUtlis.RegexString(prefectureId)
				&& IntegerUtlis.RegexString(taskId)) {
			// 调用存储过程，调用一次加1
			rs.setClickNumber(prefectureId, taskId, "1");
			resultMap.put("status", 200);
			resultMap.put("message", "参数已上传");
			return resultMap;
		} else {
			resultMap.put("status", 500);
			resultMap.put("message", "参数不正确,上传失败");
			return resultMap;
		}
	}
}
