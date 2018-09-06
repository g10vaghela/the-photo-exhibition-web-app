<%@page import="com.liferay.portal.kernel.json.JSONFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.json.JSONObject"%>
<%@include file="../init.jsp" %>
<%@include file="../common-import.jsp" %>
<%
	GeneralConfigurationInfo contestConfigurationInfo = (GeneralConfigurationInfo)request.getAttribute("contestConfigurationInfo");
	GeneralConfigurationInfo otpConfigurationInfo = (GeneralConfigurationInfo)request.getAttribute("otpConfigurationInfo");
	GeneralConfigurationInfo validDistanceConfiguration = (GeneralConfigurationInfo)request.getAttribute("validDistanceConfiguration");
	GeneralConfigurationInfo likeServiceGeneralConfig = (GeneralConfigurationInfo)request.getAttribute("likeServiceGeneralConfig");
	String contestLocationLatLong = (String)request.getAttribute("contestLocationLatLong");
	boolean isLocationTrackingOn = Boolean.parseBoolean(String.valueOf(request.getAttribute("isLocationTrackingOn")));
	int minimumRequiredLike = Integer.parseInt(String.valueOf(request.getAttribute("minimumRequiredLike")));
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
	
	boolean isLikeServiceOn = false;
	if(Validator.isNotNull(likeServiceGeneralConfig)){
		isLikeServiceOn = Boolean.parseBoolean(likeServiceGeneralConfig.getValue());
	}
	JSONObject locationLatLong = JSONFactoryUtil.createJSONObject(contestLocationLatLong);
