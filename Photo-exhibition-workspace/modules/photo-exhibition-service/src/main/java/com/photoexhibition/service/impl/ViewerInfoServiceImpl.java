package com.photoexhibition.service.impl;

import java.util.List;

import com.photoexhibition.service.ViewerInfoService;
import com.photoexhibition.service.dao.ViewerInfoDao;
import com.photoexhibition.service.model.ViewerInfo;
import com.photoexhibition.service.search.criteria.ViewerInfoSearchCriteria;

public class ViewerInfoServiceImpl implements ViewerInfoService{
	private ViewerInfoDao viewerInfoDao;
	
	public void setViewerInfoDao(ViewerInfoDao viewerInfoDao) {
		this.viewerInfoDao = viewerInfoDao;
	}

	@Override
	public void saveOrUpdate(ViewerInfo viewerInfo) {
		viewerInfoDao.saveOrUpdate(viewerInfo);
	}

	@Override
	public ViewerInfo getViewerInfoById(long viewerId) {
		return viewerInfoDao.getViewerInfoById(viewerId);
	}

	@Override
	public ViewerInfo getViewerInfoByMobileNumber(String mobileNumber) {
		return viewerInfoDao.getViewerInfoByMobileNumber(mobileNumber);
	}

	@Override
	public ViewerInfo getViewerInfoByDeviceNumber(String deviceNumber) {
		return viewerInfoDao.getViewerInfoByDeviceNumber(deviceNumber);
	}

	@Override
	public ViewerInfo getViewerInfoByMobileAndDeviceNumber(String mobileNumber, String deviceNumber) {
		return viewerInfoDao.getViewerInfoByMobileAndDeviceNumber(mobileNumber, deviceNumber);
	}

	@Override
	public ViewerInfo save(ViewerInfo viewerInfo) {
		return viewerInfoDao.save(viewerInfo);		
	}

	@Override
	public List<ViewerInfo> getViewerInfoBySearchCriteria(ViewerInfoSearchCriteria searchCriteria) {
		return this.viewerInfoDao.getViewerInfoBySearchCriteria(searchCriteria);
	}

	@Override
	public int countViewerInfoBySearchCriteria(ViewerInfoSearchCriteria searchCriteria) {
		return this.viewerInfoDao.countViewerInfoBySearchCriteria(searchCriteria);
	}

	@Override
	public List<ViewerInfo> getViewerList() {
		return this.viewerInfoDao.getViewerInfoList();
	}
}
