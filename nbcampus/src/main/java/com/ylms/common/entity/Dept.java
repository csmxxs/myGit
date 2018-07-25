package com.ylms.common.entity;

public class Dept {
	//主键
	private Long id;
	//部门编码
	private String departmentKey;
	//部门名称
	private String departmentValue;
	//描述
	private String description;
	//上级部门编码
	private String parentDepartmentkey;
	//创建时间
	private String createTime;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getDepartmentKey() {
		return departmentKey;
	}

	public void setDepartmentKey(String departmentKey) {
		this.departmentKey = departmentKey;
	}
	
	public String getDepartmentValue() {
		return departmentValue;
	}

	public void setDepartmentValue(String departmentValue) {
		this.departmentValue = departmentValue;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getParentDepartmentkey() {
		return parentDepartmentkey;
	}

	public void setParentDepartmentkey(String parentDepartmentkey) {
		this.parentDepartmentkey = parentDepartmentkey;
	}
	
	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
}
