package com.hibernate.vo;

public class UserVO extends BaseVO{

	private static final long serialVersionUID = 1L;
	private Integer userId;
	private String fullName;
	private String password;
	private Boolean passwordFlag;
	private String role;
	private Boolean isEnabled;
	
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Boolean getPasswordFlag() {
		return passwordFlag;
	}
	public void setPasswordFlag(Boolean passwordFlag) {
		this.passwordFlag = passwordFlag;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public Boolean getIsEnabled() {
		return isEnabled;
	}
	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	
	

	
	
}
