package com.photoexhibition.service.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.photoexhibition.service.model.ChildViewerLikeInfo;
import com.photoexhibition.service.vo.ChildInfoVO;

public class ChildViewerLikeInfoDao extends BaseDao{
	private static final Log log = LogFactoryUtil.getLog(ChildViewerLikeInfoDao.class);

	/**
	 * Save or update child viewer likes
	 * @param childViewerLikeInfo
	 */
	public void saveOrUpdate(ChildViewerLikeInfo childViewerLikeInfo){
		log.debug("START :: ChildViewerLikeInfoDao.saveOrUpdate()");
		Session session = getSession();
		Transaction transaction = session.getTransaction();
		try {
			transaction.begin();
			session.saveOrUpdate(childViewerLikeInfo);
			transaction.commit();
		} catch (Exception e) {
			log.error("Error :: "+e);
			transaction.rollback();
		} finally {
			closeSession(session);
		}
		log.debug("END :: ChildViewerLikeInfoDao.saveOrUpdate()");
	}

	/**
	 * Getting child viewer like info by its id
	 * @param childViewerLikeId
	 * @return
	 */
	public ChildViewerLikeInfo getChildVieweLikeInfoById(long childViewerLikeId){
		log.debug("START :: ChildViewerLikeInfoDao.getChildVieweLikeInfoById()");
		ChildViewerLikeInfo childViewerLikeInfo = null;
		Session session = getSession();
		Transaction transaction = session.getTransaction();
		try {
			transaction.begin();
			childViewerLikeInfo = (ChildViewerLikeInfo) session.get(ChildViewerLikeInfo.class, childViewerLikeId);
			transaction.commit();
		} catch (Exception e) {
			log.error("Error ::"+e);
			transaction.rollback();
		} finally {
			closeSession(session);
		}
		log.debug("END :: ChildViewerLikeInfoDao.getChildVieweLikeInfoById()");
		return childViewerLikeInfo;
	}

	/**
	 * Getting list of Child viewer like info who is liked by someone by child id
	 * @param childId
	 * @return
	 */
	public List<ChildViewerLikeInfo> getChildViewerLikeInfoListByChildId(long childId){
		log.debug("START :: ChildViewerLikeInfoDao.getChildViewerLikeInfoListByChildId()");
		List<ChildViewerLikeInfo> childViewerLikeInfoList = new ArrayList<>();
		Session session = getSession();
		Transaction transaction = session.getTransaction();
		try {
			String queryString = "from ChildViewerLikeInfo cvli where cvli.childInfo.childId =:childId and cvli.isLike =:isLike";
			transaction.begin();
			Query query = session.createQuery(queryString);
			query.setParameter("childId", childId);
			query.setParameter("isLike", true);
			childViewerLikeInfoList = query.list();
			transaction.commit();
		} catch (Exception e) {
			log.error("Error ::"+e);
			transaction.rollback();
		} finally {
			closeSession(session);
		}
		log.debug("END :: ChildViewerLikeInfoDao.getChildViewerLikeInfoListByChildId()");
		return childViewerLikeInfoList;
	}

	/**
	 * Getting list of Child viewer like info by viewer id
	 * @param childId
	 * @return
	 */
	public List<ChildViewerLikeInfo> getChildViewerLikeInfoListByViewerId(long viewerId){
		log.debug("START :: ChildViewerLikeInfoDao.getChildViewerLikeInfoByChildId()");
		List<ChildViewerLikeInfo> childViewerLikeInfoList = new ArrayList<>();
		Session session = getSession();
		Transaction transaction = session.getTransaction();
		try {
			String queryString = "from ChildViewerLikeInfo cvli where cvli.viewerInfo.viewerId =:viewerId and cvli.isLike=:isLike";
			transaction.begin();
			Query query = session.createQuery(queryString);
			query.setParameter("viewerId", viewerId);
			query.setParameter("isLike", true);
			childViewerLikeInfoList = query.list();
			transaction.commit();
		} catch (Exception e) {
			log.error("Error ::"+e);
			transaction.rollback();
		} finally {
			closeSession(session);
		}
		log.debug("END :: ChildViewerLikeInfoDao.getChildViewerLikeInfoByChildId()");
		return childViewerLikeInfoList;
	}
	
