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
   <h1><spring:message code="customer.message.list" /></h1>
   	<div class="newInquiry"><a href="<c:url value='/${storeSubDomain}/customer/customer-inquiry-seller'/>"><spring:message code="inquiry.to.seller" /></a><span>/</span> <a href="<c:url value='/${storeSubDomain}/customer/customer-inquiry-admin'/>"><spring:message code="inquiry.to.admin" /></a></div>
    <div class="message"><jsp:include page="/jsp/common/message.jsp"></jsp:include></div>
    
    <c:if test="${not empty inquiryList}">
	<div id="inquiryListDiv">
		<table id="demoTable" class="store_table">
			<thead>
				 <tr>
					<th><spring:message code="sn" /></th>						
					<th><spring:message code="sender" /></th>
					<th><spring:message code="receiver" /></th>					
					<th><spring:message code="send.date" /></th>							
					<th><spring:message code="viewConversation" /> </th>
				 </tr>
			</thead>
	        <tbody>
	
				<c:forEach items="${inquiryList}" var="inquiry" varStatus="status">
					<tr>
					   <td>${status.index+1}</td>
					   <td><c:if test="${inquiry.senderUserType==3}">
					           You
					       </c:if>
					       <c:if test="${inquiry.senderUserType==1}">
					           Seller
					       </c:if>
					       <c:if test="${inquiry.senderUserType==2}">
					           Admin
					       </c:if>
					    </td>
					    <td><c:if test="${inquiry.receiverUserType==3}">
					           You
					       </c:if>
					       <c:if test="${inquiry.receiverUserType==1}">
					           Seller
					       </c:if>
					       <c:if test="${inquiry.receiverUserType==2}">
					           Admin
					       </c:if>
					    </td>
						<td><fmt:formatDate value="${inquiry.postedDate}" type="date"/></td>	
						<td><a class="store_link" href="<c:url value='/${storeSubDomain}/customer/customer-view-inquiries'><c:param name='inquiryId' value='${inquiry.inqueryId}'/></c:url>" > <spring:message code="viewMessage"/> </a></td>	
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
	
</div>