package com.struts.action;

import org.hibernate.HibernateException;

import com.hibernate.dao.BaseDAO;
import com.hibernate.vo.UserVO;
import com.struts.util.ApplicationConstant;
import com.struts.util.ApplicationUtil;

public class LoginAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	private UserVO uservo;

	// Method to authenticate  
	public String execute() {
		if (isAdmin()) {
			sessionAttributes.put(ApplicationConstant.USER_SESSION, "Administrator");
			return "adminHome";
			
		}else if(isUser()){
			sessionAttributes.put(ApplicationConstant.USER_SESSION, uservo.getFullName());
			sessionAttributes.put("UserId", uservo.getUserId());
			return "userHome";
		}else{
			return INPUT;
		}
	}

	// private method to validate admin  
	private boolean isAdmin() {
		boolean flag = false;
		if (this.username.trim().equals(getText("admin.username")) && this.password.trim().equals(getText("admin.password"))) {
			flag = true;
		}
		return flag;
	}
	
	// private method to validate user  
	private boolean isUser() {
	boolean flag = true;
	
		if(ApplicationUtil.isInteger(this.username)==-1){
			flag = false;
			addFieldError("username", "User name should be Numeric");
		}else if(!ApplicationUtil.betweenExclusive(
				ApplicationUtil.isInteger(this.username),5,7)){
			flag = false;
			addFieldError("username", "User name should be 6 Numeric value");
		}else {
			try{
				uservo = (UserVO)BaseDAO.retrieveForValue(Integer.parseInt(this.username.trim()), ApplicationConstant.USERVO);
				if(null == uservo){
					flag = false;
					addActionError("The user name is not available in system. Please contact administrator");
				}else if(uservo.getIsEnabled() && this.password.trim().equals(uservo.getPassword())){
					flag = true;
				}else if(!uservo.getIsEnabled()){
					flag = false;
					addActionError("This user is not authorised. Please contact administrator");
				}else if(!this.password.equals(uservo.getPassword())){
					flag = false;
					addActionError("The user name or password you entered isn't correct. Try again.");
				}else{
					addActionError("Error in Login: Please contact technical Support");
					flag = false;
				}
				
			}catch(HibernateException e){
				addActionError("Error in Login: Please contact technical Support");
				return false;
			}
		}
		return flag;
	}
	
	
	public String adminHome(){
		return SUCCESS;
	}
	
	public String userHome(){
		return SUCCESS;
	}

	
	// Method to validate username and password  
	public void validate() {
		if (username == null || username.trim().equals("")) {
			addFieldError("username", "The User name is required");
		}
		if (password == null || password.trim().equals("")) {
			addFieldError("password", "The Password is required");
		}
	}

	public String logout() {
		return "logout";
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
