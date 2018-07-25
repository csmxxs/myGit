package com.ylms.controller;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.ylms.common.controller.BaseController;

/**
 * 
 * 获取省份信息
 * 
 * */
@Controller
@Scope(value = "prototype")
@RequestMapping("common")
public class getProvinceAndDictAndSchoolDictController extends BaseController {
	// 使用spring获取上下文保存的信息
	private WebApplicationContext webApplicationContext = ContextLoader
			.getCurrentWebApplicationContext();

	@RequestMapping("getProvince")
	@ResponseBody
	public Map<String, Object> getList(HttpServletRequest req,
			HttpServletResponse resp) {

		// 解析客户端头信息
	//	resp.setHeader("Access-Control-Allow-Credentials", "true");
		// 允许访问源的跨域
	//	resp.setHeader("Access-Control-Allow-Origin", req.getHeader("Origin"));

		try {
			resultMap.clear();
			ServletContext servletContext = webApplicationContext
					.getServletContext();
			resultMap.put("status", 200);
			resultMap.put("result", servletContext.getAttribute("provinceMap"));
			resultMap.put("message", "信息获取成功");
		} catch (Exception e) {
			resultMap.put("status", 500);
			resultMap.put("message", "信息获取失败");
		}
		return resultMap;
	}

	@RequestMapping("getDict")
	@ResponseBody
	public Map<String, Object> getDictList(HttpServletRequest req,
			HttpServletResponse resp) {

		try {
			resultMap.clear();
			ServletContext servletContext = webApplicationContext
					.getServletContext();
			resultMap.put("status", 200);
			resultMap.put("result", servletContext.getAttribute("dict"));
			resultMap.put("message", "信息获取成功");
		} catch (Exception e) {
			resultMap.put("status", 500);
			resultMap.put("message", "信息获取失败");
		}
		return resultMap;
	}

	@RequestMapping("getSchoolDict")
	@ResponseBody
	public Map<String, Object> getSchoolDictList(HttpServletRequest req,
			HttpServletResponse resp) {

		// 解析客户端头信息
	//	resp.setHeader("Access-Control-Allow-Credentials", "true");
		// 允许访问源的跨域
	//	resp.setHeader("Access-Control-Allow-Origin", req.getHeader("Origin"));

		try {
			resultMap.clear();
			ServletContext servletContext = webApplicationContext
					.getServletContext();
			resultMap.put("status", 200);
			resultMap.put("result", servletContext.getAttribute("schoolDict"));
			resultMap.put("message", "信息获取成功");
		} catch (Exception e) {
			resultMap.put("status", 500);
			resultMap.put("message", "信息获取失败");
		}
		return resultMap;
	}

	@RequestMapping("getPrefectureDict")
	@ResponseBody
	public Map<String, Object> getPrefectureDict(HttpServletRequest req) {
		try {
			resultMap.clear();
			ServletContext servletContext = webApplicationContext
					.getServletContext();
			resultMap.put("status", 200);
			resultMap.put("result",
					servletContext.getAttribute("prefectureMap"));
			resultMap.put("message", "信息获取成功");
		} catch (Exception e) {
			resultMap.put("status", 500);
			resultMap.put("message", "信息获取失败");
		}
		return resultMap;
	}
}
