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
import com.ylms.common.entity.CommonStudent2task;
import com.ylms.core.mybatis.page.Pagination;
import com.ylms.service.impl.ICommonStudent2TaskDao;

/**
 * 
 * 牛币派发
 * 
 * **/
@Controller
@Scope(value = "prototype")
@RequestMapping("grantCoin")
public class GrantCoinController extends BaseController {
	@Autowired
	private ICommonStudent2TaskDao common;
    /**
     * 
     * 牛币派发查询
     * 
     * */
	@RequestMapping("index")
	@ResponseBody
	public ModelAndView list(String findContent, ModelMap modelMap,
			Integer pageNo) {
		modelMap.put("findContent", findContent);
		Pagination<CommonStudent2task> commonstudent2task = common.findByPage(
				modelMap, pageNo, pageSize);
		return new ModelAndView("grantCoin/index", "page",
				commonstudent2task);
	}
    /**
     * 
     * 
     * 牛币派发操作
     * 
     * */
	@RequestMapping("update")
	@ResponseBody
	public Map<String, Object> update(String ids) {

		if (ids.equals("") || ids == null) {
			resultMap.put("status", 400);
			resultMap.put("message", "参数错误");
			return resultMap;
		}
		try {
			int i = common.update(ids);
			resultMap.put("status", 200);
			resultMap.put("message", "成功派发" + i + "条记录");
		} catch (Exception e) {
			resultMap.put("status", 500);
			resultMap.put("message", "服务发生错误");
		}

		return resultMap;
	}

}
