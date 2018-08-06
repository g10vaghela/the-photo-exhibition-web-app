package com.photoexhibition.portlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
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
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Validator;
import com.photoexhibition.portlet.constants.ControllerPortletKeys;
import com.photoexhibition.portlet.constants.MessageConstant;
import com.photoexhibition.portlet.search.criteria.util.SearchContainerUtil;
import com.photoexhibition.portlet.validator.ChildInfoValidator;
import com.photoexhibition.service.ChildInfoService;
import com.photoexhibition.service.model.ChildInfo;
import com.photoexhibition.service.search.criteria.ChildInfoSearchCriteria;
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
	private static ChildInfoService childInfoService = BeanLocalServiceUtil.getChildInfoService();

	@Override
	public void render(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		log.debug("START :: ManageChildPortlet.render()");
		String childId = renderRequest.getParameter("childId");
		String contectNumber = renderRequest.getParameter("contectNumber");
		String contectNumber1 = (String)renderRequest.getAttribute("contectNumber");
		log.info("childId ::"+childId);
		log.info("contectNumber ::"+contectNumber);
		log.info("contectNumber1 ::"+contectNumber1);
		List<ChildInfo> childInfoList = new ArrayList<>();
		int totalCount = 0;
		ThemeDisplay themeDisplay = CommonUtil.getThemeDisplay(renderRequest);
		ChildInfoSearchCriteria childInfoSearchCriteria = new ChildInfoSearchCriteria();
		if(themeDisplay.isSignedIn()) {
			childInfoSearchCriteria.setStatus(true);
			SearchContainerUtil.setPaginationParameters(renderRequest, childInfoSearchCriteria);
			if(Validator.isNotNull(childId)) {
				childInfoSearchCriteria.setChildId(Long.parseLong(childId));
			} else if(Validator.isNotNull(contectNumber)) {
				childInfoSearchCriteria.setContactNo(contectNumber);
			}
			childInfoList = childInfoService.getChildInfoList(childInfoSearchCriteria);
			totalCount = childInfoService.getChildInfoCountBySearchCriteria(childInfoSearchCriteria);
			SearchContainerUtil.setCommonRenderParameter(renderRequest, childInfoSearchCriteria, totalCount);
		} else {
			log.info("No user signed in");
		}
		renderRequest.setAttribute("searchCriteria", childInfoSearchCriteria);
		renderRequest.setAttribute("childInfoList", childInfoList);
		log.debug("END :: ManageChildPortlet.render()");
		super.render(renderRequest, renderResponse);
	}
	
	@ProcessAction(name="openAddNewChildWindow")
	public void openAddNewChildWindow(ActionRequest actionRequest, ActionResponse actionResponse) {
		log.debug("START :: ManageChildPortlet.openAddNewChildWindow()");
		actionRequest.setAttribute("isNewChild", true);
		actionRequest.setAttribute("isChildInfoEditable", false);
		actionRequest.setAttribute("isChildInfoView", false);
		actionResponse.setRenderParameter("mvcPath", "/manage-child/child-info-page.jsp");
		log.debug("END :: ManageChildPortlet.openAddNewChildWindow()");
	}
	
	@ProcessAction(name="addNewChild")
	public void addNewChild(ActionRequest actionRequest, ActionResponse actionResponse) {
		log.debug("START :: ManageChildPortlet.addNewChild()");
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String firstName = actionRequest.getParameter("firstName");
			String middleName = actionRequest.getParameter("middleName");
			String lastName = actionRequest.getParameter("lastName");
			String contactNo = actionRequest.getParameter("contactNo");
			String dateOfBirth = actionRequest.getParameter("dateOfBirth");
			String isChildActive = actionRequest.getParameter("isChildActive");
			
			System.out.println("firstName ::"+firstName);
			System.out.println("middleName ::"+middleName);
			System.out.println("lastName ::"+lastName);
			System.out.println("contactNo ::"+contactNo);
			System.out.println("dateOfBirth ::"+dateOfBirth);
			System.out.println("isChildActive ::"+isChildActive);
			if(ChildInfoValidator.isValidChildInfo(firstName, middleName, lastName, contactNo, dateOfBirth)){
				ChildInfo childInfo = new ChildInfo();
				childInfo.setFirstName(firstName);
				childInfo.setMiddleName(middleName);
				childInfo.setLastName(lastName);
				childInfo.setContactNo(contactNo);
				childInfo.setDateOfBirth(simpleDateFormat.parse(dateOfBirth));
				if(Validator.isNotNull(isChildActive)) {
					childInfo.setStatus(true);
				} else {
					childInfo.setStatus(false);
				}
				childInfoService.saveOrUpdate(childInfo);
				SessionMessages.add(actionRequest, MessageConstant.CHILD_INFO_SAVE_OR_UPDATE_SUCCESS_MESSAGE);
			} else {
				log.error("Server side validation fail");
				SessionErrors.add(actionRequest, MessageConstant.SERVER_SIDE_VALIDATION_FAIL_ERROR_MESSAGE);
			}
		} catch (Exception e) {
			log.error("Error :: "+e);
			SessionErrors.add(actionRequest, MessageConstant.CHILD_INFO_SAVE_OR_UPDATE_ERROR_MESSAGE);
		}
		log.debug("END :: ManageChildPortlet.addNewChild()");
	}
	
	@ProcessAction(name="viewChild")
	public void viewChild(ActionRequest actionRequest, ActionResponse actionResponse) {
		log.debug("START :: ManageChildPortlet.viewChild()");
		String childId = actionRequest.getParameter("childId");
		System.out.println("childId ::"+childId);
		if(Validator.isNotNull(childId)) {
			ChildInfo childInfo = childInfoService.getChildInfoById(Long.parseLong(childId));
			System.out.println("childInfo  ::"+childInfo);
			actionRequest.setAttribute("childInfo", childInfo);
		} else {
			log.error("childId Not found ");
		}
		actionRequest.setAttribute("isNewChild", false);
		actionRequest.setAttribute("isChildInfoEditable", false);
		actionRequest.setAttribute("isChildInfoView", true);
		actionResponse.setRenderParameter("mvcPath", "/manage-child/child-info-page.jsp");
		log.debug("END :: ManageChildPortlet.viewChild()");
	}
	
	@ProcessAction(name="updateChildScreen")
	public void updateChild(ActionRequest actionRequest, ActionResponse actionResponse) {
		log.debug("START :: ManageChildPortlet.updateChild()");
		String childId = actionRequest.getParameter("childId");
		System.out.println("childId ::"+childId);
		if(Validator.isNotNull(childId)) {
			ChildInfo childInfo = childInfoService.getChildInfoById(Long.parseLong(childId));
			System.out.println("childInfo  ::"+childInfo);
			actionRequest.setAttribute("childInfo", childInfo);
		} else {
			log.error("childId Not found ");
		}
		actionRequest.setAttribute("isNewChild", false);
		actionRequest.setAttribute("isChildInfoEditable", true);
		actionRequest.setAttribute("isChildInfoView", false);
		actionResponse.setRenderParameter("mvcPath", "/manage-child/child-info-page.jsp");
		log.debug("END :: ManageChildPortlet.updateChild()");
	}
	
	@ProcessAction(name="searchChild")
	public void searchChild(ActionRequest actionRequest, ActionResponse actionResponse) {
		System.out.println("ManageChildPortlet.searchChild()");
		String childId = actionRequest.getParameter("childId");
		String contectNumber = actionRequest.getParameter("contectNumber");
		String contectNumber1 = (String)actionRequest.getAttribute("contectNumber");
		log.info("childId ::"+childId);
		log.info("contectNumber ::"+contectNumber);
		log.info("contectNumber1 ::"+contectNumber1);
	}
}
