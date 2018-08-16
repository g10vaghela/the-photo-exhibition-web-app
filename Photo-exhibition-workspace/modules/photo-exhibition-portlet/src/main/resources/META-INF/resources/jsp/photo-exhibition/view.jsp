<%@page import="com.photoexhibition.service.model.AdvertiseInfo"%>
<%@page import="com.photoexhibition.service.model.ChildInfo"%>
<%@page import="java.util.List"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayPortletMode"%>
<%@page import="com.photoexhibition.portlet.util.CommonUtil"%>
<%@include file="../../init.jsp" %>

<% 

List<ChildInfo> childList = (List<ChildInfo>) renderRequest.getAttribute("childList");
AdvertiseInfo advertise = (AdvertiseInfo) renderRequest.getAttribute("advertise");

int currentPageIndex = (int) request.getAttribute("currentPageIndex");
//int cur = (int) request.getAttribute("cur");

int totalCount = (int) request.getAttribute("totalCount");
int lastPageIndex = (totalCount/CommonUtil.PAGINATION_PER_PAGE_ITEM)+1;
boolean isCurrentPageLastPage = (currentPageIndex == lastPageIndex);
%>

<liferay-portlet:renderURL varImpl="iteratorURL">
	<portlet:param name="mvcPath" value="/jsp/photo-exhibition/view.jsp" />
	<portlet:param name="currentPageIndex" value="<%= String.valueOf(currentPageIndex) %>" />
</liferay-portlet:renderURL>

<div class="photo-exhb-display-container">
	<div class="row">
		<div class="col-md-12">
			<div class="section-title-b">Photo Exhibition Display</div>
		</div>
	</div>

	<div class="card-horizontal main-content-card">
		<div aria-multiselectable="true" class="panel-group" role="tablist">
			<div class="panel-body photo-exhb-display-table">
				 <liferay-ui:search-container cssClass="repository-table-section repository-page" 
						emptyResultsMessage="LBL_NO_DATA"  delta="<%=CommonUtil.PAGINATION_PER_PAGE_ITEM%>"
						iteratorURL="<%=iteratorURL%>" deltaConfigurable="true" >
						
						<liferay-ui:search-container-results>
						<%	
							searchContainer.setTotal(totalCount);
							searchContainer.setResults(childList);
						%>	
						</liferay-ui:search-container-results>
						
						<liferay-ui:search-container-row  indexVar="i"
							className="com.photoexhibition.service.model.ChildInfo"
							modelVar="child" >

							<div style="width:15%;height:170px;margin:6px;display:inline-block;border: 1px solid #d4d4d4;">
								<img alt="child-photo" src="<%= child.getPhotoUrl() %>">	
								<%= child.getFullName() %>	
							</div>
							
							<%-- <liferay-ui:search-container-column-text name="Actions">
							
								<portlet:actionURL name="viewPaySlip" var="viewPaySlipURL">
									<portlet:param name="paySlipId" value="<%=""+paySlip.getId()%>" />
								</portlet:actionURL>

								<a href="javascript:viewPaySlip('<%=viewPaySlipURL.toString()%>');"><img src="<%=request.getContextPath()%>/images/icon-view.png" message="View"  alt="view" ></a>
							
							</liferay-ui:search-container-column-text> --%>
						</liferay-ui:search-container-row >
						
						<liferay-ui:search-iterator searchContainer="<%=searchContainer%>" markupView="lexicon"/>
						
					</liferay-ui:search-container>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
var executeIteratorUrl = function() {
console.log(" :: executeIteratorUrl :: start ");

var executeFirstPage = <%=isCurrentPageLastPage%>;
console.log(" ## isCurrentPageLastPage ==> " +executeFirstPage);
	var renderURL = Liferay.PortletURL.createRenderURL(); // = new Liferay.PortletURL('RENDER_PHASE');
	renderURL.setWindowState("<%=LiferayWindowState.NORMAL.toString() %>");
	renderURL.setParameter('mvcPath','/jsp/photo-exhibition/view.jsp');
	renderURL.setParameter('currentPageIndex',<%= String.valueOf(currentPageIndex) %>);
	
	renderURL.setPortletMode("<%=LiferayPortletMode.VIEW %>");
	renderURL.setPortletId("<%=themeDisplay.getPortletDisplay().getId() %>");
	if(executeFirstPage) {
		renderURL.setParameter('cur',1);
	} else {
		renderURL.setParameter('cur',<%= String.valueOf(currentPageIndex+1) %>);
	}
	window.location = renderURL.toString();
	// Execute iteration url with help of javascript after every 10 seconds
	
};
	$(document).ready(function() {
		 
		console.log(" doc ready... ");
		
		setTimeout( executeIteratorUrl , 15000);

	});
</script>