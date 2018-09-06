<%@include file="../init.jsp" %>
<%@include file="../common-import.jsp" %>

<aui:form name="search_child_form" id="search_child_form">
	<div class="panel-body">
		<div class="row">
			<div class="col-md-4">
				<aui:select id="searchTopNo" name="topNo" label="lbl.top.child">
					<aui:option value="3">Top 3</aui:option>
					<aui:option value="5">Top 5</aui:option>
					<aui:option value="10">Top 10</aui:option>
					<aui:option value="15">Top 15</aui:option>
					<aui:option value="20">Top 20</aui:option>
				</aui:select>
			</div >
			<div class="col-md-8">
				<aui:button cssClass="btn btn-primary btn-default btn-small search-advertisement-btn" id="search-advertisement-btn" type="submit" value="Search"></aui:button>
			</div>
		</div>
	</div>
</aui:form>
<div class="row search-result">
	<div class="panel-body transaction-result-table">
		<%-- <liferay-ui:search-container 
						emptyResultsMessage="No Cheque Found" 
						iteratorURL="${iteratorURL}" 
						delta="<%=delta%>" 
						deltaConfigurable="true" 
						cssClass="repository-table-section repository-page">

				<liferay-ui:search-container-results>
						<%	
							searchContainer.setTotal(totalCount);
							searchContainer.setResults(advertiseInfoList);
						%>	
						</liferay-ui:search-container-results>
				
				<liferay-ui:search-container-row 
					className="com.photoexhibition.service.model.AdvertiseInfo"
					modelVar="advertiseInfo" indexVar="indexVar" keyProperty="id">

					<liferay-ui:search-container-column-text name="Advertise Id" property="advertiseId"/>
	
					<liferay-ui:search-container-column-text name="Advertise Name" property="advertiseName"/>
					
					<liferay-ui:search-container-column-text name="Status">
						<c:choose>
							<c:when test="<%=advertiseInfo.isActiveStatus() %>">
								<span class="dot-green"></span>
							</c:when>
							<c:otherwise>
								<span class="dot-red"></span>
							</c:otherwise>
						</c:choose>
					</liferay-ui:search-container-column-text>
					
					 <liferay-ui:search-container-column-jsp name="Action" path="/advertise/advertise-action.jsp" />
            
				</liferay-ui:search-container-row>
			<liferay-ui:search-iterator searchContainer="<%=searchContainer %>"  markupView="lexicon"/>
		</liferay-ui:search-container>--%>
	</div> 
</div>