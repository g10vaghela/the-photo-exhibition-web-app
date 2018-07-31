package com.photoexhibition.service.util;

import com.photoexhibition.service.EmployeeInfoService;
import com.photoexhibition.service.GeneralConfigurationService;

public class BeanLocalServiceUtil {
	public static EmployeeInfoService getEmployeeInfoService() {
		return (EmployeeInfoService)BeanServiceUtil.getBeanByName("employeeInfoService");
	}
	
	public static GeneralConfigurationService getGeneralConfigurationService() {
		return (GeneralConfigurationService) BeanServiceUtil.getBeanByName("generalConfigurationService");
	}
}
