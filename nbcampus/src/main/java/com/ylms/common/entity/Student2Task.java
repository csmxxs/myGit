package com.ylms.common.entity;

public class Student2Task {
	//数据库ID
	private Long id;
	//学生电话
	private String studentTel;
	//任务专区数据库ID
	private String prefectureId;
	//任务列表数据库ID
	private Long taskId;
	//任务状态1:完成2:未完成
	private Integer taskState;
	//是否已收藏0:已收藏1:未收藏
	private Integer collect;
	//截图是否已上传1:未上传2:已上传
	private Integer ssscImagesBoolean;

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

	public Integer getTaskState() {
		return taskState;
	}

	public void setTaskState(Integer taskState) {
		this.taskState = taskState;
	}

	public String getStudentTel() {
		return studentTel;
	}

	public void setStudentTel(String studentTel) {
		this.studentTel = studentTel;
	}

	public Integer getCollect() {
		return collect;
	}

	public void setCollect(Integer collect) {
		this.collect = collect;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getSsscImagesBoolean() {
		return ssscImagesBoolean;
	}

	public void setSsscImagesBoolean(Integer ssscImagesBoolean) {
		this.ssscImagesBoolean = ssscImagesBoolean;
	}
}

