<%@page import="java.util.List"%>
<%@page import="com.photoexhibition.service.model.AdvertiseInfo"%>
<%@include file="../init.jsp" %>
<%@include file="../common-import.jsp" %>
<liferay-portlet:renderURL varImpl="backButtonURL">
	<portlet:param name="jspPage" value="/advertise/view.jsp"/>
</liferay-portlet:renderURL>
<%
	List<AdvertiseInfo> advertiseInfoList = (List<AdvertiseInfo>)request.getAttribute("advertiseInfoList");
	AdvertiseInfo advertiseInfo = advertiseInfoList.get(0);
%>
<div class="panel-body">
	<div class="row">
		<div class="col-md-4">
			<aui:input readonly="true" name="advertiseName" id="advertiseName" type="text" label="lbl.add.new.advertisement.title" value="<%=advertiseInfo.getAdvertiseName() %>"></aui:input>	
		</div>
		<div class="col-md-8">
			<div class="col-md-4">
				<label class="active-advertisement-wrapper" style="margin: 11px 0;">
					<liferay-ui:message key="lbl.is.advertisement.active" />
				</label>
			</div>
			<div class="col-md-8">
				<c:choose>
					<c:when test="<%=advertiseInfo.isActiveStatus() %>">
						<label class="switch">
							<input readonly="true" disabled="true"  checked type="checkbox" name="<portlet:namespace />isAdvertisementActive" id="<portlet:namespace />isAdvertisementActive">
							<span class="slider round"></span>
						</label>
					</c:when>
					<c:otherwise>
						<label class="switch">
							<input readonly="true" disabled="true" type="checkbox" name="<portlet:namespace />isAdvertisementActive" id="<portlet:namespace />isAdvertisementActive">
							<span class="slider round"></span>
						</label>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</div>
	<div class="col-md-8">
		<c:choose>
			<c:when  test="<%=advertiseInfo.getOrientation() == PhotoOrientation.LANDSCAPE.getValue() %>">
				<img src="<%=advertiseInfo.getAdvertisePhotoUrl() %>" alt='<%="image of "+advertiseInfo.getAdvertiseName() %>' width="350" height="250"/>
			</c:when>
			<c:otherwise>
				<img src="<%=advertiseInfo.getAdvertisePhotoUrl() %>" alt='<%="image of "+advertiseInfo.getAdvertiseName() %>'  width="250" height="350"/>
			</c:otherwise>
		</c:choose>
	</div>
	<div class="row">
		<hr/>
	</div>
	<div class="row">
		<div class="col-md-6">
			<aui:button onClick="${backButtonURL}" cssClass="btn btn-primary btn-default btn-small" value="Back To List"></aui:button>
		</div>
	</div>
</div>