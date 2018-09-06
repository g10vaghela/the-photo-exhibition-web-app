package com.photoexhibition.portlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.photoexhibition.portlet.constants.ControllerPortletKeys;
import com.photoexhibition.service.ChildViewerLikeInfoService;
import com.photoexhibition.service.util.BeanLocalServiceUtil;
import com.photoexhibition.service.vo.ChildInfoVO;

@Component(
		immediate = true,
		property = {
			"com.liferay.portlet.display-category=Photo-Exhibition",
			"com.liferay.portlet.instanceable=true",
			"com.liferay.portlet.header-portlet-css=/css/custom.css",
			"add-process-action-success-action=false",
			"javax.portlet.init-param.add-process-action-success-action=false",
			"javax.portlet.display-name=Winner Child Portlet",
			"com.liferay.portlet.private-request-attributes=false",
			"javax.portlet.init-param.template-path=/",
			"javax.portlet.init-param.view-template=/winner-child/view.jsp",
			"javax.portlet.name=" + ControllerPortletKeys.WINEER_CHILD,
			"javax.portlet.resource-bundle=content.Language",
			"javax.portlet.security-role-ref=power-user,user"
		},
		service = Portlet.class
	)
public class WinnerChildPortlet extends MVCPortlet{
	private static final Log log = LogFactoryUtil.getLog(WinnerChildPortlet.class);
	private static ChildViewerLikeInfoService childViewerLikeInfoService = BeanLocalServiceUtil.getChildViewerLikeInfoService();

	@Override
	public void render(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		log.debug("START :: WinnerChildPortlet.render()");
		String searchTopNo = renderRequest.getParameter("searchTopNo");
		List<ChildInfoVO> childInfoList = new ArrayList<>();
		log.info("searchTopNo :: "+searchTopNo);
		try {
			renderRequest.setAttribute("searchTopNo", searchTopNo);
		} catch (Exception e) {
			log.error("Error :"+e);
		}
		log.debug("END :: WinnerChildPortlet.render()");
		super.render(renderRequest, renderResponse);
	}
	
	private List<ChildInfoVO> getChildInfoVo(int topLimit){
		//childViewerLikeInfoService.get
		
		return null;
	}
}
