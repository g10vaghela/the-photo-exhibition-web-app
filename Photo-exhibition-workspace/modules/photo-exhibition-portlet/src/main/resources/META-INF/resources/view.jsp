<%@ include file="/init.jsp" %>

<portlet:actionURL var="actionURL" name="addNewEmployee"/>
<aui:form name="payment_form" id="payment_form"  action="<%=actionURL.toString()%>">
	<div class="panel-body">
		<div class="row">
			<div class="col-md-4">
				<aui:input name="empName" id="empName" type="text" label="Employee Name" >
					<aui:validator name="required">
					</aui:validator>
				</aui:input>
			</div>
			<div class="col-md-8">
		   		<button class="btn btn-primary btn-default but-small searc-policy-btn" type="Submit" value="Search">Save</button>
		   	</div>
		</div>
	</div>
</aui:form>