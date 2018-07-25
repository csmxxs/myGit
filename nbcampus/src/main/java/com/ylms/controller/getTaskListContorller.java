package com.ylms.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ylms.common.controller.BaseController;
import com.ylms.common.entity.Banner;
import com.ylms.common.entity.Prefecture;
import com.ylms.common.entity.Shortcut;
import com.ylms.common.entity.Student;
import com.ylms.common.entity.Task;
import com.ylms.common.entity.User;
import com.ylms.common.utils.JsonDateValueProcessorUtil;
import com.ylms.common.webTask.DefaultTask;
import com.ylms.service.impl.IBannerDao;
import com.ylms.service.impl.ICommonStudent2TaskDao;
import com.ylms.service.impl.IPrefectureDao;
import com.ylms.service.impl.IShortcutDao;
import com.ylms.service.impl.IStudentDao;
import com.ylms.service.impl.ITaskDao;

/**
 * 
 * 获取任务管理专区的任务列表
 * 
 * 
 * */
@Controller
@RequestMapping("common")
@Scope(value = "prototype")
public class getTaskListContorller extends BaseController {
	@Autowired
	private ITaskDao td;
	@Autowired
	private IBannerDao bannerList;
	@Autowired
	private IShortcutDao shortcutList;
	@Autowired
	private IPrefectureDao prefectureList;
	@Autowired
	private IStudentDao sd;
	@Autowired
	private ICommonStudent2TaskDao si2c;
    @Autowired
    private DefaultTask defaultTask;
	/**
	 * 获取任务管理专区的任务列表
	 * 
	 * */
	@RequestMapping(value = "PrefectureList")
	@ResponseBody
	public Map<String, Object> getPrefectureList(HttpServletRequest req,
			HttpServletResponse resp) {

		// 获取会话
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");
		if (user == null) {
			// 没有注册的用户
			List<Object> pList = new ArrayList<Object>();
			try {
				// 先查到所有在线的任务专区
				List<Prefecture> pl = prefectureList.findAllByOnlie();
				if (pl.size() < 1) {
					resultMap.put("status", 100);
					resultMap.put("message", "任务专区无信息");
					return resultMap;
				}

				for (Prefecture prefecture : pl) {

					resultMap.clear();
					// 找到所属的任务列表
					resultMap.put("prefectureId", prefecture.getId());
					// 默认显示广东地区的任务列表
					resultMap.put("provinceId", defaultTask.getProvinceId());
					List<Task> task = td.findAllByProvinceId(resultMap);
					if (task.size() < 1) {
						continue;
					}

					for (Task t : task) {
						// cll: 0:已收藏1:未收藏
						// TaskState: 0:未参与1:完成2:未完成
						t.setTaskState(0);
						t.setCll(1);
						t.setSsscImagesBoolean(1);
					}
					prefecture.setTaskList(task);
					pList.add(prefecture);
				}
				JsonConfig jc = new JsonConfig();
				jc.registerJsonValueProcessor(Date.class,
						new JsonDateValueProcessorUtil());
				JSONArray array = JSONArray.fromObject(pList, jc);
				resultMap.put("status", 200);
				resultMap.put("message", "获取默认信息列表成功");
				resultMap.put("result", array);
			} catch (Exception e) {
				resultMap.put("status", 500);
				resultMap.put("message", "获取信息失败!");
			}
		} else {

			Student s = sd.findByTel(user.getMobile());
			// 没有通过审核的用户
			if (s != null) {
				if (s.getShcoolProvinceId() == null || s.getMyCheck() != 1) {
					// 默认显现广东地区
					s.setShcoolProvinceId((defaultTask.getProvinceId()));
					List<Object> pList = new ArrayList<Object>();
					try {
						List<Prefecture> pl = prefectureList.findAllByOnlie();
						if (pl.size() < 1) {
							resultMap.put("status", 100);
							resultMap.put("message", "任务专区信息为空");
							return resultMap;
						}
						for (Prefecture prefecture : pl) {

							resultMap.clear();
							// 再找到所属的任务列表
							// 根据用户学校归属地区的查询任务列表
							resultMap.put("prefectureId", prefecture.getId());
							resultMap
									.put("provinceId", s.getShcoolProvinceId());
							List<Task> task = td.findAllByProvinceId(resultMap);
							if (task.size() < 1) {
								continue;
							}
							for (Task t : task) {
								// cll: 0:已收藏1:未收藏
								// TaskState: 0:未参与1:完成2:未完成
								t.setTaskState(0);
								t.setCll(1);
								t.setSsscImagesBoolean(1);
							}
							prefecture.setTaskList(task);
							pList.add(prefecture);
						}
						JsonConfig jc = new JsonConfig();
						jc.registerJsonValueProcessor(Date.class,
								new JsonDateValueProcessorUtil());
						JSONArray array = JSONArray.fromObject(pList, jc);
						resultMap.put("status", 200);
						resultMap.put("message", "获取默认的信息列表成功");
						resultMap.put("result", array);
					} catch (Exception e) {
						resultMap.put("status", 500);
						resultMap.put("message", "获取信息失败!");
					}
				} else {
					// 通过审核的用户
					// 查询用户收藏的任务列表
					List<Task> tcs = td.getTaskcollectByStudentTel(user
							.getMobile());
					// 用户参与公共任务列表的信息
					List<Task> cs = td.getCommonStudentTask(user.getMobile());
					// 存放在线所有任务专区(以及它所拥有的任务列表)
					List<Object> pList = new ArrayList<Object>();
					// 存放用户公共和收藏的任务列表
					List<Task> collectAndCommonTask = new ArrayList<Task>();
					if (tcs.size() > 0) {
						for (Task collect : tcs) {
							collectAndCommonTask.add(collect);
						}
					}
					if (cs.size() > 0) {
						for (Task common : cs) {
							collectAndCommonTask.add(common);
						}
					}

					try {
						List<Prefecture> pl = prefectureList.findAllByOnlie();
						if (pl.size() < 1) {
							resultMap.put("status", 100);
							resultMap.put("message", "任务专区信息为空");
							return resultMap;
						}
						for (Prefecture prefecture : pl) {
							// 再找到所属的任务列表
							// 根据用户学校归属地区的查询任务列表
							resultMap.put("prefectureId", prefecture.getId());
							resultMap
									.put("provinceId", s.getShcoolProvinceId());
							List<Task> task = td.findAllByProvinceId(resultMap);
							if (task.size() < 1) {
								continue;
							}
							for (Task task2 : task) {
								// 根据进行中的任务对比
								if (collectAndCommonTask.size() > 0) {
									Boolean flag = false;
									for (Task commonAndCollect : collectAndCommonTask) {
										if (task2.getId() == commonAndCollect
												.getId()) {
											task2.setCll(commonAndCollect
													.getCll());
											task2.setSsscImagesBoolean(commonAndCollect
													.getSsscImagesBoolean());
											task2.setTaskState(commonAndCollect
													.getTaskState());
											flag = true;
										}
									}
									if (!flag) {
										task2.setCll(1);
										task2.setSsscImagesBoolean(1);
										task2.setTaskState(0);
									}

								} else {
									// 说明用户没有收藏和参与任务
									task2.setCll(1);
									task2.setSsscImagesBoolean(1);
									task2.setTaskState(0);
								}
							}
							prefecture.setTaskList(task);
							pList.add(prefecture);
						}
						JsonConfig jc = new JsonConfig();
						jc.registerJsonValueProcessor(Date.class,
								new JsonDateValueProcessorUtil());
						// JSONArray.fromObject会把空字段的值自动变成0
						JSONArray array = JSONArray.fromObject(pList, jc);
						resultMap.put("status", 200);
						resultMap.put("message", "获取用户信息列表成功");
						resultMap.put("result", array);
					} catch (Exception e) {
						resultMap.put("status", 500);
						resultMap.put("message", "获取信息失败!");
					}

				}
			} else {
				resultMap.put("status", 400);
				resultMap.put("message", "用户信息获取失败");
				return resultMap;
			}
		}
		return resultMap;
	}

