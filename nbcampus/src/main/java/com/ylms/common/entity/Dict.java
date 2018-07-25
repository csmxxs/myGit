package com.ylms.common.entity;

import java.util.Date;

public class Dict {
	// 记录ID
	private Long id;
	// 字典名称
	private String dictName;
	// 字典KEY
	private String ckey;
	// 字典VALUE
	private String cvalue;
	// 状态(可用1不可用2)
	private Long useFlag;
	// 顺序号
	private Long orderNo;
	// 删除标志
	private Long delFlag;
	// 创建日期
	private Date createDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDictName() {
		return dictName;
	}

	public void setDictName(String dictName) {
		this.dictName = dictName;
	}

	public String getCkey() {
		return ckey;
	}

	public void setCkey(String ckey) {
		this.ckey = ckey;
	}

	public String getCvalue() {
		return cvalue;
	}

	public void setCvalue(String cvalue) {
		this.cvalue = cvalue;
	}

	public Long getUseFlag() {
		return useFlag;
	}

	public void setUseFlag(Long useFlag) {
		this.useFlag = useFlag;
	}

	public Long getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
	}

	public Long getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Long delFlag) {
		this.delFlag = delFlag;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
