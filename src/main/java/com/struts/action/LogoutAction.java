package com.struts.action;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

public class LogoutAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	public String execute() {

		// Invalidate current session 
		ServletActionContext.getRequest().getSession().invalidate();
		addActionMessage("You are successfully logout!");
		return "logout";

	}
}