	/**
	 * 
	 * 获取我的任务
	 * */
	@RequestMapping("getCommonTask")
	@ResponseBody
	public Map<String, Object> getStudentCommonTask(HttpServletRequest req,
			HttpServletResponse resp) {

		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");
		if (user == null) {
			resultMap.put("status", 100);
			resultMap.put("message", "您还没有登录,请先登录!");
			return resultMap;
		}
		try {
			// 用户公共任务列表的信息
			List<Task> cs = td.getCommonStudentTask(user.getMobile());
			if (cs.size() > 0) {
				JsonConfig jc = new JsonConfig();
				jc.registerJsonValueProcessor(Date.class,
						new JsonDateValueProcessorUtil());
				JSONArray array = JSONArray.fromObject(cs, jc);
				resultMap.put("status", 200);
				resultMap.put("message", "获取信息成功");
				resultMap.put("result", array);

			} else {
				resultMap.put("status", 200);
				resultMap.put("message", "获取信息成功,您没有参与任务");
			}

		} catch (Exception e) {
			resultMap.put("status", 500);
			resultMap.put("message", "服务发生异常!请稍后重试...");
			return resultMap;
		}
		return resultMap;
	}

	/**
	 * 
	 * 获取我的收藏
	 * */
	@RequestMapping("getCollectTask")
	@ResponseBody
	public Map<String, Object> getStudentCollectTask(HttpServletRequest req,
			HttpServletResponse resp) {

		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");
		if (user == null) {
			resultMap.put("status", 100);
			resultMap.put("message", "该用户没有登录,请先登录!");
			return resultMap;
		}
		try {
			// 查询用户收藏的任务列表
			List<Task> tcs = td.getTaskcollectByStudentTel(user.getMobile());
			if (tcs.size() > 0) {
				JsonConfig jc = new JsonConfig();
				jc.registerJsonValueProcessor(Date.class,
						new JsonDateValueProcessorUtil());
				JSONArray array = JSONArray.fromObject(tcs, jc);
				resultMap.put("status", 200);
				resultMap.put("message", "获取信息成功");
				resultMap.put("result", array);

			} else {
				resultMap.put("status", 200);
				resultMap.put("message", "获取信息成功,您没有参与任务");
			}

		} catch (Exception e) {
			resultMap.put("status", 500);
			resultMap.put("message", "服务发生异常!请稍后重试...");
			return resultMap;
		}
		return resultMap;
	}

