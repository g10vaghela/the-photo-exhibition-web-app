<%@page import="com.photoexhibition.service.model.AdvertiseInfo"%>
<%@page import="com.photoexhibition.service.model.ChildInfo"%>
<%@page import="java.util.List"%>
<%@page import="com.photoexhibition.portlet.util.CommonUtil"%>
<%@include file="../../init.jsp" %>

<% 

List<ChildInfo> childList = (List<ChildInfo>) renderRequest.getAttribute("childList");
AdvertiseInfo advertise = (AdvertiseInfo) renderRequest.getAttribute("advertise");
int currentPageIndex = (int) request.getAttribute("currentPageIndex");
int totalCount = (int) request.getAttribute("totalCount");
/* get total page
10/9 + 1 */
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
	$(document).ready(function() {
		 
		console.log(" doc ready... ");
		// Execute iteration url with help of javascript after every 10 seconds
	});
</script>