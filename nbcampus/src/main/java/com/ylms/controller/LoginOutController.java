package com.ylms.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ylms.common.controller.BaseController;

@Controller
@Scope(value = "prototype")
@RequestMapping("common")
public class LoginOutController extends BaseController {
	@RequestMapping("loginOut")
	@ResponseBody
	public Map<String, Object> LoginOut(HttpServletRequest req,
			HttpServletResponse resp) {
		// 清除会话
		req.getSession().invalidate();
		resultMap.put("status", 200);
		resultMap.put("message", "退出成功!");
		return resultMap;
	}
}
