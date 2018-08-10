<%@page import="com.photoexhibition.service.model.AdvertiseInfo"%>
<%@page import="java.util.List"%>
<%
	List<AdvertiseInfo> advertiseInfoList = (List<AdvertiseInfo>)request.getAttribute("advertiseInfoList");
	AdvertiseInfo advertiseInfo = advertiseInfoList.get(0);
%>
<%@include file="../init.jsp" %>
<%@include file="../common-import.jsp" %>

<portlet:actionURL name="updateAdvertise" var="updateAdvertiseURL">
	<portlet:param name="advertiseId" value="<%=String.valueOf(advertiseInfo.getAdvertiseId()) %>"/>
</portlet:actionURL>
<liferay-portlet:renderURL varImpl="backButtonURL">
	<portlet:param name="jspPage" value="/advertise/view.jsp"/>
</liferay-portlet:renderURL>
<aui:form name="advertisement_form" id="advertisement_form" action="<%=updateAdvertiseURL.toString() %>" enctype="multipart/form-data" method="post">
	<div class="panel-body">
		<div class="row">
			<div class="col-md-4">
				<aui:input name="advertiseName" id="advertiseName" 
							type="text" label="lbl.add.new.advertisement.title"
							value="<%=advertiseInfo.getAdvertiseName() %>">
					<aui:validator name="required" errorMessage="Please Enter Advertisement Name"/>
				</aui:input>	
			</div>
			<div class="col-md-8">
				<div class="col-md-4">
					<label class="active-advertisement-wrapper" style="margin: 11px 0;">
						<liferay-ui:message key="lbl.is.advertisement.active" />
					</label>
				</div>
				<c:choose>
					<c:when test="<%=advertiseInfo.isActiveStatus() %>">
						<div class="col-md-8">
							<label class="switch">
								<input checked type="checkbox" name="<portlet:namespace />isAdvertisementActive" id="<portlet:namespace />isAdvertisementActive">
								<span class="slider round"></span>
							</label>
						</div>
					</c:when>
					<c:otherwise>
						<div class="col-md-8">
							<label class="switch">
								<input type="checkbox" name="<portlet:namespace />isAdvertisementActive" id="<portlet:namespace />isAdvertisementActive">
								<span class="slider round"></span>
							</label>
						</div>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<div>
					<c:choose>
						<c:when test="<%=advertiseInfo.getOrientation() == PhotoOrientation.LANDSCAPE.getValue() %>">
							<img src="<%=advertiseInfo.getAdvertisePhotoUrl() %>" alt='<%="image of "+advertiseInfo.getAdvertiseName() %>' width="350" height="250"/>
						</c:when>
						<c:otherwise>
							<img src="<%=advertiseInfo.getAdvertisePhotoUrl() %>" alt='<%="image of "+advertiseInfo.getAdvertiseName() %>' width="250" height="350"/>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</div>
		<div class="row">
			<hr/>
		</div>
		<div class="row">
			<div class="col-md-6">
				<aui:button onClick="${backButtonURL}" cssClass="btn btn-primary btn-default btn-small" value="Back To List"></aui:button>
			</div>
			<div class="col-md-6">
				<button class="btn btn-primary btn-default btn-small advertise-btn pull-right" type="submit">
					<liferay-ui:message key="lbl.update.btn" />
				</button>
			</div>
		</div>
	</div>
</aui:form>