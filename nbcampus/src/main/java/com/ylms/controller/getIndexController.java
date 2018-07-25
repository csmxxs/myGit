package com.ylms.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ylms.common.controller.BaseController;
import com.ylms.common.utils.InviterUrl;


/**
 * 首页跳转
 * 
 * */
@Controller
@Scope(value="prototype")
@RequestMapping("common")
public class getIndexController extends BaseController{
	 
	@Autowired
	private InviterUrl inviterUrl;
	 /**
	  * 返回web前端首页
	 * @throws IOException 
	  * 
	  * */
	 @RequestMapping("getIndex")
	 @ResponseBody
     public void getIndexHtml(HttpServletRequest req,HttpServletResponse resp) throws IOException{
		    //获取访问参数
		   String tel=req.getParameter("params");
		   resp.sendRedirect(inviterUrl.getWebHttpUrl()+tel);
     }
}
