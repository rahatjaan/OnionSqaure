<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script type="text/javascript" >
  $(function(){
	  changeActiveBar("inquiry");
	   $('#demoTable').jTPS( {perPages:[5],scrollStep:1,scrollDelay:80} );

  });
  

</script>

<br/> <br/>
<div  id="admin-customer-complain" class="admin-complain-container">

  <c:if  test="${sender=='seller'}">
   <h1><spring:message code="admin.seller.message" /></h1>
   <div class="message"><jsp:include page="/jsp/common/message.jsp"></jsp:include></div>
    <c:if test="${not empty inquiryList}">
   
	<div id="inquiryListDiv" >
			<table id="demoTable" class="store_table">
			  <thead>
				<tr>
				    <th><spring:message code="sn" /></th>			
					<th><spring:message code="seller.name" /></th>	
					<th><spring:message code="store.name" /></th>		
					<th><spring:message code="send.date" /></th>					
					<th><spring:message code="viewMessage" /> </th>
				</tr>
		      </thead>
		      <tbody>
				<c:forEach items="${inquiryList}" var="inquiry" varStatus="status">
					<tr>
					   <td>${status.index+1}</td>
						<td>
						<c:if test="${inquiry.senderUserType==1}">
					          ${inquiry.senderName}
					       </c:if>
					       <c:if test="${inquiry.senderUserType==2}">
					           You
					       </c:if></td>
						<td>${inquiry.store.storeName}</td>	
						<td><fmt:formatDate value="${inquiry.postedDate}" type="date"/></td>								
						<td><a class="store_link" href="<c:url value='/admin/admin-view-inquiries'><c:param name='inquiryId' value='${inquiry.inqueryId}'/></c:url>" > <spring:message code="viewMessage"/> </a></td>	
					</tr>
				</c:forEach>
			  </tbody>
			  <tfoot class="nav">
				<tr>
					<td colspan="5">
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
	</c:if>
	
	<c:if  test="${sender=='customer'}">
	<div id="inquiryListDiv" >
	   <h1><spring:message code="admin.customer.message" /></h1>
	    <div class="message"><jsp:include page="/jsp/common/message.jsp"></jsp:include></div>
	    <c:if test="${not empty inquiryList}">
	    
		<table id="demoTable" class="store_table">
		  <thead>
			<tr>
				<th><spring:message code="sn" /></th>		
				<th><spring:message code="customerName" /></th>		
				<th><spring:message code="send.date" /></th>					
				<th><spring:message code="viewMessage" /> </th>
			</tr>
	     </thead> 
	     <tbody>
			<c:forEach items="${inquiryList}" var="inquiry" varStatus="status">
				<tr>
				    <td>${status.index+1}</td>
					<td>
						<c:if test="${inquiry.senderUserType==3}">
					          ${inquiry.senderName}
					     </c:if>
					     <c:if test="${inquiry.senderUserType==2}">
					           You
					     </c:if>
				      </td>		
					<td><fmt:formatDate value="${inquiry.postedDate}" type="date"/></td>								
					<td><a class="store_link" href="<c:url value='/admin/admin-view-inquiries'><c:param name='inquiryId' value='${inquiry.inqueryId}'/></c:url>" > <spring:message code="viewMessage"/> </a></td>	
				</tr>
			</c:forEach>
		 </tbody>
		  <tfoot class="nav">
			<tr>
				<td colspan="5">
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
	</c:if>
	
    <c:if  test="${sender=='admin'}">
		<div id="inquiryListDiv">
		   <h1><spring:message code="admin.admin.message" /></h1>
		   <a href="<c:url value='/admin/admin-inquiry'></c:url>" > <spring:message code="new.inquiry"/> </a>
		    <div class="message"><jsp:include page="/jsp/common/message.jsp"></jsp:include></div>
		   
		   <c:if test="${not empty inquiryList}">
			<table id="demoTable" class="store_table">
				  <thead>
					<tr>
					  	<th><spring:message code="sn" /></th>				  
						<th><spring:message code="senderName" /></th>	
						<th><spring:message code="receiverName" /></th>				
						<th><spring:message code="send.date" /></th>					
						<th><spring:message code="viewMessage" /> </th>
					</tr>
			       </thead>
		           <tbody>
						<c:forEach items="${inquiryList}" var="inquiry" varStatus="status">
							<tr>
							   <td>${status.index+1}</td>
								<td> ${inquiry.senderName} </td>
								<td> ${inquiry.receiverName} </td>								
								<td><fmt:formatDate value="${inquiry.postedDate}" type="date"/></td>								
								<td><a class="store_link" href="<c:url value='/admin/admin-view-inquiries'><c:param name='inquiryId' value='${inquiry.inqueryId}'/></c:url>" > <spring:message code="viewMessage"/> </a></td>	
							</tr>
						</c:forEach>
					</tbody>
					<tfoot class="nav">
						<tr>
							<td colspan="5">
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
	</c:if>
	
	<c:if  test="${sender=='guest'}">
		<div id="inquiryListDiv">
		   <h1><spring:message code="admin.guest.message" /></h1>
		   <div class="message"><jsp:include page="/jsp/common/message.jsp"></jsp:include></div>
		    <c:if test="${not empty inquiryList}">
		   
			<table id="demoTable" class="store_table">
			  <thead>
				<tr>
					<th><spring:message code="sn" /></th>				
					<th><spring:message code="guestName" /></th>		
					 <th><spring:message code="send.date" /></th>					
					<th><spring:message code="viewMessage" /> </th>
				</tr>
		       </thead>
		       <tbody>
					<c:forEach items="${inquiryList}" var="inquiry" varStatus="status">
						<tr>
						    <td>${status.index+1}</td>
							<td>${inquiry.senderName}</td>		
							 <td><fmt:formatDate value="${inquiry.postedDate}" type="date"/></td>								
							<td><a class="store_link" href="<c:url value='/admin/admin-view-inquiries'><c:param name='inquiryId' value='${inquiry.inqueryId}'/></c:url>" > <spring:message code="viewMessage"/> </a></td>	
						</tr>
					</c:forEach>
				</tbody>
				<tfoot class="nav">
					<tr>
						<td colspan="5">
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
	</c:if>
	
</div>