package com.ylms.common.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * banner列表
 * 
 * */
public class Banner implements Comparable<Banner>, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//
	private Long id;
	// 广告ID
	private String bannerId;
	// 状态1:上线2:下线0:待审核上线
	private Integer state;
	// 广告名称
	private String name;
	// 连接地址
	private String bannerHttp;
	// 广告图片
	private String imagesHttp;
	// 点击次数
	private Long clickNumber;
	// 删除标识1:未删除2:已删除
	private Integer flag;
	// 上线时间
	private Date onlineTime;
	// 下线时间
	private Date offlineTime;
	// 创建时间
	private Date createTime;
	// 审核时间
	private Date checkTime;
	// 1:审核通过2:审核不通过0:待审核
	private Integer noPass;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBannerId() {
		return bannerId;
	}

	public void setBannerId(String bannerId) {
		this.bannerId = bannerId;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBannerHttp() {
		return bannerHttp;
	}

	public void setBannerHttp(String bannerHttp) {
		this.bannerHttp = bannerHttp;
	}

	public String getImagesHttp() {
		return imagesHttp;
	}

	public void setImagesHttp(String imagesHttp) {
		this.imagesHttp = imagesHttp;
	}

	public Long getClickNumber() {
		return clickNumber;
	}

	public void setClickNumber(Long clickNumber) {
		this.clickNumber = clickNumber;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
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

	public Integer getNoPass() {
		return noPass;
	}

	public void setNoPass(Integer noPass) {
		this.noPass = noPass;
	}

	@Override
	public int compareTo(Banner o) {
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
