<%@include file="../init.jsp" %>
<%@include file="../common-import.jsp" %>
<%
	GeneralConfigurationInfo contestConfigurationInfo = (GeneralConfigurationInfo)request.getAttribute("contestConfigurationInfo");
	GeneralConfigurationInfo otpConfigurationInfo = (GeneralConfigurationInfo)request.getAttribute("otpConfigurationInfo");
	boolean isContestGeneralConfigFound = false;
	boolean contestConfigValue = false;
	if(Validator.isNotNull(contestConfigurationInfo)){
		isContestGeneralConfigFound = true;
		contestConfigValue = Boolean.parseBoolean(contestConfigurationInfo.getValue()); 
	}
	
	boolean isOtpGeneralConfigFound = false;
	boolean isOtpServiceOn = false;
	if(Validator.isNotNull(otpConfigurationInfo)){
		isOtpGeneralConfigFound = true;
	}	
%>
<liferay-ui:success key="general-config-save-or-update-success-message" message="Configuration Saved successfully...!!!"/>
<liferay-ui:error key="general-config-save-or-update-error-message" message="Sorry, Configuration Can not saved...!!!"/>
<c:set var="isContestGeneralConfigFound" value="<%=isContestGeneralConfigFound %>"></c:set>
<c:set var="contestConfigValue" value="<%=contestConfigValue %>"></c:set>
<portlet:actionURL var="updateContestConfigURL" name="updateContestConfig" />
<portlet:actionURL var="updateOtpConfigURL" name="updateOtpConfig" />
<aui:form name="general_config_form" id="general_config_form" method="POST">
	<div class="panel-body">
		<c:if test="${isContestGeneralConfigFound}">
			<div class="row">
				<div class="col-md-3">
					<liferay-ui:message key="lbl.contest.open" />
				</div>
				<div class="col-md-2">
					<label class="switch">
						<c:choose>
							<c:when test="<%=contestConfigValue %>">
								 <input type="checkbox" checked id="<portlet:namespace />contest-switch" name="<portlet:namespace />contest-switch">
							</c:when>
							<c:otherwise>
								<input type="checkbox" name="<portlet:namespace />contest-switch" id="<portlet:namespace />contest-switch">
							</c:otherwise>
						</c:choose>
					  <span class="slider round"></span>
					</label>
				</div>
				<div class="col-md-5">
					<c:choose>
						<c:when test="<%=!contestConfigValue %>">
							<div class="message-container">
								<aui:input name="configMessage" id="configMessage" type="text" label="lbl.general.config.message" 
								placeholder="lbl.general.config.message.placeholder"
								value = "${contestConfigurationInfo.message}">
									<aui:validator name="required">
										function() {
								    		return !$("#<portlet:namespace />contest-switch").is(':checked');
								    	}
									</aui:validator>
								</aui:input>
							</div>
						</c:when>
						<c:otherwise>
							<div class="message-container" style="display: none;">
								<aui:input name="configMessage" id="configMessage" type="text" label="lbl.general.config.message" 
								placeholder="lbl.general.config.message.placeholder">
									<aui:validator name="required">
										function() {
								    		return $("#<portlet:namespace />contest-switch").is(':checked');
								    	}
									</aui:validator>
								</aui:input>
							</div>
						</c:otherwise>
					</c:choose>
				</div>
				<div class="col-md-2">
			   		<button class="btn btn-primary btn-default but-small contest-update-btn" type="button" id="contest-update-btn" disabled onclick="submitForm('updateContestFlag')">
			   			<liferay-ui:message key="lbl.update.button" />
			   		</button>
			   	</div>
			</div>
		</c:if>
		<c:if test="<%=isOtpGeneralConfigFound %>">
			<div class="row">
				<div class="col-md-3">
					<liferay-ui:message key="lbl.opt.service.start" />
				</div>
				<div class="col-md-7">
					<label class="switch">
						<c:choose>
							<c:when test="${otpConfigurationInfo.value eq 'true'}">
								 <input type="checkbox" checked id="<portlet:namespace />otp-switch" name="<portlet:namespace />otp-switch">
							</c:when>
							<c:otherwise>
								<input type="checkbox" id="<portlet:namespace />otp-switch" name="<portlet:namespace />otp-switch">
							</c:otherwise>
						</c:choose>
					  <span class="slider round"></span>
					</label>
				</div>
				<div class="col-md-2">
			   		<button class="btn btn-primary btn-default but-small otp-update-btn " id="otp-update-btn" type="button" disabled onclick="submitForm('updateOtpFlag')">
			   			<liferay-ui:message key="lbl.update.button" />
			   		</button>
			   	</div>
			</div>	
		</c:if>
	</div>
</aui:form>
<script type="text/javascript">
$(document).ready(function(){
	$("#<portlet:namespace />contest-switch").change(function(){
		$('#contest-update-btn').prop('disabled',false);
		$('#contest-update-btn').removeClass("disabled");
		if(!this.checked){
			$('.message-container').show(500);
		} else {
			$('.message-container').hide(500);			
		}
	});
	$("#<portlet:namespace />otp-switch").change(function(){
		$('#otp-update-btn').prop('disabled',false);
		$('#otp-update-btn').removeClass("disabled");
	});
});
var submitForm = function(message){
	var submitButton = $('#<portlet:namespace />general_config_form');
	if(message == 'updateContestFlag'){
		submitButton.attr("action","${updateContestConfigURL}");
	} else if(message == 'updateOtpFlag') {
		submitButton.attr("action","${updateOtpConfigURL}");
	}
	$("#<portlet:namespace />general_config_form").submit();
}
</script>