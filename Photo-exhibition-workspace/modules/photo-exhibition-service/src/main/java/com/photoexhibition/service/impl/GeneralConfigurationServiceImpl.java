package com.photoexhibition.service.impl;

import com.photoexhibition.service.GeneralConfigurationService;
import com.photoexhibition.service.dao.GeneralConfigurationDao;
import com.photoexhibition.service.model.GeneralConfigurationInfo;

public class GeneralConfigurationServiceImpl implements GeneralConfigurationService{
	private GeneralConfigurationDao generalConfigurationDao;

	public void setGeneralConfigurationDao(GeneralConfigurationDao generalConfigurationDao) {
		this.generalConfigurationDao = generalConfigurationDao;
	}

	@Override
	public void saveOrUpdate(GeneralConfigurationInfo generalConfigurationInfo) throws Exception {
		generalConfigurationDao.saveOrUpdate(generalConfigurationInfo);
	}

	@Override
	public GeneralConfigurationInfo getGeneralCongfigurationByKey(String key) {
		return generalConfigurationDao.getGeneralCongfigurationByKey(key);
	}
}
