package com.photoexhibition.service;

import java.util.List;

import com.photoexhibition.service.model.AdvertiseInfo;

public interface AdvertiseInfoService {
	public void saveOrUpdate(AdvertiseInfo advertiseInfo);
	public List<AdvertiseInfo> getAdvertiseInfoList();
}
