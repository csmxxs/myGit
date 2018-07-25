package com.ylms.common.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 
 * 
 * 任务专区表
 * 
 * */
public class Prefecture implements Comparable<Prefecture>,Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	// 专区入口地址
	private String prefectureHttp;
	// 任务名称
	private String name;
	// 任务未完成总数
	private Long noComplete;
	// 任务完成总数
	private long complete;
	// 任务截图数
	private Long ssscNumber;
	// 状态1：上线2：下线
	private Integer state;
	// 图片地址
	private String logoImagesHttp;
	// 删除标识2:删除1:显现
	private Integer flag;
	// 创建时间
	private Date createTime;
	// 临时字段,装载包含的任务列表
	private List<Object> taskList;

	public Long getNoComplete() {
		return noComplete;
	}

	public void setNoComplete(Long noComplete) {
		this.noComplete = noComplete;
	}

	public long getComplete() {
		return complete;
	}

	public void setComplete(long complete) {
		this.complete = complete;
	}

	public Long getSsscNumber() {
		return ssscNumber;
	}

	public void setSsscNumber(Long ssscNumber) {
		this.ssscNumber = ssscNumber;
	}

	@SuppressWarnings("rawtypes")
	public List getTaskList() {
		return taskList;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setTaskList(List taskList) {
		this.taskList = taskList;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPrefectureHttp() {
		return prefectureHttp;
	}

	public void setPrefectureHttp(String prefectureHttp) {
		this.prefectureHttp = prefectureHttp;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getLogoImagesHttp() {
		return logoImagesHttp;
	}

	public void setLogoImagesHttp(String logoImagesHttp) {
		this.logoImagesHttp = logoImagesHttp;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int compareTo(Prefecture o) {
		// 根据ID对象比较
		if (new Long(this.id) - new Long(o.id) > 0) {
			return 1;
		} else if (new Long(this.id) - new Long(o.id) < 0) {
			return -1;
		} else {
			return 0;
		}

	}
}
