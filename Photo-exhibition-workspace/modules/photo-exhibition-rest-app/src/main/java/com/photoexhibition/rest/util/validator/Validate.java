package com.photoexhibition.rest.util.validator;

import java.util.regex.Pattern;

import com.liferay.portal.kernel.util.Validator;

public class Validate {
	public static Pattern pattern = Pattern.compile("\\d{10}");
	public static boolean isValidPhoneNumber(String phoneNumber){
		if(Validator.isNotNull(phoneNumber)){
			return pattern.matcher(phoneNumber).matches();
		} else {
			return false;
		}
	}
	public static boolean isValidDeviceNumber(String deviceNumber){
		return Validator.isNotNull(deviceNumber);
	}
}
