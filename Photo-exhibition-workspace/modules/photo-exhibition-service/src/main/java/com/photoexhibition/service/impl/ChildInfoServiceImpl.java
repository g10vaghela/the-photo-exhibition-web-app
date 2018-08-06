package com.photoexhibition.service.impl;

import java.util.List;

import com.photoexhibition.service.ChildInfoService;
import com.photoexhibition.service.dao.ChildInfoDao;
import com.photoexhibition.service.model.ChildInfo;
import com.photoexhibition.service.search.criteria.ChildInfoSearchCriteria;

public class ChildInfoServiceImpl implements ChildInfoService{
	private ChildInfoDao childInfoDao;

	public void setChildInfoDao(ChildInfoDao childInfoDao) {
		this.childInfoDao = childInfoDao;
	}

	@Override
	public void saveOrUpdate(ChildInfo childInfo) {
		childInfoDao.saveOrUpdate(childInfo);
	}

	@Override
	public ChildInfo getChildInfoByChildRank(long childRank) {
		return childInfoDao.getChildInfoByChildRank(childRank);
	}

	@Override
	public List<ChildInfo> getChildInfoList() {
		return childInfoDao.getChildInfoList();
	}

	@Override
	public ChildInfo getChildInfoById(long childId) {
		return childInfoDao.getChildInfoById(childId);
	}

	@Override
	public List<ChildInfo> getChildInfoList(ChildInfoSearchCriteria childInfoSearchCriteria) {
		return childInfoDao.getChildInfoList(childInfoSearchCriteria);
	}

	@Override
	public int getChildInfoCountBySearchCriteria(ChildInfoSearchCriteria childInfoSearchCriteria) {
		return childInfoDao.getChildInfoCountBySearchCriteria(childInfoSearchCriteria);
	}

}
