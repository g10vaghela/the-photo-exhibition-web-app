package com.photoexhibition.portlet.validator;

import java.util.regex.Pattern;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

public class ChildInfoValidator {
	private static final Log log = LogFactoryUtil.getLog(ChildInfoValidator.class);
	
	public static boolean isValidChildInfo(String firstName, String middleName,String lastName,String mobileNumber,String dateOfBirth){
		boolean isValidChildInfo = true;
		if(Validator.isNotNull(firstName) || Validator.isNotNull(middleName) 
				|| Validator.isNull(lastName) 
				|| Validator.isNull(mobileNumber)
				|| Validator.isNull(dateOfBirth)){
			isValidChildInfo = false;
		}
		Pattern pattern = Pattern.compile("\\d{10}");
		if(!pattern.matcher(mobileNumber).matches()){
			isValidChildInfo = false;
		}
		return isValidChildInfo;
	}

}
