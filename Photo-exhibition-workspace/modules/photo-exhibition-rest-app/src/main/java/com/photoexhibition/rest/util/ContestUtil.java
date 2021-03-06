package com.photoexhibition.rest.util;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.photoexhibition.rest.constant.RestConstants;
import com.photoexhibition.service.GeneralConfigurationService;
import com.photoexhibition.service.constant.GeneralConfigurationConstants;
import com.photoexhibition.service.model.GeneralConfigurationInfo;
import com.photoexhibition.service.util.BeanLocalServiceUtil;

public class ContestUtil {
	private static final Log log = LogFactoryUtil.getLog(ContestUtil.class);
	private static GeneralConfigurationService generalConfigurationService = BeanLocalServiceUtil.getGeneralConfigurationService();
	public static JSONArray checkContestOpen() {
		log.debug("START :: ContestUtil.checkContestOpen()");
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();
		try {
			GeneralConfigurationInfo generalConfigurationInfo = generalConfigurationService.getGeneralCongfigurationByKey(GeneralConfigurationConstants.IS_CONTENST_OPEN);
			if(Validator.isNotNull(generalConfigurationInfo)) {
				boolean isContestOpen = Boolean.parseBoolean(generalConfigurationInfo.getValue());
				log.debug("isContestOpen ::"+isContestOpen);
				JSONObject contestJsonObject = JSONFactoryUtil.createJSONObject();
				contestJsonObject.put(RestConstants.IS_CONTEST_OPEN,isContestOpen);
				if(isContestOpen) {
					contestJsonObject.put(RestConstants.MESSAGE, "");
				} else {
					contestJsonObject.put(RestConstants.MESSAGE, generalConfigurationInfo.getMessage());
				}
				jsonArray.put(contestJsonObject); 
			} else {
				log.error("Null Generel config found");
			}
		} catch (Exception e) {
			log.error(e);
			JSONObject errorJson = JSONFactoryUtil.createJSONObject();
			errorJson.put("error", e);
			jsonArray.put(errorJson);
		}
		log.debug("END :: ContestUtil.checkContestOpen()");
		return jsonArray;
	}
}
