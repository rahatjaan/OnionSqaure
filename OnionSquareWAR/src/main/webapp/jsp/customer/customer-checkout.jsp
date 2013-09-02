<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style type="text/css">
.inactiveClass{
display:none
}

.activeClass{
}
</style>

<script type="text/javascript">

$(document)
.ajaxStart(function(){
    $("#ajaxSpinnerContainer").show();
    $("#customer-checkout-div").addClass("payPalLoading");
})
.ajaxStop(function(){
    $("#ajaxSpinnerContainer").hide();
    $("#customer-checkout-div").removeClass("payPalLoading");
});

function payFromPaypal(){
	  
	  $.ajax({
		   type:"GET",
		   url: "<c:url value='/${storeSubDomain}/customer/payment'/>",		
		   data: '',
		  
		   success: function(data){					   
			   if (data) {
					for (p in data) {
						
						if(p=="page"){
							window.location.replace("<c:url value='/page-not-found'/>");
						}
						else if(p=="error")	{
							if($(".errorMessages").length > 0)
								$(".errorMessages").html("");
							$("#customer-checkout-div").prepend("<div class='errorMessages'>"+ data[p]+"</div>");
						}
						else if (p=="payKey"){
							 window.location.replace("https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_ap-payment&paykey="+data[p]);
						}
					
						
					}
				}
				
			   
		   } ,
		     error:function(x,e){
		         
		        alert("Ajax error");
		        }
		   });       
	
}

  
  
  $(function() {	

		
	  $.validator.addMethod("valueNotEqual", function(value, element,arg) {
			return (arg != value);
		});
	
				$("#customer-address-form").validate({					
					rules : {
					
						"shippingAddress.cityName" : {
							required: true
						},
						"shippingAddress.country.countryId":{
		                    valueNotEqual:'1'
		                    
		                },
						"shippingAddress.phone":{
							required: true,
							number:true
						}
					},
					messages:{					
						
						"shippingAddress.cityName" : {
							required: "City name is required"
						},
						
						"shippingAddress.country.countryId" : {
							valueNotEqual : "Select a country"
						},
						"shippingAddress.phone":{
							required: "Phone number is required",
							number:"Please enter valid phone number"
						}
						
						
					},
				
					highlight: function(element, errorClass) {
				        $(element).css({ border: '1px solid #FF0000' });
				 
				 },
				 unhighlight: function(element){
				        $(element).css({ border: '1px solid #DDDDDD' });

		            },
			           onkeyup: false,
				});	
				$("#shippingCountryId").bind('change', function() {
					countryChange("1");
				});
				
				
	});
  
	 function countryChange(addressType){
		 var countryId;
	 
		 if(addressType=="1")
			 countryId=$("#shippingCountryId").val();
		
		 $.ajax({
			   headers: { "Content-Type": "application/json", "Accept": "application/json" },
			   type:"GET",
			   url: "<c:url value='/stateList'/>",			   
			   data: {"countryId":countryId},
			   dataType: "json",
			   success: function(data){
				  
				   var html ='';
				   if(countryId != '1'){					 
				  	  html = "<option value='1'>Select</option>";
				   }				   
					if (data) {
						for (p in data) {
							
								html += "<option value='" + p + "'>" + data[p] + "</option>";
						
							
						}
					}
					if(addressType=="1"){
						$("#shippingStateId option").remove();
						$("#shippingStateId").append(html).trigger("change");
					}
					
						
				   
			   } ,
			     error:function(x,e){
			         
			        alert("Ajax error");
			        }
			   });       
	 }
