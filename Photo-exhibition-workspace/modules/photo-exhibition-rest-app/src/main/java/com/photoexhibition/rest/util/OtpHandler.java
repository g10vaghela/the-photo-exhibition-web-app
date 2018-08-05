package com.photoexhibition.rest.util;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.photoexhibition.rest.constant.RestConstants;
import com.photoexhibition.service.ViewerInfoService;
import com.photoexhibition.service.model.ViewerInfo;
import com.photoexhibition.service.util.BeanLocalServiceUtil;

public class OtpHandler {
	private static final Log log = LogFactoryUtil.getLog(OtpHandler.class);
	
	private static ViewerInfoService viewerInfoService = BeanLocalServiceUtil.getViewerInfoService();
	
	public static JSONArray verifyOtp(long viewerId, String otpString){
		log.debug("START :: OtpHandler.verifyOtp()");
		JSONArray responseJsonArray = JSONFactoryUtil.createJSONArray();
		try {
			ViewerInfo viewerInfo = viewerInfoService.getViewerInfoById(viewerId);
			log.info("Otp from viewer info ::"+viewerInfo.getOtp());
			log.info("Otp in request::"+otpString);
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
			JSONObject jsonObjectWrapper = JSONFactoryUtil.createJSONObject();
			if(viewerInfo.getOtp().equalsIgnoreCase(otpString)){
				viewerInfo.setOtpVerified(true);
				viewerInfo.setOtp(StringPool.BLANK);
				viewerInfoService.saveOrUpdate(viewerInfo);
				jsonObject.put(RestConstants.VIEWER_ID, viewerId);
				jsonObject.put(RestConstants.IS_OTP_VERIFIED, true);
				jsonObject.put(RestConstants.MESSAGE, "Your mobile number is verified successfully");
			} else {
				jsonObject.put(RestConstants.VIEWER_ID, viewerId);
				jsonObject.put(RestConstants.IS_OTP_VERIFIED, false);
				jsonObject.put(RestConstants.MESSAGE, "Otp Not matched");
			}
			jsonObjectWrapper.put(RestConstants.VIEWER_INFO, jsonObject);
			responseJsonArray.put(jsonObjectWrapper);
		} catch (Exception e) {
			log.error("Error :: "+e);
			e.printStackTrace();
		}
		log.debug("END :: OtpHandler.verifyOtp()");
		return responseJsonArray;
	}

}
