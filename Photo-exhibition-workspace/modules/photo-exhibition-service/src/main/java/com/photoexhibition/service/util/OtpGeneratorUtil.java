package com.photoexhibition.service.util;

import java.util.Random;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

public class OtpGeneratorUtil {
	private static final Log log = LogFactoryUtil.getLog(OtpGeneratorUtil.class);
	
	public static String generateOtp() {
		log.debug("START :: OtpGeneratorUtil.generateOtp()");
		StringBuilder otpString = new StringBuilder();
		int otpStringLength = GeneralConfigurationUtil.getOtpStingLength();
		log.debug("otpStringLength :: "+otpStringLength);
		for (int i = 0; i < otpStringLength; i++) {
			otpString.append(new Random().nextInt(10));
		}
		log.info("otpString :: "+otpString.toString());
		log.debug("END :: OtpGeneratorUtil.generateOtp()");
		return otpString.toString();
	}
	
	public static boolean sendOtp(String mobileNumber, String otpString) {
		boolean isOtpSent = true;
		
		return isOtpSent;
	}
}
