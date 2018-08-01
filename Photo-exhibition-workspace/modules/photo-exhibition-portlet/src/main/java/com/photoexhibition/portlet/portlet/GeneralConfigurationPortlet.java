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
		GeneralConfigurationInfo generalConfigurationInfo= generalConfigurationService.getGeneralCongfigurationByKey(GeneralConfigurationConstants.IS_CONTENST_OPEN);
		if(Validator.isNotNull(generalConfigurationInfo)){
			renderRequest.setAttribute(GeneralConfigurationConstants.IS_CONTENST_OPEN, generalConfigurationInfo);
		} else {
			renderRequest.setAttribute(GeneralConfigurationConstants.IS_CONTENST_OPEN, null);
		}
		super.render(renderRequest, renderResponse);
	}
	
	@ProcessAction(name="addOrUpdateConfiguration")
	public void addOrUpdateConfiguration(ActionRequest actionRequest, ActionResponse actionResponse) {
		log.debug("START :: GeneralConfigurationPortlet.addOrUpdateConfiguration()");
		String configKey = actionRequest.getParameter("configKey");
		String configValue = actionRequest.getParameter("configValue");
		String configMessage = actionRequest.getParameter("configMessage");
		log.debug("configKey :: "+configKey);
		log.debug("configValue :: "+configValue);
		log.debug("configMessage :: "+configMessage);
		try {
			if(Validator.isNotNull(configKey) && Validator.isNotNull(configValue)) {
				GeneralConfigurationInfo generalConfigurationInfo = new GeneralConfigurationInfo();
				generalConfigurationInfo.setKey(configKey);
				generalConfigurationInfo.setValue(configValue);
				generalConfigurationInfo.setMessage(configMessage);
				generalConfigurationService.saveOrUpdate(generalConfigurationInfo);
				log.debug("generalConfigurationInfo ::"+generalConfigurationInfo+" Saved Successfully...!!!");
				SessionMessages.add(actionRequest,MessageConstant.GENERAL_CONFIG_SAVE_OR_UPDATE_SUCCESS_MESSAGE);
			} else {
				log.error("Configuration key or value can not be null");
				SessionErrors.add(actionRequest, MessageConstant.GENERAL_CONFIG_SAVE_OR_UPDATE_ERROR_MESSAGE);
			}
		} catch (Exception e) {
			log.error("Error ::"+e);
			SessionErrors.add(actionRequest, MessageConstant.GENERAL_CONFIG_SAVE_OR_UPDATE_ERROR_MESSAGE);
		}
		log.debug("END :: GeneralConfigurationPortlet.addOrUpdateConfiguration()");
	}
}
