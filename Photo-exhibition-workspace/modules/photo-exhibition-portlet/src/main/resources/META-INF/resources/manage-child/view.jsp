<%@page import="com.photoexhibition.service.util.CommonUtil"%>
<%@page import="com.photoexhibition.service.search.criteria.ChildInfoSearchCriteria"%>
<%@page import="java.util.List"%>
<%@include file="../init.jsp" %>
<%@include file="../common-import.jsp" %>
<%
	int delta = (int) request.getAttribute("delta");
	int currentPageIndex = (int) request.getAttribute("currentPageIndex");
	int totalCount = (int) request.getAttribute("totalCount");
	List<ChildInfo> childInfoList =(List<ChildInfo>) request.getAttribute("childInfoList");
	ChildInfoSearchCriteria searchCriteria = (ChildInfoSearchCriteria)request.getAttribute("searchCriteria");
%>
<%@include file="../success-message.jsp" %>
<%@include file="../error-message.jsp" %>
<div class="row">
	<div class="col-md-12">
		<div class="section-title-b">
			<liferay-ui:message key="lbl.child.manage.title" />
		</div>
	</div>
</div>
<portlet:actionURL var="openAddNewChildWindowURL" name="openAddNewChildWindow" />
<liferay-portlet:renderURL varImpl="iteratorURL">
	<portlet:param name="mvcPath" value="/manage-child/view.jsp" />
	<portlet:param name="currentPageIndex" value="<%= String.valueOf(currentPageIndex) %>" />
	<portlet:param name="childId" value="<%=String.valueOf(searchCriteria.getChildId()) %>"/>
	<portlet:param name="contectNumber" value="<%=searchCriteria.getContactNo() %>"/>
</liferay-portlet:renderURL>
<aui:form name="search_child_form" id="search_child_form">
	<div class="panel-body">
		<div class="row">
			<div class="col-md-3">
				<aui:input 
					name="childId"
					id="childId"
					type="text"
					label="lbl.child.id"
					placeholder="lbl.child.id.placeholder">
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
			<div class="col-md-3 align-items-center">
				<button class="btn btn-primary btn-default btn-small searc-child-btn" id="search-child-btn" type="button">
					<liferay-ui:message key="btn.search" />
				</button>
			</div>
			<div class="col-md-3 align-items-center">
				<button class="btn btn-primary btn-default btn-small add-child-btn pull-right" id="add-child-btn">
					<liferay-ui:message key="btn.add.new.child" />
				</button>
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
							searchContainer.setResults(childInfoList);
						%>	
						</liferay-ui:search-container-results>
				
				<liferay-ui:search-container-row 
					className="com.photoexhibition.service.model.ChildInfo"
					modelVar="childInfo" indexVar="indexVar" keyProperty="id">

					<liferay-ui:search-container-column-text name="Child Id" property="childId"/>
	
					<liferay-ui:search-container-column-text name="Name">
						<%=childInfo.getFullName() %>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text name="Date Of Birth">
						<%=CommonUtil.displayFormattedDateWithoutDash(childInfo.getDateOfBirth()) %>
					</liferay-ui:search-container-column-text>
					
					<liferay-ui:search-container-column-text name="Contact Number" property="contactNo" />
					
					<liferay-ui:search-container-column-text name="Status">
						<c:choose>
							<c:when test="<%=childInfo.isStatus() %>">
								<span class="dot-green"></span>
							</c:when>
							<c:otherwise>
								<span class="dot-red"></span>
							</c:otherwise>
						</c:choose>
					</liferay-ui:search-container-column-text>
					
					<%-- <liferay-ui:search-container-column-image src="photoPath"></liferay-ui:search-container-column-image> --%>
					
					 <liferay-ui:search-container-column-jsp align="right" path="/manage-child/child-action.jsp" />
            
				</liferay-ui:search-container-row>
			<liferay-ui:search-iterator searchContainer="<%=searchContainer %>"  markupView="lexicon"/>
			
		</liferay-ui:search-container>
	</div>
</div>
<script>
$(document).ready(function(){
	$("#add-child-btn").click(function(){
		console.log("test111");
		var actionUrl = '${openAddNewChildWindowURL}';
		console.log("actionUrl :: "+actionUrl);
		var submitButton = $("#<portlet:namespace/>search_child_form");
		submitButton.attr("action",actionUrl);
		submitButton.submit();
	});
	
	$('#search-child-btn').click(function(){
		var contactNumber = $("#<portlet:namespace/>contectNumber").val();
		var childId = $("#<portlet:namespace/>childId").val();
		console.log("contactNumber", contactNumber);
		console.log("childId", childId);
		
 		var actionURL = Liferay.PortletURL.createActionURL();
		actionURL.setWindowState("<%=LiferayWindowState.NORMAL.toString() %>");
		actionURL.setPortletMode("<%=LiferayPortletMode.VIEW %>");
		actionURL.setParameter("contectNumber",contactNumber);
		actionURL.setParameter("childId",childId);
		actionURL.setParameter("javax.portlet.action","searchChild");
		actionURL.setPortletId("<%=themeDisplay.getPortletDisplay().getId() %>");
		window.location.href = actionURL.toString();
	});
});
</script>
<style>
.dot-green {
  height: 25px;
  width: 25px;
  background-color: #02f320;
  border-radius: 50%;
  display: inline-block;
}
.dot-red {
  height: 25px;
  width: 25px;
  background-color:#fd0202;
  border-radius: 50%;
  display: inline-block;
}
</style>