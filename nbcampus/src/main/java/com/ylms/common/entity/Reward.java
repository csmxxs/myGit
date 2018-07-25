package com.ylms.common.entity;

public class Reward {
	//派奖账号表
	private Integer id;
	//账号
	private String account;
	//账号类型
	private String accountType;
	//剩余牛币
	private Long residuum;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
	
	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	
	public Long getResiduum() {
		return residuum;
	}

	public void setResiduum(Long residuum) {
		this.residuum = residuum;
	}
}

