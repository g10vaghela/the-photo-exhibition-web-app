package com.photoexhibition.service.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.photoexhibition.service.model.AdvertiseInfo;

public class AdvertiseInfoDao extends BaseDao{
	private static final Log log = LogFactoryUtil.getLog(AdvertiseInfoDao.class);
	
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
	public List<AdvertiseInfo> getAdvertiseInfoList(){
		log.debug("START :: AdvertiseInfoDao.getAdvertiseInfoList()");
		List<AdvertiseInfo> advertiseInfoList = null;
		Session session = getSession();
		Transaction transaction = session.getTransaction();
		try {
			transaction.begin();
			advertiseInfoList = session.createQuery("from AdvertiseInfo").list();
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

}
