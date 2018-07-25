package com.ylms.common.dao;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 系统启动初始化类
 * @author zhql
 */
//用户注解配置servlet启动时自动加载，必须加value和loadOnStartup属性
//loadOnStartup启动顺序1,2,3数字起小就优先启动
@WebServlet(value="/InitServlet",loadOnStartup=1)
public class InitServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
    private Logger log = LoggerFactory.getLogger(InitServlet.class);
	@Override
	public void init(ServletConfig config) throws ServletException {
		log.info("****************************系统数据开始初始化***************************");
		//应用上下文ServletContext,在Jsp就是application
		ServletContext sc = config.getServletContext();
		//上下文路径，就是根路径
		String rootPath = sc.getContextPath();
		sc.setAttribute("_cxt", rootPath);
		//数据字典
		DictUtil.rereshDict(sc);
		//省份字典
		DictUtil.reProvince(sc);
		//学校字典
		DictUtil.reSchool(sc);
		//初始化任务专区字典
		DictUtil.rePrefecture(sc);
	}
}
