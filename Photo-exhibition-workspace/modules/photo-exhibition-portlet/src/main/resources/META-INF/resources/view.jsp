<%@ include file="/init.jsp" %>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
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

<script>
$(document).ready(function() {
	  setInterval(function() {
	    cache_clear()
	  }, 7000);
	});

	function cache_clear() {
	  window.location.reload(true);
	  // window.location.reload(); use this if you do not remove cache
	}
</script>