</script>
<div id="customer-checkout-div" class="quote_home_container reset-margin customer-checkout">
	<div id="ajaxSpinnerContainer" align="center" style="display:none; margin:0 auto; z-index:2000;top:80px;">
	<img src="<spring:url value='/static/images/loading.gif'/>" id="ajaxSpinnerImage" title="working...">
	</div>
    <jsp:include page="/jsp/common/message.jsp"></jsp:include>
	<c:if test="${empty status}"> 
	  	<div align="center"><h1><spring:message code="customer.billing.shipping.details" /></h1></div>
		
		<form:form action="edit-customerAddress" id="customer-address-form"
			method="POST" modelAttribute="customer">
			<form:hidden path="customerId" />
			<form:hidden path="shippingAddress.addressId" />

			<div id="shippingDetail">
				<table>
					<tr class="row">
						<td colspan="2" align="center"><jsp:include
								page="/jsp/common/message.jsp"></jsp:include></td>
					</tr>
					<tr class="row">
						<td colspan="2">
							<h3>
								<spring:message code="order.shippingDetail" />
							</h3>

						</td>
					</tr>

					<tr class="row">
						<td class="label2"><spring:message code="phoneNo" /></td>
						<td class="input-field"><form:input
								path='shippingAddress.phone' /></td>
					</tr>

					<tr class="row">
						<td class="label2"><spring:message code="country" /></td>
						<td class="input-field"><form:select
								path="shippingAddress.country.countryId" id="shippingCountryId" >
								<form:options items="${countryList}" itemLabel="countryName" itemValue="countryId"  />					 
							
							</form:select>
							</td>
							
					</tr>

					<tr class="row">
						<td class="label2"><spring:message code="state" /></td>
						<td class="input-field"><form:select
								path="shippingAddress.state.stateId" id="shippingStateId" >
								<form:option value="1">Select</form:option>
								<c:if test="${ not empty shippingStateList}">
									  <form:options items="${shippingStateList}" itemLabel="stateName" itemValue="stateId" />
								</c:if>	
							</form:select></td>
					</tr>

					<tr class="row">
						<td class="label2"><spring:message code="city" /></td>
						<td class="input-field"><form:input
								path='shippingAddress.cityName' /></td>
					</tr>			

					<tr class="row">
						<td class="label2"><spring:message code="zipCode" /></td>
						<td class="input-field"><form:input
								path='shippingAddress.zipCode' /></td>
					</tr>
				</table>
			</div> <!-- end of shipping detail -->


	
<%-- 			<form:button name="action" value="edit" class="form-button"> --%>
<%-- 				<spring:message code="edit" /> --%>
<%-- 			</form:button> --%>
			<form:button name="action" value="next" class="form-button">
				<spring:message code="next" />
			</form:button>
		</form:form>
	</c:if>
	<c:if test="${not empty status}">
         <h1> <spring:message code="customer.order.confirmation" /></h1>
		   <div id="productListDiv" >
				<table class="viewCart">
		          <thead>
						<tr>
							<th><spring:message code="product.name" /></th>
							<th><spring:message code="product.price" /></th>
							<th><spring:message code="product.quantity" /></th>
							<th><spring:message code="product.subTotal" /></th>					
						</tr>
					</thead>
					<tfoot>
						  <tr>
						    <td colspan="3" style="text-align:right;">Subtotal:</td>
						    <td>$ ${grandTotal}</td>
						   </tr>
						   <tr> 
						    <td colspan="3" style="text-align:right;"> Grand Total:</td>
						    <td>$ ${grandTotal}</td>
						  </tr>
					</tfoot>
					<tbody>
			          <c:forEach var="lineItem" items="${lineItemList}" varStatus="status">			   
					      <tr>		    
					            <td>${lineItem.product.productName}</td>			            
					            <td>$ ${lineItem.product.unitPrice} </td> 
					            <td>${lineItem.quantity} </td> 
					            <td>$ ${lineItem.subTotal}</td>			      		
					      </tr>
					    <br/>  			 
					</c:forEach>
					</tbody>		
				</table>
				   <br/> <br/>             
		      <a href="#" onclick="payFromPaypal();">  <input type="image"  src="https://www.paypal.com/en_US/i/btn/btn_buynowCC_LG.gif"  name="submit" alt="PayPal - The safer, easier way to pay online!"/></a>

			</div>
	
  

</c:if>
</div>