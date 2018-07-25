package com.ylms.common.entity;

import java.util.Date;
/**
 * 派奖列表
 * 
 * **/
public class Prizewinner {
	//
	private Integer id;
	//任务ID
	private String taskId;
	//任务名称
	private String taskName;
	//派奖号码
	private String tel;
	//派奖牛币数
	private Integer nbNumber;
	//派奖时间
	private Date time;
	//派奖状态
	private Integer state;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	
	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	
	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}
	
	public Integer getNbNumber() {
		return nbNumber;
	}

	public void setNbNumber(Integer nbNumber) {
		this.nbNumber = nbNumber;
	}
	
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
	
	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
}

