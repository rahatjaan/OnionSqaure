<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
  $(function(){
	  changeActiveBar("invitaion list");
	   $('#demoTable').jTPS( {perPages:[5],scrollStep:1,scrollDelay:80} );

  });
</script>
<br/><br/><br/> 

<div id="invitationListDiv" align="center" class="store_home_container">
  <h1> <spring:message code="seller.invitation.list" /></h1> <br/><br/>
	<div align="center"><jsp:include page="/jsp/common/message.jsp"></jsp:include>
	</div>
	<c:if test="${not empty invitationList}">
	<div>
		<table id="demoTable" class="store_table">
          <thead>
			<tr>
			    <th><spring:message code="sn" /></th>
				<th sort="email"><spring:message code="invitee.email" /></th>
				<th sort="date"><spring:message code="invite.date" /></th>
				<th sort="status"><spring:message code="status" /></th>

			</tr>
		  </thead>
		  <tbody>
			<c:forEach items="${invitationList}" var="invitee" varStatus="status">
				<tr>
				   <td>${status.index+1}</td>
					<td>${invitee.inviteeEmail}</td>
					<td>${invitee.invitationDate}</td>
					<c:if test="${invitee.invitationStatus=='false'}">
						<td><spring:message code="pending" /></td>
					</c:if>
					<c:if test="${invitee.invitationStatus=='true'}">
						<td><spring:message code="accepted" /></td>
					</c:if>

				</tr>
			</c:forEach>
		  </tbody>
		  <tfoot class="nav">
			<tr>
				<td colspan="4">
					<div class="pagination"></div>
					<div class="paginationTitle">Page</div>
					<div class="selectPerPage"></div>
					<div class="status"></div>
				</td>
			</tr>
		 </tfoot>
		</table>
	</div>
  </c:if>
</div>
