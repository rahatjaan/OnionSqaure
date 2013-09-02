<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script type="text/javascript" >
$(function(){

	   $('#demoTable').jTPS( {perPages:[5],scrollStep:1,scrollDelay:80} );


});

</script>

<br/> <br/>
<div  id="admin-customer-complain" class="admin-complain-container">
   <h1><spring:message code="seller.message.list" /></h1>
   <div class="newInquiry"><a href="<c:url value='/seller/seller-inquiry-admin'/>"><spring:message code="inquiry.to.admin" /></a></div>
   
    <div class="message"><jsp:include page="/jsp/common/message.jsp"></jsp:include></div>
   
    <c:if test="${not empty inquiryList}">
	<div id="inquiryListDiv" >
		<table id="demoTable" class="store_table" style="width:100%;margin-left:5px;">
			<thead>
				<tr>
				   <th><spring:message code="sn" /></th>					
				   	<th><spring:message code="sender/receiver" /></th>
				   	<th><spring:message code="senderEmail/receiverEmail" /></th>				   		
				   	<th><spring:message code="senderType/receiverType" /></th>	  			   				
					<th><spring:message code="send.date" /></th>
					<th><spring:message code="viewConversation" /> </th>
				</tr>
		    </thead>
	        <tbody>
				<c:forEach items="${inquiryList}" var="inquiry" varStatus="status">
					<tr>
					    <td>${status.index+1}</td>
					    <c:if test="${inquiry.senderUserType==1 && sellerId==inquiry.senderUserId}">
					       <td> ${inquiry.receiverName}
					        </td>
					        <td> ${inquiry.receiverEmail}
					         </td>
					       <td><c:if test="${inquiry.receiverUserType==3}">
					           Customer
					       </c:if>
					       <c:if test="${inquiry.receiverUserType==1}">
					           Seller
					       </c:if>
					       <c:if test="${inquiry.receiverUserType==2}">
					           Admin
					       </c:if>
					       <c:if test="${inquiry.receiverUserType==4}">
					           Guest
					       </c:if>
					     </td>
					  </c:if>
					  <c:if test="${inquiry.receiverUserType==1 && sellerId==inquiry.receiverUserId}">
					      <td> ${inquiry.senderName}
					      </td>
					      <td> ${inquiry.senderEmail}
					      </td>
					       <td><c:if test="${inquiry.senderUserType==3}">
					           Customer
					            </c:if>
						       <c:if test="${inquiry.senderUserType==1}">
						           Seller
						       </c:if>
						       <c:if test="${inquiry.senderUserType==2}">
						           Admin
						       </c:if>
						       <c:if test="${inquiry.senderUserType==4}">
						          Guest
						       </c:if>
					         </td>
					     
					  </c:if>
						<td><fmt:formatDate value="${inquiry.postedDate}" type="date"/></td>	
						<td><a href="<c:url value='/seller/seller-view-inquiries'><c:param name='inquiryId' value='${inquiry.inqueryId}'/></c:url>" > <spring:message code="viewMessage"/> </a></td>	
					</tr>
				</c:forEach>
			</tbody>
			<tfoot class="nav">
				<tr>
					<td colspan="6">
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