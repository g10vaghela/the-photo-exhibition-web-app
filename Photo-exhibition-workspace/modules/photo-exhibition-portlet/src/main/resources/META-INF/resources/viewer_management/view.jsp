<%@page import="com.photoexhibition.service.search.criteria.ViewerInfoSearchCriteria"%>
<%@page import="com.photoexhibition.service.model.ViewerInfo"%>
<%@page import="java.util.List"%>
<%@ include file="../init.jsp" %>
<%
	int delta = (int) request.getAttribute("delta");
	int currentPageIndex = (int) request.getAttribute("currentPageIndex");
	int totalCount = (int) request.getAttribute("totalCount");
	List <ViewerInfo> viewerInfoList =(List<ViewerInfo>) request.getAttribute("viewerInfoList");
	ViewerInfoSearchCriteria searchCriteria = (ViewerInfoSearchCriteria)request.getAttribute("searchCriteria");
%>
<%@include file="../success-message.jsp" %>
<%@include file="../error-message.jsp" %>
<liferay-portlet:renderURL varImpl="goToAddViewerScreen">
	<portlet:param name="jspPage" value="/viewer_management/addViewer.jsp"/>
</liferay-portlet:renderURL>
<liferay-portlet:renderURL varImpl="iteratorURL">
	<portlet:param name="mvcPath" value="/viewer_management/view.jsp" />
	<portlet:param name="currentPageIndex" value="<%= String.valueOf(currentPageIndex) %>" />
	<portlet:param name="viewerId" value="<%=String.valueOf(searchCriteria.getViewerId()) %>"/>
	<portlet:param name="contectNumber" value="<%=searchCriteria.getMobileNumber() %>"/>
</liferay-portlet:renderURL>
<liferay-portlet:resourceURL var="resourceURL"></liferay-portlet:resourceURL>
<portlet:actionURL var="addViewerURL" name="addViewer"/>
<aui:form name="search_child_form" id="search_child_form">
	<div class="panel-body">
		<div class="row">
			<div class="col-md-3">
				<aui:input 
					name="viewerId"
					id="viewerId"
					type="text"
					label="lbl.viewer.id"
					placeholder="lbl.viewer.id.placeholder">
				</aui:input>	
			</div>
			<div class="col-md-3">
				<aui:input 
					name="contectNumber"
					id="contectNumber"
					type="text"
					label="lbl.contact.number"
					placeholder="lbl.contact.number.placeholder">
				</aui:input>
			</div>
			<div class="col-md-2 align-items-center">
				<button class="btn btn-primary btn-default btn-small searc-child-btn" id="search-child-btn" type="Submit">
					<liferay-ui:message key="btn.search" />
				</button>
			</div>
			<div class="col-md-2">
				<aui:button onClick="${resourceURL}" cssClass="btn btn-primary btn-default btn-small pull-right" value="Export Report"></aui:button>
			</div>
			<div class="col-md-2">
				<aui:button onClick="${goToAddViewerScreen}" cssClass="btn btn-primary btn-default btn-small pull-right" value="Add Viewer"></aui:button>
			</div>
		</div>
	</div>
</aui:form>
<div class="row search-result">
	<div class="panel-body transaction-result-table">
		<liferay-ui:search-container 
						emptyResultsMessage="No Cheque Found" 
						iteratorURL="${iteratorURL}" 
						delta="<%=delta%>" 
						deltaConfigurable="true" 
						cssClass="repository-table-section repository-page">

				<liferay-ui:search-container-results>
						<%	
							searchContainer.setTotal(totalCount);
							searchContainer.setResults(viewerInfoList);
						%>	
						</liferay-ui:search-container-results>
				
				<liferay-ui:search-container-row 
					className="com.photoexhibition.service.model.ViewerInfo"
					modelVar="viewerInfo" indexVar="indexVar" keyProperty="id">

					<liferay-ui:search-container-column-text name="Viewer Id" property="viewerId"/>
	
					<liferay-ui:search-container-column-text name="Contact Number" property="mobileNumber"/>
					
					<liferay-ui:search-container-column-text name="Device Number" property="deviceNumber" />
					
					<liferay-ui:search-container-column-text name="Verified">
						<c:choose>
							<c:when test="<%=viewerInfo.isOtpVerified() %>">
								<span class="dot-green"></span>
							</c:when>
							<c:otherwise>
								<span class="dot-red"></span>
							</c:otherwise>
						</c:choose>
					</liferay-ui:search-container-column-text>
					
					<liferay-ui:search-container-column-text name="OTP" property="otp" />

					<liferay-ui:search-container-column-jsp align="right" path="/viewer_management/viewer-action.jsp" />
            
				</liferay-ui:search-container-row>
			<liferay-ui:search-iterator searchContainer="<%=searchContainer %>"  markupView="lexicon"/>
			
		</liferay-ui:search-container>
	</div>
</div>