<%@page import="com.photoexhibition.service.vo.PhotoExhbDisplayVo"%>
<%@page import="com.photoexhibition.service.util.PhotoOrientation"%>
<%@page import="com.photoexhibition.service.model.AdvertiseInfo"%>
<%@page import="com.photoexhibition.service.model.ChildInfo"%>
<%@page import="java.util.List"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayPortletMode"%>
<%@page import="com.photoexhibition.portlet.util.CommonUtil"%>
<%@include file="../../init.jsp" %>

<% 
AdvertiseInfo advertise = null;
ChildInfo child = null;
List<PhotoExhbDisplayVo> photoExhbDisplayVoList = (List<PhotoExhbDisplayVo>) renderRequest.getAttribute("exhibitionItems");

int currentPageIndex = (int) request.getAttribute("currentPageIndex");
int totalCount = (int) request.getAttribute("totalCount");
int lastPageIndex = (totalCount/CommonUtil.PAGINATION_PER_PAGE_ITEM)+1;
boolean isCurrentPageLastPage = (currentPageIndex == lastPageIndex);
%>
<c:set var="totalCount" value="<%= totalCount %>" />
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
			<div class="panel-body photo-exhb-display-body">
				 <liferay-ui:search-container cssClass="repository-table-section repository-page" 
						emptyResultsMessage="LBL_NO_DATA"  delta="<%=CommonUtil.PAGINATION_PER_PAGE_ITEM%>"
						iteratorURL="<%=iteratorURL%>" deltaConfigurable="true" >
						
						<liferay-ui:search-container-results>
						<%	
							searchContainer.setTotal(totalCount);
							searchContainer.setResults(photoExhbDisplayVoList);
						%>	
						</liferay-ui:search-container-results>
						
						<table class="photo-exhb-display-table">
							<liferay-ui:search-container-row  indexVar="i"
								className="com.photoexhibition.service.vo.PhotoExhbDisplayVo"
								modelVar="exhbItem" >

								<c:if test="${((i == 0) || (i==5) || (i==8) || (i==10))}">
									<tr>	
								</c:if>
								<c:if test="<%=!exhbItem.isAdvertise() %>">
									<% child = exhbItem.getChildInfo(); %>
									<td class="common-cell child-cell" style="width:20%;height:325px;">
										<div style="padding:5px;">
											<c:choose>
												<c:when  test="<%=child.getOrientation() == PhotoOrientation.LANDSCAPE.getValue() %>">
													<img src="<%=child.getPhotoUrl() %>" alt='<%="image of "+child.getFullName() %>' style="width:100%"/>
												</c:when>
												<c:otherwise>
													<img src="<%=child.getPhotoUrl() %>" alt='<%="image of "+child.getFullName() %>' style="width:100%"/>
												</c:otherwise>
											</c:choose>
										</div>
										<div class="child-details-wrapper"><span class="child-details"> <%= child.getChildId() +" - " +child.getFullName()  %> </span></div>
									</td>		
								</c:if>
								<c:if test="<%=exhbItem.isAdvertise() %>">
									<% advertise = exhbItem.getAdvertise(); %>
									<td class="common-cell advertise-cell" colspan="3" rowspan="2">
										<div style="padding:5px;">
		                    				<img alt="<%= advertise.getAdvertiseName() %>" src="<%= advertise.getAdvertisePhotoUrl() %>" style="width:100%">
										</div>
									</td>
								</c:if>
								<c:if test="${((i == 4) || (i==7) || (i==9) || (i==(totalCount+1)) )}">
									</tr>
								</c:if>

							</liferay-ui:search-container-row >
						</table>
						
						 <liferay-ui:search-paginator type="article" searchContainer="<%=searchContainer%>"></liferay-ui:search-paginator> 
						<%-- <liferay-ui:search-iterator displayStyle="descriptive" searchContainer="<%=searchContainer%>" markupView="lexicon"/> --%>
						
					</liferay-ui:search-container>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">

	var executeIteratorUrl = function() {
		var executeFirstPage = <%=isCurrentPageLastPage%>;
		var renderURL = Liferay.PortletURL.createRenderURL();
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
	};

	$(document).ready(function() {
		console.log(" doc ready... ");
		//setTimeout( executeIteratorUrl , 15000);
	});
</script>
