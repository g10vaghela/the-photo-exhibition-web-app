<%@page import="java.text.SimpleDateFormat"%>
<%@include file="../init.jsp" %>
<%@include file="../common-import.jsp" %>
<%@ page import="java.util.Date" %>
<%
    Date defaultDate = new Date();
    int startDay, startMonth, startYear;
    startDay = defaultDate.getDate();
    // For some reason the input-date subtracts 1900 from the year it is given,
    // so we have to add that 1900 back to it
    startYear = defaultDate.getYear()+1900; 
    // However it does work with java.util.Date counting months from 0, 
	// so you don't have to add one extra month to it
    startMonth = defaultDate.getMonth(); 
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
%>
<div class="row">
	<div class="col-md-12">
		<div class="section-title-b">
			<liferay-ui:message key="lbl.add.new.child.title" />
		</div>
	</div>
</div>
<portlet:actionURL var="addNewChildURL" name="addNewChild" />
<liferay-portlet:renderURL varImpl="backButtonURL">
	<portlet:param name="jspPage" value="/manage-child/view.jsp"/>
</liferay-portlet:renderURL>
<aui:form name="search_child_form" id="search_child_form" method="POST">
	<div class="panel-body">
		<c:choose>
			<c:when test="${isNewChild}">
				<%@include file="new-child-entry.jsp" %>
			</c:when>
			<c:otherwise>
				<c:if test="${isChildInfoEditable}">
					<%@include file="update-child-info.jsp" %>
				</c:if>
				<c:if test="${isChildInfoEditable}">
					<%@include file="view-child-info.jsp" %>
				</c:if>
				
			</c:otherwise>
		</c:choose>
		<div class="row">
			<div class="col-md-6">
				<button class="btn btn-primary btn-default btn-small back-btn" id="back-btn" onClick="backToList()" type="button">Back</button>
			</div>
			<div class="col-md-6">
				<c:choose>
					<c:when test="${isNewChild}">
						<div class="row">
							<button class="btn btn-primary btn-default btn-small save-child-btn pull-right" id="save-child-btn" type="button">Save</button>
						</div>
					</c:when>
					<c:otherwise>
						<c:if test="${isChildInfoEditable}">
							<div class="row">
								<button class="btn btn-primary btn-default btn-small update-child-btn pull-right" id="update-child-btn" type="button">Update</button>
							</div>
						</c:if>
					</c:otherwise>
				</c:choose>
			</div>			
		</div>
	</div>
</aui:form>
<script type="text/javascript">
function backToList(){
	var a = document.createElement("A");
	a.setAttribute("href","<%=backButtonURL.toString() %>");
	console.log("a ",a);
	window.location.href = a.href;
}
</script>