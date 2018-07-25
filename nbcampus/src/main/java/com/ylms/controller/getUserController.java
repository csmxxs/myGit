package com.ylms.controller;

import java.util.Date;
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
import com.ylms.common.utils.JsonDateValueProcessorUtil;
import com.ylms.liuliangbao.config.UserCoinInfo;
import com.ylms.service.impl.IStudentDao;
import com.ylms.service.impl.ITaskDao;

/***
 * 
 * 
 * 获取学生信息
 * 
 * 
 * */
@Controller
@RequestMapping("common")
@Scope(value = "prototype")
public class getUserController extends BaseController {
	@Autowired
	private IStudentDao sd;
	@Autowired
	private ITaskDao itaskdao;

	@RequestMapping(value = "getUser")
	@ResponseBody
	public Map<String, Object> getUser(HttpServletRequest req,
			HttpServletResponse resp) {

		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");
		if (user == null) {
			resultMap.put("status", 100);
			resultMap.put("message", "该用户没有登录，请先登录!");
			return resultMap;
		}
		Student s = sd.findByTel(user.getMobile());
		Integer nbNumber = 0;
		if (!user.getToken().equals("")) {
			// 拉取用户的流量宝基础数据
			nbNumber = UserCoinInfo.getUserCoinInfo(user.getMobile(),
					user.getToken());
		}
		if (s != null) {
			s.setNbNumber(nbNumber);
			JsonConfig jc = new JsonConfig();
			jc.registerJsonValueProcessor(Date.class,
					new JsonDateValueProcessorUtil());
			JSONArray array = JSONArray.fromObject(s, jc);
			resultMap.put("status", 200);
			resultMap.put("message", "信息获取成功");
			resultMap.put("result", array);

		} else {
			resultMap.put("status", 500);
			resultMap.put("message", "账号不存在!");
		}
		return resultMap;
	}
}
