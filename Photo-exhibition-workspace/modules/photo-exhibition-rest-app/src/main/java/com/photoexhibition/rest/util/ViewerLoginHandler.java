package com.photoexhibition.rest.util;

import java.net.URL;
import java.util.List;

import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.photoexhibition.rest.constant.RestConstants;
import com.photoexhibition.service.AdvertiseInfoService;
import com.photoexhibition.service.ChildInfoService;
import com.photoexhibition.service.ChildViewerLikeInfoService;
import com.photoexhibition.service.ViewerInfoService;
import com.photoexhibition.service.constant.GeneralConfigurationConstants;
import com.photoexhibition.service.model.AdvertiseInfo;
import com.photoexhibition.service.model.ChildInfo;
import com.photoexhibition.service.model.ChildViewerLikeInfo;
import com.photoexhibition.service.model.ViewerInfo;
import com.photoexhibition.service.util.BeanLocalServiceUtil;
import com.photoexhibition.service.util.GeneralConfigurationUtil;
import com.photoexhibition.service.util.LoginResponseCode;
import com.photoexhibition.service.util.OtpGeneratorUtil;

public class ViewerLoginHandler {
	private static final Log log = LogFactoryUtil.getLog(ViewerLoginHandler.class);

	private static ViewerInfoService viewerInfoService = BeanLocalServiceUtil.getViewerInfoService();

	private static AdvertiseInfoService advertiseInfoService = BeanLocalServiceUtil.getAdvertiseInfoService();

	private static ChildInfoService childInfoService = BeanLocalServiceUtil.getChildInfoService();
	
	private static ChildViewerLikeInfoService childViewerLikeInfoService = BeanLocalServiceUtil.getChildViewerLikeInfoService();

	public static JSONArray handleViewerLogin(String mobileNumber, String deviceNumber) {
		log.debug("START :: ViewerLoginHandler.handleViewerLogin()");
		JSONArray responseJsonArray = JSONFactoryUtil.createJSONArray();
		try {
			log.info("Getting viewer details for Mobile number :: "+mobileNumber+" and device number :: "+deviceNumber);
			ViewerInfo viewerInfo = viewerInfoService.getViewerInfoByMobileAndDeviceNumber(mobileNumber, deviceNumber);
			log.info("Is Gegistered Viewer :"+Validator.isNotNull(viewerInfo));
			if(Validator.isNotNull(viewerInfo)) {
				log.info("Gegistered viewer found now checking for mobile number verification");
				log.info("Is viewer mobile number : "+mobileNumber+" verified ? "+viewerInfo.isOtpVerified());
				//Registered viewer found
				if(viewerInfo.isOtpVerified()) {
					log.info("Mobile number is verified for viewer");
					//viewer is registered and mobile number is verified
					responseJsonArray = getViewerResponseJson(viewerInfo);
				} else {
					log.info("Mobile number not verified");
					log.info("Verification process started....");
					//viewer is registered but mobile number not verified
					responseJsonArray = getViewerOtpJson(viewerInfo);
				}
			} else {
				log.info("Viewer not found with given mobile number and device");
				viewerInfo = viewerInfoService.getViewerInfoByMobileNumber(mobileNumber);
				log.info("Checking whether mobile number :"+mobileNumber+" is registered with other device ?");
				if(Validator.isNotNull(viewerInfo)) {
					log.info("Yes, Mobile number is registered with other device number");
					responseJsonArray = getErrorResponseForRegisteredViewer(viewerInfo,GeneralConfigurationConstants.REGISTERED_MOBILE_NUMBER_ERROR);
				} else {
					log.info("No, Mobile number not registered");
					viewerInfo = viewerInfoService.getViewerInfoByDeviceNumber(deviceNumber);
					log.info("Checking whether device number : "+deviceNumber+" is registered with other mobile number ?");
					if(Validator.isNotNull(viewerInfo)) {
						log.info("Yes, Device is Registered with other mobile number");
						responseJsonArray = getErrorResponseForRegisteredViewer(viewerInfo,GeneralConfigurationConstants.REGISTERED_DEVICE_NUMBER_ERROR);
					} else {
						log.info("No, This is new viewer");
						viewerInfo = new ViewerInfo();
						viewerInfo.setMobileNumber(mobileNumber);
						viewerInfo.setDeviceNumber(deviceNumber);
						viewerInfo = viewerInfoService.save(viewerInfo);
						log.info("Creating new viewer in the system");
						responseJsonArray = getViewerOtpJson(viewerInfo);
					}
				}
			}
		} catch (Exception e) {
			log.error("Error ::"+e);
			e.printStackTrace();
		}
		log.debug("END :: ViewerLoginHandler.handleViewerLogin()");
		return responseJsonArray;
	}
	
