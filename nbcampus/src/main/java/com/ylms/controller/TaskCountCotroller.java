package com.ylms.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.ylms.common.controller.BaseController;
import com.ylms.common.entity.Prefecture;
import com.ylms.common.entity.Task;
import com.ylms.core.mybatis.page.Pagination;
import com.ylms.service.impl.IPrefectureDao;
import com.ylms.service.impl.ITaskCountDao;
import com.ylms.service.impl.ITaskDao;

@Controller
@Scope(value = "prototype")
@RequestMapping("taskTesultCount")
public class TaskCountCotroller extends BaseController {
	@Autowired
	private ITaskCountDao td;
	@Autowired
	private IPrefectureDao ipd;
	@Autowired
	private ITaskDao task;

	@RequestMapping("index")
	@ResponseBody
	public ModelAndView list(String findContent, ModelMap modelMap,
			Integer pageNo) {
		modelMap.put("findContent", findContent);
		Pagination<Prefecture> prefecture = ipd.findByPage(modelMap, pageNo,
				pageSize);
		if (prefecture != null) {
			for (Prefecture p : prefecture.getList()) {
				// 调用存储过程
				Map<String, Object> li = td.prefectureCount(p.getId());
				for (Map.Entry<String, Object> e : li.entrySet()) {
					if (e.getKey().equals("p2")) {
						if (e.getValue() != null) {
							p.setSsscNumber((Long) e.getValue());
						} else {
							p.setSsscNumber(0L);
						}
					}
					if (e.getKey().equals("p3")) {
						if (e.getValue() != null) {
							p.setComplete((Long) e.getValue());
						} else {
							p.setComplete(0L);
						}
					}
					if (e.getKey().equals("p4")) {
						if (e.getValue() != null) {
							p.setNoComplete((Long) e.getValue());
						} else {
							p.setNoComplete(0L);
						}
					}
				}
			}
		}
		return new ModelAndView("taskResultCount/index", "page", prefecture);
	}

	@RequestMapping("slist")
	@ResponseBody
	public ModelAndView getSlist(String id, String findContent,
			ModelMap modelMap, Integer pageNo) {
		if (findContent != null) {
			findContent = findContent.trim();
		}
		modelMap.put("findContent", findContent);
		if (id == null || id.equals("")) {
			return new ModelAndView("common/404", "message", "参数出错，或页面找到不到了...");
		}
		try {
			if (id.lastIndexOf(",") != -1) {
				id = id.substring(0, id.length() - 1);
			}
			modelMap.put("prefectureId", id);
			Pagination<Task> tk = task.listTaskCount("listTaskCount", modelMap,
					pageNo, pageSize);
			return new ModelAndView("taskResultCount/slist", "page", tk);
		} catch (Exception e) {
			return new ModelAndView("common/500", "message", "服务出错了....");

		}
	}

}
