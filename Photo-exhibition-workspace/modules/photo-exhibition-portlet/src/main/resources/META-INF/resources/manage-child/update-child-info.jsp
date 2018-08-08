<%@page import="com.photoexhibition.service.util.PhotoOrientation"%>
<%@page import="com.liferay.portal.kernel.util.Validator"%>
<%@page import="com.photoexhibition.service.model.ChildInfo"%>
<%
	ChildInfo childInfo = (ChildInfo)request.getAttribute("childInfo");
%>
<div class="row">
	<div class="col-md-4">
		<aui:input name="firstName" id="firstName" type="text" label="lbl.child.first.name"
		value="${childInfo.firstName}">
		</aui:input>	
	</div>
	<div class="col-md-4">
		<aui:input name="middleName" id="middleName" type="text" label="lbl.child.middle.name"
		value="${childInfo.middleName}">
			<aui:validator name="required"/>
		</aui:input>
	</div>
	<div class="col-md-4">
		<aui:input name="lastName" id="lastName" type="text" label="lbl.child.last.name"
		value="${childInfo.lastName}">
			<aui:validator name="required"/>
		</aui:input>
	</div>
</div>
<div class="row">
	<div class="col-md-4">
		<div>
			<aui:input name="contactNo" id="contactNo" type="text" label="lbl.child.contact.number"
			value="${childInfo.contactNo}">
				<aui:validator name="required"/>
				<aui:validator name="number"/>
				<aui:validator name="maxLength" errorMessage="Very long number">13</aui:validator>
				<aui:validator name="minLength" errorMessage="Very short number" >10</aui:validator>
			</aui:input>
		</div>
		<div style="border-bottom: 2px solid gray;">
		      <input type="text" name="<portlet:namespace/>dateOfBirth" style="border: 0px;"
		     value="<%=CommonUtil.displayFormattedDateWithoutDash(childInfo.getDateOfBirth())%>">
		</div>
		<div>
			<label class="active-child-wrapper">
				<liferay-ui:message key="lbl.is.child.active" />
			</label> 
			<label class="switch">
				<c:choose>
					<c:when test="${childInfo.status}">
						 <input type="checkbox" checked id="<portlet:namespace />isChildActive" name="<portlet:namespace />isChildActive">
					</c:when>
					<c:otherwise>
						<input type="checkbox" name="<portlet:namespace />isChildActive" id="<portlet:namespace />isChildActive">
					</c:otherwise>
				</c:choose>
			  <span class="slider round"></span>
			</label>
		</div>
		<c:if test="<%=Validator.isNull(childInfo.getPhotoUrl())%>">
			<div>
				<c:choose>
					<c:when  test="<%=childInfo.getOrientation() == PhotoOrientation.LANDSCAPE.getValue() %>">
						<input type="radio" checked name="<portlet:namespace />orientation" id="<portlet:namespace />orientation" value="<%=PhotoOrientation.LANDSCAPE.getValue() %>"><liferay-ui:message key="lbl.photo.orientation.landscape" /><br>
						<input type="radio" name="<portlet:namespace />orientation" id="<portlet:namespace />orientation" value="<%=PhotoOrientation.PORTRAIT.getValue() %>"><liferay-ui:message key="lbl.photo.orientation.portrait" /><br>
					</c:when>
					<c:otherwise>
						<input type="radio" name="<portlet:namespace />orientation" id="<portlet:namespace />orientation" value="<%=PhotoOrientation.LANDSCAPE.getValue() %>"><liferay-ui:message key="lbl.photo.orientation.landscape" /><br>
						<input type="radio" checked name="<portlet:namespace />orientation" id="<portlet:namespace />orientation" value="<%=PhotoOrientation.PORTRAIT.getValue() %>"><liferay-ui:message key="lbl.photo.orientation.portrait" /><br>
					</c:otherwise>
				</c:choose>
			</div>
		</c:if>
	</div>
	<div class="col-md-8">
		<c:choose>
			<c:when test="<%=Validator.isNotNull(childInfo.getPhotoUrl())%>">
				<c:if test="<%=childInfo.getOrientation() == PhotoOrientation.LANDSCAPE.getValue() %>">
					<img src="<%=childInfo.getPhotoUrl() %>" alt='<%="image of "+childInfo.getFullName() %>' width="350" height="250"/>
				</c:if>
				<c:if test="<%=childInfo.getOrientation() == PhotoOrientation.PORTRAIT.getValue() %>">
					<img src="<%=childInfo.getPhotoUrl() %>" alt='<%="image of "+childInfo.getFullName() %>' width="250" height="350"/>
				</c:if>
			</c:when>
			<c:otherwise>
				<div>
					<aui:input name="fileUpload" type="file" label="Child Photo" value="Save" accept="image/*" >
						<aui:validator name="required" ></aui:validator>
					</aui:input>
				</div>
			</c:otherwise>
		</c:choose>
	</div>
</div>