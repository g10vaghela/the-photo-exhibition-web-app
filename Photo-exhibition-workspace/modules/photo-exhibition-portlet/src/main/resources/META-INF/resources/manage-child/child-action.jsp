<%@page import="com.liferay.portal.kernel.util.ParamUtil"%>
<%@page import="com.liferay.portal.kernel.dao.search.ResultRow"%>
<%@page import="com.photoexhibition.service.model.ChildInfo"%>
<%@include file="../init.jsp"%>

<%
    String mvcPath = ParamUtil.getString(request, "mvcPath");

    ResultRow row = (ResultRow) request.getAttribute("SEARCH_CONTAINER_RESULT_ROW");

    ChildInfo childInfo = (ChildInfo) row.getObject();
%>

<liferay-ui:icon-menu>

	<portlet:actionURL name="viewChild" var="viewChildURL">
            <portlet:param name="childId"
                value="<%= String.valueOf(childInfo.getChildId()) %>" />
    </portlet:actionURL>
  
    <portlet:actionURL name="updateChildScreen" var="updateChildURL">
            <portlet:param name="childId"
                value="<%= String.valueOf(childInfo.getChildId()) %>" />
    </portlet:actionURL>

     <liferay-ui:icon icon="icon-eye-open" message="View" url="<%=viewChildURL.toString() %>" />
     <liferay-ui:icon icon="icon-save" message="Update" url="<%=updateChildURL.toString() %>" />

</liferay-ui:icon-menu>