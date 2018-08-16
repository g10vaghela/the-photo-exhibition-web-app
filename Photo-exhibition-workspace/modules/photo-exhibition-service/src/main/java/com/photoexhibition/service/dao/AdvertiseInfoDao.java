package com.photoexhibition.service.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.photoexhibition.service.model.AdvertiseInfo;
import com.photoexhibition.service.search.criteria.AdvertiseInfoSearchChiteria;
import com.photoexhibition.service.util.StatusValue;

public class AdvertiseInfoDao extends BaseDao{
	private static final Log log = LogFactoryUtil.getLog(AdvertiseInfoDao.class);
	
	public AdvertiseInfo save(AdvertiseInfo advertiseInfo){
		log.debug("START :: AdvertiseInfoDao.save()");
		Session session = getSession();
		Transaction transaction = session.getTransaction();
		try {
			transaction.begin();
			session.save(advertiseInfo);
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			log.error("Error :: "+e);
		} finally {
			closeSession(session);
		}
		log.debug("END :: AdvertiseInfoDao.save()");
		return advertiseInfo;
	}
	
	/**
	 * Save or update advertisement info
	 * @param advertiseInfo
	 */
	public void saveOrUpdate(AdvertiseInfo advertiseInfo) {
		log.debug("START :: AdvertiseInfoDao.saveOrUpdate()");
		Session session = getSession();
		Transaction transaction = session.getTransaction();
		try {
			transaction.begin();
			session.saveOrUpdate(advertiseInfo);
			transaction.commit();
		} catch (Exception e) {
			log.error("Error: "+e);
			transaction.rollback();
		} finally {
			closeSession(session);
		}
		log.debug("END :: AdvertiseInfoDao.saveOrUpdate()");
	}
	
	/**
	 * getting list of advertisement info
	 * @return
	 */
	public List<AdvertiseInfo> getAdvertiseInfoList(boolean status){
		log.debug("START :: AdvertiseInfoDao.getAdvertiseInfoList()");
		List<AdvertiseInfo> advertiseInfoList = null;
		Session session = getSession();
		Transaction transaction = session.getTransaction();
		try {
			transaction.begin();
			Query query = session.createQuery("from AdvertiseInfo where activeStatus=:activeStatus");
			query.setParameter("activeStatus", status);
			advertiseInfoList = query.list();
			transaction.commit();
		} catch (Exception e) {
			log.error("Error ::"+e);
			transaction.rollback();
		} finally {
			closeSession(session);
		}
		log.debug("END :: AdvertiseInfoDao.getAdvertiseInfoList()");
		return advertiseInfoList;
	}
	
	public AdvertiseInfo getAdvertiseInfoById(long advertiseId){
		log.debug("START :: AdvertiseInfoDao.getAdvertiseInfoById()");
		log.info("advertiseId : "+advertiseId);
		Session session = getSession();
		Transaction transaction = session.getTransaction();
		AdvertiseInfo advertiseInfo = null;
		try {
			transaction.begin();
			advertiseInfo = (AdvertiseInfo) session.get(AdvertiseInfo.class, advertiseId);
			transaction.commit();
		} catch (Exception e) {
			log.error("Error ::"+e);
			transaction.rollback();
		} finally {
			closeSession(session);
		}
		log.debug("END :: AdvertiseInfoDao.getAdvertiseInfoById()");
		return advertiseInfo;
	}
	

	public int getAdvertiseInfoCount(AdvertiseInfoSearchChiteria advertiseInfoSearchChiteria){
		log.debug("START :: AdvertiseInfoDao.getAdvertiseInfoCount()");
		Session session = getSession();
		Transaction transaction = session.getTransaction();
		int count = 0;
		try {
			transaction.begin();
			Query query = getSearchQuery(session, advertiseInfoSearchChiteria, true);
			count = Integer.parseInt(String.valueOf(query.uniqueResult()));
			log.info("count :: " + count);
			transaction.commit();
		} catch (Exception e) {
			log.error("Error : "+e);
			transaction.rollback();
		} finally {
			closeSession(session);
		}
		log.debug("END :: AdvertiseInfoDao.getAdvertiseInfoList()");
		return count;
	}
	
