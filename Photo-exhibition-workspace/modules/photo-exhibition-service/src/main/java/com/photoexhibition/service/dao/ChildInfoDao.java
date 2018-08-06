package com.photoexhibition.service.dao;


import java.util.List;

import javax.swing.text.AsyncBoxView.ChildState;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.engine.query.HQLQueryPlan;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.photoexhibition.service.model.ChildInfo;
import com.photoexhibition.service.search.criteria.ChildInfoSearchCriteria;

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
	
	public List<ChildInfo> getChildInfoList(ChildInfoSearchCriteria childInfoSearchCriteria){
		log.debug("START :: ChildInfoDao.getChildInfoList()");
		List<ChildInfo> childInfoList = null;
		Session session = getSession();
		Transaction transaction = session.getTransaction();
		try {
			
			transaction.begin();
			Query query = getSearchQuery(childInfoSearchCriteria, session, false);//session.createQuery("from ChildInfo where status =:status");
			childInfoList = query.list();
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
	
	public int getChildInfoCountBySearchCriteria(ChildInfoSearchCriteria childInfoSearchCriteria){
		log.debug("START :: ChildInfoDao.getChildInfoCountBySearchCriteria()");
		int childInfoCount = 0;
		Session session = getSession();
		Transaction transaction = session.getTransaction();
		try {
			
			transaction.begin();
			Query query = getSearchQuery(childInfoSearchCriteria, session, true);
			childInfoCount = Integer.parseInt(String.valueOf(query.uniqueResult()));
			transaction.commit();
		} catch (Exception e) {
			log.error("Error ::"+e);
			transaction.rollback();
		} finally {
			closeSession(session);
		}
		log.debug("END :: ChildInfoDao.getChildInfoCountBySearchCriteria()");
		return childInfoCount;
	}
	
	public Query getSearchQuery(ChildInfoSearchCriteria childInfoSearchCriteria, Session session, boolean isCountQuery) {
		log.debug("START :: ChildInfoDao.getSearchQuery()");
		StringBuilder queryString = new StringBuilder();
		
		if(isCountQuery) {
			queryString.append("select count(*) ");
		}
		
		queryString.append("from ChildInfo where status=:status ");
		
		if(Validator.isNotNull(childInfoSearchCriteria.getChildId())) {
			queryString.append(" and childId =:childId");
		} else if(Validator.isNotNull(childInfoSearchCriteria.getContactNo())) {
			queryString.append(" and contactNo =:contactNo");
		}

		Query query = session.createQuery(queryString.toString());
		
		if(Validator.isNotNull(childInfoSearchCriteria.getChildId())) {
			query.setParameter("childId", childInfoSearchCriteria.getChildId());
		} else if(Validator.isNotNull(childInfoSearchCriteria.getContactNo())) {
			query.setParameter("contactNo",childInfoSearchCriteria.getContactNo());
		}
		query.setParameter("status", childInfoSearchCriteria.isStatus());
		
		if(childInfoSearchCriteria.isPagination() && !isCountQuery) {
			query.setFirstResult((childInfoSearchCriteria.getPaginationPage() -1) * childInfoSearchCriteria.getPaginationDelta());
			query.setMaxResults(childInfoSearchCriteria.getPaginationDelta());
		}
		log.debug("END :: ChildInfoDao.getSearchQuery()");
		return query;
	}
}

