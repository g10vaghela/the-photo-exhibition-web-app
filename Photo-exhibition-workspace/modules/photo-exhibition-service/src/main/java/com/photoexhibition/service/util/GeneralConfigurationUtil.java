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
	
	/**
	 * Check contest is open or not
	 * @return
	 */
	public static GeneralConfigurationInfo isContestOpenConfiguration() {
		return getGeneralConfigurationService().getGeneralCongfigurationByKey(GeneralConfigurationConstants.IS_CONTENST_OPEN);
	}
	
	/**
	 * Checking configuration for otp service on or not
	 * @return
	 */
	public static GeneralConfigurationInfo isOtpServiceOn() {
		return getGeneralConfigurationService().getGeneralCongfigurationByKey(GeneralConfigurationConstants.IS_OTP_SERVICE_ON);
	}
	
	/**
	 * getting otp string length
	 * @return
	 */
	public static int getOtpStingLength() {
		return Integer.parseInt(getGeneralConfigurationService().getGeneralCongfigurationByKey(GeneralConfigurationConstants.OTP_STRING_LENGTH).getValue());
	}
	
	public static String getOtpSendMessage() {
		return getGeneralConfigurationService().getGeneralCongfigurationByKey(GeneralConfigurationConstants.OTP_SENT_SUCCESSFULLY_MSG).getMessage();
	}
	
	public static String getProbleInOtpSendingMessage() {
		return getGeneralConfigurationService().getGeneralCongfigurationByKey(GeneralConfigurationConstants.PROBLEM_IN_OTP_SENDING_MSG).getMessage();
	}
	
	public static String getErrorMessageForViewerMobileDevice(String errorMessageKey) {
		return getGeneralConfigurationService().getGeneralCongfigurationByKey(errorMessageKey).getMessage();
	}
}
