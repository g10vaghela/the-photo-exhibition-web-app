<%@page import="com.photoexhibition.service.model.ChildInfo"%>
<%
	ChildInfo childInfo = (ChildInfo)request.getAttribute("childInfo");
%>
<div class="row">
	<div class="col-md-4">
		<aui:input readonly="true" name="firstName" id="firstName" type="text" label="lbl.child.first.name"
		value="${childInfo.firstName}">
		</aui:input>	
	</div>
	<div class="col-md-4">
		<aui:input readonly="true" name="middleName" id="middleName" type="text" label="lbl.child.middle.name"
		value="${childInfo.middleName}">
			<aui:validator name="required"/>
		</aui:input>
	</div>
	<div class="col-md-4">
		<aui:input readonly="true" name="lastName" id="lastName" type="text" label="lbl.child.last.name"
		value="${childInfo.lastName}">
			<aui:validator name="required"/>
		</aui:input>
	</div>
</div>
<div class="row">
	<div class="col-md-4">
		<div>
			<aui:input readonly="true" name="contactNo" id="contactNo" type="text" label="lbl.child.contact.number"
			value="${childInfo.contactNo}">
				<aui:validator name="required"/>
			</aui:input>
		</div>
		<div style="border-bottom: 2px solid gray;">
		     <input readonly="true" disabled="true" type="text" name="<portlet:namespace/>dateOfBirth" style="border: 0px;"
		     value="<%=CommonUtil.displayFormattedDateWithoutDash(childInfo.getDateOfBirth())%>">
		</div>
		<div> 
			<label class="switch">
				<c:choose>
					<c:when test="${childInfo.status}">
						 <input readonly="true" disabled="true" type="checkbox" checked id="<portlet:namespace />isChildActive" name="<portlet:namespace />isChildActive">
					</c:when>
					<c:otherwise>
						<input readonly="true" disabled="true" type="checkbox" name="<portlet:namespace />isChildActive" id="<portlet:namespace />isChildActive">
					</c:otherwise>
				</c:choose>
			  <span class="slider round"></span>
			</label>
		</div>
	</div>
	<div class="col-md-8">
		<img src="<%=childInfo.getPhotoUrl() %>" alt='<%="image of "+childInfo.getFullName() %>' width="250" height="300"/>
	</div>
</div>