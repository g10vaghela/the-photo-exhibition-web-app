package com.photoexhibition.portlet;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.ProcessAction;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.Validator;
import com.photoexhibition.portlet.constants.ControllerPortletKeys;
import com.photoexhibition.portlet.constants.MessageConstant;
import com.photoexhibition.service.GeneralConfigurationService;
import com.photoexhibition.service.constant.GeneralConfigurationConstants;
import com.photoexhibition.service.model.GeneralConfigurationInfo;
import com.photoexhibition.service.util.BeanLocalServiceUtil;
import com.photoexhibition.service.util.GeneralConfigurationUtil;

@Component(
		immediate = true,
		property = {
			"com.liferay.portlet.display-category=Photo-Exhibition",
			"com.liferay.portlet.instanceable=true",
			"com.liferay.portlet.header-portlet-css=/css/custom.css",
			"add-process-action-success-action=false",
			"javax.portlet.display-name=General Configuration Portlet",
			"javax.portlet.init-param.template-path=/",
			"javax.portlet.init-param.view-template=/general-configuration/generalConfig.jsp",
			"javax.portlet.name=" + ControllerPortletKeys.GeneralConfigurationController,
			"javax.portlet.resource-bundle=content.Language",
			"javax.portlet.security-role-ref=power-user,user"
		},
		service = Portlet.class
	)
public class GeneralConfigurationPortlet extends MVCPortlet {
	private static final Log log = LogFactoryUtil.getLog(GeneralConfigurationPortlet.class);
	private GeneralConfigurationService generalConfigurationService = BeanLocalServiceUtil.getGeneralConfigurationService();
	@Override
	public void render(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		//GeneralConfigurationInfo contestConfigurationInfo= generalConfigurationService.getGeneralCongfigurationByKey(GeneralConfigurationConstants.IS_CONTENST_OPEN);
		GeneralConfigurationInfo contestConfigurationInfo= GeneralConfigurationUtil.isContestOpenConfiguration();
		//GeneralConfigurationInfo otpConfigurationInfo= generalConfigurationService.getGeneralCongfigurationByKey(GeneralConfigurationConstants.IS_OTP_SERVICE_ON);				
		GeneralConfigurationInfo otpConfigurationInfo= GeneralConfigurationUtil.isOtpServiceOn();
		GeneralConfigurationInfo validDistanceConfiguration = GeneralConfigurationUtil.getValidDistanceFromContestLocation();
		GeneralConfigurationInfo likeServiceGeneralConfig = GeneralConfigurationUtil.checkLikeServiceOpen();
		String  contestLocationLatLong = GeneralConfigurationUtil.getContestLocationLatLong();
		boolean isLocationTrackingOn = GeneralConfigurationUtil.isLocationTrackingOn();
		int minimumRequiredLike = GeneralConfigurationUtil.getMinimumLikeRequired();
		renderRequest.setAttribute("contestConfigurationInfo", contestConfigurationInfo);
		renderRequest.setAttribute("otpConfigurationInfo", otpConfigurationInfo);
		renderRequest.setAttribute("validDistanceConfiguration", validDistanceConfiguration);
		renderRequest.setAttribute("likeServiceGeneralConfig", likeServiceGeneralConfig);
		renderRequest.setAttribute("contestLocationLatLong", contestLocationLatLong);
		renderRequest.setAttribute("isLocationTrackingOn", isLocationTrackingOn);
		renderRequest.setAttribute("minimumRequiredLike", minimumRequiredLike);
		
		super.render(renderRequest, renderResponse);
	}

