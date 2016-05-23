package com.hibernate.vo;

import java.util.Date;

public class ProjectVO extends BaseVO{
	
	private static final long serialVersionUID = 1L;
	
	private Integer projectId;
	private String projectNum;
	private String projectName;
	private Date startDate;
	private Date endDate;
	private Integer totalEstEffort;
	private Integer designEffort;
	private Integer buildEffort;
	private Integer sitEffort;
	private Integer uatEffort;
	private Integer implEffort;
	private Boolean isActive;
	private Boolean isEnabled;
	
	private Integer actualEffort;

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}


	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getTotalEstEffort() {
		return totalEstEffort;
	}

	public void setTotalEstEffort(Integer totalEstEffort) {
		this.totalEstEffort = totalEstEffort;
	}

	public Integer getDesignEffort() {
		return designEffort;
	}

	public void setDesignEffort(Integer designEffort) {
		this.designEffort = designEffort;
	}

	public Integer getBuildEffort() {
		return buildEffort;
	}

	public void setBuildEffort(Integer buildEffort) {
		this.buildEffort = buildEffort;
	}

	public Integer getSitEffort() {
		return sitEffort;
	}

	public void setSitEffort(Integer sitEffort) {
		this.sitEffort = sitEffort;
	}

	public Integer getUatEffort() {
		return uatEffort;
	}

	public void setUatEffort(Integer uatEffort) {
		this.uatEffort = uatEffort;
	}

	public Integer getImplEffort() {
		return implEffort;
	}

	public void setImplEffort(Integer implEffort) {
		this.implEffort = implEffort;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Boolean getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public Integer getActualEffort() {
		return actualEffort;
	}

	public void setActualEffort(Integer actualEffort) {
		this.actualEffort = actualEffort;
	}

	public String getProjectNum() {
		return projectNum;
	}

	public void setProjectNum(String projectNum) {
		this.projectNum = projectNum;
	}
	
	
	

}
