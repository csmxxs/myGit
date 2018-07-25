package com.ylms.common.entity;

import java.io.Serializable;
import java.util.Date;

public class TaskCount implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//任务专区ID
	private String prefectureId;
	//任务ID
	private Long taskId;
	//完成
	private Long complete;
	//未完成
	private Long nocomplete;
	//截图数量
	private Long ssscNumber;
	//创建时间
	private Date createTime;
	//临时字段
	private String countName;
	
	public String getCountName() {
		return countName;
	}

	public void setCountName(String countName) {
		this.countName = countName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getPrefectureId() {
		return prefectureId;
	}

	public void setPrefectureId(String prefectureId) {
		this.prefectureId = prefectureId;
	}
	
	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	
	public Long getComplete() {
		return complete;
	}

	public void setComplete(Long complete) {
		this.complete = complete;
	}
	
	public Long getNocomplete() {
		return nocomplete;
	}

	public void setNocomplete(Long nocomplete) {
		this.nocomplete = nocomplete;
	}
	
	public Long getSsscNumber() {
		return ssscNumber;
	}

	public void setSsscNumber(Long ssscNumber) {
		this.ssscNumber = ssscNumber;
	}
	
	public TaskCount (){}
	
	/**
	 * 
	 * 添加任务完成数
	 * */
	public TaskCount (String prefectureId,Long taskId,Long complete){
		
		this.prefectureId=prefectureId;
		this.taskId=taskId;
		this.complete=complete;

		
	}

	/**
	 * 
	 * 添加点击数
	 * */
	public TaskCount (String prefectureId,Long taskId){
		
		this.prefectureId=prefectureId;
		this.taskId=taskId;
		
	}
	/**
	 *初始化任务汇总 
	 * 
	 * */
	public TaskCount (String prefectureId,Long taskId,Long complete,Long nocomplete,Long ssscNumber){
		
		this.prefectureId=prefectureId;
		this.taskId=taskId;
		this.complete=complete;
		this.nocomplete=nocomplete;
		this.ssscNumber=ssscNumber;
		
	}
	
}

