package com.struts.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;

import com.hibernate.dao.BaseDAO;
import com.hibernate.vo.UserVO;
import com.struts.util.ApplicationConstant;
import com.struts.util.ApplicationUtil;

public class UserAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private List<UserVO> userList;
	private Map<String, String> roleMap;
	private UserVO userVO = new UserVO();
	private String userid;
	private String fullname;
	private String role;
	private Boolean status;


	// View user records in User page
	public String viewUser() {
		try{
			userList = new ArrayList<UserVO>();
			userList = BaseDAO.retrieve(ApplicationConstant.USERVO, null, null, false, ApplicationConstant.USERVO_USERID);
			return SUCCESS;
			}catch(HibernateException e){
				addActionError("Error in Retriving values: Please contact technical Support ");
				return INPUT;
			}
	}
	
	// Add new user button action
	public String addUser() {
		setRoleMap();
		role = "U";
		status= false;
		return SUCCESS;
	}
	
	// save new user button action
	public String saveUser() {
		if (validateUser()) {
			setRoleMap();
			addActionError("*Mandatory fields are required");
			return INPUT;
		} else {
			setFormToDB();
			userVO.setPassword(getText("user.default.password"));
			userVO.setPasswordFlag(true);
			userVO.setCreatedDate(new Date());
			userVO.setCreatedBy(getText("admin.username"));
			try {
				if (null == BaseDAO.retrieveForValue(userVO.getUserId(), ApplicationConstant.USERVO)) {

					userVO = (UserVO) BaseDAO.save(userVO);
					addActionMessage("Save Success : User " + userVO.getUserId()
							+ " is saved succesfully, Default password is " + getText("user.default.password")
							+ " and it can be reset after first login");
					return SUCCESS;
				} else {
					addActionError("Save Failed : User " + userVO.getUserId() + " already exists");
					setRoleMap();
					role = userVO.getRole();
					return INPUT;
				}
			} catch (HibernateException e) {
				addActionError("Save Failed : Please contact technical Support");
				setRoleMap();
				role = userVO.getRole();
				return INPUT;
			}
		}
	}

	// Set User Id for request.
	public String setUserID() {
		sessionAttributes.put("UserId", userid);
		return SUCCESS;
	}
	
	// Edit existing user button action opens Edit page with required fields.
	public String editUser() {

		if (sessionAttributes.containsKey("UserId")) {
			String UserId = (String) sessionAttributes.get("UserId");
			userVO = (UserVO) BaseDAO.retrieveForValue(Integer.parseInt(UserId), ApplicationConstant.USERVO);
		}
		sessionAttributes.put("UserVO", userVO);
		setDBtoForm();
		setRoleMap();
		return SUCCESS;
	}
	
	// Update existing user button action.
	public String updateUser() {
		if (validateUser()) {
			addActionError("*Mandatory fields are required");
			return INPUT;
		} else {
			if (sessionAttributes.containsKey("UserVO")) {
				userVO = (UserVO) sessionAttributes.get("UserVO");
			}
			setFormToDB();
			try {
				userVO = (UserVO) BaseDAO.merge(userVO);
				addActionMessage("Update Success : User" + userVO.getUserId() + "is Updated succesfully");
				return SUCCESS;

			} catch (HibernateException e) {
				addActionError("Update Failed : Please contact technical Support");
				setRoleMap();
				role = userVO.getRole();
				return INPUT;
			}

		}

	}
	
	// Reset password for existing user.
	public String resetUser() {
		if (sessionAttributes.containsKey("UserId")) {
			userid = (String) sessionAttributes.get("UserId");
		}
		if (BaseDAO.resetPassword(Integer.parseInt(userid), getText("user.default.password"), true)) {
			addActionMessage("Reset Passoword Success: Reset password succesfully for user: " + userid
					+ ", Default password is " + getText("user.default.password")
					+ " and it can be reset after first login");
		} else {
			addActionError("Reset Passoword Failed: Please contact technical Support");
		}
		return SUCCESS;
	}

	// Enable user for existing user.
	public String enableUser() {
		if (sessionAttributes.containsKey("UserId")) {
			userid = (String) sessionAttributes.get("UserId");
		}
		if (BaseDAO.userEnableDisable(Integer.parseInt(userid), true)) {
			addActionMessage("Enable Success: " + userid + " enabled succesfully");
		} else {
			addActionError("Enable Failed: Please contact technical Support");
		}

		return SUCCESS;
	}
	
	// Disable user for existing user.
	public String disableUser() {
		if (sessionAttributes.containsKey("UserId")) {
			userid = (String) sessionAttributes.get("UserId");
		}
		if (BaseDAO.userEnableDisable(Integer.parseInt(userid), false)) {
			addActionMessage("Disable Success: " + userid + " disabled succesfully");
		} else {
			addActionError("Disable Failed: Please contact technical Support");
		}
		return SUCCESS;
	}

	// private user for validation method 
	private boolean validateUser() {
		boolean flag = false;
		if (fullname == null || fullname.trim().equals("")) {
			flag = true;
			addFieldError("fullname", "The Full Name is required");
		}
		if (userid == null || userid.equals("")) {
			flag = true;
			addFieldError("userid", "The User id is required");
		} else if (ApplicationUtil.isInteger(this.userid) == -1) {
			flag = true;
			addFieldError("userid", "User id should be Numeric");
		} else if (!ApplicationUtil.betweenExclusive(ApplicationUtil.isInteger(this.userid), 5, 7)) {
			flag = true;
			addFieldError("userid", "User id should be 6 Numeric value");
		}
		return flag;
	}

	// Assign dropdown value for Role field in user form
	private void setRoleMap() {
		roleMap = new HashMap<String, String>();
		roleMap.put("A", "Admin");
		roleMap.put("U", "User");
		
	}

	// set DB to form variables.
	private void setDBtoForm() {
		userid = String.valueOf(userVO.getUserId());
		fullname = userVO.getFullName();
		role = userVO.getRole();
		status = userVO.getIsEnabled();
	}
	// set Form to DB variables.
	private void setFormToDB() {
		userVO.setUserId(Integer.parseInt(userid));
		userVO.setIsEnabled(status);
		userVO.setFullName(fullname);
		userVO.setRole(role);
		userVO.setUpdatedDate(new Date());
		userVO.setUpdatedBy(getText("admin.username"));
		}
	
	
	public List<UserVO> getUserList() {
		return userList;
	}

	public void setUserList(List<UserVO> userList) {
		this.userList = userList;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	
	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Map<String, String> getRoleMap() {
		return roleMap;
	}

	public void setRoleMap(Map<String, String> roleMap) {
		this.roleMap = roleMap;
	}
}
