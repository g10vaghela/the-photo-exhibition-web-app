<%@page import="java.util.List"%>
<%@page import="com.photoexhibition.service.vo.ChildLikeVo"%>
<%@page import="com.photoexhibition.service.model.ViewerInfo"%>
<%@include file="../init.jsp" %>
<%
	ViewerInfo viewerInfo = (ViewerInfo) request.getAttribute("viewerInfo");
	List<ChildLikeVo> childLikeVoList = (List<ChildLikeVo>) request.getAttribute("childLikeVoList"); 
	int totalRow = childLikeVoList.size()/10;
	if(childLikeVoList.size() % 10 > 0){
		totalRow++;
	}
	int total = childLikeVoList.size();
%>
<portlet:actionURL var="saveViewerLikeURL" name="saveViewerLike">
	<portlet:param name="viewerId" value="<%=String.valueOf(viewerInfo.getViewerId())%>"/>
</portlet:actionURL>
<aui:form name="viewer_form" action="${saveViewerLikeURL}" method="post">
	<div class="row" style="padding: 15px;">
		<div class="col-md-3">
			<strong>Mobile No :</strong><%=viewerInfo.getMobileNumber() %>
		</div>
		<div class="col-md-5">
			<strong>Device Number :</strong>  <%=viewerInfo.getDeviceNumber() %>
		</div>
		<div class="col-md-1">
			<c:choose>
				<c:when test="<%=viewerInfo.isOtpVerified() %>">
					<span class="dot-green"></span>
				</c:when>
				<c:otherwise>
					<span class="dot-red"></span>
				</c:otherwise>
			</c:choose>
		</div>
		<c:choose>
			<c:when test="${isLikeCounted}">
				<div class="col-md-3 green" style="background-color: rgba(8, 181, 8, 0.66);">
					Likes : ${totalLikes} / (${minLike} Minimum)
				</div>
			</c:when>
			<c:otherwise>
				<div class="col-md-3" style="background-color: rgba(255, 0, 0, 0.52);">
					Likes : ${totalLikes} / (${minLike} Minimum)
				</div>
			</c:otherwise>
		</c:choose>
	</div>
	<div class="row">
		<div class="col-md-12">
			<aui:input name="selectAll" cssClass="selectAllCheckbox" type="checkbox"></aui:input>
		</div>
	</div>
	<div>
		<hr/>
	</div>
	<div>
	<%
			int maxCell = 0;
			if(childLikeVoList.size() > 10){
				maxCell = 10;
			} else {
				maxCell = childLikeVoList.size();
			}
	int index = 0;
	for(int i = 0; i<totalRow ; i++) {	
	%>
		<div class="row">
			<div class="col-md-2 row-header"><h1><%=i %></h1></div>
		<%
			for(int j = 0;j<maxCell;j++,index++){
				if(index >= total)break;
		%>
			<c:choose>
				<c:when test="<%=childLikeVoList.get(index).isLike() %>">
					<div class="col-md-1">
						<aui:input type="checkbox" checked="true" disabled="true" name='<%="child-"+childLikeVoList.get(index).getChildId() %>' value="" title="<%=childLikeVoList.get(index).getChildName() %>"/>
					</div>
				</c:when>
				<c:otherwise>
					<div class="col-md-1">
						<aui:input type="checkbox" cssClass="unchecke-child-checkbox" name='<%="child-"+childLikeVoList.get(index).getChildId() %>' value="" title="<%=childLikeVoList.get(index).getChildName() %>"/>
					</div>
				</c:otherwise>
			</c:choose>
		<%
			}
		%>
		</div>
		<hr>
	<%
	}
	%>
		<div class="row">
			<div class="col-md-12">
				<button class="btn btn-primary btn-default btn-small viewer-btn pull-right" type="submit">
					<liferay-ui:message key="lbl.save.btn" />
				</button>
			</div>
		</div>
	</div>
</aui:form>
<script type="text/javascript">
$(document).ready(function(){
	$('.selectAllCheckbox').click(function(){
		if ($('.selectAllCheckbox').is(":checked")){
			var allCheckbox = $('.unchecke-child-checkbox');
			
			$('.unchecke-child-checkbox').attr('checked', true);
			/* $.each(allCheckbox,function(inxex, element){
				$(element).
			}); */
		}else{
			$('.unchecke-child-checkbox').attr('checked', false);
			console.log("unchecked");
		}		
	});
});
</script>
<style>
.row-header{
    text-align: center;
    background: #B1A5A5;
}
</style>