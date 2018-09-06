package com.photoexhibition.service.impl;

import java.util.List;

import com.photoexhibition.service.ChildViewerLikeInfoService;
import com.photoexhibition.service.dao.ChildViewerLikeInfoDao;
import com.photoexhibition.service.model.ChildViewerLikeInfo;
import com.photoexhibition.service.vo.ChildInfoVO;

public class ChildViewerLikeInfoServiceImpl implements ChildViewerLikeInfoService{
	private ChildViewerLikeInfoDao childViewerLikeInfoDao;

	public void setChildViewerLikeInfoDao(ChildViewerLikeInfoDao childViewerLikeInfoDao) {
		this.childViewerLikeInfoDao = childViewerLikeInfoDao;
	}

	@Override
	public void saveOrUpdate(ChildViewerLikeInfo childViewerLikeInfo) {
		childViewerLikeInfoDao.saveOrUpdate(childViewerLikeInfo);
	}

	@Override
	public ChildViewerLikeInfo getChildVieweLikeInfoById(long childViewerLikeId) {
		return childViewerLikeInfoDao.getChildVieweLikeInfoById(childViewerLikeId);
	}

	@Override
	public List<ChildViewerLikeInfo> getChildViewerLikeInfoListByChildId(long childId) {
		return childViewerLikeInfoDao.getChildViewerLikeInfoListByChildId(childId);
	}

	@Override
	public List<ChildViewerLikeInfo> getChildViewerLikeInfoListByViewerId(long viewerId) {
		return childViewerLikeInfoDao.getChildViewerLikeInfoListByViewerId(viewerId);
	}

	@Override
	public ChildViewerLikeInfo getChildViewerLikeInfoByChildAndViewerId(long childId, long viewerId) {
		return childViewerLikeInfoDao.getChildViewerLikeInfoByChildAndViewerId(childId, viewerId);
	}

	@Override
	public List<ChildInfoVO> getChildInfoVOByTopLimit(int topLimit) {
		return null;
	}

	@Override
	public int countTotalLikeByChildId(long childId) {
		return childViewerLikeInfoDao.countTotalLikeByChildId(childId);
	}
}