	/**
	 * 
	 * 广告列表
	 * 
	 * */

	@SuppressWarnings("unused")
	@RequestMapping(value = "BannerList")
	@ResponseBody
	public Map<String, Object> getBannerList(HttpServletRequest req,
			HttpServletResponse resp) {

		// 先查到所有在线的banner列表
		try {
			List<Banner> bList = bannerList.findAllByOnlie();
			Map<Banner, List<Banner>> map = new TreeMap<Banner, List<Banner>>();
			JsonConfig jc = new JsonConfig();
			jc.registerJsonValueProcessor(Date.class,
					new JsonDateValueProcessorUtil());
			JSONArray array = JSONArray.fromObject(bList, jc);
			resultMap.put("status", 200);
			resultMap.put("message", "获取信息成功");
			resultMap.put("result", array);
		} catch (Exception e) {
			resultMap.put("status", 500);
			resultMap.put("message", "获取信息失败");
		}
		return resultMap;
	}

	/**
	 * 
	 * 快捷入口列表
	 * 
	 * 
	 * */

	@SuppressWarnings("unused")
	@RequestMapping(value = "ShortcutList")
	@ResponseBody
	public Map<String, Object> getShortcutList(HttpServletRequest req,
			HttpServletResponse resp) {

		// 先查到所有在线的任务专区
		try {
			List<Shortcut> sList = shortcutList.findAllByOnlie();
			Map<Shortcut, List<Shortcut>> map = new TreeMap<Shortcut, List<Shortcut>>();
			JsonConfig jc = new JsonConfig();
			jc.registerJsonValueProcessor(Date.class,
					new JsonDateValueProcessorUtil());
			JSONArray array = JSONArray.fromObject(sList, jc);
			resultMap.put("status", 200);
			resultMap.put("message", "获取信息成功");
			resultMap.put("result", array);
		} catch (Exception e) {
			resultMap.put("status", 500);
			resultMap.put("message", "获取信息失败");
		}

		return resultMap;
	}

}
