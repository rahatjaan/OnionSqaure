<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script type="text/javascript">
  $(function(){
	  changeActiveBar("orderDetail");
	  
	   $( "#fromDate" ).datepicker();
	   $( "#toDate" ).datepicker();
		  
	   $( "#fromDate").val( $.format.date('${order.fromDate}', "MM/dd/yyyy"));
	   $( "#toDate").val( $.format.date('${order.toDate}', "MM/dd/yyyy"));
	   
	   $('#demoTable').jTPS( {perPages:[5],scrollStep:1,scrollDelay:80} );

  });

</script>

<div id="store-order-div" class="store_order admin-view-stores" >
	<div align="center"><h1><spring:message code="order.history" /></h1></div>
	<br/><br/>
	<form:form action="store-order" modelAttribute="order" method="POST">
		<table>
		    <tr>
		      <td class="label2" ><spring:message code="from.date" /> </td> <td><form:input id="fromDate" path="fromDate"/></td>
		      <td class="label2"><spring:message code="to.date" /> </td> <td><form:input id="toDate" path="toDate"/></td>
		      <td><form:button name="action" id="search"  class="form-button" >
		      			<spring:message code='search' />
		      	  </form:button>
		      </td>
		    </tr>
    
   		</table>
   	</form:form>
	<br/><br/>
	<div  align="center"><jsp:include page="/jsp/common/message.jsp"></jsp:include></div>
	<c:if test="${ not empty orderList}">
	<div class="store_home_container">
	<table id="demoTable" class="store_table">
	  <thead>
		<tr>
		    <th><spring:message code="sn" /></th>
			<th><spring:message code="order.id" /></th>
			<th><spring:message code="order.customerName" /></th>
			<th><spring:message code="order.date" /></th>
			<th><spring:message code="order.totalAmount" /></th>
            <th><spring:message code="order.view.detail" />  </th>
		</tr>
      </thead>
      <tbody>
		<c:forEach items="${orderList}" var="order" varStatus="status">
			<tr>
			    <td>${status.index+1}</td>
				<td>${order.orderId}</td>
				<td>${order.customer.fullName}</td>				
				<td><fmt:formatDate value="${order.createdDate}" type="date"/></td>
				<td>$ ${order.totalAmount}</td>
				<td><a class="store_link" href="<c:url value='/seller/store-orderDetail'><c:param name='order' value='order_history'/><c:param name='orderId' value='${order.orderId}'/>  </c:url>"><label style="text-decoration:underline;"> <spring:message code="viewDetail" /></label>
				</a></td>
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
