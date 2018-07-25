package com.ylms.service.imple;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ylms.common.dao.DictMapper;
import com.ylms.common.entity.Dict;
import com.ylms.common.utils.LoggerUtils;
import com.ylms.core.mybatis.BaseMybatisDao;
import com.ylms.core.mybatis.page.Pagination;
import com.ylms.service.impl.IDictDao;
@Service
public class DictDao extends BaseMybatisDao<DictMapper>implements IDictDao {
    @Autowired
    private DictMapper mapper;
	@Override
	public List<Dict> list(Map<String, Object> param) {
		return mapper.findList(param);
	}

	@Override
	public int deleteById(Long id) {
		
		return mapper.deleteById(id);
	}

	@Override
	public int update(Dict obj) {
		return mapper.update(obj);
	}

	@Override
	public int add(Dict entity) {
		return mapper.add(entity);
	}

	@Override
	public Dict findById(Long id) {
		return mapper.findById(id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Pagination<Dict> findByPage(Map<String, Object> resultMap,
			Integer pageNo, Integer pageSize) {
		return super.findPage(resultMap, pageNo, pageSize);
	}

	@Override
	public Map<String, Object> deleteById(String ids) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			int count=0;
			String resultMsg = "删除成功。";
			String[] idArray = new String[]{};
			if(StringUtils.contains(ids, ",")){
				idArray = ids.split(",");
			}else{
				idArray = new String[]{ids};
			}
			
			c:for (String idx : idArray) {
				Long id = new Long(idx);
				Dict dict=this.findById(id);
				//对不可用的标签批量删除
				if(dict.getUseFlag()!=1){
					if(this.deleteById(id)>0){
						count++;
					}
				}else{
					resultMsg = "操作成功，But'可用的标签不能删除。";
					continue c;
				}
			}
			resultMap.put("status", 200);
			resultMap.put("count", count);
			resultMap.put("message", resultMsg);
		} catch (Exception e) {
			LoggerUtils.fmtError(getClass(), e, "根据IDS删除标签出现错误，ids[%s]", ids);
			resultMap.put("status", 500);
			resultMap.put("message", "删除标签出现错误，请刷新后再试！");
		}
		return resultMap;
	}

}
