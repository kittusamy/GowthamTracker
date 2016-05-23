package com.hibernate.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hibernate.util.HibernateUtil;
import com.hibernate.vo.TempVO;
import com.hibernate.vo.EffortsVO;
import com.hibernate.vo.ProjectVO;
import com.hibernate.vo.UserVO;
import com.struts.util.ApplicationConstant;

public class BaseDAO {
	private final static Logger logger = LoggerFactory.getLogger(BaseDAO.class);

	/**
	 * Common method to save single object
	 * 
	 */
	
	
	
	public static Object save(Object objectVo) throws HibernateException {

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			logger.info("Inside BaseDAO::save()");
			session.beginTransaction();
			session.save(objectVo);
			session.flush();
			session.getTransaction().commit();
			logger.info("Exit BaseDAO::save()");
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			throw new HibernateException("Error occured while data insertion");
		}
		return objectVo;
	}

	
	/**
	 * Common method to saveOrUpdate single object
	 * 
	 */
	public static Object saveOrUpdate(Object objectVo) throws HibernateException {

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			logger.info("Inside BaseDAO::saveOrUpdate()");
			session.beginTransaction();
			session.saveOrUpdate(objectVo);
			session.flush();
			session.getTransaction().commit();
			logger.info("Exit BaseDAO::saveOrUpdate()");
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			throw new HibernateException("Error occured while data insertion or updation");
		}
		return objectVo;
	}

	
	/**
	 * Common method to merge single object
	 * 
	 */
	public static Object merge(Object objectVo) throws HibernateException {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			logger.info("Entered BaseDAO:: merge");
			session.beginTransaction();
			session.merge(objectVo);
			session.flush();
			session.getTransaction().commit();
			logger.info("Exit BaseDAO:: merge");
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			throw new HibernateException("Error occured while data merge");
		}
		return objectVo;
	}

	
	/**
	 * This method is used to delete the record from the database
	 * 
	 */
	public static void delete(Integer id, String className) throws HibernateException {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			logger.info("Entered BaseDAO:: delete");
			session.beginTransaction();
			Object persistentInstance = session.load(Class.forName(className), id);
			if (persistentInstance != null) {
				session.delete(persistentInstance);
			}
			logger.info("Exit BaseDAO:: delete");
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			throw new HibernateException("Error occured while data deletion");
		}
		session.getTransaction().commit();

	}

	
	/**
	 * This method is used to reset user Password
	 * 
	 */
	public static boolean resetPassword(Integer userId, String password, boolean passwordFlag) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		boolean flag = false;
		int result;

		try {
			logger.info("Entered BaseDAO:: resetPassword");
			String hql = "UPDATE UserVO set password = :password, passwordFlag =:passwordFlag, updatedDate =:updatedDate "
					+ "WHERE userId = :user";
			Query query = session.createQuery(hql);
			query.setParameter("password", password);
			query.setParameter("passwordFlag", passwordFlag);
			query.setParameter("updatedDate", new Date());
			query.setInteger("user", userId);
			result = query.executeUpdate();
			session.flush();
			session.getTransaction().commit();
			logger.info("Exit BaseDAO:: resetPassword");
		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			throw new HibernateException("Error occured while Reset Password");
		}
		if (0 < result) {
			flag = true;
		}
		return flag;
	}

	
	/**
	 * method for retrieve objects for primary key
	 */
	public static Object retrieveForValue(Integer id, String className) throws HibernateException {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Object obj = null;
		try {
			logger.info("Entered BaseDAO:: retrieveForValue");
			obj = session.get(Class.forName(className), id);
			session.clear();
			logger.info("Exit BaseDAO:: retrieveForValue");
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			throw new HibernateException("Error occured while retrieve For Value");
		}
		session.getTransaction().commit();
		return obj;
	}

	
	/**
	 * method to retrieve objects for primary key with some filters
	 */
	public static List retrieve(String className, Boolean enabled, Boolean active, boolean order, String orderBy)
			throws HibernateException {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List objList = null;
		try {
			logger.info("Entered BaseDAO:: retrieve");
			Criteria criteria = session.createCriteria(Class.forName(className));
			if (null != enabled) {
				criteria.add(Restrictions.eq("isEnabled", enabled));
			}
			if (null != active) {
				criteria.add(Restrictions.eq("isActive", active));
			}
			if (order) {
				criteria.addOrder(Order.desc(orderBy));
			} else {
				criteria.addOrder(Order.asc(orderBy));
			}
			objList = criteria.list();
			logger.info("Exit BaseDAO:: retrieve");
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			throw new HibernateException("Error occured while retrieving");
		}
		session.getTransaction().commit();
		return objList;
	}

	
	/**
	 * method to Enable or Disable user
	 */
	public static boolean userEnableDisable(Integer userId, Boolean status) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		boolean flag = false;
		int result;

		try {
			logger.info("Entered BaseDAO:: userEnableDisable");
			String hql = "UPDATE UserVO set isEnabled = :status " + "WHERE userId = :user";
			Query query = session.createQuery(hql);
			query.setParameter("status", status);
			query.setInteger("user", userId);
			result = query.executeUpdate();
			session.flush();
			session.getTransaction().commit();
			logger.info("Exit BaseDAO:: userEnableDisable");
		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			throw new HibernateException("Error occured while Enable Disable User");
		}
		if (0 < result) {
			flag = true;
		}
		return flag;
	}

	
	/**
	 * method for Enable or Disable project
	 */
	public static boolean projectEnableDisable(Integer projectId, Boolean status) {

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		boolean flag = false;
		int result;

		try {
			logger.info("Entered BaseDAO:: projectEnableDisable");
			String hql = "UPDATE ProjectVO set isEnabled = :status " + "WHERE projectId = :projectId";
			Query query = session.createQuery(hql);
			query.setParameter("status", status);
			query.setInteger("projectId", projectId);
			result = query.executeUpdate();
			session.flush();
			session.getTransaction().commit();
			logger.info("Exit BaseDAO:: projectEnableDisable");
		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			throw new HibernateException("Error occured while Enable Disable Project");
		}
		if (0 < result) {
			flag = true;
		}
		return flag;
	}

	
	/**
	 * method to retrieve Efforts For User
	 */
	public static List retrieveEffortsForUser(UserVO userVO) throws HibernateException {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List objList = null;
		try {
			logger.info("Entered BaseDAO:: retrieveEffortsForUser");
			Criteria criteria = session.createCriteria(EffortsVO.class).setFetchMode("projectVo", FetchMode.JOIN);
			criteria.add(Restrictions.eq("userVo", userVO));
			criteria.addOrder(Order.desc(ApplicationConstant.UPDATED_DATE));
			objList = criteria.list();
			logger.info("Exit BaseDAO:: retrieveEffortsForUser");
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			throw new HibernateException("Error occured while retrieving Efforts For User");
		}
		session.getTransaction().commit();
		return objList;
	}

	
	/**
	 * method to retrieve Efforts For Project
	 */
	public static List<EffortsVO> retrieveEffortsForProject(ProjectVO projectVO, Integer userId, String effortType,
			Date startDate, Date endDate) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List objList = null;
		try {
			logger.info("Entered BaseDAO:: retrieveEffortsForProject");
			Criteria criteria = session.createCriteria(EffortsVO.class).setFetchMode("userVo", FetchMode.JOIN);
			criteria.add(Restrictions.eq("projectVo", projectVO));
			if (null != userId && -1 != userId) {
				criteria.add(Restrictions.eq("userVo.userId", userId));
			}
			if (null != effortType && !effortType.equals("null")) {
				criteria.add(Restrictions.eq("effortType", effortType));
			}
			if (null != startDate) {
				criteria.add(Restrictions.ge("effortDate", startDate));
			}
			if (null != endDate) {
				criteria.add(Restrictions.le("effortDate", endDate));
			}

			criteria.addOrder(Order.desc(ApplicationConstant.UPDATED_DATE));
			objList = criteria.list();
			logger.info("Exit BaseDAO:: retrieveEffortsForProject");
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();

			throw new HibernateException("Error occured while retrieving Efforts For Project");
		}
		session.getTransaction().commit();
		return objList;
	}

	/**
	 * method to get Actual Efforts
	 */
	public static List getActualEfforts(ProjectVO projectVO) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List objList = null;
		try {
			logger.info("Entered BaseDAO:: getActualEfforts");
			Criteria criteria = session.createCriteria(EffortsVO.class);
			criteria.add(Restrictions.eq("projectVo", projectVO));

			ProjectionList projList = Projections.projectionList();
			projList.add(Projections.groupProperty("effortType"), "stringValue");
			projList.add(Projections.sum("effortHrs"), "longValue");
			criteria.setProjection(projList);

			criteria.setResultTransformer(Transformers.aliasToBean(TempVO.class));

			objList = criteria.list();
			logger.info("Exit BaseDAO:: getActualEfforts");
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			throw new HibernateException("Error occured while retrieving Actual Efforts");
		}
		session.getTransaction().commit();
		return objList;
	}

	/**
	 * method to get All User for Project
	 */
	public static List getAllUserforProject(Integer projectId) throws HibernateException {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List useridList = null;
		try {
			logger.info("Entered BaseDAO:: getAllUserforProject");
			Criteria criteria = session.createCriteria(EffortsVO.class).setFetchMode("userVo", FetchMode.JOIN);

			ProjectionList projList = Projections.projectionList();
			projList.add(Projections.property("userVo.userId"));
			criteria.setProjection(Projections.distinct(projList));

			criteria.add(Restrictions.eq("projectVo.projectId", projectId));
			useridList = criteria.list();
			logger.info("Exit BaseDAO:: getAllUserforProject");
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			throw new HibernateException("Error occured while retrieving get allUser for Project");
		}
		session.getTransaction().commit();
		return useridList;
	}

}
