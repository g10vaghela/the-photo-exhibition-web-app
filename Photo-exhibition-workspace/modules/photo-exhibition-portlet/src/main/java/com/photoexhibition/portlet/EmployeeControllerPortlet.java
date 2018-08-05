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

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.Validator;
import com.photoexhibition.portlet.constants.ControllerPortletKeys;
import com.photoexhibition.service.EmployeeInfoService;
import com.photoexhibition.service.model.EmployeeInfo;
import com.photoexhibition.service.util.BeanLocalServiceUtil;

/**
 * @author jiten
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=photo-exhibition-portlet Portlet",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + ControllerPortletKeys.EmployeeController,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class EmployeeControllerPortlet extends MVCPortlet {
	private EmployeeInfoService employeeInfoService = BeanLocalServiceUtil.getEmployeeInfoService();
	@Override
	public void render(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		// TODO Auto-generated method stub
		super.render(renderRequest, renderResponse);
	}
	
	@ProcessAction(name="addNewEmployee")
	public void addNewEmployee(ActionRequest actionRequest, ActionResponse actionResponse) {
		System.out.println("EmployeeControllerPortlet.addNewEmployee()");
		String name = actionRequest.getParameter("empName");
		System.out.println("name  ::"+name);
		if(Validator.isNotNull(name)) {
			EmployeeInfo employeeInfo = new EmployeeInfo();
			employeeInfo.setEmployeeName(name);
			employeeInfoService.saveOrUpdateEmployee(employeeInfo);
			System.out.println("Successfully saved");
		}
	}
}