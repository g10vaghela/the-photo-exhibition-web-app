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
import com.photoexhibition.portlet.constants.ControllerPortletKeys;
import com.photoexhibition.service.model.ChildInfo;

@Component(
		immediate = true,
		property = {
			"com.liferay.portlet.display-category=Photo-Exhibition",
			"com.liferay.portlet.instanceable=true",
			"add-process-action-success-action=false",
			"javax.portlet.display-name=Manage Child Portlet",
			"com.liferay.portlet.private-request-attributes=false",
			"javax.portlet.init-param.template-path=/",
			"javax.portlet.init-param.view-template=/manage-child/view.jsp",
			"javax.portlet.name=" + ControllerPortletKeys.ManageChildPortlet,
			"javax.portlet.resource-bundle=content.Language",
			"javax.portlet.security-role-ref=power-user,user"
		},
		service = Portlet.class
	)
public class ManageChildPortlet extends MVCPortlet{

	private static final Log log = LogFactoryUtil.getLog(ManageChildPortlet.class);

	@Override
	public void render(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		log.debug("START :: ManageChildPortlet.render()");
		String childId = renderRequest.getParameter("childId");
		String contectNumber = renderRequest.getParameter("contactNumber");
		System.out.println("childId ::"+childId);
		System.out.println("contectNumber ::"+contectNumber);
		List<ChildInfo> childInfoList = new ArrayList<>();
		
		renderRequest.setAttribute("childId", childId);
		renderRequest.setAttribute("contectNumber", contectNumber);
		renderRequest.setAttribute("childInfoList", childInfoList);
		renderRequest.setAttribute("delta", 10);
		renderRequest.setAttribute("currentPageIndex", 1);
		renderRequest.setAttribute("totalCount", 0);
		log.debug("END :: ManageChildPortlet.render()");
		super.render(renderRequest, renderResponse);
	}
	
	@ProcessAction(name="openAddNewChildWindow")
	public void openAddNewChildWindow(ActionRequest actionRequest, ActionResponse actionResponse) {
		log.debug("START :: ManageChildPortlet.openAddNewChildWindow()");
		actionRequest.setAttribute("isNewChild", true);
		actionResponse.setRenderParameter("mvcPath", "/manage-child/child-info-page.jsp");
		log.debug("END :: ManageChildPortlet.openAddNewChildWindow()");
	}
	
	@ProcessAction(name="addNewChild")
	public void addNewChild(ActionRequest actionRequest, ActionResponse actionResponse) {
		log.debug("START :: ManageChildPortlet.addNewChild()");
		
		log.debug("END :: ManageChildPortlet.addNewChild()");
	}
	
	@ProcessAction(name="viewChild")
	public void viewChild(ActionRequest actionRequest, ActionResponse actionResponse) {
		log.debug("START :: ManageChildPortlet.viewChild()");
		actionRequest.setAttribute("isNewChild", false);
		actionRequest.setAttribute("isChildInfoEditable", false);
		actionResponse.setRenderParameter("mvcPath", "/manage-child/child-info-page.jsp");
		log.debug("END :: ManageChildPortlet.viewChild()");
	}
	
	@ProcessAction(name="updateChild")
	public void updateChild(ActionRequest actionRequest, ActionResponse actionResponse) {
		log.debug("START :: ManageChildPortlet.updateChild()");
		actionRequest.setAttribute("isNewChild", false);
		actionRequest.setAttribute("isChildInfoEditable", true);
		actionResponse.setRenderParameter("mvcPath", "/manage-child/child-info-page.jsp");
		log.debug("END :: ManageChildPortlet.updateChild()");
	}
}
