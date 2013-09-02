<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://ckeditor.com" prefix="ckeditor" %>
<script type="text/javascript" >
  $(function(){	  
	  changeActiveBar("managerList");
	   $('#demoTable').jTPS( {perPages:[5],scrollStep:1,scrollDelay:80} );

  });
</script>
    <div class="admin_home_container">
		<div align="center"><h1><spring:message code="manager.list" /></h1></div>
		<br/><br/>
		<div class="message"><jsp:include page="/jsp/common/message.jsp"></jsp:include></div>
		<c:if test="${not empty managerList}">
				<table id="demoTable" class="store_table">
				     <thead>
						<tr>
						    <th><spring:message code="sn" /></th>
							<th><spring:message code="username" /></th>
							<th><spring:message code="email" /></th>
							<th>Action </th>
				
						</tr>
				     </thead>
				     <tbody>
						<c:forEach items="${managerList}" var="manager" varStatus="status">
							<tr>
							    <td>${status.index+1}</td>
								<td>${manager.username}</td>
								<td>${manager.adminEmail}</td>
								<td><a class="store_link" href="<c:url value='/admin/edit-manager'><c:param name='managerId' value='${manager.adminId}'/></c:url>"> <spring:message code="edit"/> </a>
								</td>
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
		</c:if>
	</div>
