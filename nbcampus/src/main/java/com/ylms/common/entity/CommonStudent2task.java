package com.ylms.common.entity;

/**
 * 
 * 记录用户的公共任务列表的信息
 * 
 * 
 * */
public class CommonStudent2task {
	// 数据库ID
	private Long id;
	// 用户电话
	private String studentTel;
	// 任务专区数据库ID
	private String commonPrefectureId;
	// 任务列表数据库ID
	private Long commonTaskId;
	// 任务列表完成状态1:完成2:未完成
	private Integer commonTaskState;
	// 收藏状态0:已收藏1:未收藏
	private Integer commonCollect;
	// 截图是否已上传1:未上传2:已上传
	private Integer ssscImagesBoolean;
	// 派发状态1:未派发2:派发
	private Integer commonState;
	// 平台生成的订单号
	private String orderNo;
	// 客户端请求的流水号
	private String requestNo;
	// 剩余赠送次数
	private Integer availableNum;
	// 任务截图地址
	private String taskSsscImagesUrl;

	public Integer getCommonState() {
		return commonState;
	}

	public void setCommonState(Integer commonState) {
		this.commonState = commonState;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getRequestNo() {
		return requestNo;
	}

	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}

	public Integer getAvailableNum() {
		return availableNum;
	}

	public void setAvailableNum(Integer availableNum) {
		this.availableNum = availableNum;
	}

	public String getStudentTel() {
		return studentTel;
	}

	public void setStudentTel(String studentTel) {
		this.studentTel = studentTel;
	}

	public String getCommonPrefectureId() {
		return commonPrefectureId;
	}

	public void setCommonPrefectureId(String commonPrefectureId) {
		this.commonPrefectureId = commonPrefectureId;
	}

	public Long getCommonTaskId() {
		return commonTaskId;
	}

	public void setCommonTaskId(Long commonTaskId) {
		this.commonTaskId = commonTaskId;
	}

	public Integer getCommonTaskState() {
		return commonTaskState;
	}

	public void setCommonTaskState(Integer commonTaskState) {
		this.commonTaskState = commonTaskState;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getCommonCollect() {
		return commonCollect;
	}

	public void setCommonCollect(Integer commonCollect) {
		this.commonCollect = commonCollect;
	}

	public Integer getSsscImagesBoolean() {
		return ssscImagesBoolean;
	}

	public void setSsscImagesBoolean(Integer ssscImagesBoolean) {
		this.ssscImagesBoolean = ssscImagesBoolean;
	}

	public String getTaskSsscImagesUrl() {
		return taskSsscImagesUrl;
	}

	public void setTaskSsscImagesUrl(String taskSsscImagesUrl) {
		this.taskSsscImagesUrl = taskSsscImagesUrl;
	}

}
