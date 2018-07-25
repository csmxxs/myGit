package com.ylms.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.ylms.common.controller.BaseController;
import com.ylms.common.entity.Student;
import com.ylms.common.utils.DateUtil;
import com.ylms.common.utils.ImagesUtil;
import com.ylms.common.utils.LoggerUtils;
import com.ylms.common.utils.vcode.CommonImagesUrl;
import com.ylms.core.mybatis.page.Pagination;
import com.ylms.service.impl.IStudentDao;

@Controller
@Scope(value = "prototype")
@RequestMapping("student")
public class StudentController extends BaseController {
	@Autowired
	private IStudentDao sd;
	@Autowired
	private CommonImagesUrl imagesURL;

	@InitBinder
	// 日期自动转换器
	public void initBinder(ServletRequestDataBinder bin) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		CustomDateEditor cust = new CustomDateEditor(sdf, true);
		bin.registerCustomEditor(Date.class, cust);
	}

	/**
	 * 
	 * 学生信息查询
	 * 
	 * */
	@RequestMapping("index")
	@ResponseBody
	public ModelAndView list(String findContent, ModelMap modelMap,
			Integer pageNo) {
		if (findContent != null && !(findContent.equals(""))) {
			modelMap.put("findContent", findContent.trim());
		}
		Pagination<Student> stu = sd.findByPage(modelMap, pageNo, pageSize);
		return new ModelAndView("student/index", "page", stu);
	}

	/**
	 * 
	 * 学生信息添加
	 * 
	 * */
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> add(Student student) {
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
						String url = ImagesUtil
								.getImageStr(imagesURL.getLogoImagesHttp(),student.getImages());
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
					
					if (DateUtil.sameDate(new Date(), stu.getCreateTime())) {
						
						// 把审核数加上1,myCheck值改为0
					student.setId(stu.getId());
					student.setCheckNumber(Integer.valueOf(stu.getCheckNumber() + 1));
					student.setMyCheck(Integer.valueOf(0));
					if (student.getImages() != null && !student.equals("")) {
						String url = ImagesUtil
								.getImageStr(imagesURL.getLogoImagesHttp(),student.getImages());
						if (url != null && !url.equals("")) {
							student.setImages(url);
						} else {
							resultMap.put("status", 400);
							resultMap.put("message", "图片上传失败,请刷新后再试！");
							return resultMap;
						}

					}
					}else{
						student.setId(stu.getId());
						student.setCheckNumber(1);
						student.setMyCheck(Integer.valueOf(0));
						if (student.getImages() != null && !student.equals("")) {
							String url = ImagesUtil
									.getImageStr(
											imagesURL.getLogoImagesHttp(),student.getImages());
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
				}

				// 当月审核数大于等于3次
				if (stu.getCheckNumber() >= 3) {

					// 在同年同月里为真时
					if (DateUtil.sameDate(new Date(), stu.getCreateTime())) {
						resultMap.put("status", 300);
						resultMap.put("message", "超过当月最大审核数,提交失败!");
					} else {
						// 如果时间跨月份,改变审核数为1,myCheck字段改为0
						if (student.getImages() != null && !student.equals("")) {
							String url = ImagesUtil.getImageStr(
									imagesURL.getLogoImagesHttp(),student.getImages());
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
					}

				}

			}else{
				String url = ImagesUtil.getImageStr(imagesURL.getLogoImagesHttp(),student.getImages());
				student.setImages(url);
				student.setCheckNumber(1);
				sd.add(student);
				resultMap.put("status", 200);
				resultMap.put("message", "上传成功,将在三个工作日审核完成!");
			}

		} catch (Exception e) {
			LoggerUtils.fmtError(getClass(), e, "服务端错误，ids[%s]", student);
			resultMap.put("status", 500);
			resultMap.put("message", "服务端错误，请刷新后再试！");
		}
		return resultMap;
	}

	/**
	 * 
	 * 学生信息批量删除
	 * 
	 **/
	@RequestMapping("deleteById")
	@ResponseBody
	public Map<String, Object> deleteById(String ids) {
		if (ids == null || ids.equals("")) {
			return resultMap;
		}
		return sd.deleteById(ids);
	}

	/**
	 * 
	 * 批量审核学生信息
	 * */
	@RequestMapping("checkById")
	@ResponseBody
	public Map<String, Object> checkById(String ids, Integer myCheck) {
		if (ids == null || ids.equals("") || myCheck == null) {
			return resultMap;
		}
		return sd.checkById(ids, myCheck);
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> update(Long id, String causeReason) {

		if (id == null || causeReason == null || causeReason.equals("")) {
			return resultMap;
		}

		try {
			Student student = sd.findById(id);
			if (student != null) {
				student.setCauseReason(causeReason);
				sd.update(student);
				resultMap.put("status", 200);
				resultMap.put("message", "修改成功!");
			}
		} catch (Exception e) {
			resultMap.put("status", 500);
			resultMap.put("message", "修改失败!");
		}
		return resultMap;
	}

	@RequestMapping(value = "findById", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> findById(Long id) {

		if (id == null) {
			resultMap.put("status", 100);
			resultMap.put("message", "参数错误!");
			return resultMap;
		}
		try {
			Student student = sd.findById(id);
			if (student != null) {
				resultMap.put("student", JSONArray.toJSON(student).toString());
				resultMap.put("status", 200);
				resultMap.put("message", "查询成功!");
			}
		} catch (Exception e) {
			resultMap.put("status", 500);
			resultMap.put("message", "查询失败!");
		}
		return resultMap;
	}

}
