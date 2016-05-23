package com.servlet.listener;

import com.hibernate.util.HibernateUtil;
import com.opensymphony.xwork2.util.LocalizedTextUtil;
import com.struts.util.ApplicationConstant;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class GlobalListener implements ServletContextListener{
	
	public void contextInitialized(ServletContextEvent arg0) {
		// Load application.prop file at server initialized 
		LocalizedTextUtil.addDefaultResourceBundle(ApplicationConstant.DEFAULT_RESOURCE);
		// Load SessionFactory at server initialized 
		HibernateUtil.getSessionFactory();
		
	}

	public void contextDestroyed(ServletContextEvent arg0) {
		// close SessionFactory at server destroy.
		HibernateUtil.closeSessionFactory();
		
	}
	
}
