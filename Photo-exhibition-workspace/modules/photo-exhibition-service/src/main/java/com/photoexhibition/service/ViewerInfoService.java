package com.photoexhibition.service;

import java.util.List;

import com.photoexhibition.service.model.ViewerInfo;
import com.photoexhibition.service.search.criteria.ViewerInfoSearchCriteria;

public interface ViewerInfoService {
	public ViewerInfo save(ViewerInfo viewerInfo);
	public void saveOrUpdate(ViewerInfo viewerInfo);
	public ViewerInfo getViewerInfoById(long viewerId);
	public List<ViewerInfo> getViewerList();
	public ViewerInfo getViewerInfoByMobileNumber(String mobileNumber);
	public ViewerInfo getViewerInfoByDeviceNumber(String deviceNumber);
	public ViewerInfo getViewerInfoByMobileAndDeviceNumber(String mobileNumber, String deviceNumber);
	public List<ViewerInfo> getViewerInfoBySearchCriteria(ViewerInfoSearchCriteria searchCriteria);
	public int countViewerInfoBySearchCriteria(ViewerInfoSearchCriteria searchCriteria);
}