	public ChildViewerLikeInfo getChildViewerLikeInfoByChildAndViewerId(long childId, long viewerId) {
		log.debug("START :: ChildViewerLikeInfoDao.getChildViewerLikeInfoByChildAndViewerId()");
		ChildViewerLikeInfo childViewerLikeInfo = null;
		Session session = getSession();
		Transaction transaction = session.getTransaction();
		
		try {
			String queryString = "from ChildViewerLikeInfo where childInfo.childId =:childId and viewerInfo.viewerId =:viewerId";
			transaction.begin();
			Query query = session.createQuery(queryString);
			query.setParameter("childId", childId);
			query.setParameter("viewerId", viewerId);
			childViewerLikeInfo = (ChildViewerLikeInfo) query.uniqueResult();
			transaction.commit();
		} catch (Exception e) {
			log.error("Error:: "+e);
			transaction.rollback();
		} finally {
			closeSession(session);
		}
		log.debug("END :: ChildViewerLikeInfoDao.getChildViewerLikeInfoByChildAndViewerId()");
		return childViewerLikeInfo;
	}
	
	public List<ChildInfoVO> getChildInfoVOUptoTopLimit(int topLimit){
		log.debug("START :: ChildViewerLikeInfoDao.getChildInfoVOUptoTopLimit()");
		List childInfoVOList;
		Session session = getSession();
		Transaction transaction = session.getTransaction();
		try {
			transaction.begin();
			Query query = getQuery(session, topLimit);
			childInfoVOList = (List) query.uniqueResult();
			System.out.println("return childInfoVOList; : " +  childInfoVOList);
			transaction.commit();
			return childInfoVOList;
		} catch (Exception e) {
			log.error("Error :: "+e);
			transaction.rollback();
		} finally {
			closeSession(session);
		}
		log.debug("END :: ChildViewerLikeInfoDao.getChildInfoVOUptoTopLimit()");
		return null;
	}
	public int countTotalLikeByChildId(long childId){
		log.debug("START :: ChildViewerLikeInfoDao.countTotalLikeByChildId()");
		Session session = getSession();
		Transaction transaction = session.getTransaction();
		int totalLikes = 0;
		try {
			String queryString = "select count(*) from ChildViewerLikeInfo cvli where cvli.childInfo.childId =:childId and cvli.isLike =:isLike";
			transaction.begin();
			Query query = session.createQuery(queryString);
			query.setParameter("childId", childId);
			query.setParameter("isLike", true);
			totalLikes = Integer.parseInt(String.valueOf(query.uniqueResult()));
			log.debug("totalLikes  :: "+totalLikes);
			transaction.commit();
		} catch(Exception e){
			log.error(e);
			transaction.rollback();
		}finally {
			closeSession(session);
		}
		log.debug("END :: ChildViewerLikeInfoDao.countTotalLikeByChildId()");
		return totalLikes;
	}
	
	public int countTotalLikeByViewerId(long viewerId){
		log.debug("START :: ChildViewerLikeInfoDao.countTotalLikeByViewerId()");
		Session session = getSession();
		Transaction transaction = session.getTransaction();
		int totalLikes = 0;
		try {
			String queryString = "select count(*) from ChildViewerLikeInfo cvli where cvli.viewerInfo.viewerId =:viewerId and cvli.isLike =:isLike";
			transaction.begin();
			Query query = session.createQuery(queryString);
			query.setParameter("viewerId", viewerId);
			query.setParameter("isLike", true);
			totalLikes = Integer.parseInt(String.valueOf(query.uniqueResult()));
			log.debug("totalLikes  :: "+totalLikes);
			transaction.commit();
		} catch(Exception e){
			log.error(e);
			transaction.rollback();
		}finally {
			closeSession(session);
		}
		log.debug("END :: ChildViewerLikeInfoDao.countTotalLikeByChildId()");
		return totalLikes;
	}
	private Query getQuery(Session session, int topLimit) {
		log.debug("START :: ChildViewerLikeInfoDao.getQuery()");
		
		String queryString = "select GROUP_CONCAT(child_Id) as child_id, likes from (select child_Id, count(is_like) as likes from child_view_like where is_like = 1 group by child_Id order by likes DESC) as abc group by likes order by likes DESC LIMIT 10";
		Transaction transaction = session.getTransaction();
		
		String temp = "select child_Id, COUNT(is_like) as likes from child_view_like where is_like = 1 group by child_Id order by likes DESC";
		
		transaction.begin();
		Query query = session.createSQLQuery(temp);
		List<Object[]> objList = query.list();
		System.out.println("After result found");
		
		for (Object[] object : objList) {
			System.out.println("--> " + object[0]);
			System.out.println("--> " + object[1]);
		}
		
		log.debug(" ChildViewerLikeInfoDao.getQuery() :: " );
		System.out.println(" ChildViewerLikeInfoDao.getQuery() :: " + query);
		log.debug("END :: ChildViewerLikeInfoDao.getQuery()");
		return query;
	}
}