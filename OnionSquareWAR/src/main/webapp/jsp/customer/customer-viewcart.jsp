<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<script type="text/javascript">

 
 function changeQuantity(subDomainName,productId,prevQuantity,quantity){   
	 
	if($('#quantity').val() != "" && $('#quantity').val()!="0") {
		    var value = $('#quantity').val().replace(/^\s\s*/, '').replace(/\s\s*$/, '');
		    var intRegex = /^\d+$/;
		    if(!intRegex.test(value)) {
		       alert("Quantity must be positive integer.");
		       $('#quantity').val(prevQuantity);
		       $('#quantity').focus();
		       return false;
		    }
		} else {
		      alert("Quantity must be greater than 0.");
		      $('#quantity').val(prevQuantity);
		      $('#quantity').focus();
		       return false;
		}
			
     
	 window.location.replace("<c:url value='/"+subDomainName+"/update-cart/productId/"+productId+"/quantity/"+quantity+"'/>");
	 
 }

</script>



		
		 
		<br/>
	<div id="productListDiv" >
	  <h1><spring:message code="customer.shopping.cart" /></h1>
	  <br/><br/>
	   <div  align="center"><jsp:include page="/jsp/common/message.jsp"></jsp:include></div>
	     <c:if test="${ empty grandTotal}">
         	There are no products in your cart. <br/>
         	To add a product to your cart, first browse for it  and then click its "Add to Cart" button. <br/>
        </c:if>
        <a style="float:right" href="<c:url value='/${storeSubDomain}'/>">Continue Shopping</a>
	  	<c:forEach items="${lineItemListByStore}" var="lineItemList" varStatus="status">
	      <h2>${lineItemList[0].product.store.storeName}</h2>
	    
        
		<table class="viewCart">
           <thead>
			<tr>
				<th><spring:message code="product.name" /></th>
				<th><spring:message code="product.price" /></th>
				<th><spring:message code="product.quantity" /></th>
				<th><spring:message code="product.subTotal" /></th>
				<th><spring:message code="remove" /></th>
					
			</tr>
			</thead>
			<tfoot>
			  <tr>
			    <td colspan="4" style="text-align:right;">Subtotal:</td>
			    <td>$ ${grandTotal[status.index]}</td>
			   </tr>
			   <tr> 
			    <td colspan="4" style="text-align:right;"> Grand Total:</td>
			    <td>$ ${grandTotal[status.index]}</td>
			  </tr>
			</tfoot>
			<tbody>
	          <c:forEach var="lineItem" items="${lineItemList}" varStatus="status">			   
			      <tr>		    
			            <td>${lineItem.product.productName}</td>			            
			            <td>$ ${lineItem.product.unitPrice} </td> 
			            <td> 
			              <input type="text" id="quantity" value ='${lineItem.quantity}' onchange="changeQuantity('${storeSubDomain}',${lineItem.getProduct().getProductId()},${lineItem.quantity},this.value);" />
			              </td>
			            <td>$ ${lineItem.subTotal}</td>
			            <td><a href="<c:url value='/${storeSubDomain}/removeitem/productId/${lineItem.getProduct().getProductId()}'/>" >Remove</a>
			      		
			      	 		
			      </tr>
			    <br/>  			 
			</c:forEach>	
			</tbody>	
		</table>
		
		<br/><br/>
		
		<a href="<c:url value='/${storeSubDomain}/proceedToCheckout/store/${lineItemList[0].product.store.storeId}'></c:url>"> <input type="image" src="<spring:url value="/static/images/proceedToCheckout.gif"/>"></a>
        <br/><br/>
        </c:forEach>
        
        
	</div>

	




