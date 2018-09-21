package com.photoexhibition.service.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.portlet.PortletRequest;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.photoexhibition.service.model.ViewerInfo;

public class CommonUtil {
	public static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	public static SimpleDateFormat yyyy_MM_dd_sdf = new SimpleDateFormat("yyyy-MM-dd");
	public static ThemeDisplay getThemeDisplay(PortletRequest request) {
		if (null == request) {
			throw new IllegalArgumentException("request is null");
		}
		return (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
	}
	
	public static String displayFormattedDateWithoutDash(Date date) {
		String displayData = "";
		if(date != null) {
			if (date instanceof Date) {
				displayData = String.valueOf(sdf.format(date));
			}
		} 
		return displayData;
	}
	
	public static String displayYYY_MM_DD_Format(Date date) {
		String displayData = "";
		if(date != null) {
			if (date instanceof Date) {
				displayData = String.valueOf(sdf.format(date));
			}
		} 
		return displayData;
	}
	public static boolean sendOtp(ViewerInfo viewerInfo) {
		/*private static boolean sendOtp(String mobileNumber, String otp) {*/
		boolean isOtpSent = false;
		try {
			String otpUrl = "https://2factor.in/API/V1/{api_key}/SMS/+91{user's_phone_no}/{custom_otp_val}";
			otpUrl = StringUtil.replace(otpUrl, new String[] {"{api_key}","{user's_phone_no}","{custom_otp_val}"}, 
												new String[] {"9177b68d-798c-11e6-a584-00163ef91450",viewerInfo.getMobileNumber(),viewerInfo.getOtp()});
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
//				String[] responseArray = response.split("|");
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
	public static String generateOtp() {
		StringBuilder otpString = new StringBuilder();
		int otpStringLength = GeneralConfigurationUtil.getOtpStingLength();
		for (int i = 0; i < otpStringLength; i++) {
			otpString.append(new Random().nextInt(10));
		}
		return otpString.toString();
	}
}
