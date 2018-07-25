package com.ylms.liuliangbao.webLogin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.ylms.common.controller.BaseController;
import com.ylms.common.entity.Student;
import com.ylms.common.entity.StudentInviter;
import com.ylms.common.entity.User;
import com.ylms.common.utils.CookieUtil;
import com.ylms.common.utils.IntegerUtlis;
import com.ylms.common.webTask.DefaultTask;
import com.ylms.liuliangbao.config.Common;
import com.ylms.liuliangbao.config.SmsCode;
import com.ylms.liuliangbao.config.VerifySmsCode;
import com.ylms.liuliangbao.utlis.HttpUtil;
import com.ylms.service.impl.IStudentDao;
import com.ylms.service.impl.IStudentInviterDao;

@Controller
@RequestMapping("liuliangbao")
@Scope(value = "prototype")
public class Hclient extends BaseController {
	private static final String smsCodeHttpUrl = "https://nb.189.cn/open/auth/getSmsCode.do";
	private static final String verifySmsCodeHttpUrl = "http://nb.189.cn/open/auth/verifySmsCode.do";
	@Autowired
	private IStudentDao sd;
	@Autowired
	private IStudentInviterDao ssd;
	@Autowired
	private DefaultTask defaultTask;

	// private Logger log = LoggerFactory.getLogger(Hclient.class);
	/**
	 * 获取手机验证码
	 * 
	 * */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "getSmsCode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getSmsCode(HttpServletRequest req,
			HttpServletResponse resp, String mobile) {

		if (mobile == null || !IntegerUtlis.RegexString(mobile)
				|| mobile.length() > 11) {
			resultMap.put("status", 100);
			resultMap.put("message", "参数有误!");
			return resultMap;
		}

		Map<String, String> params = new HashMap<String, String>();
		// 开始构建请求参数
		String param = SmsCode.getParams(mobile);
		String sign = SmsCode.getSign(param);
		params.put("timeStamp", "" + SmsCode.timeStamp);
		params.put("appId", Common.APP_ID);
		params.put("version", Common.version);
		params.put("params", param);
		params.put("sign", sign);
		String msg = HttpUtil.doGet(smsCodeHttpUrl, params);
		if (msg != null) {
			// 转换成map格式
			Map<String, Object> map = JSON.parseObject(msg, Map.class);
			if (map.containsKey("result")) {
				for (Map.Entry<String, Object> s : map.entrySet()) {
					if (s.getKey().equals("result")) {
						if ((Integer) s.getValue() == 221310000) {
							resultMap.put("status", 221310000);
							resultMap.put("message", "获取验证码成功");
						} else {
							resultMap.put("status", (Integer) s.getValue());
							resultMap.put("message", "获取验证码失败");
						}
					}
				}
			}
		} else {
			resultMap.put("status", 500);
			resultMap.put("message", "服务器发生错误");
		}

		return resultMap;

	}

	/**
	 * @param mobile
	 *            电话号码
	 * @param smsCode
	 *            短信验证码
	 * @param inviterTel
	 *            加密过的邀请人电话号码 提交手机号和验证码，验证用户信息 前端需对参数提前加密,防止信息泄露(未实行)
	 * 
	 * */
	@RequestMapping("getVerifySmsCode")
	@ResponseBody
	public Map<String, Object> getVerifySmsCode(HttpServletRequest req,
			HttpServletResponse resp, String mobile, String smsCode,
			String inviterTel) {

		if (mobile == null || smsCode == null || mobile.length() > 11
				|| !IntegerUtlis.RegexString(mobile) || smsCode.equals("")
				|| mobile.equals("") || !IntegerUtlis.RegexString(smsCode)) {
			resultMap.put("status", 100);
			resultMap.put("message", "参数有误!");
			return resultMap;
		}

		Map<String, String> params = new HashMap<String, String>();
		String param = VerifySmsCode.getParams(mobile, smsCode);
		String sign = VerifySmsCode.getSign(param);
		params.put("timeStamp", "" + VerifySmsCode.timeStamp);
		params.put("appId", Common.APP_ID);
		params.put("version", Common.version);
		params.put("params", param);
		params.put("sign", sign);
		// 开始构建请求参数
		String msg = HttpUtil.doGet(verifySmsCodeHttpUrl, params);
		if (msg != null) {
			@SuppressWarnings("unchecked")
			Map<String, Object> map = JSON.parseObject(msg, Map.class);
			String accessToken = "";
			for (Map.Entry<String, Object> m : map.entrySet()) {
				if (m.getKey().equals("accessToken")) {
					accessToken = (String) m.getValue();
					break;
				}
			}
			if (map.containsKey("result")) {
				for (Map.Entry<String, Object> m : map.entrySet()) {
					if (m.getKey().equals("result")) {
						if ((Integer) m.getValue() == 221310000) {
							resultMap.put("status", m.getValue());
							resultMap.put("message", "短信验证成功");
							// 先查询该号码是否进行了学生身份审核
							Student ss = sd.findByTel(mobile);
							if (ss == null) {
								// 给他注册一个默认的账号
								Student student = new Student();
								student.setTel(mobile);
								if (sd.addTel(student) < 1) {
									resultMap.put("status", 500);
									resultMap.put("message", "服务发生错误");
									return resultMap;
								}

								// 如果有邀请人电话号码参数，查询该用户的邀请人是谁
								if (inviterTel != null
										&& !inviterTel.equals("")) {
									// 解密手机号
									String tel = CookieUtil
											.decodeCookie(inviterTel);
									if (tel != null) {
										if (tel.length() == 11) {
											// 查询邀请人信息
											Student st = sd.findByTel(tel);
											// 则添加新用户的邀请人,并把当前邀请人级别设置为一级邀请人
											ssd.addStudentInviter(new StudentInviter(
													mobile, st.getName(), 1, st
															.getTel()));
											// 查询新用户的一级邀请人有没有他的一级邀请人，若果有则把他的一级邀请人设置成新用户的二级邀请人
											List<StudentInviter> studentInviter = ssd
													.listById(st.getTel());
											if (studentInviter.size() > 0) {
												for (StudentInviter studentInviter2 : studentInviter) {
													if (studentInviter2
															.getInviteLevel() == 1) {
														// 添加该一级邀请人到新用户的二级邀请人当中
														ssd.addStudentInviter(new StudentInviter(
																mobile,
																studentInviter2
																		.getInviteName(),
																2,
																studentInviter2
																		.getInviteTel()));
													}
												}
											}
										}
									}
								}
							}
							// 不存在会话则返回null
							// HttpSession session=req.getSession(false);
							// 不存在会话则创建 相当于req.getSession(true);
							HttpSession session = req.getSession();
							// 存放会话
							User user = new User();
							user.setToken(accessToken);
							user.setMobile(mobile);
							session.setAttribute("user", user);
							session.setMaxInactiveInterval(defaultTask.getWebSessionOutTime());
						} else {
							resultMap.put("status", 400);
							resultMap.put("message", "短信验证失败!");
						}
					}
				}
			} else {
				resultMap.put("status", 500);
				resultMap.put("message", "服务发生错误");
			}
		} else {

			resultMap.put("status", 500);
			resultMap.put("message", "服务发生错误");

		}
		return resultMap;
	}
}