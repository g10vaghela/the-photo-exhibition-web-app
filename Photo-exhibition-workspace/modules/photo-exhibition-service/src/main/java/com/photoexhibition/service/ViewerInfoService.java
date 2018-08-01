package com.photoexhibition.service;

import com.photoexhibition.service.model.ViewerInfo;

public interface ViewerInfoService {
	public void saveOrUpdate(ViewerInfo viewerInfo);
	public ViewerInfo getViewerInfoById(long viewerId);
	public ViewerInfo getViewerInfoByMobileNumber(String mobileNumber);
	public ViewerInfo getViewerInfoByDeviceNumber(String deviceNumber);
	public ViewerInfo getViewerInfoByMobileAndDeviceNumber(String mobileNumber, String deviceNumber);
}
