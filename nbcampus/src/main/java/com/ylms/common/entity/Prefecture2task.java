package com.ylms.common.entity;
/**
 * 中间表
 * 
 * */
public class Prefecture2task {
	//任务专区ID
	private String prefectureId;
	//任务列表ID
	private Long taskId;
	
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
}
