package com.hibernate.vo;

import java.util.Date;

public class EffortsVO extends BaseVO{


	private static final long serialVersionUID = 1L;
	private Integer effortId;
	private ProjectVO projectVo;
	private UserVO userVo;
	private Date effortDate;
	private String effortType;
	private Integer effortHrs;

	public Integer getEffortId() {
		return effortId;
	}
	public void setEffortId(Integer effortId) {
		this.effortId = effortId;
	}
	public ProjectVO getProjectVo() {
		return projectVo;
	}
	public void setProjectVo(ProjectVO projectVo) {
		this.projectVo = projectVo;
	}
	public UserVO getUserVo() {
		return userVo;
	}
	public void setUserVo(UserVO userVo) {
		this.userVo = userVo;
	}
	public Date getEffortDate() {
		return effortDate;
	}
	public void setEffortDate(Date effortDate) {
		this.effortDate = effortDate;
	}
	public String getEffortType() {
		return effortType;
	}
	public void setEffortType(String effortType) {
		this.effortType = effortType;
	}
	public Integer getEffortHrs() {
		return effortHrs;
	}
	public void setEffortHrs(Integer effortHrs) {
		this.effortHrs = effortHrs;
	}
}
