package com.ylms.core.tags;
import java.io.IOException;
import java.util.Map;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;
/**
 * 翻译标签
 * @author zhql
 */
public class TranTag extends BodyTagSupport {

	private static final long serialVersionUID = 1L;
	//数据字典集
    private Map<String,String> data;
    private String value;
	
	@Override
	public int doStartTag() throws JspException {
		return SKIP_BODY;
	}
	@Override
	public int doEndTag() throws JspException {
		//指向客户端的输出流
		JspWriter out = this.pageContext.getOut();
		try {
			if(data!=null && data.get(value)!=null){
				out.write(data.get(value));
			}else{
				out.write("--");
			}			
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;//执行标签后的内容;
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







