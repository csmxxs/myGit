package com.ylms.core.tags;

import static freemarker.template.ObjectWrapper.DEFAULT_WRAPPER;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import freemarker.core.Environment;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateNumberModel;

@Service
public class MyTags implements TemplateDirectiveModel {

	@Autowired
	private WebApplicationContext webApplicationContext = ContextLoader
			.getCurrentWebApplicationContext();

	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {

		// 获取页面的参数
		// 当前端传过来的参数是整数时(paramId:页面自定义标签的参数key)
		String str = "";
		Object paramId = params.get("paramId");
		if (!params.isEmpty()) {

			if (paramId instanceof TemplateNumberModel) {
				// int i = ((TemplateNumberModel) paramId).getAsNumber()
				// .intValue();
				// 调用相应的service返回信息给body

			}
			// 字符串类型
			if (paramId instanceof SimpleScalar) {
				// 使用spring获取上下文保存的信息
				ServletContext servletContext = webApplicationContext
						.getServletContext();
				str = ((SimpleScalar) paramId).getAsString();
				// 获得目录列表
				Map<String, Object> map = new HashMap<String, Object>();
				String[] idArray = new String[] {};
				if (StringUtils.contains(str, ",")) {
					idArray = str.split(",");
				} else {
					idArray = new String[] { str };
				}

				Object obj = servletContext.getAttribute(idArray[0]);
				String cvalue = "";
				if (obj != null) {
					Map<String, String> p = (Map) obj;
					if (p.size() > 0) {
						for (Map.Entry<String, String> j : p.entrySet()) {
							if (j.getKey().equals(idArray[1])) {
								cvalue = j.getValue();
								env.setVariable("dictCvalue",
										DEFAULT_WRAPPER.wrap(cvalue));
								body.render(env.getOut());
								return;
							}
						}
					} else {
						cvalue = "-";
						env.setVariable("dictCvalue",
								DEFAULT_WRAPPER.wrap(cvalue));
						body.render(env.getOut());
						return;
					}
				} else {
					env.setVariable("dictCvalue", DEFAULT_WRAPPER.wrap(cvalue));
					body.render(env.getOut());
					return;
				}
			}
		} else {
			throw new TemplateModelException("自定义标签传递的参数为空!");
		}

	}
}
