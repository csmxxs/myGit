package com.ylms.core.tags;

import static freemarker.template.ObjectWrapper.DEFAULT_WRAPPER;

import java.util.HashMap;
import java.util.Map;

import com.ylms.common.utils.LoggerUtils;
import com.ylms.common.utils.SpringContextUtil;
import com.ylms.core.statics.Constant;

import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

/**
 * 
 * Freemarker 自定义标签 API公共入口
 * 
 * <p>
 * 
 * 区分　责任人　日期　　　　说明<br/>
 * 创建　谢雄世　2018年4月1日 　<br/>
 *
 * @author zhou-baicheng
 * @email  so@ylms.com
 * @version 1.0,2018年4月1日 <br/>
 * 
 */

public class APITemplateModel extends WYFTemplateModel {

	@Override
	@SuppressWarnings({  "unchecked" })
	protected Map<String, TemplateModel> putValue(Map params)
			throws TemplateModelException {
		
		Map<String, TemplateModel> paramWrap = null ;
		if(null != params && params.size() != 0 || null != params.get(Constant.TARGET)){
			String name =  params.get(Constant.TARGET).toString() ;
			paramWrap = new HashMap<String, TemplateModel>(params);
			
			/**
			 * 获取子类，用父类接收，
			 */
			SuperCustomTag tag =  SpringContextUtil.getBean(name,SuperCustomTag.class);
			//父类调用子类方法
			Object result = tag.result(params);
			
			//输出
			paramWrap.put(Constant.OUT_TAG_NAME, DEFAULT_WRAPPER.wrap(result));
		}else{
			LoggerUtils.error(getClass(), "Cannot be null, must include a 'name' attribute!");
		}
		return paramWrap;
	}

	
	
	
	
	
}
