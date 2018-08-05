package com.photoexhibition.rest.util;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.photoexhibition.rest.constant.RestConstants;
import com.photoexhibition.service.ChildInfoService;
import com.photoexhibition.service.ChildViewerLikeInfoService;
import com.photoexhibition.service.ViewerInfoService;
import com.photoexhibition.service.model.ChildInfo;
import com.photoexhibition.service.model.ChildViewerLikeInfo;
import com.photoexhibition.service.model.GeneralConfigurationInfo;
import com.photoexhibition.service.model.ViewerInfo;
import com.photoexhibition.service.util.BeanLocalServiceUtil;
import com.photoexhibition.service.util.GeneralConfigurationUtil;
import com.photoexhibition.service.util.LoginResponseCode;

public class LikeHandler {
	private static final Log log = LogFactoryUtil.getLog(LikeHandler.class);
	
	private static ChildViewerLikeInfoService childViewerLikeInfoService  = BeanLocalServiceUtil.getChildViewerLikeInfoService();
	private static ViewerInfoService viewerInfoService = BeanLocalServiceUtil.getViewerInfoService();
	private static ChildInfoService childInfoService = BeanLocalServiceUtil.getChildInfoService();
	
	public static JSONObject manageLike(long childId, long viewerId, boolean isLiked) {
		log.debug("START :: LikeHandler.manageLike()");
		JSONObject responseJsonObject = JSONFactoryUtil.createJSONObject();
		try {
			GeneralConfigurationInfo likeServiceConfig = GeneralConfigurationUtil.checkLikeServiceOpen(); 
			boolean isLikeServiceOpen = Boolean.parseBoolean(likeServiceConfig.getValue());
			log.info("isLikeServiceOpen :: "+isLikeServiceOpen);
			if(isLikeServiceOpen) {
				log.info("Like service open, Now Getting childInfo by child ID :: "+childId);
				ChildInfo childInfo = childInfoService.getChildInfoById(childId);
				if(Validator.isNotNull(childInfo)) {
					log.info("Child found with given child id, Now getting viewer info by viewer Id :"+viewerId);
					ViewerInfo viewerInfo = viewerInfoService.getViewerInfoById(viewerId);
					if(Validator.isNotNull(viewerInfo)) {
						log.info("Viewer found with given viewer id");
						ChildViewerLikeInfo childViewerLikeInfo = new ChildViewerLikeInfo();
						childViewerLikeInfo.setLike(isLiked);
						childViewerLikeInfo.setChildInfo(childInfo);
						childViewerLikeInfo.setViewerInfo(viewerInfo);
						childViewerLikeInfoService.saveOrUpdate(childViewerLikeInfo);
						log.info("Child Viewer like saved successfully");
						responseJsonObject.put(RestConstants.MESSAGE, "You liked :"+childInfo.getFirstName());
						responseJsonObject.put(RestConstants.IS_LIKE, true);
					} else {
						log.error("No viewer found with given viewer id");
						responseJsonObject.put(RestConstants.MESSAGE, "Viewer not exist");
						responseJsonObject.put(RestConstants.IS_LIKE, false);
					}
				} else {
					log.error("No child found with given child id");
					responseJsonObject.put(RestConstants.MESSAGE, "Child not exist");
					responseJsonObject.put(RestConstants.IS_LIKE, false);
				}
			} else {
				log.error("Like service not open");
				responseJsonObject.put(RestConstants.MESSAGE, likeServiceConfig.getMessage());
				responseJsonObject.put(RestConstants.IS_LIKE, false);
			}
		} catch (Exception e) {
			log.error("Error :: "+e);
			e.printStackTrace();
		}
		log.debug("START :: LikeHandler.manageLike()");
		return  responseJsonObject;
	}
}
