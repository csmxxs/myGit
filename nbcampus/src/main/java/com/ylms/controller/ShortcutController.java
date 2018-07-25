package com.ylms.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.ylms.common.controller.BaseController;
import com.ylms.common.entity.Shortcut;
import com.ylms.common.utils.DataToRedis;
import com.ylms.common.utils.ImagesUtil;
import com.ylms.common.utils.LoggerUtils;
import com.ylms.common.utils.vcode.CommonImagesUrl;
import com.ylms.core.mybatis.page.Pagination;
import com.ylms.mysqlcall.RedisDataUtils;
import com.ylms.service.impl.IShortcutDao;

/**
 * 
 * 快捷功能入口控制
 * 
 * */
@Controller
@Scope(value = "prototype")
@RequestMapping("shortcut")
public class ShortcutController extends BaseController {
	@Autowired
	private IShortcutDao iscd;
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
		Pagination<Shortcut> shortcut = iscd.findByPage(modelMap, pageNo,
				pageSize);
		return new ModelAndView("shortcut/index", "page", shortcut);
	}

	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> add(Shortcut shortcut) {
		if (shortcut == null) {
			return resultMap;
		}
		if (shortcut.getImages() == null || shortcut.getImages().equals("")
				|| shortcut.getName().equals("") || shortcut.getName() == null
				|| shortcut.getNumber().equals("")
				|| shortcut.getNumber() == null
				|| shortcut.getShortcutHttp().equals("")
				|| shortcut.getShortcutHttp() == null
				|| shortcut.getState() == null
				|| shortcut.getState().equals("")) {
			resultMap.put("status", 100);
			resultMap.put("message", "参数不正确!");
			return resultMap;
		}

		try {
			String logoImagesHttp = ImagesUtil.getImageStr(
					imagesURL.getLogoImagesHttp(),shortcut.getImages());
			if (logoImagesHttp == null) {
				resultMap.put("status", 300);
				resultMap.put("message", "图片上传失败!");
				return resultMap;
			}
			shortcut.setImages(logoImagesHttp);
			iscd.add(shortcut);
			resultMap.put("status", 200);
			resultMap.put("message", "添加成功!");
		} catch (Exception e) {
			resultMap.put("status", 500);
			resultMap.put("message", "添加失败，请刷新后再试！");
			LoggerUtils.fmtError(getClass(), e, "添加快捷功能入口报错。source[%s]",
					shortcut.toString());
		}
		return resultMap;
	}

	/**
	 * 
	 * 为修改时使用
	 * 
	 * */
	@RequestMapping("findById")
	@ResponseBody
	public Map<String, Object> findById(Long id) {
		if (id == null) {
			resultMap.put("status", 100);
			resultMap.put("message", "参数不正确!");
			return resultMap;
		}

		try {
			Shortcut shortcut = iscd.findById(id);
			if (shortcut != null) {
				// 保存老数据到缓存
				RedisDataUtils.setData(
						RedisDataUtils.getRedisIdKey(redisAtomicLong), shortcut,
						dataToRedis.dbImages);
			}
			resultMap.put("status", 200);
			resultMap.put("shortcut", JSONArray.toJSON(shortcut).toString());
		} catch (Exception e) {
			resultMap.put("status", 500);
			resultMap.put("message", "查询出现错误，请刷新后再试！");
		}
		return resultMap;
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> update(Shortcut shortcut) {
		if (shortcut == null) {
			resultMap.put("status", 100);
			resultMap.put("message", "参数不正确!");
			return resultMap;
		}
		if (shortcut.getId() == null || shortcut.getId().equals("")
				|| shortcut.getImages().equals("")
				|| shortcut.getName().equals("") || shortcut.getName() == null
				|| shortcut.getNumber().equals("")
				|| shortcut.getNumber() == null
				|| shortcut.getShortcutHttp().equals("")
				|| shortcut.getShortcutHttp() == null
				|| shortcut.getState() == null
				|| shortcut.getState().equals("")) {
			resultMap.put("status", 100);
			resultMap.put("message", "参数不正确!");
			return resultMap;
		}
		try {
			if (shortcut.getImages().length() > 100) {
				String imagesHttp = ImagesUtil.getImageStr(
						imagesURL.getLogoImagesHttp(),shortcut.getImages());
				if (imagesHttp != null) {
					shortcut.setImages(imagesHttp);
				} else {
					resultMap.put("status", 300);
					resultMap.put("message", "图片更新失败!");
					return resultMap;
				}
			}
			iscd.update(shortcut);//发生异常老图片就无法删除......
			resultMap.put("status", 200);
			resultMap.put("message", "修改成功!");
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
						if (mp.getValue() instanceof Shortcut) {
							Shortcut b = (Shortcut) mp.getValue();
							if (b.getId().equals(shortcut.getId())) {
								boolean f = ImagesUtil.delAllFile(
										imagesURL.getLogoImagesHttp(),
										b.getImages());
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
			resultMap.put("message", "修改失败...");
		}
		return resultMap;
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
		return iscd.deleteById(ids);
	}

}
