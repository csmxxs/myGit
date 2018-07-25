package com.ylms.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ylms.common.controller.BaseController;
import com.ylms.common.entity.Student;
import com.ylms.common.entity.User;
import com.ylms.common.utils.DataToRedis;
import com.ylms.common.utils.DateUtil;
import com.ylms.common.utils.ImagesUtil;
import com.ylms.common.utils.LoggerUtils;
import com.ylms.common.utils.vcode.CommonImagesUrl;
import com.ylms.mysqlcall.RedisDataUtils;
import com.ylms.service.impl.IStudentDao;

/**
 * 
 * 
 * 学生信息提交的公共接口
 * */
@Controller
@Scope(value = "prototype")
@RequestMapping("common")
public class SubmitStudentMessageController extends BaseController {
	@Autowired
	private IStudentDao sd;
	@Autowired
	private CommonImagesUrl imagesURL;
	@Autowired
	private RedisAtomicLong redisAtomicLong;
	@Autowired
	private DataToRedis dataToRedis;

	@InitBinder
	// 日期自动转换器
	public void initBinder(ServletRequestDataBinder bin) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		CustomDateEditor cust = new CustomDateEditor(sdf, true);
		bin.registerCustomEditor(Date.class, cust);
	}

	/**
	 * 
	 * 学生信息资料审核
	 * 
	 * */
	@RequestMapping(value = "addStudentMessage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> add(HttpServletResponse resp,
			HttpServletRequest req, Student student) {

		HttpSession session = req.getSession();
		// 获取会话，取出手机号
		User user = (User) session.getAttribute("user");
		if (user == null) {
			resultMap.put("status", 100);
			resultMap.put("message", "该用户没有登录，请先登录!");
			return resultMap;
		}
		if (student == null) {
			resultMap.put("status", 100);
			resultMap.put("message", "参数错误!");
			return resultMap;
		}
		if (student.getIdentity().length() > 18
				|| student.getIdentity().equals("")
				|| student.getIdentity() == null
				|| student.getImages().equals("")
				|| student.getImages() == null || student.getName() == null
				|| student.getName().equals("")
				|| student.getProfession().equals("")
				|| student.getProfession() == null
				|| student.getSchool().equals("")
				|| student.getSchool() == null
				|| student.getShcoolProvinceId() == null
				|| student.getStudentIdCard().equals("")
				|| student.getStudentIdCard() == null
				|| student.getTimeofEnrollment() == null
				|| student.getTimeofEnrollment().equals("")
				|| student.getTel().equals("") || student.getTel() == null
				|| student.getTel().length() > 11) {
			resultMap.put("status", 100);
			resultMap.put("message", "参数错误!");
			return resultMap;
		}
		// 如果cookie中的号码与要注册的电话号码不正确则停止，防止恶意注册
		if (!student.getTel().equals(user.getMobile())) {
			resultMap.put("status", 500);
			resultMap.put("message", "提交的号码不正确!");
			return resultMap;
		}
		try {
			Student stu = sd.findByTel(student.getTel());
			if (stu != null) {
				if (stu.getMyCheck() == 1) {
					resultMap.put("status", 200);
					resultMap.put("message", "该号码已审核ok,提交失败!");
					return resultMap;
				}
				if (stu.getMyCheck() == 0) {
					resultMap.put("status", 500);
					resultMap.put("message", "信息审核中,提交失败!");
					return resultMap;
				}

				if (stu.getMyCheck() == 3) {
					// 直接提交
					student.setId(stu.getId());
					student.setCheckNumber(Integer.valueOf(stu.getCheckNumber() + 1));
					student.setMyCheck(Integer.valueOf(0));
					if (student.getImages() != null && !student.equals("")) {
						String url = ImagesUtil.getImageStr(
								imagesURL.getLogoImagesHttp(),
								student.getImages());
						if (url != null && !url.equals("")) {
							student.setImages(url);
						} else {
							resultMap.put("status", 400);
							resultMap.put("message", "图片上传失败,请刷新后再试！");
							return resultMap;
						}

					}
					sd.update(student);
					resultMap.put("status", 200);
					resultMap.put("message", "提交成功!");
					return resultMap;

				}

				// 当审核数小于等于3次
				if (stu.getCheckNumber() < 3) {
					// 保存老数据到缓存
					RedisDataUtils.setData(
							RedisDataUtils.getRedisIdKey(redisAtomicLong), stu,
							dataToRedis.dbImages);
					// 同一个月提交时
					if (DateUtil.sameDate(new Date(), stu.getCreateTime())) {
						// 把审核数加上1,myCheck值改为0,不通过原因清空
						student.setId(stu.getId());
						student.setCauseReason(null);
						student.setCheckNumber(Integer.valueOf(stu
								.getCheckNumber() + 1));
						student.setMyCheck(Integer.valueOf(0));
						if (student.getImages() != null && !student.equals("")) {
							String url = ImagesUtil.getImageStr(
									imagesURL.getLogoImagesHttp(),
									student.getImages());
							if (url != null && !url.equals("")) {
								student.setImages(url);
							} else {
								resultMap.put("status", 400);
								resultMap.put("message", "图片上传失败,请刷新后再试！");
								return resultMap;
							}

						}
					} else {

						// 不是同一个月提交,重置审核数为设为1，审核状态为零,不通过原因
						student.setId(stu.getId());
						student.setCauseReason(null);
						student.setCheckNumber(1);
						student.setMyCheck(Integer.valueOf(0));
						if (student.getImages() != null && !student.equals("")) {
							String url = ImagesUtil.getImageStr(
									imagesURL.getLogoImagesHttp(),
									student.getImages());
							if (url != null && !url.equals("")) {
								student.setImages(url);
							} else {
								resultMap.put("status", 400);
								resultMap.put("message", "图片上传失败,请刷新后再试！");
								return resultMap;
							}

						}

					}

					sd.update(student);
					resultMap.put("status", 200);
					resultMap.put("message", "提交成功!");
					deleteOldData(stu);

				}

				// 当月审核数大于等于3次
				if (stu.getCheckNumber() >= 3) {

					// 在同年同月
					if (DateUtil.sameDate(new Date(), stu.getCreateTime())) {
						resultMap.put("status", 300);
						resultMap.put("message", "超过当月最大审核数,提交失败!");
					} else {
						// 不是同一个月提交,审核数为设为1，审核状态为零,清空不通过原因
						if (student.getImages() != null && !student.equals("")) {
							String url = ImagesUtil.getImageStr(
									imagesURL.getLogoImagesHttp(),
									student.getImages());
							if (url != null && !url.equals("")) {
								student.setImages(url);
							} else {
								resultMap.put("status", 400);
								resultMap.put("message", "图片上传失败，请刷新后再试！");
								return resultMap;
							}
						}
						student.setId(stu.getId());
						student.setCheckNumber(Integer.valueOf(1));
						student.setMyCheck(Integer.valueOf(0));
						sd.update(student);
						resultMap.put("status", 200);
						resultMap.put("message", "提交成功!");
						deleteOldData(stu);
					}

				}

			}

		} catch (Exception e) {
			LoggerUtils.fmtError(getClass(), e, "服务器错误，ids[%s]", student);
			resultMap.put("status", 500);
			resultMap.put("message", "服务器错误，请刷新后再试！");
		}
		return resultMap;
	}

	/**
	 * 
	 * 删除缓存中老数据
	 * */
	protected void deleteOldData(Student student) {
		if (student == null) {
			return;
		}
		// 从缓存中获取对应的老数据
		List<Map<String, Object>> list = RedisDataUtils.getList(dataToRedis
				.getDbImages());
		if (list.size() > 0) {
			int i = 0;
			for (Map<String, Object> m : list) {
				if (i > 0) {
					break;
				}
				for (Map.Entry<String, Object> mp : m.entrySet()) {
					if (mp.getValue() instanceof Student) {
						Student b = (Student) mp.getValue();
						if (b.getId().equals(student.getId())) {
							// 删除老图片
							boolean f = ImagesUtil.delAllFile(
									imagesURL.getLogoImagesHttp(),
									b.getImages());
							if (!f) {
								resultMap.put("status", 200);
								resultMap.put("message", "更新成功,原图片未能成功删除.");
							}
							// 删除缓存对应数据
							RedisDataUtils.deleteKey(dataToRedis.getDbImages(),
									mp.getKey());
							i++;
						}
					}
				}
			}
		}
	}

}
