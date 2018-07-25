package com.ylms.core.shiro.filter;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.web.filter.AccessControlFilter;
import com.ylms.common.entity.UUser;
import com.ylms.common.utils.LoggerUtils;
import com.ylms.core.shiro.token.manager.TokenManager;

/**
 * 
 * 
 * 判断登录
 * 
 * <p>
 * 
 * 区分　责任人　日期　　　　说明<br/>
 * 创建　谢雄世　2018年4月2日 　<br/>
 * 
 * @author xie-xiongshi
 * @email so@ylms.com
 * @version 1.0,2018年4月2日 <br/>
 * 
 */
public class LoginFilter extends AccessControlFilter {
	final static Class<LoginFilter> CLASS = LoginFilter.class;
	@Override
	protected boolean isAccessAllowed(ServletRequest request,
			ServletResponse response, Object mappedValue) throws Exception {

		UUser token = TokenManager.getToken();

		if (null != token || isLoginRequest(request, response)) {// &&
																	// isEnabled()
			return Boolean.TRUE;
		}
		if (ShiroFilterUtils.isAjax(request)) {// ajax请求
			Map<String, String> resultMap = new HashMap<String, String>();
			LoggerUtils.debug(getClass(), "当前用户没有登录，并且是Ajax请求！");
			resultMap.put("login_status", "300");
			resultMap.put("message","当前用户没有登录！");
			ShiroFilterUtils.out(response, resultMap);
		}
		return Boolean.FALSE;

	}

	@Override
	protected boolean onAccessDenied(ServletRequest request,
			ServletResponse response) throws Exception {
		// 保存Request和Response 到登录后的链接
		saveRequestAndRedirectToLogin(request, response);
		return Boolean.FALSE;
	}

}
