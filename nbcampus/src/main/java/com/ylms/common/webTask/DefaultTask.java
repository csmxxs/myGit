package com.ylms.common.webTask;


/**
 * Title:DefaultTask.java <br>
 * Description:默认的任务列表以及移动端会话时长<br>
 * Company:www.ylms.com <br>
 * @author XieXiongShi
 * @date 2018年7月9日 下午12:15:15
 * @version V 1.0 
 */
public class DefaultTask {
	// 省份ID
	private Integer provinceId;
	// 会话时长
	private int webSessionOutTime;

	public Integer getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}

	public int getWebSessionOutTime() {
		return webSessionOutTime;
	}

	public void setWebSessionOutTime(int webSessionOutTime) {
		this.webSessionOutTime = webSessionOutTime;
	}

}
