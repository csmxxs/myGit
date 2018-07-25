package com.ylms.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSONArray;
import com.ylms.common.controller.BaseController;
import com.ylms.common.dao.DictUtil;
import com.ylms.common.entity.Prefecture;
import com.ylms.common.entity.Task;
import com.ylms.common.utils.DataToRedis;
import com.ylms.common.utils.ImagesUtil;
import com.ylms.common.utils.SnowflakeIdWorker;
import com.ylms.common.utils.vcode.CommonImagesUrl;
import com.ylms.core.mybatis.page.Pagination;
import com.ylms.mysqlcall.RedisDataUtils;
import com.ylms.service.impl.IPrefectureDao;
import com.ylms.service.impl.ITaskDao;

/**
 * 
 * 任务专区控制
 * 
 * */
@Controller
@Scope(value = "prototype")
@RequestMapping("prefecture")
public class PrefectureController extends BaseController {
	// 使用spring获取上下文保存的信息
	private WebApplicationContext webApplicationContext = ContextLoader
			.getCurrentWebApplicationContext();
	@Autowired
	private IPrefectureDao ipd;
	@Autowired
	private ITaskDao itaskdao;
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

	@RequestMapping("index")
	@ResponseBody
	public ModelAndView list(String findContent, ModelMap modelMap,
			Integer pageNo) {
		modelMap.put("findContent", findContent);
		Pagination<Prefecture> prefecture = ipd.findByPage(modelMap, pageNo,
				pageSize);
		return new ModelAndView("prefecture/index", "page", prefecture);
	}
	@RequestMapping("updatejsp")
	@ResponseBody
	public ModelAndView update() {
		return new ModelAndView("update");
	}

	/**
	 * 
	 * 新增任务专区和添加任务列表
	 * 
	 * **/
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> add(Prefecture prefecture) {
		if (prefecture == null) {
			resultMap.put("status", 400);
			resultMap.put("message", "参数有误");
			return resultMap;
		}
		if (prefecture.getLogoImagesHttp().equals("")
				|| prefecture.getLogoImagesHttp() == null
				|| prefecture.getName().equals("")
				|| prefecture.getName() == null
				|| prefecture.getState() == null
				|| prefecture.getState().equals("")) {
			resultMap.put("status", 400);
			resultMap.put("message", "提交的信息不符合规范!");
			return resultMap;
		}
		// 用雪花算法获得该prefecture的ID值
		Long prefectureId = new SnowflakeIdWorker(0, 0).nextId();
		prefecture.setId(prefectureId.toString());
		String logoImagesHttp = ImagesUtil.getImageStr(imagesURL.getLogoImagesHttp(),prefecture.getLogoImagesHttp());
		if (logoImagesHttp == null) {
			resultMap.put("status", 300);
			resultMap.put("message", "图片上传失败,添加失败!");
			return resultMap;
		}
		prefecture.setLogoImagesHttp(logoImagesHttp);
		try {
			ipd.add(prefecture);
			DictUtil.rePrefecture(webApplicationContext.getServletContext());
			resultMap.put("status", 200);
			resultMap.put("message", "添加成功!");
		} catch (Exception e) {
			resultMap.put("status", 500);
			resultMap.put("message", "添加失败!");
		}
		return resultMap;
	}

	/**
	 * 
	 * 为修改时任务列表时使用
	 * 
	 * */
	@RequestMapping("findById")
	@ResponseBody
	public Map<String, Object> findById(String id) {
		if (id == null || id.equals("")) {
			resultMap.put("status", 400);
			resultMap.put("message", "参数有误");
			return resultMap;
		}

		try {
			Prefecture prefecture = ipd.findByPrefectureId(id);
			if (prefecture != null) {
				resultMap.put("status", 200);
				resultMap.put("prefecture", JSONArray.toJSON(prefecture)
						.toString());

			} else {
				resultMap.put("status", 300);
				resultMap.put("message", "找不到相应信息!");
				return resultMap;
			}
		} catch (Exception e) {
			resultMap.put("status", 500);
			resultMap.put("message", "查询出现错误，请刷新后再试！");
		}
		return resultMap;
	}

