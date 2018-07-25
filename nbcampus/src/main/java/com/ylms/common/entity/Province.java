package com.ylms.common.entity;
/**
 * 
 * 省份信息列表
 * 
 * */
public class Province {
	//数据库ID
	private Long id;
	//省份名称
	private String provinceName;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
}


