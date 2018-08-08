<%@include file="../init.jsp" %>
<%@include file="../common-import.jsp" %>

<portlet:actionURL var="updateBulkChildURL" name="importBulkChildData"></portlet:actionURL>

<aui:form name="import_bulk_child_form" action="${updateBulkChildURL}" enctype="multipart/form-data" method="post">
	<div class="panel-body">
		<div class="row">
			<div class="col-md-6">
				<aui:input name="fileUpload" type="file" label="Import Bulk Child data">
					<aui:validator name="required" ></aui:validator>
				</aui:input>
			</div>
			<div class="col-md-6">
				<button class="btn btn-primary btn-default btn-small" value="Import child Data" type="submit">
					Import child Data
				</button>
			</div>
		</div>
	</div>
</aui:form>