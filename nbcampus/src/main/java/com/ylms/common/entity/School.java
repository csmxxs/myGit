package com.ylms.common.entity;

public class School {
	//
	private Long id;
	//
	private String schoolKey;
	//
	private String schoolName;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getSchoolKey() {
		return schoolKey;
	}

	public void setSchoolKey(String schoolKey) {
		this.schoolKey = schoolKey;
	}
	
	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
}

