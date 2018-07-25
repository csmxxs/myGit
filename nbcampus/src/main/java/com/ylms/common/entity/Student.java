package com.ylms.common.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 学生身份信息
 * 
 * */
public class Student implements Comparable<Student>, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 学生身份信息
	private Long id;
	//学校所在省份
	private Integer shcoolProvinceId;
	// 姓名
	private String name;
	//电话号码
	private String tel;
	// 身份证
	private String identity;
	// 学生证
	private String studentIdCard;
	// 就读学校
	private String school;
	// 入学时间
	private Date timeofEnrollment;
	// 专业
	private String profession;
	// 证件照
	private String images;
	// 每月验证提交数(从0开始),每月不超过3次
	private Integer checkNumber;
	// 该学生的推荐人(官方渠道下填写)
	private String llb;
	// 0:待审核,1:审核通过，2:不通过3.待完善信息
	private Integer myCheck;
	// 不通过原因
	private String causeReason;
	// 创建时间
	private Date createTime;
	// 删除状态1:未删除2:已删除
	private Integer flag;
	// 审核时间
	private Date checkTime;
    //邀请人级别
	private Integer inviteLevel;
	//牛币数
	private Integer nbNumber;
	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}

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

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getStudentIdCard() {
		return studentIdCard;
	}

	public void setStudentIdCard(String studentIdCard) {
		this.studentIdCard = studentIdCard;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public Date getTimeofEnrollment() {
		return timeofEnrollment;
	}

	public void setTimeofEnrollment(Date timeofEnrollment) {
		this.timeofEnrollment = timeofEnrollment;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

	public Integer getCheckNumber() {
		return checkNumber;
	}

	public void setCheckNumber(Integer checkNumber) {
		this.checkNumber = checkNumber;
	}

	public String getLlb() {
		return llb;
	}

	public void setLlb(String llb) {
		this.llb = llb;
	}

	public Integer getMyCheck() {
		return myCheck;
	}

	public void setMyCheck(Integer myCheck) {
		this.myCheck = myCheck;
	}

	public String getCauseReason() {
		return causeReason;
	}

	public void setCauseReason(String causeReason) {
		this.causeReason = causeReason;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public String getTel() {
		return tel;
	}
	
	public void setTel(String tel) {
		this.tel = tel;
	}
	
	public Integer getShcoolProvinceId() {
		return shcoolProvinceId;
	}
	
	public void setShcoolProvinceId(Integer shcoolProvinceId) {
		this.shcoolProvinceId = shcoolProvinceId;
	}
	@Override
	public int compareTo(Student o) {
		// 根据ID对象比较
		if (this.id - o.id > 0) {
			return 1;
		} else if (this.id - o.id < 0) {
			return -1;
		} else {
			return 0;
		}
	}

	public Integer getInviteLevel() {
		return inviteLevel;
	}

	public void setInviteLevel(Integer inviteLevel) {
		this.inviteLevel = inviteLevel;
	}

	public Integer getNbNumber() {
		return nbNumber;
	}

	public void setNbNumber(Integer nbNumber) {
		this.nbNumber = nbNumber;
	}

}
