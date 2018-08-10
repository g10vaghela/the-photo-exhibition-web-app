package com.photoexhibition.portlet.validator;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

public class AdvertiseInfoValidator {
	private static final Log log = LogFactoryUtil.getLog(AdvertiseInfoValidator.class);
	
	public static boolean isValidAdvertisement(String advertiseName, String photoOrientation){
		log.debug("START :: AdvertiseInfoValidator.isValidAdvertisement()");
		boolean isValideAdvertisement = true;
		if(Validator.isNull(advertiseName) || Validator.isNull(photoOrientation)){
			isValideAdvertisement = false;
		}
		log.debug("END :: AdvertiseInfoValidator.isValidAdvertisement()");
		return isValideAdvertisement;
	}
}
