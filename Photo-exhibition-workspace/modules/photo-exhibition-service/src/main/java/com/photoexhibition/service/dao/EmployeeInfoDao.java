package com.photoexhibition.service.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.photoexhibition.service.model.EmployeeInfo;

public class EmployeeInfoDao extends BaseDao{
	public void saveOrUpdateEmployeeInfo(EmployeeInfo employeeInfo) {
		Session session = getSession();
		Transaction transaction = session.getTransaction();
		try {
			transaction.begin();
			session.saveOrUpdate(employeeInfo);
			transaction.commit();
		} catch(Exception e){
			transaction.rollback();
			e.printStackTrace();
		} finally {
			closeSession(session);
		}
	}
}