package com.ylms.service.imple;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ylms.common.dao.BannerMapper;
import com.ylms.common.entity.Banner;
import com.ylms.common.utils.LoggerUtils;
import com.ylms.core.mybatis.BaseMybatisDao;
import com.ylms.core.mybatis.page.Pagination;
import com.ylms.service.impl.IBannerDao;

@Service
public class BannerDao extends BaseMybatisDao<BannerMapper> implements
		IBannerDao {
	@Autowired
	private BannerMapper mapper;

	@Override
	public List<Banner> list(Map<String, Object> param) {
		return mapper.list(param);
	}

	@Override
	public int deleteById(Long id) {
		return mapper.deleteById(id);
	}

	@Override
	public int update(Banner obj) {
		return mapper.update(obj);
	}

	@Override
	public int add(Banner entity) {
		return mapper.add(entity);
	}

	@Override
	public Banner findById(Long id) {
		return mapper.findById(id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Pagination<Banner> findByPage(Map<String, Object> resultMap,
			Integer pageNo, Integer pageSize) {
		return super.findPage(resultMap, pageNo, pageSize);
	}

	/**
	 * 
	 * 对广告列表的批量删除
	 * 
	 * */
	@Override
	public Map<String, Object> deleteById(String ids) {

		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			int count = 0;
			String resultMsg = "删除成功。";
			String[] idArray = new String[] {};
			if (StringUtils.contains(ids, ",")) {
				idArray = ids.split(",");
			} else {
				idArray = new String[] { ids };
			}

			for (String idx : idArray) {
				Long id = new Long(idx);
				Banner banner = this.findById(id);
				if (banner.getState() == 1) {
					resultMsg = "上线列表不能删除!";
					continue;
				} else {
					// 改变为已删除状态
					// banner.setFlag(2);
					if (this.deleteById(id) > 0) {
						count++;
					}
				}
			}
			resultMap.put("status", 200);
			resultMap.put("count", count);
			resultMap.put("message", resultMsg);
		} catch (Exception e) {
			LoggerUtils.fmtError(getClass(), e, "根据IDS删除列表出现错误，ids[%s]", ids);
			resultMap.put("status", 500);
			resultMap.put("message", "删除列表出现错误，请刷新后再试！");
		}
		return resultMap;
	}

	/***
	 * 
	 * 对banner的列表上线和下线的批量操作
	 * 
	 * */
	@Override
	public Map<String, Object> online_or_offline_ById(String id, Integer state) {
         
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			int count = 0;
			String resultMsg = "操作成功。";
				Long bannerid = new Long(id);
				Banner banner = this.findById(bannerid);

				if (banner != null) {
					// 审核通过且不在上线时才允许上线操作
					if (banner.getNoPass() == 1 && banner.getState() != 1
							&& state == 1) {
						if (mapper.onlineById(bannerid) > 0) {
							count++;
						}
					} else if (banner.getNoPass() != 1) {
						resultMsg = "列表未审核,操作失败!";
					} else if (banner.getState() == 1 && state == 2) {
						// 在线状态允许下线操作
						if (mapper.offlineById(bannerid) > 0) {
							resultMsg = "操作成功,列表已下线!";
							count++;
						}
					}else if(banner.getState() != 1 && state == 2){
						   resultMsg = "列表未上线!";
					}else if(banner.getState() == 1 && state == 1){
						   resultMsg = "列表已上线!";
					}
				}
			
			resultMap.put("status", 200);
			resultMap.put("count", count);
			resultMap.put("message", resultMsg);
		} catch (Exception e) {
			LoggerUtils.fmtError(getClass(), e,
					"根据ID对bannner列表上线和下线时出现错误，id[%s]", id);
			resultMap.put("status", 500);
			resultMap.put("message", "bannner列表上线和下线时出现错误，请刷新后再试！");
		}
		return resultMap;

	}

	/**
	 * 
	 * banner列表的批量审核
	 * 
	 * */
	@Override
	public Map<String, Object> checkById(String ids, Integer nopass) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			int count = 0;
			String resultMsg = "审核完成。";
			String[] idArray = new String[] {};
			if (StringUtils.contains(ids, ",")) {
				idArray = ids.split(",");
			} else {
				idArray = new String[] { ids };
			}

			for (String idx : idArray) {
				Long id = new Long(idx);
				Banner banner = this.findById(id);
				if (banner != null) {
					// 没有审核过才进行审核
					if (banner.getNoPass() == 0) {
						if (nopass == 1 && banner.getNoPass() == 0) {
							mapper.check1ById(id);
							count++;
						}
						if (nopass == 2 && banner.getNoPass() == 0) {
							mapper.check2ById(id);
							count++;
						}
					} else {
						resultMsg = "列表已审核!";
					}
				}
			}
			resultMap.put("status", 200);
			resultMap.put("count", count);
			resultMap.put("message", resultMsg);
		} catch (Exception e) {
			LoggerUtils.fmtError(getClass(), e, "根据IDS审核列表出现错误，ids[%s]", ids);
			resultMap.put("status", 500);
			resultMap.put("message", "审核列表出现错误，请刷新后再试！");
		}
		return resultMap;

	}

	@Override
	public List<Banner> findAllByOnlie() {
		return mapper.findAllByOnlie();
	}
}
