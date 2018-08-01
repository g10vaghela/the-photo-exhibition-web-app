package com.photoexhibition.service.impl;

import com.photoexhibition.service.ViewerInfoService;
import com.photoexhibition.service.dao.ViewerInfoDao;
import com.photoexhibition.service.model.ViewerInfo;

public class ViewerInfoServiceImpl implements ViewerInfoService{
	private ViewerInfoDao viewerinfoDao;

	public void setViewerinfoDao(ViewerInfoDao viewerinfoDao) {
		this.viewerinfoDao = viewerinfoDao;
	}

	@Override
	public void saveOrUpdate(ViewerInfo viewerInfo) {
		viewerinfoDao.saveOrUpdate(viewerInfo);
	}

	@Override
	public ViewerInfo getViewerInfoById(long viewerId) {
		return viewerinfoDao.getViewerInfoById(viewerId);
	}

	@Override
	public ViewerInfo getViewerInfoByMobileNumber(String mobileNumber) {
		return viewerinfoDao.getViewerInfoByMobileNumber(mobileNumber);
	}

	@Override
	public ViewerInfo getViewerInfoByDeviceNumber(String deviceNumber) {
		return viewerinfoDao.getViewerInfoByDeviceNumber(deviceNumber);
	}

	@Override
	public ViewerInfo getViewerInfoByMobileAndDeviceNumber(String mobileNumber, String deviceNumber) {
		return viewerinfoDao.getViewerInfoByMobileAndDeviceNumber(mobileNumber, deviceNumber);
	}
}
