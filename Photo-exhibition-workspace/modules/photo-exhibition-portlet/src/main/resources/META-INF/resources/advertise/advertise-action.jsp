<%@page import="com.photoexhibition.service.model.AdvertiseInfo"%>
<%@page import="com.liferay.portal.kernel.util.ParamUtil"%>
<%@page import="com.liferay.portal.kernel.dao.search.ResultRow"%>
<%@page import="com.photoexhibition.service.model.ChildInfo"%>
<%@include file="../init.jsp"%>

<%
    String mvcPath = ParamUtil.getString(request, "mvcPath");

    ResultRow row = (ResultRow) request.getAttribute("SEARCH_CONTAINER_RESULT_ROW");

    AdvertiseInfo advertiseInfo = (AdvertiseInfo)row.getObject();
%>

<liferay-ui:icon-menu>
	<portlet:renderURL var="viewAdvertiseURL">
		<portlet:param name="advertiseId" value="<%=String.valueOf(advertiseInfo.getAdvertiseId())%>"/>
		<c:choose>
			<c:when test="<%=advertiseInfo.isActiveStatus() %>">
				<portlet:param name="advertiseStatus" value="1"/>
			</c:when>
			<c:otherwise>
				<portlet:param name="advertiseStatus" value="2"/>
			</c:otherwise>
		</c:choose>
		<portlet:param name="mvcPath" value="/advertise/viewAdvertisement.jsp" />
	</portlet:renderURL>
	
	<portlet:renderURL var="updateAdvertiseURL">
		<portlet:param name="advertiseId" value="<%=String.valueOf(advertiseInfo.getAdvertiseId())%>"/>
		<c:choose>
			<c:when test="<%=advertiseInfo.isActiveStatus() %>">
				<portlet:param name="advertiseStatus" value="1"/>
			</c:when>
			<c:otherwise>
				<portlet:param name="advertiseStatus" value="2"/>
			</c:otherwise>
		</c:choose>
		<portlet:param name="mvcPath" value="/advertise/updateAdvertise.jsp" />
	</portlet:renderURL>

	<liferay-ui:icon icon="icon-eye-open" message="View" url="<%=viewAdvertiseURL.toString() %>" />
	<liferay-ui:icon icon="icon-eye-open" message="update" url="<%=updateAdvertiseURL.toString() %>" />
</liferay-ui:icon-menu>