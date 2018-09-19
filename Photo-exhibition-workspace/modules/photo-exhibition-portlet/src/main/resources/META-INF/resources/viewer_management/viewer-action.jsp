<%@page import="com.photoexhibition.service.model.ViewerInfo"%>
<%@page import="com.liferay.portal.kernel.util.ParamUtil"%>
<%@page import="com.liferay.portal.kernel.dao.search.ResultRow"%>
<%@include file="../init.jsp"%>

<%
    String mvcPath = ParamUtil.getString(request, "mvcPath");

    ResultRow row = (ResultRow) request.getAttribute("SEARCH_CONTAINER_RESULT_ROW");

    ViewerInfo viewerInfo  = (ViewerInfo) row.getObject();
%>

<liferay-ui:icon-menu>

	<portlet:actionURL name="viewerDetails" var="viewerViewURL">
            <portlet:param name="viewerId"
                value="<%= String.valueOf(viewerInfo.getViewerId()) %>" />
    </portlet:actionURL>
  
    <portlet:actionURL name="deleteViewer" var="deleteViewerURL">
            <portlet:param name="viewerId"
                value="<%= String.valueOf(viewerInfo.getViewerId()) %>" />
    </portlet:actionURL>

     <liferay-ui:icon icon="icon-eye-open" message="View" url="<%=viewerViewURL.toString() %>" />
     <liferay-ui:icon icon="icon-delete" message="Delete" url="<%=deleteViewerURL.toString() %>" />

</liferay-ui:icon-menu>