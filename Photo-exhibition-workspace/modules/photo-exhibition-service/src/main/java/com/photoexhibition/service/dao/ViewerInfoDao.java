package com.photoexhibition.service.dao;


import java.util.ArrayList;
import java.util.List;

import org.aspectj.weaver.ISourceContext;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.photoexhibition.service.constant.ViewerConstants;
import com.photoexhibition.service.model.ViewerInfo;
import com.photoexhibition.service.search.criteria.ViewerInfoSearchCriteria;

public class ViewerInfoDao extends BaseDao{
	private static final Log log = LogFactoryUtil.getLog(ViewerInfoDao.class);

	
	/**
	 * Save viewer info
	 * @param viewerInfo
	 */
	public ViewerInfo save(ViewerInfo viewerInfo){
		log.debug("START :: ViewerInfoDao.save()");
		Session session = getSession();
		Transaction transaction = session.getTransaction();
		try {
			transaction.begin();
			System.out.println("Before viewerInfo :: "+viewerInfo);
			session.save(viewerInfo);
			System.out.println("after viewerInfo :: "+viewerInfo);
			/*viewerInfo = (ViewerInfo)session.get(ViewerInfo.class, viewerId);*/
			transaction.commit();
		} catch(Exception e){
			log.error("Error :: "+e);
			transaction.rollback();
		} finally {
			closeSession(session);
		}		
		log.debug("END :: ViewerInfoDao.save()");
		return viewerInfo;
	}
	
	/**
	 * Save or update viewer info
	 * @param viewerInfo
	 */
	public void saveOrUpdate(ViewerInfo viewerInfo){
		log.debug("START :: ViewerInfoDao.saveOrUpdate()");
		Session session = getSession();
		Transaction transaction = session.getTransaction();
		try {
			transaction.begin();
			session.saveOrUpdate(viewerInfo);
			transaction.commit();
		} catch(Exception e){
			log.error("Error :: "+e);
			transaction.rollback();
		} finally {
			closeSession(session);
		}		
		log.debug("END :: ViewerInfoDao.saveOrUpdate()");
	}

	/**
	 * Get viewer info by viewer primary key
	 * @param viewerId primary key of viewer
	 * @return
	 */
	public ViewerInfo getViewerInfoById(long viewerId){
		log.debug("START :: ViewerInfoDao.getViewerInfoById()");
		ViewerInfo viewerInfo = null;
		Session session = getSession();
		Transaction transaction = session.getTransaction();
		try {
			transaction.begin();
			viewerInfo = (ViewerInfo)session.get(ViewerInfo.class, viewerId);
			transaction.commit();
		} catch (Exception e) {
			log.error("Error :: "+e);
			transaction.rollback();
		} finally {
			closeSession(session);
		}
		log.debug("END :: ViewerInfoDao.getViewerInfoById()");
		return viewerInfo;
	}

	/**
	 * Getting viewer info by mobile number
	 * @param mobileNumber
	 * @return
	 */
	public ViewerInfo getViewerInfoByMobileNumber(String mobileNumber){
		log.debug("START :: ViewerInfoDao.getViewerInfoByMobileNumber()");
		ViewerInfo viewerInfo = null;
		Session session = getSession();
		Transaction transaction = session.getTransaction();
		try {
			String queryString = "from ViewerInfo where mobileNumber =:mobileNumber";
			transaction.begin();
			Query query = session.createQuery(queryString);
			query.setParameter(ViewerConstants.MOBILE_NUMBER, mobileNumber);
			viewerInfo = (ViewerInfo) query.uniqueResult();
			transaction.commit();
		} catch (Exception e) {
			log.error("Error :: "+e);
			transaction.rollback();
		} finally {
			closeSession(session);
		}
		log.debug("END :: ViewerInfoDao.getViewerInfoByMobileNumber()");
		return viewerInfo;
	}

	/**
	 * Getting viewer info by device number
	 * @param deviceNumber
	 * @return
	 */
	public ViewerInfo getViewerInfoByDeviceNumber(String deviceNumber){
		log.debug("START :: ViewerInfoDao.getViewerInfoByDeviceNumber()");
		ViewerInfo viewerInfo = null;
		Session session = getSession();
		Transaction transaction = session.getTransaction();
		try {
			String queryString = "from ViewerInfo where deviceNumber =:deviceNumber";
			transaction.begin();
			Query query = session.createQuery(queryString);
			query.setParameter(ViewerConstants.DEVICE_NUMBER, deviceNumber);
			viewerInfo = (ViewerInfo) query.uniqueResult();
			transaction.commit();
		} catch (Exception e) {
			log.error("Error :: "+e);
			transaction.rollback();
		} finally {
			closeSession(session);
		}
		log.debug("END :: ViewerInfoDao.getViewerInfoByDeviceNumber()");
		return viewerInfo;
	}