	/**
	 * Sending error message for already registered user by mobile or device number
	 * but not in current pair
	 * @param viewerInfo
	 * @param errorMessageKey
	 * @return
	 */
	private static JSONArray getErrorResponseForRegisteredViewer(ViewerInfo viewerInfo,String errorMessageKey) {
		JSONArray responseJsonArray = JSONFactoryUtil.createJSONArray();
		JSONObject viewerJsonObjectWrapper = JSONFactoryUtil.createJSONObject();
		JSONObject viewerJsonObject = JSONFactoryUtil.createJSONObject();
		viewerJsonObject.put(RestConstants.VIEWER_ID, viewerInfo.getViewerId());
		viewerJsonObject.put(RestConstants.IS_OTP_VERIFIED, false);
		viewerJsonObject.put(RestConstants.RESPONSE_CODE, LoginResponseCode.ERROR.getValue());
		viewerJsonObject.put(RestConstants.MESSAGE, GeneralConfigurationUtil.getErrorMessageForViewerMobileDevice(errorMessageKey));
		viewerJsonObjectWrapper.put(RestConstants.VIEWER_INFO, viewerJsonObject);
		responseJsonArray.put(viewerJsonObjectWrapper);
		return responseJsonArray;
	}

	public static JSONArray getViewerOtpJson(ViewerInfo viewerInfo) {
		JSONArray responseJsonArray = JSONFactoryUtil.createJSONArray();
		log.info("Otp verification process started...");
		try {
			//send otp to registred mobile number and otp screen to mobile app
			boolean isOtpServiceOn = Boolean.parseBoolean(GeneralConfigurationUtil.isOtpServiceOn().getValue());
			log.info("isOtpServiceOn :: "+isOtpServiceOn);
			JSONObject viewerJsonWrapper = JSONFactoryUtil.createJSONObject();
			JSONObject viewerJson = JSONFactoryUtil.createJSONObject();
			if(isOtpServiceOn) {
				log.info("Yes, Otp service is on, now generating otp");
				String otpString = OtpGeneratorUtil.generateOtp();
				log.info("otpString :: "+otpString);
				viewerInfo.setOtp(otpString);
				viewerInfoService.saveOrUpdate(viewerInfo);
				log.info("Otp saved for user, now sending otp");
				boolean isOtpSent = sendOtp(viewerInfo);
				log.info("isOtpSent :: "+isOtpSent);
				if(isOtpSent) {
					log.info("Yes, Otp is sent from system to registered mobile number");
					//OTP SENT SUCCESSFULLY AND MESSAGE BIND FOR THE SAME
					viewerJson.put(RestConstants.VIEWER_ID, viewerInfo.getViewerId());
					viewerJson.put(RestConstants.IS_OTP_VERIFIED, false);
					viewerJson.put(RestConstants.RESPONSE_CODE, LoginResponseCode.OTP_SENT.getValue());
					viewerJson.put(RestConstants.MESSAGE, GeneralConfigurationUtil.getOtpSendMessage());
				} else {
					log.info("No, There is problem while sending otp");
					//ERROR FOUND IN OTP SENDING AND MESSAGE BIND FOR THE SAME
					viewerJson.put(RestConstants.VIEWER_ID, viewerInfo.getViewerId());
					viewerJson.put(RestConstants.IS_OTP_VERIFIED, false);
					viewerJson.put(RestConstants.RESPONSE_CODE, LoginResponseCode.OTP_SENT.getValue());
					viewerJson.put(RestConstants.MESSAGE, GeneralConfigurationUtil.getProbleInOtpSendingMessage());
				}
			} else {
				log.info("No, Otp service off, So bypassing otp verification");
				viewerJson.put(RestConstants.VIEWER_ID, viewerInfo.getViewerId());
				viewerJson.put(RestConstants.IS_OTP_VERIFIED, true);
				viewerJson.put(RestConstants.RESPONSE_CODE, LoginResponseCode.SUCCESS.getValue());
				viewerJson.put(RestConstants.MESSAGE, "");
			}
			viewerJsonWrapper.put(RestConstants.VIEWER_INFO, viewerJson);
			responseJsonArray.put(viewerJsonWrapper);
		} catch (Exception e) {
			log.error("Error ::"+e);
		}
		return responseJsonArray;
	}
	
