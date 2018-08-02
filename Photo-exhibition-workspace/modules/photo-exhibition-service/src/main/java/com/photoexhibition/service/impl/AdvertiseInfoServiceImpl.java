package com.photoexhibition.service.impl;

import java.util.List;

import com.photoexhibition.service.AdvertiseInfoService;
import com.photoexhibition.service.dao.AdvertiseInfoDao;
import com.photoexhibition.service.model.AdvertiseInfo;

public class AdvertiseInfoServiceImpl implements AdvertiseInfoService{
	private AdvertiseInfoDao advertiseInfoDao;

	public AdvertiseInfoDao getAdvertiseInfoDao() {
		return advertiseInfoDao;
	}

	public void setAdvertiseInfoDao(AdvertiseInfoDao advertiseInfoDao) {
		this.advertiseInfoDao = advertiseInfoDao;
	}

	@Override
	public void saveOrUpdate(AdvertiseInfo advertiseInfo) {
		advertiseInfoDao.saveOrUpdate(advertiseInfo);
	}

	@Override
	public List<AdvertiseInfo> getAdvertiseInfoList() {
		return advertiseInfoDao.getAdvertiseInfoList();
	}
	
}