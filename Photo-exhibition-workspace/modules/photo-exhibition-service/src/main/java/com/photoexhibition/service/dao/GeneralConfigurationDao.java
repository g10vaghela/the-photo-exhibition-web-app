package com.photoexhibition.service.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.photoexhibition.service.model.GeneralConfigurationInfo;

public class GeneralConfigurationDao extends BaseDao{
	public static final Log log = LogFactoryUtil.getLog(GeneralConfigurationDao.class);
	
	public void saveOrUpdate(GeneralConfigurationInfo generalConfigurationInfo) throws Exception {
		log.debug("START :: GeneralConfigurationDao.saveOrUpdate()");
		Session session = getSession();
		Transaction transaction = session.getTransaction();
		try {
			transaction.begin();
			session.saveOrUpdate(generalConfigurationInfo);
			transaction.commit();
		} catch(Exception e) {
			log.error("Error ::"+e);
			transaction.rollback();
			throw new Exception("Error :"+e);
		} finally {
			closeSession(session);
		}
		log.debug("END :: GeneralConfigurationDao.saveOrUpdate()");
	}
	
	public GeneralConfigurationInfo getGeneralCongfigurationByKey(String key) {
		log.debug("START :: GeneralConfigurationDao.getGeneralCongfigurationByKey()");
		GeneralConfigurationInfo generalConfigurationInfo = null;
		Session session = getSession();
		Transaction transaction = session.getTransaction();
		try {
			transaction.begin();
			generalConfigurationInfo = (GeneralConfigurationInfo) session.get(GeneralConfigurationInfo.class, key);
			transaction.commit();
		} catch(Exception e) {
			log.error("Error :: "+e);
			transaction.rollback();
		}finally {
			closeSession(session);
		}
		log.debug("END :: GeneralConfigurationDao.getGeneralCongfigurationByKey()");		
		return generalConfigurationInfo;
	}
}
