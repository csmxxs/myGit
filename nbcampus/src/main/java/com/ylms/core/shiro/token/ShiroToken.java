package com.ylms.core.shiro.token;

import org.apache.shiro.authc.UsernamePasswordToken;
/**
 * 
 * <p>
 * 
 * Shiro token
 * 
 * <p>
 * 
 * 区分　责任人　日期　　　　说明<br/>
 * 创建　谢雄世　2018年3月25日 　<br/>
 *
 * @author zhou-baicheng
 * @email  so@ylms.com
 * @version 1.0,2018年3月25日 <br/>
 * 
 */
public class ShiroToken extends UsernamePasswordToken  implements java.io.Serializable{
	
	private static final long serialVersionUID = -6451794657814516274L;
	
	/** 登录密码[字符串类型] 因为父类是char[] ] **/
	private String pswd ;

	public ShiroToken(String username, String pswd) {
		super(username,pswd);
		this.pswd = pswd ;
	}
	

	public String getPswd() {
		return pswd;
	}


	public void setPswd(String pswd) {
		this.pswd = pswd;
	}
	
	
	
	
	
}
