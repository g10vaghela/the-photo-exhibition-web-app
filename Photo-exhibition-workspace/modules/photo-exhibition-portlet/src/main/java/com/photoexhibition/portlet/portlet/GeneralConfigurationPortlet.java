package com.photoexhibition.portlet.portlet;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.ProcessAction;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.omg.CORBA.Request;
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

@Component(
		immediate = true,
		property = {
			"com.liferay.portlet.display-category=Photo-Exhibition",
			"com.liferay.portlet.instanceable=true",
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
		GeneralConfigurationInfo contestConfigurationInfo= generalConfigurationService.getGeneralCongfigurationByKey(GeneralConfigurationConstants.IS_CONTENST_OPEN);
		GeneralConfigurationInfo otpConfigurationInfo= generalConfigurationService.getGeneralCongfigurationByKey(GeneralConfigurationConstants.IS_OTP_SERVICE_ON);				
		renderRequest.setAttribute("contestConfigurationInfo", contestConfigurationInfo);
		renderRequest.setAttribute("otpConfigurationInfo", otpConfigurationInfo);
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
}
