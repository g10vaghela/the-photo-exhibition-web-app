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
  
    <portlet:actionURL name="activeViewer" var="activeViewerURL">
            <portlet:param name="viewerId"
                value="<%= String.valueOf(viewerInfo.getViewerId()) %>" />
    </portlet:actionURL>

     <liferay-ui:icon icon="icon-eye-open" message="View" url="<%=viewerViewURL.toString() %>" />
     <c:choose>
     	<c:when test="<%=viewerInfo.isOtpVerified() == false %>">
     		     <liferay-ui:icon icon="icon-delete" message="Active" url="<%=activeViewerURL.toString() %>" />
     	</c:when>
     </c:choose>

</liferay-ui:icon-menu>