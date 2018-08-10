package com.photoexhibition.service.impl;

import java.util.List;

import com.photoexhibition.service.AdvertiseInfoService;
import com.photoexhibition.service.dao.AdvertiseInfoDao;
import com.photoexhibition.service.model.AdvertiseInfo;
import com.photoexhibition.service.search.criteria.AdvertiseInfoSearchChiteria;

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
	public List<AdvertiseInfo> getAdvertiseInfoList(boolean status) {
		return advertiseInfoDao.getAdvertiseInfoList(status);
	}

	@Override
	public AdvertiseInfo save(AdvertiseInfo advertiseInfo) {
		return advertiseInfoDao.save(advertiseInfo);
	}

	@Override
	public AdvertiseInfo getAdvertiseInfoById(long advertiseId) {
		return advertiseInfoDao.getAdvertiseInfoById(advertiseId);
	}

	@Override
	public List<AdvertiseInfo> getAdvertiseInfoList(AdvertiseInfoSearchChiteria advertiseInfoSearchChiteria) {
		return advertiseInfoDao.getAdvertiseInfoList(advertiseInfoSearchChiteria);
	}

	@Override
	public int getAdvertiseInfoCount(AdvertiseInfoSearchChiteria advertiseInfoSearchChiteria) {
		return advertiseInfoDao.getAdvertiseInfoCount(advertiseInfoSearchChiteria);
	}
}