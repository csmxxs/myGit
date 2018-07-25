package com.ylms.common.corsFilter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.ylms.common.utils.InviterUrl;
import com.ylms.common.utils.SpringContextUtil;
import com.ylms.liuliangbao.webLogin.Hclient;
/**
 * 
 * 自定义跨域拦截器
 * 
 * */
public class CorsFilter implements Filter {
//	private Logger log = LoggerFactory.getLogger(Hclient.class);
//	private static final InviterUrl inviterUrl =SpringContextUtil.getBean("inviterUrl",InviterUrl.class);
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
//		HttpServletResponse response = (HttpServletResponse) resp;
//		HttpServletRequest req = (HttpServletRequest) request;
//		//设置响应请求的来源
//		log.info("请求的来源是:*********+++++>>"+req.getHeader("Origin"));
//		response.setHeader("Access-Control-Allow-Origin",
//				req.getHeader("Origin"));
//		//跨域请求的方法
//		response.setHeader("Access-Control-Allow-Methods",
//				"POST, GET, PUT, OPTIONS, DELETE");
//		//
//		response.setHeader("Access-Control-Max-Age", "3600");
//		response.setHeader("Access-Control-Allow-Headers",
//				"x-requested-with, Content-Type");
//		//允许跨域
//		response.setHeader("Access-Control-Allow-Credentials", "true");
//		// 定义移动端请求的所有可能类型
//		String ua = req.getHeader("User-Agent");
//		log.info("请求的设备型号是:*********+++++>>"+ua);
//		if (checkAgentIsMobile(ua)) {
//			// 如果是移动端则重定向到对应首页
//			response.sendRedirect(inviterUrl.webHttpUrl);
//		}
		chain.doFilter(request, resp);

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}
	/**
	 * 判断User-Agent 是不是来自于手机
	 * 
	 * @param ua
	 * @return
	 */
//	public boolean checkAgentIsMobile(String ua) {
//		boolean flag = false;
//		if (!ua.contains("Windows NT")
//				|| (ua.contains("Windows NT") && ua
//						.contains("compatible; MSIE 9.0;"))) {
//			// 排除 苹果桌面系统
//			if (!ua.contains("Windows NT") && !ua.contains("Macintosh")) {
//				for (String item : inviterUrl.getAgent()) {
//					if (ua.contains(item)) {
//						flag = true;
//						break;
//					}
//				}
//			}
//		}
//		return flag;
//	}
}
