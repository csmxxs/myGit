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
import com.ylms.common.entity.Banner;
import com.ylms.common.utils.DataToRedis;
import com.ylms.common.utils.ImagesUtil;
import com.ylms.common.utils.LoggerUtils;
import com.ylms.common.utils.vcode.CommonImagesUrl;
import com.ylms.core.mybatis.page.Pagination;
import com.ylms.mysqlcall.RedisDataUtils;
import com.ylms.service.impl.IBannerDao;

/**
 * 
 * 对广告列表的增删改查的操作
 * 
 * 
 * */
@Controller
@Scope(value = "prototype")
@RequestMapping("banner")
public class BannerController extends BaseController {
	@Autowired
	private IBannerDao bd;
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
		modelMap.put("findContent", findContent);
		Pagination<Banner> banner = bd.findByPage(modelMap, pageNo, pageSize);
		return new ModelAndView("banner/index", "page", banner);
	}

	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> add(Banner banner) {

		if (banner == null) {
			resultMap.put("status", 100);
			resultMap.put("message", "参数错误!");
			return resultMap;
		}
		if (banner.getBannerHttp().equals("") || banner.getBannerHttp() == null
				|| banner.getImagesHttp().equals("")
				|| banner.getImagesHttp() == null
				|| banner.getName().equals("") || banner.getName() == null
				|| banner.getState().equals("") || banner.getState() == null) {
			resultMap.put("status", 100);
			resultMap.put("message", "参数错误!");
			return resultMap;
		}
		try {
			String logoImagesHttp = ImagesUtil.getImageStr(
					imagesURL.getLogoImagesHttp(), banner.getImagesHttp());
			if (logoImagesHttp == null) {
				resultMap.put("status", 300);
				resultMap.put("message", "图片上传失败!");
				return resultMap;
			}
			banner.setImagesHttp(logoImagesHttp);
			bd.add(banner);
			resultMap.put("status", 200);
			resultMap.put("message", "成功添加");
		} catch (Exception e) {
			resultMap.put("status", 500);
			resultMap.put("message", "添加失败，请刷新后再试！");
			LoggerUtils.fmtError(getClass(), e, "添加广告列表报错。source[%s]",
					banner.toString());
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
			resultMap.put("status", 100);
			resultMap.put("message", "参数错误!");
			return resultMap;
		}
		try {
			Banner banner = bd.findById(id);
			if (banner != null) {
				// 保存老数据到缓存
				RedisDataUtils.setData(
						RedisDataUtils.getRedisIdKey(redisAtomicLong), banner,
						dataToRedis.dbImages);
			}
			resultMap.put("status", 200);
			resultMap.put("banner", JSONArray.toJSON(banner).toString());
			resultMap.put("message", "获取信息成功!");
		} catch (Exception e) {
			resultMap.put("status", 500);
			resultMap.put("message", "查询出现错误，请刷新后再试！");
		}
		return resultMap;
	}

	@RequestMapping("update")
	@ResponseBody
	public Map<String, Object> update(Banner banner) {

		if (banner == null) {
			resultMap.put("status", 100);
			resultMap.put("message", "参数错误!");
			return resultMap;
		}
		if (banner.getBannerHttp().equals("") || banner.getBannerHttp() == null
				|| banner.getImagesHttp().equals("")
				|| banner.getImagesHttp() == null
				|| banner.getName().equals("") || banner.getName() == null
				|| banner.getState().equals("") || banner.getState() == null
				|| banner.getId().equals("") || banner.getId() == null) {
			resultMap.put("status", 100);
			resultMap.put("message", "参数错误!");
			return resultMap;
		}
		try {
			if (banner.getImagesHttp().length() > 100) {
				String logoImagesHttp = ImagesUtil.getImageStr(
						imagesURL.getLogoImagesHttp(), banner.getImagesHttp());
				if (logoImagesHttp != null) {
					banner.setImagesHttp(logoImagesHttp);
				} else {
					resultMap.put("status", 500);
					resultMap.put("message", "更新失败!");
					return resultMap;
				}

			}
			bd.update(banner);
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
						if (mp.getValue() instanceof Banner) {
							Banner b = (Banner) mp.getValue();
							if (b.getId().equals(banner.getId())) {
								boolean f = ImagesUtil.delAllFile(
										imagesURL.getLogoImagesHttp(),
										b.getImagesHttp());
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
	 * banner列表批量审核
	 * 
	 * */
	@RequestMapping("checkById")
	@ResponseBody
	public Map<String, Object> checkById(String ids, Integer check) {
		if (ids == null || ids.equals("") || check == null) {
			return resultMap;
		}

		return bd.checkById(ids, check);
	}

	/**
	 * 批量删除操作
	 * 
	 * */
	@RequestMapping("deleteById")
	@ResponseBody
	public Map<String, Object> deleteById(String ids) {
		if (ids == null || ids.equals("")) {
			return resultMap;
		}

		return bd.deleteById(ids);
	}

	/**
	 * 
	 * 对bannner列表的批量上线和下线操作
	 * 
	 * */
	@RequestMapping("online_or_offline_ById")
	@ResponseBody
	public Map<String, Object> online_or_offline_ById(String id, Integer state) {

		if (id == null || id.equals("") || state == null) {
			return resultMap;
		}

		return bd.online_or_offline_ById(id, state);
	}

}
