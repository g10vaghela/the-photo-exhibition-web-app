package com.photoexhibition.service.dao;


import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.photoexhibition.service.constant.ViewerConstants;
import com.photoexhibition.service.model.ViewerInfo;

public class ViewerInfoDao extends BaseDao{
	private static final Log log = LogFactoryUtil.getLog(ViewerInfoDao.class);

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
}
