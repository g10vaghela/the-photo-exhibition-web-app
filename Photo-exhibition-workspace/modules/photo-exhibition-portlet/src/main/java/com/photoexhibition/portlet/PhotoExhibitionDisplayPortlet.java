package com.photoexhibition.portlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
import com.photoexhibition.portlet.vo.PhotoExhbDisplayVo;
import com.photoexhibition.service.AdvertiseInfoService;
import com.photoexhibition.service.ChildInfoService;
import com.photoexhibition.service.ChildViewerLikeInfoService;
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
			"com.liferay.portlet.footer-portlet-css=/css/photo-exh-display.css",
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
	private static ChildViewerLikeInfoService childViewerLikeInfoService = BeanLocalServiceUtil.getChildViewerLikeInfoService(); 
	
	@Override
	public void render(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		// TODO Auto-generated method stub
		log.info(" :: PhotoExhibitionDisplayPortlet :: render() ");
		
		int delta = ParamUtil.getInteger(renderRequest,"delta",CommonUtil.PAGINATION_PER_PAGE_ITEM);
		int currentPageIndex = ParamUtil.getInteger(renderRequest,"currentPageIndex",1);
		
		int cur = ParamUtil.getInteger(renderRequest,"cur", 0);
		if(cur > 0) {
			currentPageIndex = cur;
		} else {
			currentPageIndex = 1;
		}
		setChildAndAdvertiseItems(renderRequest, delta, currentPageIndex);
		super.render(renderRequest, renderResponse);
	}

	private void setChildAndAdvertiseItems(RenderRequest renderRequest, int delta, int currentPageIndex) {
		int totalCount = 0;
		ChildInfoSearchCriteria childSearchCriteria = new ChildInfoSearchCriteria();
		childSearchCriteria.setStatus(true);
		
		int childTotalCount= childInfoService.getChildInfoCountBySearchCriteria(childSearchCriteria);
		childSearchCriteria.setPagination(true);
		childSearchCriteria.setPaginationDelta(delta);
		childSearchCriteria.setPaginationPage(currentPageIndex);
		List<ChildInfo> childList = childInfoService.getChildInfoList(childSearchCriteria);

		List<PhotoExhbDisplayVo> exhibitionItems = new ArrayList<PhotoExhbDisplayVo>();
		setExhibitionItems(exhibitionItems, childList);
		
		renderRequest.setAttribute("exhibitionItems", exhibitionItems);
		totalCount = childTotalCount; // No need to calculate advertise count - consider only child count
		SearchContainerUtil.setCommonRenderParameter(renderRequest, childSearchCriteria, totalCount);
	}

	private AdvertiseInfo getRandomAdvertise() {
		AdvertiseInfo advertise = null;
		List<AdvertiseInfo> advertiseList = advertiseInfoService.getAdvertiseInfoList(true);

		if((advertiseList != null) && (advertiseList.size() > 0)) {
			Random rand = new Random();
			int index = rand.nextInt(advertiseList.size());
			advertise = advertiseList.get(index);
		}
		return advertise;
	}

	private void setExhibitionItems(List<PhotoExhbDisplayVo> exhibitionItems, List<ChildInfo> childList) {
		log.info(" :: setExhibitionItems :: ");
		if(childList != null) {
			for(int i =0; i<(childList.size()); i++) {
				PhotoExhbDisplayVo photoExhbDisplayVo = new PhotoExhbDisplayVo();
				ChildInfo child = childList.get(i);
				if(child !=null) {
					//childViewerLikeInfoService.getChildViewerLikeInfoListByChildId(childId).getChildId()
					//childInfoService.
					photoExhbDisplayVo.setChildInfo(child);
				}
				exhibitionItems.add(photoExhbDisplayVo);
			}
			PhotoExhbDisplayVo photoExhbDisplayVo = new PhotoExhbDisplayVo();
			AdvertiseInfo advertise = getRandomAdvertise();
			if(advertise !=null) {
				photoExhbDisplayVo.setAdvertise(true);
				photoExhbDisplayVo.setAdvertise(advertise);
			}
			exhibitionItems.add(6, photoExhbDisplayVo);
		}
	}
}
