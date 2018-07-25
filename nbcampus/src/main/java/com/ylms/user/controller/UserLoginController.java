package com.ylms.user.controller;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.ylms.common.controller.BaseController;
import com.ylms.common.entity.UUser;
import com.ylms.common.utils.LoggerUtils;
import com.ylms.common.utils.StringUtils;
import com.ylms.common.utils.VerifyCodeUtils;
import com.ylms.core.shiro.token.manager.TokenManager;
import com.ylms.user.manager.UserManager;
import com.ylms.user.service.IUUserDao;


/**
 * Title:UserLoginController.java <br>
 * Description: 用户登录&注册服务,且服务是开放的<br>
 * Company:www.ylms.com <br>
 * @author XieXiongShi
 * @date 2018年7月9日 下午4:36:59
 * @version V 1.0 
 */
@Controller
@Scope(value = "prototype")
@RequestMapping("u")
public class UserLoginController extends BaseController {
	@Resource
	IUUserDao userService;

	/**
	 * 
	 * @MethodName: login
	 * @Description: 跳转登录页
	 * @param: @param req
	 * @param: @param resp
	 * @param: @return
	 * @param: @throws Exception   
	 * @return: ModelAndView   
	 * @throws
	 */
	@RequestMapping(value = "login", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView login(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		return new ModelAndView("user/login");
	}

	/**
	 * 
	 * @MethodName: register
	 * @Description: 跳转注册页
	 * @param: @return   
	 * @return: ModelAndView   
	 * @throws
	 */
	@RequestMapping(value = "register", method = RequestMethod.GET)
	public ModelAndView register() {

		return new ModelAndView("user/register");
	}

	/**
	 * 
	 * @MethodName: subRegister
	 * @Description: 登录 & 注册
	 * @param: @param 验证码
	 * @param: @param 一个登录用户实例
	 * @param: @return
	 * @return: Map<String,Object>
	 * @throws
	 */
	@RequestMapping(value = "subRegister", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> subRegister(String vcode, UUser entity) {
		resultMap.put("status", 400);
		if (!VerifyCodeUtils.verifyCode(vcode)) {
			resultMap.put("message", "验证码不正确！");
			return resultMap;
		}
		String email = entity.getEmail();

		UUser user = userService.findUserByEmail(email);
		if (null != user) {
			resultMap.put("message", "帐号|Email已经存在！");
			return resultMap;
		}
		Date date = new Date();
		entity.setCreateTime(date);
		entity.setLastLoginTime(date);
		// 把密码md5
		entity = UserManager.md5Pswd(entity);
		// 设置有效
		entity.setStatus(UUser._1);

		entity = userService.insert(entity);
		LoggerUtils.fmtDebug(getClass(), "注册插入完毕！",
				JSONObject.fromObject(entity).toString());
		entity = TokenManager.login(entity, Boolean.TRUE);
		LoggerUtils.fmtDebug(getClass(), "注册后，登录完毕！",
				JSONObject.fromObject(entity).toString());
		resultMap.put("message", "注册成功！");
		resultMap.put("status", 200);
		return resultMap;
	}

	/**
	 * 登录提交
	 * 
	 * @param entity
	 *            登录的UUser
	 * @param rememberMe
	 *            是否记住
	 * @param request
	 *            request，用来取登录之前Url地址，用来登录后跳转到没有登录之前的页面。
	 * @return
	 */
	@RequestMapping(value = "submitLogin", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> submitLogin(UUser entity, Boolean rememberMe,
			HttpServletRequest request) {

		try {
			entity = TokenManager.login(entity, rememberMe);
			resultMap.put("status", 200);
			resultMap.put("message", "登录成功");

			/**
			 * shiro 获取登录之前的地址 之前0.1版本这个没判断空。
			 */
			SavedRequest savedRequest = WebUtils.getSavedRequest(request);
			String url = null;
			if (null != savedRequest) {
				url = savedRequest.getRequestUrl();
			}
			/**
			 * 我们平常用的获取上一个请求的方式，在Session不一致的情况下是获取不到的 String url = (String)
			 * request.getAttribute(WebUtils.FORWARD_REQUEST_URI_ATTRIBUTE);
			 */
			LoggerUtils.fmtDebug(getClass(), "获取登录之前的URL:[%s]", url);
			// 如果登录之前没有地址，那么就跳转到首页。
			if (StringUtils.isBlank(url)) {
				url = request.getContextPath() + "/user/index.shtml";
			}
			// 跳转地址
			resultMap.put("back_url", url);
			/**
			 * 这里其实可以直接catch Exception，然后抛出 message即可，但是最好还是各种明细catch 好点。。
			 */
		} catch (DisabledAccountException e) {
			resultMap.put("status", 500);
			resultMap.put("message", "帐号已经禁用。");
		} catch (Exception e) {
			resultMap.put("status", 500);
			resultMap.put("message", "帐号或密码错误");
		}

		return resultMap;
	}

	/**
	 * 
	 *
	 * @Description: 退出系统
	 * @param: @return   
	 * @return: Map<String,Object>   
	 * @throws
	 */
	@RequestMapping(value = "logout", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> logout() {
		try {
			TokenManager.logout();
			resultMap.put("status", 200);
		} catch (Exception e) {
			resultMap.put("status", 500);
			logger.error("errorMessage:" + e.getMessage());
			LoggerUtils.fmtError(getClass(), e, "退出出现错误，%s。", e.getMessage());
		}
		return resultMap;
	}

}
