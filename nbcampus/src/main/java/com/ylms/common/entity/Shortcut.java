package com.ylms.common.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 快捷功能入口列表
 * 
 * **/
public class Shortcut implements Comparable<Shortcut>, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 快捷入口列表
	private Long id;
	private String shortcutId;
	// 入口名称
	private String name;
	// 状态
	private Integer state;
	// 列表排序
	private Integer number;
	// 连接地址
	private String shortcutHttp;
	// 列表图片
	private String images;
	// 2：已删除
	private Integer flag;
	// 创建日期
	private Date createTime;

	public String getShortcutId() {
		return shortcutId;
	}
	public void setShortcutId(String shortcutId) {
		this.shortcutId = shortcutId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getShortcutHttp() {
		return shortcutHttp;
	}

	public void setShortcutHttp(String shortcutHttp) {
		this.shortcutHttp = shortcutHttp;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	@Override
	public int compareTo(Shortcut o) {
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
