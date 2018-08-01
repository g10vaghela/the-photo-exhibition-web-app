<%@page import="com.liferay.portal.kernel.util.Validator"%>
<%@page import="org.springframework.validation.annotation.Validated"%>
<%@page import="com.photoexhibition.service.constant.GeneralConfigurationConstants"%>
<%@page import="com.photoexhibition.service.model.GeneralConfigurationInfo"%>
<%@include file="../init.jsp" %>
<%@include file="../common-import.jsp" %>
<%
	GeneralConfigurationInfo generalConfigurationInfo = (GeneralConfigurationInfo)request.getAttribute(GeneralConfigurationConstants.IS_CONTENST_OPEN);
	boolean isGeneralConfigFound = false;
	if(Validator.isNotNull(generalConfigurationInfo)){
		isGeneralConfigFound = true;
	}
%>
<liferay-ui:success key="general-config-save-or-update-success-message" message="Configuration Saved successfully...!!!"/>
<liferay-ui:error key="general-config-save-or-update-error-message" message="Sorry, Configuration Can not saved...!!!"/>

<portlet:actionURL var="actionURL" name="addOrUpdateConfiguration" windowState="<%=LiferayWindowState.EXCLUSIVE.toString() %>"/>
<aui:form name="general_config_form" id="general_config_form"  action="<%=actionURL.toString()%>">
	<div class="panel-body">
		<div class="row">
			<div>
			
			</div>
		
			<%-- <div class="col-md-3">
				<aui:input name="configKey" id="configKey" type="text" label="lbl.general.config.key" 
				placeholder="lbl.general.config.key.placeholder">
					<aui:validator name="required">
					</aui:validator>
				</aui:input>
			</div>
			<div class="col-md-3">
				<aui:input name="configValue" id="configValue" type="text" label="lbl.general.config.value" 
				placeholder="lbl.general.config.value.placeholder">
					<aui:validator name="required">
					</aui:validator>
				</aui:input>
			</div>
			<div class="col-md-3">
				<aui:input name="configMessage" id="configMessage" type="text" label="lbl.general.config.message" 
					placeholder="lbl.general.config.message.placeholder">
					<aui:validator name="required">
					</aui:validator>
				</aui:input>
			</div>
			<div class="col-md-3">
		   		<button class="btn btn-primary btn-default but-small searc-policy-btn" type="Submit" value="Search">
		   			<liferay-ui:message key="lbl.save.or.update.button" />
		   		</button>
		   	</div> --%>
		</div>
	</div>
</aui:form>