	private static boolean sendOtp(ViewerInfo viewerInfo) {
		
		return false;
	}

	public static JSONArray getViewerResponseJson(ViewerInfo viewerInfo) {
		//otp is verified just send child list and advertise list
		JSONArray responseJsonArray = JSONFactoryUtil.createJSONArray();
		try {
			List<AdvertiseInfo> advertiseInfoList = advertiseInfoService.getAdvertiseInfoList();
			List<ChildInfo> childInfoList = childInfoService.getChildInfoList();
			JSONArray childJsonArray =  JSONFactoryUtil.createJSONArray();
			JSONObject childJson = JSONFactoryUtil.createJSONObject();
			JSONObject advertiseJson = JSONFactoryUtil.createJSONObject();
			JSONObject viewerInfoJson = JSONFactoryUtil.createJSONObject();
			for (ChildInfo childInfo : childInfoList) {
				ChildViewerLikeInfo childViewerLikeInfo = childViewerLikeInfoService.getChildViewerLikeInfoByChildAndViewerId(childInfo.getChildId(), viewerInfo.getViewerId());
				if(Validator.isNotNull(childViewerLikeInfo)) {
					childJsonArray.put(getJsonByObject(childInfo, childViewerLikeInfo.isLike()));
				} else {
					childJsonArray.put(getJsonByObject(childInfo, false));
				}
			}
			childJson.put("childData", childJsonArray);
			advertiseJson.put("advertiseData", advertiseInfoList);
			viewerInfoJson.put("viewerId", viewerInfo.getViewerId());
			viewerInfoJson.put("isOtpVerified", viewerInfo.isOtpVerified());
			viewerInfoJson.put("responseCode", LoginResponseCode.SUCCESS.getValue());
			JSONObject viewerJsonWrapper = JSONFactoryUtil.createJSONObject();
			viewerJsonWrapper.put("viewerInfo", viewerInfoJson);
			responseJsonArray.put(viewerJsonWrapper);
			responseJsonArray.put(childJson);
			responseJsonArray.put(advertiseJson);
		} catch (Exception e) {
			log.error("Error :: "+e);
		}
		return responseJsonArray;
	}
	
	public static JSONObject getJsonByObject(ChildInfo childInfo, boolean isLike) throws PortalException {
		JSONObject childJsonObject = JSONFactoryUtil.createJSONObject();
		childJsonObject.put("childId", childInfo.getChildId());
		childJsonObject.put("childName", childInfo.getFullName());
		childJsonObject.put("isLike", isLike);
		childJsonObject.put("photoUrl", childInfo.getPhotoUrl());
		return childJsonObject;
	}
}
