package com.ylms.common.entity;

import java.util.Date;

public class Partner {
	//合作方管理
	private Long id;
	//合作方名称
	private String name;
	//联系电话
	private String tel;
	//牛币数
	private Long nbNumber;
	//状态
	private Integer state;
	//登记时间
	private Date time;
	//合作方式
	private Integer way;
	//法人名称
	private String legalPerson;
	//法人身份证号
	private String identityCard;
	//营业执照
	private String images;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}
	
	public Long getNbNumber() {
		return nbNumber;
	}

	public void setNbNumber(Long nbNumber) {
		this.nbNumber = nbNumber;
	}
	
	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
	
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
	
	public Integer getWay() {
		return way;
	}

	public void setWay(Integer way) {
		this.way = way;
	}
	
	public String getLegalPerson() {
		return legalPerson;
	}

	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson;
	}
	
	public String getIdentityCard() {
		return identityCard;
	}

	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}
	
	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}
}
