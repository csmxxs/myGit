package com.ylms.common.entity;

/**
 * 用户邀请人
 * 
 * */
public class StudentInviter {
	// 被邀请人电话
	private String inviteeTel;
	// 被邀请人名称
	private String inviteName;
	// 邀请人级别
	private Integer inviteLevel;
	// 邀请人电话
	private String inviteTel;

	public String getInviteName() {
		return inviteName;
	}

	public void setInviteName(String inviteName) {
		this.inviteName = inviteName;
	}

	public Integer getInviteLevel() {
		return inviteLevel;
	}

	public void setInviteLevel(Integer inviteLevel) {
		this.inviteLevel = inviteLevel;
	}

	public String getInviteTel() {
		return inviteTel;
	}

	public void setInviteTel(String inviteTel) {
		this.inviteTel = inviteTel;
	}

	public StudentInviter(String inviteeTel, String inviteName,
			Integer inviteLevel, String inviteTel) {
		this.inviteeTel = inviteeTel;
		this.inviteName = inviteName;
		this.inviteLevel = inviteLevel;
		this.inviteTel = inviteTel;
	}

	public StudentInviter() {

	}

	public String getInviteeTel() {
		return inviteeTel;
	}

	public void setInviteeTel(String inviteeTel) {
		this.inviteeTel = inviteeTel;
	}

}
