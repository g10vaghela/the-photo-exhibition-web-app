package com.photoexhibition.portlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Validator;
import com.photoexhibition.portlet.constants.ControllerPortletKeys;
import com.photoexhibition.portlet.search.criteria.util.SearchContainerUtil;
import com.photoexhibition.service.ViewerInfoService;
import com.photoexhibition.service.model.ViewerInfo;
import com.photoexhibition.service.search.criteria.ViewerInfoSearchCriteria;
import com.photoexhibition.service.util.BeanLocalServiceUtil;
import com.photoexhibition.service.util.CommonUtil;

@Component(
		immediate = true,
		property = {
			"com.liferay.portlet.display-category=Photo-Exhibition",
			"com.liferay.portlet.instanceable=true",
			"com.liferay.portlet.header-portlet-css=/css/custom.css",
			"add-process-action-success-action=false",
			"javax.portlet.init-param.add-process-action-success-action=false",
			"javax.portlet.display-name=Manage Viewer Portlet",
			"com.liferay.portlet.private-request-attributes=false",
			"javax.portlet.init-param.template-path=/",
			"javax.portlet.init-param.view-template=/viewer_management/view.jsp",
			"javax.portlet.name=" + ControllerPortletKeys.VIEWER_MANAGEMENT,
			"javax.portlet.resource-bundle=content.Language",
			"javax.portlet.security-role-ref=power-user,user"
		},
		service = Portlet.class
	)
public class ManageViewerControllerPortlet extends MVCPortlet{

	private static final Log log = LogFactoryUtil.getLog(ManageViewerControllerPortlet.class);
	private ViewerInfoService viewerInfoService = BeanLocalServiceUtil.getViewerInfoService();
	
	@Override
	public void render(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		String viewerId = renderRequest.getParameter("viewerId");
		String mobileNumber = renderRequest.getParameter("contectNumber");
		int totalCount = 0;
		ThemeDisplay themeDisplay = CommonUtil.getThemeDisplay(renderRequest);
		ViewerInfoSearchCriteria searchCriteria = new ViewerInfoSearchCriteria();
		List<ViewerInfo> viewerInfoList = new ArrayList<>();
		if(themeDisplay.isSignedIn()){
			if(Validator.isNotNull(viewerId)){
				searchCriteria.setViewerId(Long.parseLong(viewerId));
			} else if(Validator.isNotNull(mobileNumber)){
				searchCriteria.setMobileNumber(mobileNumber);
			} else {
				log.error("Null param found");
			}
			viewerInfoList = viewerInfoService.getViewerInfoBySearchCriteria(searchCriteria);
			totalCount = viewerInfoService.countViewerInfoBySearchCriteria(searchCriteria);
			SearchContainerUtil.setCommonRenderParameter(renderRequest, searchCriteria, totalCount);
		} else {
			log.error("User not signed in");
		}
		renderRequest.setAttribute("viewerInfoList", viewerInfoList);
		renderRequest.setAttribute("searchCriteria", searchCriteria);
		super.render(renderRequest, renderResponse);
	}
	
	@ProcessAction(name="addViewer")
	public void addViewer(ActionRequest actionRequest, ActionResponse actionResponse){
		
	}
}