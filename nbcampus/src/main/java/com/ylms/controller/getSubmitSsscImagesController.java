package com.ylms.controller;

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

import com.ylms.common.controller.BaseController;
import com.ylms.common.entity.CommonStudent2task;
import com.ylms.common.entity.Student;
import com.ylms.common.entity.Student2Task;
import com.ylms.common.entity.User;
import com.ylms.common.utils.ImagesUtil;
import com.ylms.common.utils.IntegerUtlis;
import com.ylms.common.utils.vcode.CommonImagesUrl;
import com.ylms.service.impl.ICommonStudent2TaskDao;
import com.ylms.service.impl.IRedisServiceImplDao;
import com.ylms.service.impl.IStudentDao;
import com.ylms.service.impl.ITaskcollectDao;

/**
 * 
 * 提交任务完成时的任务截图的公共接口
 * 
 * */
@Controller
@Scope(value = "prototype")
@RequestMapping("submit")
public class getSubmitSsscImagesController extends BaseController {

	@Autowired
	private CommonImagesUrl imagesURL;
	@Autowired
	private IRedisServiceImplDao rs;
	@Autowired
	private IStudentDao sd;
	@Autowired
	private ICommonStudent2TaskDao commonsssc;
	@Autowired
	private ITaskcollectDao collect;
	@Autowired
	private ICommonStudent2TaskDao common;

	/**
	 * 
	 * 
	 * 图片进行64位解码保存
	 * 
	 * @param imagesUrl
	 *            图片的本地地址
	 * @param string64encode
	 *            64位编码图片
	 * @param taskId
	 *            任务数据库Id
	 * @param prefectureId
	 *            任务专区数据库Id
	 */
	@RequestMapping(value = "submitSsscImages", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> submitSsscImages(HttpServletRequest req,
			HttpServletResponse resp, String prefectureId, String taskId,
			String string64encode) {
		
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");
		if (user == null) {
			resultMap.put("status", 100);
			resultMap.put("message", "该用户没有登录，请先登录!");
			return resultMap;
		}
		if (prefectureId == null || taskId == null || prefectureId.equals("")
				|| string64encode == null
				|| !IntegerUtlis.RegexString(prefectureId)
				|| prefectureId.length() > 20 || taskId.length() > 20
				|| taskId.equals("") || !IntegerUtlis.RegexString(taskId)
				|| string64encode.equals("")) {
			resultMap.put("status", 300);
			resultMap.put("message", "参数不正确!");
			return resultMap;

		}
		Student s = sd.findByTel(user.getMobile());
		if (s != null) {
			if (s.getMyCheck() != 1) {
				// 未经过审核
				resultMap.put("status", 400);
				resultMap.put("message", "审核未通过!");
				return resultMap;
			}
		}
		// 先查询
		//resultMap.put("prefectureId", prefectureId);
		resultMap.put("taskId", taskId);
		resultMap.put("studentTel", user.getMobile());
		CommonStudent2task c = common
				.findByPrefectureIdTaskIdStudentTel(resultMap);
		// 公共列表没有
		if (c == null) {
			resultMap.put("status", 600);
			resultMap.put("message", "您没有参与该任务,无法上传");
			return resultMap;
		}
		Boolean flag = false;
		if (c.getSsscImagesBoolean() == 2) {
			resultMap.put("status", 550);
			resultMap.put("message", "图片已经上传过了!");
			return resultMap;
		} else if (c.getCommonCollect() == 0) {
			// 已收藏
			flag = true;
		}
		// 5.得到用户任务列表的截图地址
		String imagesUrl = ImagesUtil.getSsscImageName(
				imagesURL.getLogoImagesHttp(), string64encode, user.getMobile(), taskId);
		if (imagesUrl != null) {
			// 调用存储过程
			rs.setSsscImages(prefectureId, taskId);
			// 修改用户公共任务列表的截图是否已上传字段为2
			c.setSsscImagesBoolean(2);
			c.setTaskSsscImagesUrl(imagesUrl);
			common.update(c);
			if (flag) {
				Student2Task stu = collect
						.findByPrefectureIdTaskIdStudentTel(resultMap);
				// 修改用户收藏任务列表的截图是否已上传字段为2
				stu.setSsscImagesBoolean(2);
				collect.update(stu);
			}
			resultMap.put("status", 200);
			resultMap.put("message", "图片上传成功!");
		} else {
			resultMap.put("status", 500);
			resultMap.put("message", "图片上传失败!");
		}
		return resultMap;
	}
}
