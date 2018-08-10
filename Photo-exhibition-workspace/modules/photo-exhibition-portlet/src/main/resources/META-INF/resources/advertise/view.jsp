<%@page import="com.photoexhibition.service.search.criteria.AdvertiseInfoSearchChiteria"%>
<%@page import="java.util.List"%>
<%@page import="com.photoexhibition.service.model.AdvertiseInfo"%>
<%@include file="../init.jsp" %>
<%@include file="../common-import.jsp" %>
<%-- <%@incluse file="../success-message.jsp" %>
<%@incluse file="../error-message.jsp" %> --%>
<%
	int delta = (int) request.getAttribute("delta");
	int currentPageIndex = (int) request.getAttribute("currentPageIndex");
	int totalCount = (int) request.getAttribute("totalCount");
	List<AdvertiseInfo> advertiseInfoList = (List<AdvertiseInfo>) request.getAttribute("advertiseInfoList");
	AdvertiseInfoSearchChiteria searchCriteria = (AdvertiseInfoSearchChiteria)request.getAttribute("searchCriteria");

%>
<%@include file="../success-message.jsp" %>
<%@include file="../error-message.jsp" %>
<liferay-portlet:renderURL varImpl="iteratorURL">
	<portlet:param name="mvcPath" value="/advertise/view.jsp" />
	<portlet:param name="currentPageIndex" value="<%= String.valueOf(currentPageIndex) %>" />
	<portlet:param name="advertiseId" value="<%=String.valueOf(searchCriteria.getAdvertiseId()) %>"/>
	<portlet:param name="advertiseName" value="<%=searchCriteria.getAdvertiseName() %>"/>
	<portlet:param name="advertiseStatus" value="<%=String.valueOf(searchCriteria.getStatus()) %>"/>
</liferay-portlet:renderURL>
<liferay-portlet:renderURL varImpl="addNewAdvertisementURL">
	<portlet:param name="mvcPath" value="/advertise/addNewAdvertisement.jsp" />
</liferay-portlet:renderURL>
<liferay-portlet:actionURL name="searchAdvertisement" var="searchAdvertisementURL"></liferay-portlet:actionURL>
<aui:form name="search_child_form" id="search_child_form" action="<%=searchAdvertisementURL.toString() %>">
	<div class="panel-body">
		<div class="row">
			<div class="col-md-2">
				<aui:input 
					name="advertiseId"
					id="advertiseId"
					type="text"
					label="lbl.advertise.id"
					placeholder="lbl.advertise.id.placeholder">
				</aui:input>	
			</div>
			<div class="col-md-2">
				<aui:input 
					name="advertiseName"
					id="advertiseName"
					type="text"
					label="lbl.advertise.name"
					placeholder="lbl.advertise.name.placeholder">
				</aui:input>	
			</div>
			<div class="col-md-2">
				<aui:select id="advertiseStatus" name="advertiseStatus" label="lbl.advertise.status">
					<aui:option value="0">Select All</aui:option>
					<aui:option value="1">Active</aui:option>
					<aui:option value="2">In-Active</aui:option>
				</aui:select>
			</div>
			<div class="col-md-3 align-items-center btn-align-center">
				<aui:button cssClass="btn btn-primary btn-default btn-small search-advertisement-btn" id="search-advertisement-btn" type="submit" value="Search"></aui:button>
			</div>
			<div class="col-md-3 align-items-center btn-align-center">
				<aui:button onClick="<%=addNewAdvertisementURL.toString() %>" cssClass="btn btn-primary btn-default btn-small advertise-btn" value="btn.add.new.advertisement" ></aui:button>
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
							searchContainer.setResults(advertiseInfoList);
						%>	
						</liferay-ui:search-container-results>
				
				<liferay-ui:search-container-row 
					className="com.photoexhibition.service.model.AdvertiseInfo"
					modelVar="advertiseInfo" indexVar="indexVar" keyProperty="id">

					<liferay-ui:search-container-column-text name="Advertise Id" property="advertiseId"/>
	
					<liferay-ui:search-container-column-text name="Advertise Name" property="advertiseName"/>
					
					<liferay-ui:search-container-column-text name="Status">
						<c:choose>
							<c:when test="<%=advertiseInfo.isActiveStatus() %>">
								<span class="dot-green"></span>
							</c:when>
							<c:otherwise>
								<span class="dot-red"></span>
							</c:otherwise>
						</c:choose>
					</liferay-ui:search-container-column-text>
					
					 <liferay-ui:search-container-column-jsp name="Action" path="/advertise/advertise-action.jsp" />
            
				</liferay-ui:search-container-row>
			<liferay-ui:search-iterator searchContainer="<%=searchContainer %>"  markupView="lexicon"/>
		</liferay-ui:search-container>
	</div>
</div>