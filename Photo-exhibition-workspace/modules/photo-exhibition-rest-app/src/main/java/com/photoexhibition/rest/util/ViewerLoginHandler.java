package com.photoexhibition.rest.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.photoexhibition.rest.constant.RestConstants;
import com.photoexhibition.rest.util.validator.Validate;
import com.photoexhibition.service.ViewerInfoService;
import com.photoexhibition.service.constant.GeneralConfigurationConstants;
import com.photoexhibition.service.model.ViewerInfo;
import com.photoexhibition.service.util.BeanLocalServiceUtil;
import com.photoexhibition.service.util.GeneralConfigurationUtil;
import com.photoexhibition.service.util.LoginResponseCode;
import com.photoexhibition.service.util.OtpGeneratorUtil;

public class ViewerLoginHandler {
	private static final Log log = LogFactoryUtil.getLog(ViewerLoginHandler.class);

	private static ViewerInfoService viewerInfoService = BeanLocalServiceUtil.getViewerInfoService();

	public static JSONArray handleViewerLogin(String mobileNumber, String deviceNumber) {
		log.debug("START :: ViewerLoginHandler.handleViewerLogin()");
		JSONArray responseJsonArray = JSONFactoryUtil.createJSONArray();
		try {
			log.info("Getting viewer details for Mobile number :: "+mobileNumber+" and device number :: "+deviceNumber);
			if(Validate.isValidPhoneNumber(mobileNumber) && Validate.isValidDeviceNumber(deviceNumber)){
				ViewerInfo viewerInfo = viewerInfoService.getViewerInfoByMobileAndDeviceNumber(mobileNumber, deviceNumber);
				log.info("Is Gegistered Viewer :"+Validator.isNotNull(viewerInfo));
				if(Validator.isNotNull(viewerInfo)) {
					log.info("Gegistered viewer found now checking for mobile number verification");
					log.info("Is viewer mobile number : "+mobileNumber+" verified ? "+viewerInfo.isOtpVerified());
					//Registered viewer found
					if(viewerInfo.isOtpVerified()) {
						log.info("Mobile number is verified for viewer");
						//viewer is registered and mobile number is verified
						responseJsonArray = ViewerDataHandler.getViewerResponseJson(viewerInfo);
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
			} else {
				log.info("Invalid mobile number or device number");
				JSONObject viewerJson = JSONFactoryUtil.createJSONObject();
				JSONObject viewerJsonWrapper = JSONFactoryUtil.createJSONObject();
				viewerJson.put(RestConstants.VIEWER_ID, "");
				viewerJson.put(RestConstants.IS_OTP_VERIFIED, false);
				viewerJson.put(RestConstants.RESPONSE_CODE, LoginResponseCode.VALIDATION_FAIL.getValue());
				viewerJson.put(RestConstants.MESSAGE, "Invalid Mobile number and/or device number");
				viewerJsonWrapper.put(RestConstants.VIEWER_INFO, viewerJson);
				responseJsonArray.put(viewerJsonWrapper);
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
	/*private static boolean sendOtp(String mobileNumber, String otp) {*/
		boolean isOtpSent = false;
		try {
			String otpUrl = "https://2factor.in/API/V1/{api_key}/SMS/+91{user's_phone_no}/{custom_otp_val}";
			otpUrl = StringUtil.replace(otpUrl, new String[] {"{api_key}","{user's_phone_no}","{custom_otp_val}"}, 
												new String[] {"9177b68d-798c-11e6-a584-00163ef91450",viewerInfo.getMobileNumber(),viewerInfo.getOtp()});
			log.info("otpUrl :: "+otpUrl);
			URLConnection myURLConnection=null;
			URL url = new URL(otpUrl);
			myURLConnection = url.openConnection();
			BufferedReader reader=null;
			myURLConnection.connect();
			reader= new BufferedReader(new InputStreamReader(myURLConnection.getInputStream()));
			String response;
			while ((response = reader.readLine()) != null) {break;}
						//{"Status":"Success","Details":"6460fcc0-9719-11e8-a895-0200cd936042"}
			System.out.println(response);
			/*response = StringUtil.replace(response, 
											new String[] {"{\"","\":\"","\",\"","\"}"}, 
											new String[] {"","|","|",""});*/
			System.out.println(response);
//			String[] responseArray = response.split("|");
			JSONObject responseJson = JSONFactoryUtil.createJSONObject(response);
			System.out.println("Response :: "+responseJson);
			//finally close connection
			/*isOtpSent = "Success".equalsIgnoreCase(responseArray[1]);*/
			isOtpSent = "Success".equals(responseJson.getString("Status"));
			System.out.println("isOtpSent  ::"+isOtpSent);
			reader.close();
			
		} catch (Exception e) {
			System.out.println(e);
		}
		return isOtpSent;
	}
}
