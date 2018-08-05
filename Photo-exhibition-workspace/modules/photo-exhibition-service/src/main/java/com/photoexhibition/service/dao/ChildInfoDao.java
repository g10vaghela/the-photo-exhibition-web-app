package com.photoexhibition.service.dao;


import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.photoexhibition.service.model.ChildInfo;

public class ChildInfoDao extends BaseDao{
	private static final Log log = LogFactoryUtil.getLog(ChildInfoDao.class);
	
	public void saveOrUpdate(ChildInfo childInfo){
		log.debug("START :: ChildInfoDao.saveOrUpdate()");
		Session session = getSession();
		Transaction transaction = session.getTransaction();
		try {
			transaction.begin();
			session.saveOrUpdate(childInfo);
			transaction.commit();
		} catch (Exception e) {
			log.error("Error ::"+e);
			transaction.rollback();
		} finally {
			closeSession(session);
		}
		log.debug("END :: ChildInfoDao.saveOrUpdate()");
	}
	
	public ChildInfo getChildInfoById(long childId) {
		log.debug("START :: ChildInfoDao.getChildInfoById()");
		ChildInfo childInfo = null;
		Session session = getSession();
		Transaction transaction = session.getTransaction();
		try {
			transaction.begin();
			childInfo = (ChildInfo) session.get(ChildInfo.class, childId);
			transaction.commit();
		} catch (Exception e) {
			log.error("Error ::"+e);
			e.printStackTrace();
			transaction.rollback();
		} finally {
			closeSession(session);
		}
		log.debug("END :: ChildInfoDao.getChildInfoById()");
		return childInfo;
	}
	
	public ChildInfo getChildInfoByChildRank(long childRank){
		log.debug("START :: ChildInfoDao.getChildInfoByChildRank()");
		ChildInfo childInfo = null;
		Session session = getSession();
		Transaction transaction = session.getTransaction();
		try {
			String queryString = "from ChildInfo where childRank =:childRank";
			transaction.begin();
			Query query = session.createQuery(queryString);
			query.setParameter("childRank", childRank);
			childInfo = (ChildInfo)query.uniqueResult();
			transaction.commit();
		} catch(Exception e) {
			log.error("Error :: "+e);
			transaction.rollback();
		} finally {
			closeSession(session);
		}
		log.debug("END :: ChildInfoDao.getChildInfoByChildRank()");
		return childInfo;
	}
	
	public List<ChildInfo> getChildInfoList(){
		log.debug("START :: ChildInfoDao.getChildInfoList()");
		List<ChildInfo> childInfoList = null;
		Session session = getSession();
		Transaction transaction = session.getTransaction();
		try {
			transaction.begin();
			childInfoList = session.createQuery("from ChildInfo where status =:status").setParameter("status", true).list();
			transaction.commit();
		} catch (Exception e) {
			log.error("Error ::"+e);
			transaction.rollback();
		} finally {
			closeSession(session);
		}
		log.debug("END :: ChildInfoDao.getChildInfoList()");
		return childInfoList;
	}
}

