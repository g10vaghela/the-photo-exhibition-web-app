package com.photoexhibition.service;

import java.util.List;

import com.photoexhibition.service.model.AdvertiseInfo;
import com.photoexhibition.service.search.criteria.AdvertiseInfoSearchChiteria;

public interface AdvertiseInfoService {
	public AdvertiseInfo save(AdvertiseInfo advertiseInfo);
	public void saveOrUpdate(AdvertiseInfo advertiseInfo);
	public AdvertiseInfo getAdvertiseInfoById(long advertiseId);
	public List<AdvertiseInfo> getAdvertiseInfoList(boolean status);
	public List<AdvertiseInfo> getAdvertiseInfoList(AdvertiseInfoSearchChiteria advertiseInfoSearchChiteria);
	public int getAdvertiseInfoCount(AdvertiseInfoSearchChiteria advertiseInfoSearchChiteria);
	
}
