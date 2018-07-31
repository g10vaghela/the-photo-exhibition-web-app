<%@include file="../init.jsp" %>


<liferay-ui:success key="general-config-save-or-update-success-message" message="Configuration Saved successfully...!!!"/>
<liferay-ui:error key="general-config-save-or-update-error-message" message="Sorry, Configuration Can not saved...!!!"/>

<portlet:actionURL var="actionURL" name="addOrUpdateConfiguration"/>
<aui:form name="general_config_form" id="general_config_form"  action="<%=actionURL.toString()%>">
	<div class="panel-body">
		<div class="row">
			<div class="col-md-3">
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
		   	</div>
		</div>
	</div>
</aui:form>