package com.photoexhibition.service.impl;

import java.util.List;

import com.photoexhibition.service.ChildInfoService;
import com.photoexhibition.service.dao.ChildInfoDao;
import com.photoexhibition.service.model.ChildInfo;

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

}
