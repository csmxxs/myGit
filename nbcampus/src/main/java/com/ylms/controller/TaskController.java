package com.ylms.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSONArray;
import com.ylms.common.controller.BaseController;
import com.ylms.common.entity.Task;
import com.ylms.common.utils.DataToRedis;
import com.ylms.common.utils.ImagesUtil;
import com.ylms.common.utils.LoggerUtils;
import com.ylms.common.utils.vcode.CommonImagesUrl;
import com.ylms.core.mybatis.page.Pagination;
import com.ylms.mysqlcall.RedisDataUtils;
import com.ylms.service.impl.ITaskDao;

@Controller
@Scope(value = "prototype")
@RequestMapping("task")
public class TaskController extends BaseController {
	@Autowired
	private ITaskDao td;
	@Autowired
	private CommonImagesUrl imagesURL;
	@Autowired
	private RedisAtomicLong redisAtomicLong;
	@Autowired
	private DataToRedis dataToRedis;
	@RequestMapping("index")
	@ResponseBody
	public ModelAndView list(String findContent, ModelMap modelMap,
			Integer pageNo) {
		if (findContent != null && !(findContent.equals(""))) {
			modelMap.put("findContent", findContent.trim());
		}
		Pagination<Task> task = td.findByPage(modelMap, pageNo, pageSize);
		return new ModelAndView("task/index", "page", task);
	}

	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> add(Task task) {

		if (task == null) {
			resultMap.put("status", 400);
			resultMap.put("message", "参数错误!");
			return resultMap;
		}
		if (task.getLogoImages().equals("") || task.getName().equals("")
				|| task.getTel().length() > 11 || task.getText().length() > 255
				|| task.getNbStimulate().equals("")
				|| task.getTaskFulfilImg().equals("")) {
			resultMap.put("status", 400);
			resultMap.put("message", "请正确填写信息!");
			return resultMap;
		}

		try {
			String fulfilImgHttp = ImagesUtil.getImageStr(
					imagesURL.getLogoImagesHttp(),task.getTaskFulfilImg());

			String logoImagesHttp = ImagesUtil.getImageStr(
					imagesURL.getLogoImagesHttp(), task.getLogoImages());
			if (logoImagesHttp == null || fulfilImgHttp == null) {
				resultMap.put("status", 300);
				resultMap.put("message", "图片上传失败!");
				return resultMap;
			}
			task.setLogoImages(logoImagesHttp);
			task.setTaskFulfilImg(fulfilImgHttp);
			td.add(task);
			resultMap.put("status", 200);
			resultMap.put("message", "成功添加!");
		} catch (Exception e) {
			resultMap.put("status", 500);
			resultMap.put("message", "添加失败，请刷新后再试！");
			LoggerUtils.fmtError(getClass(), e, "添加报错。source[%s]",
					task.toString());
		}
		return resultMap;
	}

