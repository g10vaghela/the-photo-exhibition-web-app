<div class="row">
	<div class="col-md-4">
		<aui:input name="firstName" id="firstName" type="text" label="lbl.child.first.name">
		</aui:input>	
	</div>
	<div class="col-md-4">
		<aui:input name="middleName" id="middleName" type="text" label="lbl.child.middle.name">
			<aui:validator name="required"/>
		</aui:input>
	</div>
	<div class="col-md-4">
		<aui:input name="lastName" id="lastName" type="text" label="lbl.child.last.name">
			<aui:validator name="required"/>
		</aui:input>
	</div>
</div>
<div class="row">
	<div class="col-md-4">
		<div>
			<aui:input name="contactNo" id="contactNo" type="text" label="lbl.child.contact.number">
				<aui:validator name="required"/>
			</aui:input>
		</div>
		<div style="border-bottom: 2px solid gray;">
		     <input type="date" name="<portlet:namespace/>dateOfBirth" style="border: 0px;">
		</div>
	</div>
	<div class="col-md-8">
	</div>
</div>