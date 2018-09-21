<%@ include file="../init.jsp" %>
<portlet:actionURL var="addViewerURL" name="addViewer" />
<aui:form name="viewer_form" id="viewer_form" action="<%=addViewerURL.toString() %>" enctype="multipart/form-data" method="post">
	<div class="row">
		<div class="col-md-6">
			<aui:input name="contactNo" id="contactNo" type="text" label="lbl.child.contact.number">
				<aui:validator name="required"/>
				<aui:validator name="number" errorMessage="Please enter number only"/>
				<aui:validator name="maxLength" errorMessage="Very long number">10</aui:validator>
				<aui:validator name="minLength" errorMessage="Very short number" >10</aui:validator>
			</aui:input>
		</div>
		<div class="col-md-6">
			<button class="btn btn-primary btn-default btn-small viewer-btn pull-right" type="submit">
				<liferay-ui:message key="lbl.save.btn" />
			</button>
		</div>
	</div>
</aui:form>