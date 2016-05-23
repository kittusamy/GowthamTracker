package com.struts.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;

import com.hibernate.dao.BaseDAO;
import com.hibernate.vo.EffortsVO;
import com.hibernate.vo.ProjectVO;
import com.hibernate.vo.UserVO;
import com.struts.util.ApplicationConstant;
import com.struts.util.ApplicationUtil;

public class EffortsAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private Map projectMap;
	private Map effortTypeMap;

	private List<EffortsVO> effortsUserList;

	private String userIdName;
	private String password1;
	private String password2;
	private UserVO uservo;
	private EffortsVO effortsvo = new EffortsVO();
	private ProjectVO projectVo = new ProjectVO();
	private Integer effortsId;
	private Integer projectId;
	private String effortType;
	private Date effortDate;
	private Integer actualEffort;
	private boolean editFlag;

	
	public String userHome() {
		if (sessionAttributes.containsKey("actionMessage")) {
			addActionMessage((String) sessionAttributes.get("actionMessage"));
			sessionAttributes.remove("actionMessage");
		}
		if (sessionAttributes.containsKey("actionError")) {
			addActionError((String) sessionAttributes.get("actionError"));
			sessionAttributes.remove("actionError");
		}
		if (sessionAttributes.containsKey("UserId")) {
			uservo = (UserVO) BaseDAO.retrieveForValue((Integer) sessionAttributes.get("UserId"),
					ApplicationConstant.USERVO);
			sessionAttributes.put("USERVO", uservo);
		}
		userIdName = uservo.getUserId() + " - " + uservo.getFullName();
		if (uservo.getPasswordFlag()) {
			return "resetUserPassword";
		} else {
			setEffortsUserList(new ArrayList<EffortsVO>());
			setEffortsUserList(BaseDAO.retrieveEffortsForUser(uservo));
			if (!editFlag) {
				setDefaultValues();
			}
			return SUCCESS;
		}

	}

	public String saveEfforts() {
		
		if (validateEfforts()) {

			if (sessionAttributes.containsKey("USERVO")) {
				uservo = (UserVO) sessionAttributes.get("USERVO");
			}
			setFormToDB();
			effortsvo.setCreatedDate(new Date());
			effortsvo.setCreatedBy(uservo.getFullName());
			
			try {
				BaseDAO.saveOrUpdate(effortsvo);
				sessionAttributes.put("actionMessage", "Success : Efforts added succesfully");
				setEditFlag(false);
				return SUCCESS;
			} catch (HibernateException e) {
				sessionAttributes.put("actionError", "Save Failed : Please contact technical Support");
				setEditFlag(false);
				return INPUT;
			}
		} else {
			addActionError("*Mandatory fields are required");
			return INPUT;
		}

	}

	private void setDefaultValues() {
		projectId = -1;
		effortType = "-1";
		effortDate = new Date();
		actualEffort = 0;
	}

	private boolean validateEfforts() {
		boolean flag = true;
		if (projectId == null || projectId.equals(-1)) {
			flag = false;
			addFieldError("projectId", "Please select your project");
		}
		if (effortType == null || effortType.trim().equals("-1")) {
			flag = false;
			addFieldError("effortType", "Please select the effort type of phase");
		}
		if (effortDate == null || effortDate.equals("")) {
			flag = false;
			effortDate = new Date();
		}
		if (actualEffort == null || actualEffort.equals("")) {
			flag = false;
			addFieldError("actualEffort", "Please enter the actual effort");
		}
		return flag;

	}

	public String resetPassword() {
		if (sessionAttributes.containsKey("UserId")) {
			uservo = (UserVO) BaseDAO.retrieveForValue((Integer) sessionAttributes.get("UserId"),
					ApplicationConstant.USERVO);
		}
		if (validateNewPassword()) {
			BaseDAO.resetPassword(uservo.getUserId(), password1, false);
			return SUCCESS;
		}
		return INPUT;
	}

	private boolean validateNewPassword() {

		boolean flag = true;
		if (password1 == null || password1.trim().equals("")) {
			flag = false;
			addFieldError("password1", "The new password is required");
		}
		if (password2 == null || password2.trim().equals("")) {
			flag = false;
			addFieldError("password2", "The re-type new password is required");
		}
		if (!password1.trim().equals(password2.trim())) {
			flag = false;
			addFieldError("password2", "password is not matched with new password");
		}

		return flag;
	}

	public String setEffortID() {
		sessionAttributes.put("EffortsId", effortsId);
		return SUCCESS;
	}

	public String deleteEffort() {
		if (sessionAttributes.containsKey("EffortsId")) {
			effortsId = (Integer) sessionAttributes.get("EffortsId");
			BaseDAO.delete(effortsId, ApplicationConstant.EFFORTSVO);
			addActionMessage("Delete Success");
		}
		return SUCCESS;
	}

	public String editEffort() {
		if (sessionAttributes.containsKey("EffortsId")) {
			effortsId = (Integer) sessionAttributes.get("EffortsId");
			effortsvo = (EffortsVO) BaseDAO.retrieveForValue(effortsId, ApplicationConstant.EFFORTSVO);
			setDBtoForm();

			setEditFlag(true);

		}
		return SUCCESS;
	}

	// set DB to form variables.
	private void setDBtoForm() {
		effortsId = effortsvo.getEffortId();
		uservo = effortsvo.getUserVo();
		projectVo = effortsvo.getProjectVo();
		projectId = effortsvo.getProjectVo().getProjectId();
		effortType = effortsvo.getEffortType();
		effortDate = effortsvo.getEffortDate();
		actualEffort = effortsvo.getEffortHrs();

	}

	// set Form to DB variables.
	private void setFormToDB() {
		effortsvo.setEffortId(effortsId);
		effortsvo.setUserVo(uservo);
		projectVo = (ProjectVO) BaseDAO.retrieveForValue(projectId, ApplicationConstant.PROJECTVO);
		effortsvo.setProjectVo(projectVo);
		effortsvo.setEffortType(effortType);
		effortsvo.setEffortDate(effortDate);
		effortsvo.setEffortHrs(actualEffort);
		effortsvo.setUpdatedDate(new Date());
		effortsvo.setUpdatedBy(uservo.getFullName());
	}

	public String getPassword1() {
		return password1;
	}

	public void setPassword1(String password1) {
		this.password1 = password1;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	public String getUserIdName() {
		return userIdName;
	}

	public void setUserIdName(String userIdName) {
		this.userIdName = userIdName;
	}

	public Integer getEffortsId() {
		return effortsId;
	}

	public void setEffortsId(Integer effortsId) {
		this.effortsId = effortsId;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public String getEffortType() {
		return effortType;
	}

	public void setEffortType(String effortType) {
		this.effortType = effortType;
	}

	public Date getEffortDate() {
		return effortDate;
	}

	public void setEffortDate(Date effortDate) {
		this.effortDate = effortDate;
	}

	public Map getProjectMap() {
		projectMap = new HashMap<Integer, String>();
		List<ProjectVO> projectList = BaseDAO.retrieve(ApplicationConstant.PROJECTVO, true, true, true,
				ApplicationConstant.UPDATED_DATE);
		for (ProjectVO projectVO : projectList) {
			projectMap.put(projectVO.getProjectId(), projectVO.getProjectNum() + " - " + projectVO.getProjectName());
		}
		return projectMap;
	}

	public void setProjectMap(Map projectMap) {

		this.projectMap = projectMap;
	}

	public Map getEffortTypeMap() {
		return ApplicationUtil.getEffortTypeMap();
	}

	public void setEffortTypeMap(Map effortTypeMap) {
		this.effortTypeMap = effortTypeMap;
	}

	public Integer getActualEffort() {
		return actualEffort;
	}

	public void setActualEffort(Integer actualEffort) {
		this.actualEffort = actualEffort;
	}

	public List<EffortsVO> getEffortsUserList() {
		return effortsUserList;
	}

	public void setEffortsUserList(List<EffortsVO> effortsUserList) {
		this.effortsUserList = effortsUserList;
	}

	public boolean getEditFlag() {
		return editFlag;
	}

	public void setEditFlag(boolean editFlag) {
		this.editFlag = editFlag;
	}

}