	@ProcessAction(name="updateContestConfig")
	public void updateContestConfig(ActionRequest actionRequest, ActionResponse actionResponse) {
		log.info("START :: GeneralConfigurationPortlet.updateContestConfig()");
		String configKey = actionRequest.getParameter("contest-switch");
		String configMessage = actionRequest.getParameter("configMessage");
		log.info("contest-switch :: "+configKey);
		log.info("configMessage :: "+configMessage);
		try {
			GeneralConfigurationInfo generalConfigurationInfo = new GeneralConfigurationInfo();
			generalConfigurationInfo.setKey(GeneralConfigurationConstants.IS_CONTENST_OPEN);
			if(Validator.isNotNull(configKey)) {
				generalConfigurationInfo.setValue("true");
				generalConfigurationInfo.setMessage(null);
			} else {
				generalConfigurationInfo.setValue("false");
				generalConfigurationInfo.setMessage(configMessage);
			}
			generalConfigurationService.saveOrUpdate(generalConfigurationInfo);
			SessionMessages.add(actionRequest, MessageConstant.GENERAL_CONFIG_SAVE_OR_UPDATE_SUCCESS_MESSAGE);
		} catch (Exception e) {
			log.error("Error ::"+e);
			SessionErrors.add(actionRequest, MessageConstant.GENERAL_CONFIG_SAVE_OR_UPDATE_ERROR_MESSAGE);
		}
		log.info("END :: GeneralConfigurationPortlet.updateContestConfig()");
	}

	@ProcessAction(name="updateOtpConfig")
	public void updateOtpConfig(ActionRequest actionRequest, ActionResponse actionResponse) {
		log.info("START :: GeneralConfigurationPortlet.updateOtpConfig()");
		String otpSwitch = actionRequest.getParameter("otp-switch");
		log.info("otpSwitch :: "+otpSwitch);
		try {
			GeneralConfigurationInfo generalConfigurationInfo = new GeneralConfigurationInfo();
			generalConfigurationInfo.setKey(GeneralConfigurationConstants.IS_OTP_SERVICE_ON);
			if(Validator.isNotNull(otpSwitch)) {
				generalConfigurationInfo.setValue("true");
			} else {
				generalConfigurationInfo.setValue("false");
			}
			generalConfigurationService.saveOrUpdate(generalConfigurationInfo);
			SessionMessages.add(actionRequest, MessageConstant.GENERAL_CONFIG_SAVE_OR_UPDATE_SUCCESS_MESSAGE);
		} catch (Exception e) {
			log.error("Error ::"+e);
			SessionErrors.add(actionRequest, MessageConstant.GENERAL_CONFIG_SAVE_OR_UPDATE_ERROR_MESSAGE);
		}
		log.info("END :: GeneralConfigurationPortlet.updateOtpConfig()");
	}

	@ProcessAction(name="validDistanceConfigurationUpdate")
	public void validDistanceConfigurationUpdate(ActionRequest actionRequest, ActionResponse actionResponse){
		log.info("START :: GeneralConfigurationPortlet.validDistanceConfigurationUpdate()");
		String validDistance = actionRequest.getParameter("validDistance");
		try {
			log.info("validDistance ::"+validDistance);
			if(Validator.isNotNull(validDistance)){
				GeneralConfigurationInfo generalConfigurationInfo = new GeneralConfigurationInfo();
				generalConfigurationInfo.setKey(GeneralConfigurationConstants.VALID_DISTANCE_FROM_CONTEST_LOCATION);
				generalConfigurationInfo.setValue(validDistance);
				generalConfigurationService.saveOrUpdate(generalConfigurationInfo);
				SessionMessages.add(actionRequest, MessageConstant.GENERAL_CONFIG_SAVE_OR_UPDATE_SUCCESS_MESSAGE);
			}
		} catch (Exception e) {
			log.error("Error ::"+e);
			SessionErrors.add(actionRequest, MessageConstant.GENERAL_CONFIG_SAVE_OR_UPDATE_ERROR_MESSAGE);
		}
		log.info("END :: GeneralConfigurationPortlet.validDistanceConfigurationUpdate()");
	}

