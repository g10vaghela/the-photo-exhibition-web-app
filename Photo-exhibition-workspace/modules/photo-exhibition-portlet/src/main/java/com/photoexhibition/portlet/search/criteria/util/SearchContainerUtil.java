package com.photoexhibition.portlet.search.criteria.util;

import javax.portlet.PortletRequest;

import com.liferay.portal.kernel.util.ParamUtil;
import com.photoexhibition.service.search.criteria.BaseSearchCriteria;

public class SearchContainerUtil {
	public static void setPaginationParameters(PortletRequest request, BaseSearchCriteria searchCriteria) {
		int delta = ParamUtil.getInteger(request,"delta",10);
		int currentPageIndex = ParamUtil.getInteger(request,"currentPageIndex",1);
		int cur = ParamUtil.getInteger(request,"cur", 0);
		if(cur > 0) {
			currentPageIndex = cur;
		} else {
			currentPageIndex = 1;
		}
		searchCriteria.setPagination(true);
		searchCriteria.setPaginationDelta(delta);
		searchCriteria.setPaginationPage(currentPageIndex);
	}
	
	public static void setCommonRenderParameter(PortletRequest request, BaseSearchCriteria searchCriteria, int totalCount) {
		request.setAttribute("totalCount", totalCount);
		request.setAttribute("searchCriteria", searchCriteria);
		request.setAttribute("delta", searchCriteria.getPaginationDelta());
		request.setAttribute("currentPageIndex", searchCriteria.getPaginationPage());
	}
}