	/**
	 * 
	 * 为修改列表时使用
	 * 
	 * */
	@RequestMapping("findById")
	@ResponseBody
	public Map<String, Object> findById(Long id) {

		if (id == null) {
			return resultMap;
		}

		try {
			Task task = td.findById(id);
			RedisDataUtils.setData(
					RedisDataUtils.getRedisIdKey(redisAtomicLong), task,
					dataToRedis.dbImages);
			resultMap.put("status", 200);
			resultMap.put("message", "获取信息成功!");
			resultMap.put("task", JSONArray.toJSON(task).toString());
			return resultMap;
		} catch (Exception e) {
			resultMap.put("status", 500);
			resultMap.put("message", "查询出现错误，请刷新后再试！");
			return resultMap;
		}
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> update(Task task) {
		if (task == null) {
			return resultMap;
		}
		if (task.getLogoImages().equals("") || task.getName().equals("")
				|| task.getTel().length() > 11 || task.getText().length() > 255
				|| task.getNbStimulate().equals("")
				|| task.getTaskFulfilImg().equals("")||task.getLogoImages().equals("")) {
			resultMap.put("status", 400);
			resultMap.put("message", "提交的信息不完整!");
			return resultMap;
		}
		try {
			Boolean flag1=false;
			Boolean flag2=false;
			if(task.getTaskFulfilImg().length()>100){
				flag1=true;
				String fulfilImgHttp = ImagesUtil.getImageStr(imagesURL.getLogoImagesHttp(),task.getTaskFulfilImg());
				if(fulfilImgHttp!=null){
					task.setTaskFulfilImg(fulfilImgHttp);
				}else{
					resultMap.put("status", 300);
					resultMap.put("message", "更新失败!图片更新过程出错!");
					return resultMap;
				}
			}
           if(task.getLogoImages().length()>100){
        	   flag2=true;
        	   String logoImagesHttp = ImagesUtil.getImageStr(
        			   imagesURL.getLogoImagesHttp(), task.getLogoImages());
        	   if(logoImagesHttp!=null){
        		   task.setLogoImages(logoImagesHttp);
        	   }else{
        		    resultMap.put("status", 300);
					resultMap.put("message", "更新失败!图片更新过程出错!");
					return resultMap; 
        	   }
           }
			td.update(task);
			resultMap.put("status", 200);
			resultMap.put("message", "更新成功!");
			// 从缓存中获取对应的老数据
			List<Map<String, Object>> list = RedisDataUtils.getList(dataToRedis
					.getDbImages());
			if (list.size() > 0) {
				int i=0;
				for (Map<String, Object> m : list) {
					if(i>0){
						break;
					}
					for (Map.Entry<String, Object> mp : m.entrySet()) {
						if (mp.getValue() instanceof Task) {
							Task b = (Task) mp.getValue();
							if (b.getId().equals(task.getId())) {
								boolean f=false;
								if(flag1){
									f = ImagesUtil.delAllFile(
											imagesURL.getLogoImagesHttp(),
											b.getLogoImages());
								}
								if(flag2){
									f = ImagesUtil.delAllFile(
											imagesURL.getLogoImagesHttp(),
											b.getTaskFulfilImg());
								}
								if (!f) {
									resultMap.put("status", 200);
									resultMap.put("message", "更新成功,原图片未能成功删除.");
								}
								// 删除缓存对应数据
								RedisDataUtils.deleteKey(dataToRedis.getDbImages(),mp.getKey());
								i++;
							}
						}
					}
				}
			}

		} catch (Exception e) {
			resultMap.put("status", 500);
			resultMap.put("message", "更新失败!");
		}
		return resultMap;
	}

	/**
	 * Task列表批量审核
	 * 
	 * */
	@RequestMapping(value = "checkById", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkById(String ids, Integer check) {
		if (ids == null || ids.equals("") || check == null) {
			return resultMap;
		}
		return td.checkById(ids, check);
	}

	/**
	 * 批量删除操作
	 * 
	 * */
	@RequestMapping(value = "deleteById", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteById(String ids) {
		if (ids == null || ids.equals("")) {
			return resultMap;
		}
		return td.deleteById(ids);
	}

	/**
	 * 
	 * 对bannner列表的批量上线和下线操作
	 * 
	 * */
	@RequestMapping(value = "online_or_offline_ById", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> online_or_offline_ById(String id, Integer state) {
		if (id == null || id.equals("") || state == null) {
			return resultMap;
		}
		return td.online_or_offline_ById(id, state);
	}

	/**
	 * 
	 * 专门为任务管理专区更新/添加任务列表
	 * 
	 * */
	@RequestMapping("updatePrefectureSlist")
	@ResponseBody
	public ModelAndView updateslist(String id, String findContent,
			ModelMap modelMap, Integer pageNo) {
		if (findContent != null && !(findContent.equals(""))) {
			modelMap.put("findContent", findContent.trim());
		}
		// 默认只查询属于该专区审核通过的任务列表
		modelMap.put("noPass", 1);
		// modelMap.put("property",id);
		Pagination<Task> task = td.findByPage(modelMap, pageNo, pageSize);
		return new ModelAndView("task/updatePrefectureslist", "page", task);
	}

}
