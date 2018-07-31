package com.photoexhibition.service.util;

import com.photoexhibition.service.ChildInfoService;
import com.photoexhibition.service.EmployeeInfoService;
import com.photoexhibition.service.GeneralConfigurationService;
import com.photoexhibition.service.constant.BeanNameConstant;

public class BeanLocalServiceUtil {
	public static EmployeeInfoService getEmployeeInfoService() {
		return (EmployeeInfoService)BeanServiceUtil.getBeanByName(BeanNameConstant.EMPLOYEE_INFO_SERVICE);
	}
	
	public static GeneralConfigurationService getGeneralConfigurationService() {
		return (GeneralConfigurationService) BeanServiceUtil.getBeanByName(BeanNameConstant.GENERAL_CONFIGURATION_SERVICE);
	}
	
	public static ChildInfoService getChildInfoService(){
		return (ChildInfoService) BeanServiceUtil.getBeanByName(BeanNameConstant.CHILD_INFO_SERVICE);
	}
}
