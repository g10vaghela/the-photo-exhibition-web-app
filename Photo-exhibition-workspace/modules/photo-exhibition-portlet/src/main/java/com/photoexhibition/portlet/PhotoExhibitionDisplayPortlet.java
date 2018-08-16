package com.photoexhibition.portlet;

import java.io.IOException;
import java.util.List;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.ParamUtil;
import com.photoexhibition.portlet.constants.ControllerPortletKeys;
import com.photoexhibition.portlet.search.criteria.util.SearchContainerUtil;
import com.photoexhibition.portlet.util.CommonUtil;
import com.photoexhibition.service.AdvertiseInfoService;
import com.photoexhibition.service.ChildInfoService;
import com.photoexhibition.service.model.AdvertiseInfo;
import com.photoexhibition.service.model.ChildInfo;
import com.photoexhibition.service.search.criteria.AdvertiseInfoSearchChiteria;
import com.photoexhibition.service.search.criteria.ChildInfoSearchCriteria;
import com.photoexhibition.service.util.BeanLocalServiceUtil;
import com.photoexhibition.service.util.StatusValue;

@Component(
		immediate = true,
		property = {
			"com.liferay.portlet.display-category=Photo-Exhibition",
			"com.liferay.portlet.instanceable=true",
			"com.liferay.portlet.header-portlet-css=/css/custom.css",
			"add-process-action-success-action=false",
			"javax.portlet.init-param.add-process-action-success-action=false",
			"javax.portlet.display-name=Photo Exhibition Display Portlet",
			"com.liferay.portlet.private-request-attributes=false",
			"javax.portlet.init-param.template-path=/",
			"javax.portlet.init-param.view-template=/jsp/photo-exhibition/view.jsp",
			"javax.portlet.name=" + ControllerPortletKeys.PHOTO_EXHIBITION_DISPLAY,
			"javax.portlet.resource-bundle=content.Language",
			"javax.portlet.security-role-ref=power-user,user"
		},
		service = Portlet.class
	)
public class PhotoExhibitionDisplayPortlet extends MVCPortlet {

	private final Log log = LogFactoryUtil.getLog(PhotoExhibitionDisplayPortlet.class);
	
	private static ChildInfoService childInfoService = BeanLocalServiceUtil.getChildInfoService();
	private static AdvertiseInfoService advertiseInfoService = BeanLocalServiceUtil.getAdvertiseInfoService();
	
	@Override
	public void render(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		// TODO Auto-generated method stub
		log.info(" :: PhotoExhibitionDisplayPortlet :: render() ");
		
		int totalCount = 0;
		int delta = ParamUtil.getInteger(renderRequest,"delta",CommonUtil.PAGINATION_PER_PAGE_ITEM);
		int currentPageIndex = ParamUtil.getInteger(renderRequest,"currentPageIndex",1);
		
		int cur = ParamUtil.getInteger(renderRequest,"cur", 0);
		if(cur > 0) {
			currentPageIndex = cur;
		} else {
			currentPageIndex = 1;
		}
		ChildInfoSearchCriteria childSearchCriteria = new ChildInfoSearchCriteria();
		childSearchCriteria.setStatus(true);
		
		int childTotalCount= childInfoService.getChildInfoCountBySearchCriteria(childSearchCriteria);
		childSearchCriteria.setPagination(true);
		childSearchCriteria.setPaginationDelta(delta);
		childSearchCriteria.setPaginationPage(currentPageIndex);
		
		List<ChildInfo> childList = childInfoService.getChildInfoList(childSearchCriteria);
		/*
		AdvertiseInfoSearchChiteria advertiseSC = new AdvertiseInfoSearchChiteria();
		advertiseSC.setStatus(StatusValue.ACTIVE.getValue());
		int	advertiseTotalCount = advertiseInfoService.getAdvertiseInfoCount(advertiseSC);
		
		advertiseSC.setPagination(true);
		advertiseSC.setPaginationDelta(1);
		advertiseSC.setPaginationPage(currentPageIndex);
		*/
		AdvertiseInfo advertiseInfo = advertiseInfoService.getAdvertiseInfoById(currentPageIndex);
	
		renderRequest.setAttribute("childList", childList);
		renderRequest.setAttribute("advertise", advertiseInfo);
		
		totalCount = childTotalCount; // No need to calculate advertise count - consider only child count
		SearchContainerUtil.setCommonRenderParameter(renderRequest, childSearchCriteria, totalCount);
		super.render(renderRequest, renderResponse);
	}

	
}
