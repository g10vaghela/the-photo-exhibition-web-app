<%@include file="../init.jsp" %>
<%@include file="../common-import.jsp" %>

<portlet:actionURL name="addAdvertisement" var="addAdvertisementURL"></portlet:actionURL>
<liferay-portlet:renderURL varImpl="backButtonURL">
	<portlet:param name="jspPage" value="/advertise/view.jsp"/>
</liferay-portlet:renderURL>
<aui:form name="advertisement_form" id="advertisement_form" action="<%=addAdvertisementURL.toString() %>" enctype="multipart/form-data" method="post">
	<div class="panel-body">
		<div class="row">
			<div class="col-md-4">
				<aui:input name="advertiseName" id="advertiseName" type="text" label="lbl.add.new.advertisement.title">
					<aui:validator name="required" errorMessage="Please Enter Advertisement Name"/>
				</aui:input>	
			</div>
			<div class="col-md-8">
				<div class="col-md-4">
					<label class="active-advertisement-wrapper" style="margin: 11px 0;">
						<liferay-ui:message key="lbl.is.advertisement.active" />
					</label>
				</div>
				<div class="col-md-8">
					<label class="switch">
						<input checked type="checkbox" name="<portlet:namespace />isAdvertisementActive" id="<portlet:namespace />isAdvertisementActive">
						<span class="slider round"></span>
					</label>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-4" style="border-bottom: 2px solid gray;">
				<div>
					Photo Orientation
				</div>
				<div>
					<input type="radio" name="<portlet:namespace />orientation" id="<portlet:namespace />orientation" value="<%=PhotoOrientation.LANDSCAPE.getValue() %>"><liferay-ui:message key="lbl.photo.orientation.landscape" /><br>
					<input type="radio" checked name="<portlet:namespace />orientation" id="<portlet:namespace />orientation" value="<%=PhotoOrientation.PORTRAIT.getValue() %>"><liferay-ui:message key="lbl.photo.orientation.portrait" /><br>
				</div>
			</div>
			<div class="col-md-8">
				<div>
					<aui:input name="fileUpload" type="file" label="Advertisement Photo" accept="image/*" >
						<aui:validator name="required" ></aui:validator>
					</aui:input>
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
					<liferay-ui:message key="lbl.save.btn" />
				</button>
			</div>
		</div>
	</div>
</aui:form>