<%@page import="java.util.List"%>
<%@include file="../init.jsp" %>
<%@include file="../common-import.jsp" %>
<%
	int delta = (int) request.getAttribute("delta");
	int currentPageIndex = (int) request.getAttribute("currentPageIndex");
	int totalCount = (int) request.getAttribute("totalCount");
	List<ChildInfo> childInfoList =(List<ChildInfo>) request.getAttribute("childInfoList");
%>
<div class="row">
	<div class="col-md-12">
		<div class="section-title-b">
			<liferay-ui:message key="lbl.child.manage.title" />
		</div>
	</div>
</div>
<portlet:actionURL var="openAddNewChildWindowURL" name="openAddNewChildWindow" />
<liferay-portlet:renderURL varImpl="iteratorURL"></liferay-portlet:renderURL>
<aui:form name="search_child_form" id="search_child_form" method="POST">
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
				<button class="btn btn-primary btn-default btn-small searc-child-btn" id="search-child-btn" type="submit">
					<liferay-ui:message key="btn.search" />
				</button>
			</div>
			<div class="col-md-3 align-items-center">
				<button class="btn btn-primary btn-default btn-small add-child-btn pull-right" id="add-child-btn" onClick="addNewChild()">
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
	
					<liferay-ui:search-container-column-text name="First Name" property="firstName" />
					
					<liferay-ui:search-container-column-text name="Middle Name" property="middleName" />
					
					<liferay-ui:search-container-column-text name="Last Name" property="lastName" />

					<liferay-ui:search-container-column-text name="Date Of Birth" property="dateOfBirth" />
					
					<liferay-ui:search-container-column-text name="Contact Number" property="contactNo" />
					
					<liferay-ui:search-container-column-image src="photoPath"></liferay-ui:search-container-column-image>
					
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
});
function addNewChild(){
	//console.log("openAddNewChildWindowURL ", '${openAddNewChildWindowURL}');
	/* var submitButton = $("<portlet:namespace/>search_child_form");
	submitButton.attr("action","${openAddNewChildWindowURL}");
	$("<portlet:namespace/>search_child_form").submit(); */
	console.log("test");
}
</script>