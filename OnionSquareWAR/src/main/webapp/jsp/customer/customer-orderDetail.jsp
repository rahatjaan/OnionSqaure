<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<div id="order-detail-div" class="order-detail">
    <br/><br/>
    <h1><spring:message code="order.Detail" /></h1>
    <br/><br/>
	<div class="back-button">
	
	 	<a href="<c:url value='/${storeSubDomain}/customer/customer-order'/>" data-icon="&#xe001;">
	    </a>
	</div>
	<div  align="center"> <jsp:include page="/jsp/common/message.jsp"></jsp:include></div>

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
				<td><c:if test="${order.shippingAddress.country.countryId!=1}">${order.shippingAddress.country.countryName}</c:if></td>
			</tr>
			<tr class="row">
				<td class="label3"><spring:message code="state" /></td>
				<td><c:if test="${order.shippingAddress.state.stateId!=1}">${order.shippingAddress.state.stateName}</c:if></td>
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
				<td><c:if test="${order.billingAddress.country.countryId!=1}">${order.billingAddress.country.countryName}</c:if></td>
			</tr>
			<tr class="row">
				<td class="label3"><spring:message code="state" /></td>
				<td><c:if test="${order.billingAddress.state.stateId!=1}">${order.billingAddress.state.stateName}</c:if></td>
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
		<table class="orderDetail">
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
					<td>${lineItem.product.tagLine}</td>
					<td>$ ${lineItem.product.unitPrice}</td>						
					<td>${lineItem.quantity}</td>						
					<td>$ ${lineItem.subTotal}</td>
				</tr>

			</c:forEach>
			</tbody>
		</table>

	</div>
</div>
