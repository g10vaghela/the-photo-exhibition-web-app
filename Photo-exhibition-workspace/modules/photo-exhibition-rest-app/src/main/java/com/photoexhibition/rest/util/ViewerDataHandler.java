package com.photoexhibition.rest.util;

import java.util.List;

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
import com.photoexhibition.service.model.AdvertiseInfo;
import com.photoexhibition.service.model.ChildInfo;
import com.photoexhibition.service.model.ChildViewerLikeInfo;
import com.photoexhibition.service.model.ViewerInfo;
import com.photoexhibition.service.util.BeanLocalServiceUtil;
import com.photoexhibition.service.util.LoginResponseCode;

public class ViewerDataHandler {
	private static final Log log = LogFactoryUtil.getLog(ViewerDataHandler.class);
	
	private static ViewerInfoService viewerInfoService = BeanLocalServiceUtil.getViewerInfoService();
	private static AdvertiseInfoService advertiseInfoService = BeanLocalServiceUtil.getAdvertiseInfoService();
	private static ChildInfoService childInfoService = BeanLocalServiceUtil.getChildInfoService();
	private static ChildViewerLikeInfoService childViewerLikeInfoService = BeanLocalServiceUtil.getChildViewerLikeInfoService();

	public static JSONArray getViewerData(long viewerId) {
		log.debug("START :: ViewerDataHandler.getViewerData()");
		JSONArray responseJsonArray = JSONFactoryUtil.createJSONArray();
		try {
			ViewerInfo viewerInfo = viewerInfoService.getViewerInfoById(viewerId);
			if(Validator.isNotNull(viewerInfo)) {
				responseJsonArray = getViewerResponseJson(viewerInfo);
			} else {
				log.error("Viewer not found with id :"+viewerId);
			}
		} catch (Exception e) {
			log.error("Error ::"+e);
			e.printStackTrace();
		}
		log.debug("END :: ViewerDataHandler.getViewerData()");
		return responseJsonArray;
	}
	
	public static JSONArray getViewerResponseJson(ViewerInfo viewerInfo) {
		log.debug("START :: ViewerDataHandler.getViewerResponseJson()");
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
			childJson.put(RestConstants.CHILD_INFO, childJsonArray);
			advertiseJson.put(RestConstants.ADVERTISE_INFO, advertiseInfoList);
			viewerInfoJson.put(RestConstants.VIEWER_ID, viewerInfo.getViewerId());
			viewerInfoJson.put(RestConstants.IS_OTP_VERIFIED, viewerInfo.isOtpVerified());
			viewerInfoJson.put(RestConstants.MESSAGE, "Viewer Login Successfully");
			viewerInfoJson.put(RestConstants.RESPONSE_CODE, LoginResponseCode.SUCCESS.getValue());
			JSONObject viewerJsonWrapper = JSONFactoryUtil.createJSONObject();
			viewerJsonWrapper.put(RestConstants.VIEWER_INFO, viewerInfoJson);
			responseJsonArray.put(viewerJsonWrapper);
			responseJsonArray.put(childJson);
			responseJsonArray.put(advertiseJson);
		} catch (Exception e) {
			log.error("Error :: "+e);
			e.printStackTrace();
		}
		log.debug("END :: ViewerDataHandler.getViewerResponseJson()");
		return responseJsonArray;
	}

	public static JSONObject getJsonByObject(ChildInfo childInfo, boolean isLike) throws PortalException {
		log.debug("START :: ViewerDataHandler.getJsonByObject()");
		JSONObject childJsonObject = JSONFactoryUtil.createJSONObject();
		childJsonObject.put(RestConstants.CHILD_ID, childInfo.getChildId());
		childJsonObject.put(RestConstants.CHILD_NAME, childInfo.getFullName());
		childJsonObject.put(RestConstants.IS_LIKE, isLike);
		if(Validator.isNull(childInfo.getPhotoUrl())){
			childJsonObject.put(RestConstants.PHOTO_URL, "null");
		} else {
			childJsonObject.put(RestConstants.PHOTO_URL, childInfo.getPhotoUrl());
		}
		log.debug("END :: ViewerDataHandler.getJsonByObject()");
		return childJsonObject;
	}
}