	public List<AdvertiseInfo> getAdvertiseInfoList(AdvertiseInfoSearchChiteria advertiseInfoSearchChiteria){
		log.debug("START :: AdvertiseInfoDao.getAdvertiseInfoList()");
		List<AdvertiseInfo> advertiseInfoList = new ArrayList<>();
		Session session = getSession();
		Transaction transaction = session.getTransaction();
		try {
			transaction.begin();
			Query query = getSearchQuery(session, advertiseInfoSearchChiteria, false);
			advertiseInfoList = query.list();
			transaction.commit();
		} catch (Exception e) {
			log.error("Error : "+e);
			transaction.rollback();
		} finally {
			closeSession(session);
		}
		log.debug("END :: AdvertiseInfoDao.getAdvertiseInfoList()");
		return advertiseInfoList;
	}

	private Query getSearchQuery(Session session, AdvertiseInfoSearchChiteria advertiseInfoSearchChiteria, boolean isCountQuery) {
		log.debug("START :: AdvertiseInfoDao.getSearchQuery()");
		StringBuilder queryString = new StringBuilder();
		
		if(isCountQuery){
			queryString.append("SELECT COUNT(*) ");
		}
		
		queryString.append("from AdvertiseInfo ");
		if(advertiseInfoSearchChiteria.getStatus() == StatusValue.ACTIVE.getValue()) {
			queryString.append("where activeStatus =:active ");
		} else if (advertiseInfoSearchChiteria.getStatus() == StatusValue.IN_ACTIVE.getValue()){
			queryString.append("where activeStatus =:diactive ");
		} else {
			queryString.append("where (activeStatus =:active or activeStatus =:diactive) ");
		}
		
		if(Validator.isNotNull(advertiseInfoSearchChiteria.getAdvertiseId()) && (advertiseInfoSearchChiteria.getAdvertiseId() > 0)) {
			queryString.append("and advertiseId =:advertiseId ");
		} 
		
		if(Validator.isNotNull(advertiseInfoSearchChiteria.getAdvertiseName()) && !advertiseInfoSearchChiteria.getAdvertiseName().isEmpty()){
			queryString.append("and advertiseName =:advertiseName ");
		}

		Query searchQuery = session.createQuery(queryString.toString());
		
		if(advertiseInfoSearchChiteria.getStatus() == StatusValue.ACTIVE.getValue()) {
			searchQuery.setParameter("active", true);
		} else if (advertiseInfoSearchChiteria.getStatus() == StatusValue.IN_ACTIVE.getValue()){
			searchQuery.setParameter("diactive", false);
		} else {
			searchQuery.setParameter("active", true);
			searchQuery.setParameter("diactive", false);
		}
		
		if(Validator.isNotNull(advertiseInfoSearchChiteria.getAdvertiseId()) && (advertiseInfoSearchChiteria.getAdvertiseId() > 0)) {
			searchQuery.setParameter("advertiseId", advertiseInfoSearchChiteria.getAdvertiseId());
		} 
		
		if(Validator.isNotNull(advertiseInfoSearchChiteria.getAdvertiseName()) && !advertiseInfoSearchChiteria.getAdvertiseName().isEmpty()){
			searchQuery.setParameter("advertiseName", advertiseInfoSearchChiteria.getAdvertiseName());
		}
		
		if(advertiseInfoSearchChiteria.isPagination() && !isCountQuery) {
			searchQuery.setFirstResult((advertiseInfoSearchChiteria.getPaginationPage() -1) * advertiseInfoSearchChiteria.getPaginationDelta());
			searchQuery.setMaxResults(advertiseInfoSearchChiteria.getPaginationDelta());
		}
		System.out.println("searchQuery ::"+searchQuery.getQueryString());
		log.debug("START :: AdvertiseInfoDao.getSearchQuery()");
		return searchQuery;
	}
}
