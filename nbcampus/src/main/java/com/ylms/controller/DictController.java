package com.ylms.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.ylms.common.controller.BaseController;
import com.ylms.common.dao.DictUtil;
import com.ylms.common.entity.Dict;
import com.ylms.common.utils.LoggerUtils;
import com.ylms.core.mybatis.page.Pagination;
import com.ylms.service.impl.IDictDao;

/**
 * 
 * 对字典列表的增删改查的操作
 * 
 * 
 * */
@Controller
@Scope(value = "prototype")
@RequestMapping("dict")
public class DictController extends BaseController {
	// 使用spring获取上下文保存的信息
		private WebApplicationContext webApplicationContext = ContextLoader
				.getCurrentWebApplicationContext();
	@Autowired
	private IDictDao ipd;

	@RequestMapping("index")
	@ResponseBody
	public ModelAndView list(String findContent, ModelMap modelMap,
			Integer pageNo) {
		modelMap.put("findContent", findContent);
		Pagination<Dict> dict = ipd.findByPage(modelMap, pageNo,
				pageSize);
		return new ModelAndView("dict/index", "page", dict);
	}

	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> add(Dict p) {

		if (p == null ||p.getCkey().equals("")||p.getCkey()==null||p.getDictName().equals("")||p.getDictName()==null||p.getCvalue().equals("")||p.getCvalue()==null) {
			resultMap.put("status", 400);
			resultMap.put("message", "参数错误!");
			return resultMap;
		}

		try {
			ipd.add(p);
			DictUtil.rereshDict(webApplicationContext.getServletContext());
			resultMap.put("status", 200);
			resultMap.put("message", "成功添加");
		} catch (Exception e) {
			resultMap.put("status", 500);
			resultMap.put("message", "添加失败，请刷新后再试！");
			LoggerUtils.fmtError(getClass(), e, "添加列表报错。source[%s]",
					p.toString());
		}
		return resultMap;
	}

	/**
	 * 
	 * 为修改列表时使用
	 * 
	 * */
	@RequestMapping("findById")
	@ResponseBody
	public Map<String, Object> findById(Long id) {
		if (id == null) {
			resultMap.put("status", 400);
			resultMap.put("message", "参数错误!");
			return resultMap;
		}
		try {
			Dict p = ipd.findById(id);
			resultMap.put("status", 200);
			resultMap.put("dict", JSONArray.toJSON(p).toString());
			resultMap.put("message", "查到信息!");
		} catch (Exception e) {
			resultMap.put("status", 500);
			resultMap.put("message", "查询出现错误，请刷新后再试！");
		}
		return resultMap;
	}

	@RequestMapping(value="update" ,method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> update(String id, Boolean cupdate, Dict p) {

		if (id == null) {
			resultMap.put("status", 400);
			resultMap.put("message", "参数错误!");
			return resultMap;
		}

		if (cupdate) {
			Dict dict = ipd.findById(new Long(id));
			resultMap.put("status", 200);
			resultMap.put("dict", dict);
			resultMap.put("message", "查到信息!");
		} else {

			if (p.getCvalue() == null || p.getCkey() == null ||p.getDictName()==null) {
				resultMap.put("status", 400);
				resultMap.put("message", "参数错误!");
				return resultMap;
			}

			try {
				ipd.update(p);
				DictUtil.rereshDict(webApplicationContext.getServletContext());
				resultMap.put("status", 200);
				resultMap.put("message", "修改成功!");
			} catch (Exception e) {
				resultMap.put("status", 500);
				resultMap.put("message", "修改失败!");
			}
		}
		return resultMap;
	}
}
