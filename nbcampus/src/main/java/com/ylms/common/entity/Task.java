package com.ylms.common.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 任务列表
 * 
 * */
public class Task implements Comparable<Task>, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//
	private Long id;
	// 任务名称
	private String name;
	// 任务所属
	private String property;
	// 任务类型
	private Integer taskType;
	// 牛币激励
	private Integer nbStimulate;
	// 推广地区
	private Integer generalize;
	// logo图
	private String logoImages;
	// 描述
	private String text;
	// 联系方式
	private String tel;
	// 任务id
	private String taskId;
	// 任务列表1:通过2:不通过0:待审核
	private Integer noPass;
	// 任务地址
	private String taskHttp;
	// 完成步骤图片存放地址
	private String taskFulfilImg;
	// 上线时间
	private Date onlineTime;
	// 下线时间
	private Date offlineTime;
	// 删除状态1:未删除:2:已删除
	private Integer flag;
	// 是否上线1:上线2:下线
	private Integer state;
	// 创建时间
	private Date createTime;
	// 审核时间
	private Date checkTime;
	// 临时属性
	private Long dayclickNumber;
	private Long daycompleteNumber;
	private Date dayCountTime;
	//任务完成状态
	private Integer taskState;
	//任务是否已收藏
	private Integer cll;
	//任务专区ID
	private String prefectureId;
	//截图是否已上传1:未上传2:已上传
	private Integer ssscImagesBoolean;
	public Date getDayCountTime() {
		return dayCountTime;
	}

	public void setDayCountTime(Date dayCountTime) {
		this.dayCountTime = dayCountTime;
	}

	public Long getDayclickNumber() {
		return dayclickNumber;
	}

	public void setDayclickNumber(Long dayclickNumber) {
		this.dayclickNumber = dayclickNumber;
	}

	public Long getDaycompleteNumber() {
		return daycompleteNumber;
	}

	public void setDaycompleteNumber(Long daycompleteNumber) {
		this.daycompleteNumber = daycompleteNumber;
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

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public Integer getTaskType() {
		return taskType;
	}

	public void setTaskType(Integer taskType) {
		this.taskType = taskType;
	}

	public Integer getNbStimulate() {
		return nbStimulate;
	}

	public void setNbStimulate(Integer nbStimulate) {
		this.nbStimulate = nbStimulate;
	}

	public Integer getGeneralize() {
		return generalize;
	}

	public void setGeneralize(Integer generalize) {
		this.generalize = generalize;
	}

	public String getLogoImages() {
		return logoImages;
	}

	public void setLogoImages(String logoImages) {
		this.logoImages = logoImages;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public Integer getNoPass() {
		return noPass;
	}

	public void setNoPass(Integer noPass) {
		this.noPass = noPass;
	}

	public String getTaskHttp() {
		return taskHttp;
	}

	public void setTaskHttp(String taskHttp) {
		this.taskHttp = taskHttp;
	}

	public String getTaskFulfilImg() {
		return taskFulfilImg;
	}

	public void setTaskFulfilImg(String taskFulfilImg) {
		this.taskFulfilImg = taskFulfilImg;
	}

	public Date getOnlineTime() {
		return onlineTime;
	}

	public void setOnlineTime(Date onlineTime) {
		this.onlineTime = onlineTime;
	}

	public Date getOfflineTime() {
		return offlineTime;
	}

	public void setOfflineTime(Date offlineTime) {
		this.offlineTime = offlineTime;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}

	public Integer getTaskState() {
		return taskState;
	}

	public void setTaskState(Integer taskState) {
		this.taskState = taskState;
	}
	public String getPrefectureId() {
		return prefectureId;
	}

	public void setPrefectureId(String prefectureId) {
		this.prefectureId = prefectureId;
	}
	public Integer getCll() {
		return cll;
	}
	
	public void setCll(Integer cll) {
		this.cll = cll;
	}

	public Integer getSsscImagesBoolean() {
		return ssscImagesBoolean;
	}

	public void setSsscImagesBoolean(Integer ssscImagesBoolean) {
		this.ssscImagesBoolean = ssscImagesBoolean;
	}

	@Override
	public int compareTo(Task o) {
		// 根据ID对象比较
		if (this.id - o.id > 0) {
			return 1;
		} else if (this.id - o.id < 0) {
			return -1;
		} else {
			return 0;
		}
	}

}