	@ProcessAction(name="likeServiceConfigurationUpdate")
	public void likeServiceConfigurationUpdate(ActionRequest actionRequest, ActionResponse actionResponse){
		log.info("START :: GeneralConfigurationPortlet.likeServiceConfigurationUpdate()");
		String likeService = actionRequest.getParameter("like-service-switch");
		String configMessage = actionRequest.getParameter("likeConfigMessage");
		try {
			GeneralConfigurationInfo generalConfigurationInfo = new GeneralConfigurationInfo();
			generalConfigurationInfo.setKey(GeneralConfigurationConstants.IS_LIKE_SERVICE_OPEN);
			if(Validator.isNotNull(likeService)){
				generalConfigurationInfo.setValue("true");
			} else {
				generalConfigurationInfo.setValue("false");
			}
			generalConfigurationInfo.setMessage(configMessage);
			generalConfigurationService.saveOrUpdate(generalConfigurationInfo);
			SessionMessages.add(actionRequest, MessageConstant.GENERAL_CONFIG_SAVE_OR_UPDATE_SUCCESS_MESSAGE);
		} catch (Exception e) {
			log.error("Error ::"+e);
			SessionErrors.add(actionRequest, MessageConstant.GENERAL_CONFIG_SAVE_OR_UPDATE_ERROR_MESSAGE);
		}
		log.info("END :: GeneralConfigurationPortlet.likeServiceConfigurationUpdate()");
	}

	@ProcessAction(name="locationLatLongUpdate")
	public void locationLatLongUpdate(ActionRequest actionRequest, ActionResponse actionResponse){
		log.info("START :: GeneralConfigurationPortlet.locationLatLongUpdate()");
		String latitude = actionRequest.getParameter("locationLatitude");
		String longitude = actionRequest.getParameter("locationLongitude");
		log.info("latitude ::"+latitude);
		log.info("longitude ::"+longitude);
		try {
			String configMessage = "{\"latitude\":\""+latitude+"\",\"longitude\":\""+longitude+"\"}";
			System.out.println("configMessage ::"+configMessage);
			GeneralConfigurationInfo generalConfigurationInfo = new GeneralConfigurationInfo();
			generalConfigurationInfo.setKey(GeneralConfigurationConstants.CONTEST_LOCATION_LAT_LONG);
			generalConfigurationInfo.setMessage(configMessage);
			generalConfigurationService.saveOrUpdate(generalConfigurationInfo);
			SessionMessages.add(actionRequest, MessageConstant.GENERAL_CONFIG_SAVE_OR_UPDATE_SUCCESS_MESSAGE);
		} catch (Exception e) {
			log.error("Error : "+e);
			SessionErrors.add(actionRequest, MessageConstant.GENERAL_CONFIG_SAVE_OR_UPDATE_ERROR_MESSAGE);
		}
		log.info("END :: GeneralConfigurationPortlet.locationLatLongUpdate()");
	}

	@ProcessAction(name="minimumLikeRequired")
	public void minimumLikeRequired(ActionRequest actionRequest, ActionResponse actionResponse){
		log.info("START :: GeneralConfigurationPortlet.minimumLikeRequired()");
		String minimumRequiredLike = actionRequest.getParameter("minimumLikeRequired");
		log.info("minimumRequiredLike :: "+minimumRequiredLike);
		try {
			GeneralConfigurationInfo generalConfigurationInfo = new GeneralConfigurationInfo();
			generalConfigurationInfo.setKey(GeneralConfigurationConstants.MINIMUM_LIKE_REQUIRED);
			generalConfigurationInfo.setValue(minimumRequiredLike);
			generalConfigurationService.saveOrUpdate(generalConfigurationInfo);
			SessionMessages.add(actionRequest, MessageConstant.GENERAL_CONFIG_SAVE_OR_UPDATE_SUCCESS_MESSAGE);
		} catch (Exception e) {
			log.error("Error : "+e);
			SessionErrors.add(actionRequest, MessageConstant.GENERAL_CONFIG_SAVE_OR_UPDATE_ERROR_MESSAGE);
		}
		log.info("END :: GeneralConfigurationPortlet.minimumLikeRequired()");
	}
}
