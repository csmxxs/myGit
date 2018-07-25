package com.ylms.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;
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
import com.ylms.common.entity.Student;
import com.ylms.common.entity.User;
import com.ylms.common.utils.CookieUtil;
import com.ylms.common.utils.InviterUrl;
import com.ylms.common.utils.JsonDateValueProcessorUtil;
import com.ylms.service.impl.IStudentDao;
import com.ylms.service.impl.IStudentInviterDao;

/**
 * 获取到被邀请人，生成邀请链接
 * 
 * */
@Controller
@Scope(value = "prototype")
@RequestMapping("submit")
public class getStudentInviterUrlController extends BaseController {
	@Autowired
	private IStudentInviterDao sd;
	@Autowired
	private IStudentDao isd;
	@Autowired
	private InviterUrl inviterUrl;

	/**
	 * 
	 * 获取被邀请人信息
	 * 
	 * */
	@RequestMapping("getStudentInviter")
	@ResponseBody
	public Map<String, Object> getStudentInviter(HttpServletRequest req,
			HttpServletResponse resp) {
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");
		if (user == null) {
			resultMap.put("status", 100);
			resultMap.put("message", "该用户没有登录，请先登录!");
			return resultMap;
		}
		// 根据电话号码查出被邀请人
		List<Student> invice = isd.findStudentInviter(user.getMobile());
		if (invice.size() > 0) {
			resultMap.put("status", 200);
			resultMap.put("message", "获取信息成功!");
			JsonConfig jc = new JsonConfig();
			jc.registerJsonValueProcessor(Date.class,
					new JsonDateValueProcessorUtil());
			JSONArray array = JSONArray.fromObject(invice, jc);
			resultMap.put("result", array);
		} else {
			resultMap.put("status", 200);
			resultMap.put("message", "获取信息成功!");
			resultMap.put("result", invice);
		}
		return resultMap;
	}

	/**
	 * 
	 * 获取邀请链接 当用户邀请其他人时，需主动请求该接口 接口生成连接后返回给前端 前端下次需提交该url信息给后台去做业务逻辑
	 * */

	@RequestMapping("getStudentInviterUrl")
	@ResponseBody
	public Map<String, Object> getStudentInviterUrl(HttpServletRequest req,
			HttpServletResponse resp) {
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");
		if (user == null) {
			resultMap.put("status", 100);
			resultMap.put("message", "该用户没有登录，请先登录!");
			return resultMap;
		}
		// 根据电话号码查出邀请人
		Student s = isd.findByTel(user.getMobile());
		if (s != null) {
			if (s.getMyCheck() != 1) {
				resultMap.put("status", 400);
				resultMap.put("message", "待审核通过后,再进行邀请!");
			} else {
				// 生成邀请链接
				String inviceUrl = inviterUrl.getHttpUrl()
						+ CookieUtil.encodeCookie(user.getMobile());
				// 返回邀请人连接信息
				resultMap.put("status", 200);
				resultMap.put("message", "获取邀请链接地址成功!");
				resultMap.put("result", inviceUrl);
			}
		} else {
			resultMap.put("status", 404);
			resultMap.put("message", "该用户未注册!");
		}
		return resultMap;
	}
}