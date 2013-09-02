<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script type="text/javascript">
  $(function(){
	   $('#demoTable').jTPS( {perPages:[5],scrollStep:1,scrollDelay:80} );
  
  });
</script>
<div id="customer-order-div" class=" quote_home_container reset-margin ">
    <h1> Reviewing Orders </h1>
    <br/><br/>
    <div  align="center"><jsp:include page="/jsp/common/message.jsp"></jsp:include></div>
    <br/><br/>
    <c:if test="${ not empty orderList}">
	<table id="demoTable" class="store_table">
		  <thead>
			<tr>
			    <th><spring:message code="sn" /></th>
				<th><spring:message code="order.id" /></th>
				<th><spring:message code="order.date" /></th>			
				<th><spring:message code="order.totalAmount" /></th>			
	            <th><spring:message code="viewDetail" /> </th>
			</tr>
	      </thead>
	      <tbody>
			<c:forEach items="${orderList}" var="order" varStatus="status">
				<tr>
					 <td>${status.index+1}</td>			
					<td>${order.orderId}</td>
					<td><fmt:formatDate value="${order.createdDate}" type="date"/></td>				
					<td>$${order.totalAmount}</td>
					<td><a href="<c:url value='/${storeSubDomain}/customer/customer-orderDetail/orderId/${order.orderId}' ></c:url>" id="printThis"> <spring:message code="viewDetail" />
					</a></td>
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
