<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script type="text/javascript">
  $(function(){
	  changeActiveBar("orderDetail");
	  
	   var orderStatusId = '${order.orderStatus.orderStatusId}';
	   $("#orderStatusId").val(orderStatusId);
	 
	  
	  $("#orderStatusButton").click(function(){
		  var  statusId = $("#orderStatusId").find(":selected").val();
		  $.post("<c:url value='/seller/change-order-status'/>",
				  {
				    statusId:statusId,
				    orderId :'${order.orderId}'
				  },
				  function(data,status){
					 if(status=='success') {
					  $("#messageId").html("<div style='color: green;'>Order Status is updated Successfully</div>");
					 }
			});
	 
	  });
  });

</script>

<div id="order-detail-div" class="order-detail">
    <br/><br/>
    <h1><spring:message code="store.order.Detail" /> </h1>
    <br/><br/>
     
	<div class="back-button">
	  <c:if test="${order_type=='current_order'}">
		<a href="<c:url value='/seller/store-current-order'/>" data-icon="&#xe001;">
		</a>
	 </c:if>
	 <c:if test="${order_type!='current_order'}">
	 	<a href="<c:url value='/seller/store-order'/>" data-icon="&#xe001;">
	    </a>
	 </c:if>
	</div>
	
	<div  align="center"  id="messageId"><jsp:include page="/jsp/common/message.jsp"></jsp:include></div>
		<div id="orderDetail" class="order-customer-detail">
		<h2>
			<spring:message code="order.Detail" />
		</h2>
		<table>
		  
			<tr class="row">
				<td class="label3"><spring:message code="order.id" /></td>
				<td>${order.orderId}</td>
			</tr>
			<tr class="row">
				<td class="label3"><spring:message code="order.date" /></td>
				<td><fmt:formatDate value="${order.createdDate}" type="date"/></td>
			</tr>
		
			
			<tr class="row">
				<td class="label3"><spring:message code="order.orderStatus" /></td>
				<td>
				  <c:if test="${order_type=='current_order'}">
					<select id="orderStatusId" >
					      <option value="0">Select</option>
					      <option value="1">Pending </option>
					      <option value="2">Completed</option>
					    
					</select> 
					<security:authorize access="hasRole('ROLE_SELLER')">	
					  <button id="orderStatusButton"  >Change Status</button> 
					</security:authorize>
				  </c:if>
				  <c:if test="${order_type!='current_order'}">
				     ${order.orderStatus.statusName}
				  </c:if>
				</td>
			</tr>

			<tr class="row">
				<td class="label3"><spring:message code="order.totalAmount" /></td>
				<td>$ ${order.totalAmount}</td>
			</tr>
			
			<tr class="row">
				<td class="label3"><spring:message code="order.numberOfItems" /></td>
				<td>${order.numberOfItems}</td>
			</tr>


		</table>
	</div>
	<br/><<br/>
	<div id="customerDetail" class="order-customer-detail">
		<h2>
			<spring:message code="customerDetail" />
		</h2>
		<table>
		  
			<tr class="row">
				<td class="label3"><spring:message code="customerName" /></td>
				<td>${order.customer.fullName}</td>
			</tr>
		

			<tr class="row">
				<td class="label3"><spring:message code="email" /></td>
				<td>${order.customer.email}</td>
			</tr>

			<tr class="row">
				<td class="label3"><spring:message code="phoneNo" /></td>
				<td>${order.customer.phone}</td>
			</tr>


		</table>
	</div>
	<br />
	<br />
	<br />

	<div id="shippingDetail" class="order-shipping-detail">
		<h2>
			<spring:message code="order.shippingDetail" />
		</h2>
		<table>
		   
			<tr class="row">
				<td class="label3"><spring:message code="phoneNo" /></td>
				<td>${order.shippingAddress.phone}</td>
			</tr>
			<tr class="row">
				<td class="label3"><spring:message code="country" /></td>
				<td><c:if test="${order.shippingAddress.country.countryId !=1}">${order.shippingAddress.country.countryName}</c:if></td>
			</tr>
			<tr class="row">
				<td class="label3"><spring:message code="state" /></td>
				<td><c:if test="${order.shippingAddress.state.stateId !=1}">${order.shippingAddress.state.stateName}</c:if></td>
			</tr>

			<tr class="row">
				<td class="label3"><spring:message code="city" /></td>
				<td>${order.shippingAddress.cityName}</td>
			</tr>

			<tr class="row">
				<td class="label3"><spring:message code="zipCode" /></td>
				<td>${order.shippingAddress.zipCode}</td>
			</tr>
		</table>
	</div>



	<div id="billingDetail" class="order-billing-detail">
		<h2>
			<spring:message code="order.billingDetail" />
		</h2>
		<table>
			
			<tr class="row">
				<td class="label3"><spring:message code="phoneNo" /></td>
				<td>${order.billingAddress.phone}</td>
			</tr>
			<tr class="row">
				<td class="label3"><spring:message code="country" /></td>
				<td><c:if test="${order.billingAddress.country.countryId !=1}">${order.billingAddress.country.countryName}</c:if></td>
			</tr>
			<tr class="row">
				<td class="label3"><spring:message code="state" /></td>
				<td><c:if test="${order.billingAddress.state.stateId !=1}">${order.billingAddress.state.stateName}</c:if></td>
			</tr>

			<tr class="row">
				<td class="label3"><spring:message code="city" /></td>
				<td>${order.billingAddress.cityName}</td>
			</tr>

			
			<tr class="row">
				<td class="label3"><spring:message code="zipCode" /></td>
				<td>${order.billingAddress.zipCode}</td>
			</tr>
		</table>

	</div>
    <br><br><br>
	
	<div id="productDetail" class="order-product-detail">
		<h2>
			<spring:message code="productDetail" />
		</h2>
		<table class="viewCart">
		 <thead>
		    <tr>
		     <th class="label2"><spring:message code="product.name" /></th>
		      <th class="label2"><spring:message code="product.description" /></th>
		       <th class="label2"><spring:message code="product.price" /></th>
		     <th class="label2"><spring:message code="product.quantity" /></th>
		     <th class="label2"><spring:message code="product.subTotal" /></th>
		    </tr>
		  </thead>
		  <tbody>
			<c:forEach items="${lineItemList}" var="lineItem">
				<tr class="row">					
					<td>${lineItem.product.productName}</td>
					<td>${lineItem.product.description}</td>
					<td>$ ${lineItem.product.unitPrice}</td>						
					<td>${lineItem.quantity}</td>						
					<td>$ ${lineItem.subTotal}</td>
				</tr>

			</c:forEach>
			</tbody>
		</table>

	</div>
</div>
