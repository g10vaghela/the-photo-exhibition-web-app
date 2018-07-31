package com.photoexhibition.service.util;

import com.liferay.portal.kernel.util.Validator;
import com.photoexhibition.service.GeneralConfigurationService;
import com.photoexhibition.service.constant.GeneralConfigurationConstants;
import com.photoexhibition.service.model.GeneralConfigurationInfo;

public class GeneralConfigurationUtil {
	private static GeneralConfigurationService generalConfigurationService;
	
	public static GeneralConfigurationService getGeneralConfigurationService() {
		if(Validator.isNull(generalConfigurationService)) {
			generalConfigurationService  = BeanLocalServiceUtil.getGeneralConfigurationService();
		}
		return generalConfigurationService;
	}
	
	public static GeneralConfigurationInfo isContestOpenConfiguration() {
		return getGeneralConfigurationService().getGeneralCongfigurationByKey(GeneralConfigurationConstants.IS_CONTENST_OPEN);
	}

}
