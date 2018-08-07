<div class="row">
	<div class="col-md-4">
		<aui:input name="firstName" id="firstName" type="text" label="lbl.child.first.name">
			<aui:validator name="required" errorMessage="Please Enter First Name"/>
		</aui:input>	
	</div>
	<div class="col-md-4">
		<aui:input name="middleName" id="middleName" type="text" label="lbl.child.middle.name">
			<aui:validator name="required" errorMessage="Please Enter Middle Name"/>
		</aui:input>
	</div>
	<div class="col-md-4">
		<aui:input name="lastName" id="lastName" type="text" label="lbl.child.last.name">
			<aui:validator name="required" errorMessage="Please Enter Last Name"/>
		</aui:input>
	</div>
</div>
<div class="row">
	<div class="col-md-4">
		<div>
			<aui:input name="contactNo" id="contactNo" type="text" label="lbl.child.contact.number">
				<aui:validator name="required"/>
				<aui:validator name="number" errorMessage="Please enter number only"/>
				<aui:validator name="maxLength" errorMessage="Very long number">13</aui:validator>
				<aui:validator name="minLength" errorMessage="Very short number" >10</aui:validator>
			</aui:input>
		</div>
		<div style="border-bottom: 2px solid gray;">
		     <input type="date" name="<portlet:namespace/>dateOfBirth" style="border: 0px;">
		</div>
		<div>
			<label class="active-child-wrapper">
				<liferay-ui:message key="lbl.is.child.active" />
			</label>
			<label class="switch">
				<input checked type="checkbox" name="<portlet:namespace />isChildActive" id="<portlet:namespace />isChildActive">
				<span class="slider round"></span>
			</label>
		</div>
	</div>
	<div class="col-md-8">
		<div>
			<aui:input name="fileUpload" type="file" label="Child Photo" value="Save" accept="image/*" >
				<aui:validator name="required" ></aui:validator>
			</aui:input>
		</div>
	</div>
</div>