	/**
	 * 
	 * 更新任务专区和更新任务列表
	 * 
	 * **/

	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> update(Prefecture prefecture, Boolean cupdate,
			String ids) {
		List<Task> task = new ArrayList<Task>();
		if (cupdate) {
			// 初始化更新
			Prefecture p = ipd.findByPrefectureId(ids);
			if (p != null) {
				// 保存老数据到缓存
				RedisDataUtils.setData(
						RedisDataUtils.getRedisIdKey(redisAtomicLong), p,
						dataToRedis.dbImages);
			};
			resultMap.put("status", 200);
			resultMap.put("prefecture", p);
			// 查询回任务专区的任务列表
			task = itaskdao.listById(ids);
			resultMap.put("task", task);
			return resultMap;
		} else {

			if (!testArray(ids)) {
				resultMap.put("status", 400);
				resultMap.put("message", "任务列表重复,更新失败!");
				return resultMap;
			}

			if (prefecture.getLogoImagesHttp().equals("")
					|| prefecture.getLogoImagesHttp() == null
					|| prefecture.getName().equals("")
					|| prefecture.getName() == null
					|| prefecture.getState() == null
					|| prefecture.getState().equals("")) {
				resultMap.put("status", 400);
				resultMap.put("message", "提交的信息不符合规范!");
				return resultMap;
			}
			// 当有图片更新时，更新图片
			if (prefecture.getLogoImagesHttp().length() > 100) {
				String logoImagesHttp = ImagesUtil.getImageStr(imagesURL.getLogoImagesHttp(),prefecture.getLogoImagesHttp());
				if (logoImagesHttp != null) {
					prefecture.setLogoImagesHttp(logoImagesHttp);
				}else{
					resultMap.put("status",500);
					resultMap.put("message", "更新失败!");
					return resultMap;
				}
			} 
			try {
				ipd.update(prefecture, ids);
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
							if (mp.getValue() instanceof Prefecture) {
								Prefecture b = (Prefecture) mp.getValue();
								if (b.getId().equals(prefecture.getId())) {
									boolean f = ImagesUtil.delAllFile(
											imagesURL.getLogoImagesHttp(),
											b.getLogoImagesHttp());
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
				resultMap.put("status",500);
				resultMap.put("message", "更新失败!");
			}
			return resultMap;
		}
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
		return ipd.deleteById(ids);
	}

	/**
	 * 
	 * 根据传过来的ids参数为任务专区查回任务列表
	 * 
	 * **/
	@RequestMapping(value = "getTask", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getTask(String taskIds) {

		if (taskIds != null && !taskIds.equals("")) {
			List<Task> list = itaskdao.list(taskIds);
			if (list.size() > 0) {
				resultMap.put("status", 200);
				resultMap.put("task", list);
				resultMap.put("message", "查到" + list.size() + "个列表");
			} else {
				resultMap.put("status", 500);
				resultMap.put("message", "查询发生错误，请稍后重试!");
			}
		}
		return resultMap;
	}

	/**
	 * 
	 * 根据传过来的id参数为任务专区查回所属的任务列表
	 * 
	 * **/
	@RequestMapping(value = "getTaskById", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getTaskById(String id) {

		if (id != null && !id.equals("")) {
			List<Task> list = itaskdao.listById(id);
			if (list.size() > 0) {
				resultMap.put("status", 200);
				resultMap.put("task", list);
				resultMap.put("message", "该专区包含有" + list.size() + "个任务列表");
			} else {
				resultMap.put("status", 500);
				resultMap.put("message", "查询发生错误，请稍后重试!");
			}
		}
		return resultMap;
	}
    /**
     * 
     * 判断任务列表是否重复
     * 
     * */
	@SuppressWarnings("null")
	private Boolean testArray(String ids) {
		Boolean flag = false;
		if (ids != null || !ids.equals("")) {
			// 判断是否有重复的ID值，有重复则返回！
			String[] idArray = new String[] {};
			if (StringUtils.contains(ids, ",")) {
				idArray = ids.split(",");
			} else {
				idArray = new String[] { ids };
			}
			HashSet<String> hashSet = new HashSet<String>();
			for (int i = 0; i < idArray.length; i++) {
				hashSet.add(idArray[i]);
			}
			if (hashSet.size() == idArray.length) {
				flag = true;
			} else {
				flag = false;
			}
		}
		return flag;
	}
}
