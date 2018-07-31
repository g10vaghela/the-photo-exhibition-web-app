package com.photoexhibition.service.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class BaseDao {
	
	protected SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public void closeSession(Session session) {
		if(session.isOpen()) {
			session.close();
		}
	}
}