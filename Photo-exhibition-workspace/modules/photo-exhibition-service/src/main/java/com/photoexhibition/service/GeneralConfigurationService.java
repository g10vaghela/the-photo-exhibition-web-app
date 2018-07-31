package com.photoexhibition.service;

import com.photoexhibition.service.model.GeneralConfigurationInfo;

public interface GeneralConfigurationService {
	public void saveOrUpdate(GeneralConfigurationInfo generalConfigurationInfo) throws Exception;
	public GeneralConfigurationInfo getGeneralCongfigurationByKey(String key);
}
