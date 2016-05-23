package com.struts.action;

import com.hibernate.dao.BaseDAO;
import com.hibernate.vo.UserVO;

public class HomeAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private int id;
	private String firstName;
	private String lastName;
	private String email;
	private String phone;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String execute() {
		UserVO userVo =  new UserVO();
		userVo.setUserId(123456);

		try {
			userVo = (UserVO) BaseDAO.save(userVo);
			return "SUCCESS";
		} catch (Exception e) {
			System.out.println("exceprion" + e);
			return "SUCCESS";
		}

	}

	public String getDetail() {
		System.out.println("Test2");
		return SUCCESS;
	}

}