	/**
	 * Getting viewer by mobile and device number, It will return viewer who's mobile and device in are mapped in db
	 * @param mobileNumber
	 * @param deviceNumber
	 * @return
	 */
	public ViewerInfo getViewerInfoByMobileAndDeviceNumber(String mobileNumber, String deviceNumber){
		log.debug("START :: ViewerInfoDao.getViewerInfoByMobileAndDeviceNumber()");
		ViewerInfo viewerInfo = null;
		Session session = getSession();
		Transaction transaction = session.getTransaction();
		try {
			String queryString = "from ViewerInfo where mobileNumber =:mobileNumber and deviceNumber =:deviceNumber";
			transaction.begin();
			Query query = session.createQuery(queryString);
			query.setParameter(ViewerConstants.DEVICE_NUMBER, deviceNumber);
			query.setParameter(ViewerConstants.MOBILE_NUMBER, mobileNumber);
			viewerInfo = (ViewerInfo) query.uniqueResult();
			transaction.commit();
		} catch (Exception e) {
			log.error("Error :: "+e);
			transaction.rollback();
		} finally {
			closeSession(session);
		}
		log.debug("END :: ViewerInfoDao.getViewerInfoByMobileAndDeviceNumber()");
		return viewerInfo;
	}
	
	public List<ViewerInfo> getViewerInfoBySearchCriteria(ViewerInfoSearchCriteria searchCriteria){
		List<ViewerInfo> viewerInfoList = new ArrayList<>();
		Session session = getSession();
		Transaction transaction = session.getTransaction();
		try {
			transaction.begin();
			Query query = createQuery(session, searchCriteria, false);
			viewerInfoList = query.list();
			transaction.commit();
		} catch (Exception e) {
			log.error(e);
		}
		return viewerInfoList;
	}
	
	public int countViewerInfoBySearchCriteria(ViewerInfoSearchCriteria searchCriteria){
		Session session = getSession();
		Transaction transaction = session.getTransaction();
		int count = 0;
		try {
			transaction.begin();
			Query query = createQuery(session, searchCriteria, true);
			count = Integer.parseInt(String.valueOf(query.uniqueResult()));
			transaction.commit();
		} catch (Exception e) {
			log.error(e);
			transaction.rollback();
		} finally{
			closeSession(session);
		}
		return count;
	}
	
	public Query createQuery(Session session, ViewerInfoSearchCriteria searchCriteria, boolean isCountQuery){
		StringBuilder queryString = new StringBuilder();
		
		if(isCountQuery){
			queryString.append("SELECT COUNT(*) "); 
		}
		
		queryString.append("from ViewerInfo ");
		if(Validator.isNotNull(searchCriteria.getViewerId())){
			queryString.append(" where viewerId =:viewerId");
		} else if(Validator.isNotNull(searchCriteria.getMobileNumber())){
			queryString.append(" where mobileNumber =:mobileNumber");
		}
		
		Query searchQuery = session.createQuery(queryString.toString());
		
		if(Validator.isNotNull(searchCriteria.getViewerId())){
			searchQuery.setParameter("viewerId", searchCriteria.getViewerId());
		} else if(Validator.isNotNull(searchCriteria.getMobileNumber())){
			searchQuery.setParameter("mobileNumber", searchCriteria.getMobileNumber());
		}
		
		if(searchCriteria.isPagination() && !isCountQuery) {
			searchQuery.setFirstResult((searchCriteria.getPaginationPage() -1) * searchCriteria.getPaginationDelta());
			searchQuery.setMaxResults(searchCriteria.getPaginationDelta());
		}
		
		return searchQuery;
	}
	
	public List<ViewerInfo> getViewerInfoList(){
		List<ViewerInfo> viewerInfoList = null;
		Session session = getSession();
		Transaction transaction = session.getTransaction();
		try {
			transaction.begin();
			Query query = session.createQuery("from ViewerInfo");
			viewerInfoList = query.list();
			transaction.commit();
		} catch (Exception e) {
			log.error(e);
			transaction.rollback();
		} finally {
			closeSession(session);
		}
		return viewerInfoList;
	}
}
