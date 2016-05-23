package com.struts.action;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport implements SessionAware {

	private static final long serialVersionUID = 1L;
	
	Map<String, Object> sessionAttributes;
	  
	// creating sessionAttributes object from sessionAware for session maintain.
	@Override
	public void setSession(Map<String, Object> sessionAttributes) {
		 this.sessionAttributes = sessionAttributes;
		
	}

}
