package com.ylms.core.tags;

import java.util.Map;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;
/**
 * 下拉框标签
 * @author zhql
 */
public class SelectTag extends BodyTagSupport {

	private static final long serialVersionUID = 1L;
	//数据Map<字典key,字典value>
	private Map<String,String> data;
	//待翻译的值
	private String value;
	private String name;
	private String cssClass;
	private String id;
	//可写，可不写，格式:  0:--请选择--
	private String header;
	@Override
	public int doStartTag() throws JspException {
		//什么都不做，直接跳过
		return SKIP_BODY;
	}

	@Override
	public int doEndTag() throws JspException {
		//通过页面上下文获取指向页面的输出流
		JspWriter out = pageContext.getOut();
		try {
			StringBuilder s = new StringBuilder();
			s.append("<select "+ (id!=null?" id='"+id+"'":"")
					+ (name!=null?" name='"+name+"'":"")
					+ (cssClass!=null?" class='"+cssClass+"'":"")
			        +" >\n");
			//首选项
			if(header!=null && !header.equals("")){
				String[] h = header.split(":");
				s.append("<option value='"+ h[0]+"'>"+h[1]+"</option>\n");
			}
			if(data!=null && data.size()>0){
				for (Map.Entry<String,String> e:data.entrySet()) {
				   if(value!=null && !value.equals("") 
				   && value.equals(e.getKey())){
					   s.append("<option selected='selected' value='"+ e.getKey() +"'>"
				                       +e.getValue()+"</option>\n"); 
				   }else{
					   s.append("<option value='"+ e.getKey() +"'>"+e.getValue()+"</option>\n");
				   }					
				}			
			}
			s.append("</select>\n");
			out.write(s.toString());
			//刷新缓冲区
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;//执行标签后的内容
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCssClass() {
		return cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public Map<String, String> getData() {
		return data;
	}

	public void setData(Map<String, String> data) {
		this.data = data;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
