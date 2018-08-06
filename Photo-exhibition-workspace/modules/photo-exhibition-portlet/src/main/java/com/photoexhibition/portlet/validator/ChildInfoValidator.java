package com.photoexhibition.portlet.validator;

import java.util.regex.Pattern;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

public class ChildInfoValidator {
	private static final Log log = LogFactoryUtil.getLog(ChildInfoValidator.class);
	
	public static boolean isValidChildInfo(String firstName, String middleName,String lastName,String mobileNumber,String dateOfBirth){
		log.info("START :: ChildInfoValidator.isValidChildInfo()");
		boolean isValidChildInfo = true;
		if(Validator.isNull(firstName) || Validator.isNull(middleName) 
				|| Validator.isNull(lastName) 
				|| Validator.isNull(mobileNumber)
				|| Validator.isNull(dateOfBirth)){
			isValidChildInfo = false;
		}
		Pattern pattern = Pattern.compile("\\d{10}");
		if(!pattern.matcher(mobileNumber).matches()){
			isValidChildInfo = false;
		}
		log.info("isValidChildInfo :: "+isValidChildInfo);
		log.info("END :: ChildInfoValidator.isValidChildInfo()");
		return isValidChildInfo;
	}

}