%>
<%@include file="../success-message.jsp" %>
<%@include file="../error-message.jsp" %>
<c:set var="isContestGeneralConfigFound" value="<%=isContestGeneralConfigFound %>"></c:set>
<c:set var="contestConfigValue" value="<%=contestConfigValue %>"></c:set>
<portlet:actionURL var="updateContestConfigURL" name="updateContestConfig" />
<portlet:actionURL var="updateOtpConfigURL" name="updateOtpConfig" />
<portlet:actionURL var="validDistanceConfigurationUpdateURL" name="validDistanceConfigurationUpdate" />
<portlet:actionURL var="likeServiceConfigurationUpdateURL" name="likeServiceConfigurationUpdate" />
<portlet:actionURL var="locationLatLongUpdateURL" name="locationLatLongUpdate" />
<portlet:actionURL var="locationTrackingServiceUpdateURL" name="locationTrackingServiceUpdate" />
<portlet:actionURL var="minimumLikeRequiredURL" name="minimumLikeRequired" />
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
		<div class="row">
			<hr/>
		</div>
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
		<div class="row">
			<hr/>
		</div>
		<c:if test="<%=Validator.isNotNull(validDistanceConfiguration) %>">
			<div class="row">
				<div class="col-md-3">
					<liferay-ui:message key="lbl.location.distance"/>
				</div>
				<div class="col-md-3">
					<aui:input name="validDistance" id="validDistance" type="text" label="lbl.general.config.location.distance"
					value="<%=validDistanceConfiguration.getValue() %>">
						<aui:validator name="required"/>
					</aui:input>
				</div>
				<div class="col-md-4">
					<aui:input name="outOfRangeMessage" id="outOfRangeMessage" type="text" label="lbl.general.config.location.distance.out.of.range"
					value="<%=validDistanceConfiguration.getMessage() %>">
						<aui:validator name="required"/>
					</aui:input>
				</div>
				<div class="col-md-2">				
					<button class="btn btn-primary btn-default but-small locaiton-distance-update-btn " id="locaiton-distance-update-btn" type="button" onclick="submitForm('updateLocationDistanceFlag')">
			   			<liferay-ui:message key="lbl.update.button" />
			   		</button>				
				</div>
			</div>
		</c:if>
		<div class="row">
			<hr/>
		</div>
		<c:if test="<%=Validator.isNotNull(likeServiceGeneralConfig)%>">
			<div class="row">
				<div class="col-md-3">
					<liferay-ui:message key="lbl.like.service.on" />
				</div>
				<div class="col-md-2">
					<label class="switch">
						<c:choose>
							<c:when test="<%=isLikeServiceOn %>">
								 <input type="checkbox" checked id="<portlet:namespace />like-service-switch" name="<portlet:namespace />like-service-switch">
							</c:when>
							<c:otherwise>
								<input type="checkbox" name="<portlet:namespace />like-service-switch" id="<portlet:namespace />like-service-switch">
							</c:otherwise>
						</c:choose>
					  <span class="slider round"></span>
					</label>
				</div>
				<div class="col-md-5">
					<c:choose>
						<c:when test="<%=!isLikeServiceOn %>">
							<div class="like-message-container">
								<aui:input name="likeConfigMessage" id="likeConfigMessage" type="text" label="lbl.general.config.message" 
								placeholder="lbl.general.config.message.placeholder"
								value = "${likeServiceGeneralConfig.message}">
									<aui:validator name="required">
										function() {
								    		return !$("#<portlet:namespace />like-service-switch").is(':checked');
								    	}
									</aui:validator>
								</aui:input>
							</div>
						</c:when>
						<c:otherwise>
							<div class="like-message-container" style="display: none;">
								<aui:input name="likeConfigMessage" id="likeConfigMessage" type="text" label="lbl.general.config.message" 
								placeholder="lbl.general.config.message.placeholder">
									<aui:validator name="required">
										function() {
								    		return $("#<portlet:namespace />like-service-switch").is(':checked');
								    	}
									</aui:validator>
								</aui:input>
							</div>
						</c:otherwise>
					</c:choose>
				</div>
				<div class="col-md-2">
			   		<button class="btn btn-primary btn-default but-small like-service-update-btn" type="button" id="like-service-update-btn" disabled onclick="submitForm('updateLikeServiceFlag')">
			   			<liferay-ui:message key="lbl.update.button" />
			   		</button>
			   	</div>
			</div>
		</c:if>
		<div class="row">
			<hr/>
		</div>
		<c:if test="<%=Validator.isNotNull(contestLocationLatLong) %>">
			<div class="row">
				<div class="col-md-3">
					<liferay-ui:message key="lbl.location.lat.long"/>
				</div>
				<div class="col-md-3">
					<aui:input name="locationLatitude" id="locationLatitude" type="text" label="lbl.location.latitude"
					value="<%=locationLatLong.get("latitude") %>">
						<aui:validator name="required"/>
					</aui:input>
				</div>
				<div class="col-md-4">
					<aui:input name="locationLongitude" id="locationLongitude" type="text" label="lbl.location.longitude"
					value="<%=locationLatLong.get("longitude") %>">
						<aui:validator name="required"/>
					</aui:input>
				</div>
				<div class="col-md-2">
					<button class="btn btn-primary btn-default but-small locaiton-lat-long-update-btn " id="locaiton-lat-long-update-btn" type="button" onclick="submitForm('updateLatLongFlag')">
			   			<liferay-ui:message key="lbl.update.button" />
			   		</button>				
				</div>
			</div>
		</c:if>
		<div class="row">
			<hr/>
		</div>
		<c:if test="<%=Validator.isNotNull(isLocationTrackingOn) %>">
			<div class="row">
				<div class="col-md-3">
					<liferay-ui:message key="lbl.location.tracking.on" />
				</div>
				<div class="col-md-7">
					<label class="switch">
						<c:choose>
							<c:when test="${isLocationTrackingOn eq 'true'}">
								 <input type="checkbox" checked id="<portlet:namespace />location-tracking-switch" name="<portlet:namespace />location-tracking-switch">
							</c:when>
							<c:otherwise>
								<input type="checkbox" id="<portlet:namespace />location-tracking-switch" name="<portlet:namespace />location-tracking-switch">
							</c:otherwise>
						</c:choose>
					  <span class="slider round"></span>
					</label>
				</div>
				<div class="col-md-2">
			   		<button class="btn btn-primary btn-default but-small location-tracking-update-btn" id="location-tracking-update-btn" type="button" disabled onclick="submitForm('updateLocationTrackingFlag')">
			   			<liferay-ui:message key="lbl.update.button" />
			   		</button>
			   	</div>
			</div>	
		</c:if>
		<div class="row">
			<hr/>
		</div>
		<c:if test="<%=Validator.isNotNull(minimumRequiredLike) %>">
			<div class="row">
				<div class="col-md-3">
					<liferay-ui:message key="lbl.minimum.like.required"/>
				</div>
				<div class="col-md-7">
					<aui:input name="minimumLikeRequired" id="minimumLikeRequired" type="text" label="lbl.minimum.like.required.placeholder"
					value="<%=minimumRequiredLike %>">
						<aui:validator name="required"/>
					</aui:input>
				</div>
				<div class="col-md-2">				
					<button class="btn btn-primary btn-default but-small minimum-like-required-update-btn " id="minimum-like-required-update-btn" type="button" onclick="submitForm('updateMinimumLikeRequiredFlag')">
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
	$("#<portlet:namespace />like-service-switch").change(function(){
		$('#like-service-update-btn').prop('disabled',false);
		$('#like-service-update-btn').removeClass("disabled");
		if(!this.checked){
			$('.like-message-container').show(500);
		} else {
			$('.like-message-container').hide(500);			
		}
	});
	$("#<portlet:namespace />otp-switch").change(function(){
		$('#otp-update-btn').prop('disabled',false);
		$('#otp-update-btn').removeClass("disabled");
	});
	$("#<portlet:namespace />location-tracking-switch").change(function(){
		$('#location-tracking-update-btn').prop('disabled',false);
		$('#location-tracking-update-btn').removeClass("disabled");
	});
});
var submitForm = function(message){
	var submitButton = $('#<portlet:namespace />general_config_form');
	if(message == 'updateContestFlag'){
		submitButton.attr("action","${updateContestConfigURL}");
	} else if(message == 'updateOtpFlag') {
		submitButton.attr("action","${updateOtpConfigURL}");
	} else if(message == 'updateLocationDistanceFlag') {
		submitButton.attr("action","${validDistanceConfigurationUpdateURL}");
	} else if(message == 'updateLikeServiceFlag'){
		submitButton.attr("action","${likeServiceConfigurationUpdateURL}");
	} else if(message == 'updateLatLongFlag'){
		submitButton.attr("action","${locationLatLongUpdateURL}");
	} else if(message == 'updateLocationTrackingFlag'){
		submitButton.attr("action","${locationTrackingServiceUpdateURL}");
	} else if(message == 'updateMinimumLikeRequiredFlag'){
		submitButton.attr("action","${minimumLikeRequiredURL}");
	}
	$("#<portlet:namespace />general_config_form").submit();
}
</script>