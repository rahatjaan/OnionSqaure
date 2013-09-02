<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" >
  $(function(){
	  $.validator.addMethod("valueNotEqual", function(value, element,arg) {
			return (arg != value);
		});
	  $.validator.addMethod("urlValidation", function(value, element,arg) {
			return (value.indexOf(arg) >= 0);
		});
	  
	  jQuery.validator.addMethod("noSpace", function(value, element) { 
		  return value.indexOf(" ") < 0 && value != ""; 
		}, "Sub-domain must not have space in between");
	  
	  $("#store-form").validate({					
			rules : {
				storeName : {
					required : true
				},
				
				subDomainName : {
					required : true,
					noSpace  : true
				},	
				paypalEmail :{
					required : true,
					email : true

				},
				storeDescription : {
					required : true
				},
			     "country.countryId" : {
			    	 valueNotEqual:'1'			
				},
				cityName : {
					required : true
				},
				zipCode : {
					required : true
				}
			},
			messages:{
				storeName : {
					required : "Store name is required"
				},
				
				subDomainName : {
					required : "Store subdomain is required "
				},
				paypalEmail: {
					required : "Email is required",
					email : "Please enter valid email"

				},
				storeDescription : {
					required : "Store description is required"
				},
			     "country.countryId" : {
			    	 valueNotEqual : "Select a country"
				},
				cityName : {
					required : "City  is required"
				},
				zipCode : {
					required : "Zipcode is required"
				}				
				
			},
			 highlight: function(element, errorClass) {
			        $(element).css({ border: '1px solid #FF0000' });
			 
			 },
			 unhighlight: function(element){
			        $(element).css({ border: '1px solid #DDDDDD' });

	         },
	        onkeyup: false
	  });
	  
		$("#countryId").bind('change', function() {
			countryChange();
		});	
  });
  
  function resetForm(){
	  $("#store-form").find("input").val("");
	  $("#store-form").find("select").val("0");
	  return false;
  }
  
 function countryChange(){
	 $.ajax({
		   headers: { "Content-Type": "application/json", "Accept": "application/json" },
		   type:"GET",
		   url: "<c:url value='/stateList'/>",			   
		   data: {"countryId":$("#countryId").val()},
		   dataType: "json",
		   success: function(data){
			  
			   var html ='';
			   if($("#countryId").val()!='1'){					 
			  	  html = "<option value='1'>Select</option>";
			   }			   
				if (data) {
					for (p in data) {
						
							html += "<option value='" + p + "'>" + data[p] + "</option>";
					
						
					}
				}
				$("#stateId option").remove();
				$("#stateId").append(html).trigger("change");
			   
		   } ,
		     error:function(x,e){
		         
		        alert("Ajax error");
		        }
		   });       
    }

  </script>
<%-- <c:if test="${empty mode}"> --%>
<!-- <div class="warningMessages">Your store has not been created yet. Why not create a store?</div> -->
<%-- </c:if> --%>

<div id="store-form-div" class="store_home_container store-form">
    <c:if test="${empty mode}">
	<h1><spring:message	code="create.store" /></h1>
	</c:if>
	<c:if test="${mode=='edit'}">
	<h1><spring:message	code="update.store" /></h1>
	</c:if>
	<form:form id="store-form" method="POST" action="save-store"
		modelAttribute="store" cssStyle="width=100%" enctype="multipart/form-data">
	    <form:hidden path="seller.sellerId"/>	
	    
		<div id="form_body">				        
			<table >
			
				<tr>
				  <td></td>
					<td ><jsp:include
							page="/jsp/common/message.jsp"></jsp:include></td>
				</tr>
				<tr class="row">
				    <form:hidden path="storeId" id="storeId" />
				
					<td class="label2"><spring:message code="store.name" /></td>
					<td ><form:input path="storeName"
							id="storeName" /></td>
				</tr>
			
				
				<c:if test="${empty mode}">
				<tr class="row">
					<td class="label2"><spring:message code="store.subDomainName" /></td>
					<td >www.onionsquare.com/<form:input path="subDomainName"
							id="subDomainName"  style="width:90px"/></td>
				</tr>
				</c:if>
				<c:if test="${mode=='edit'}">
				<form:hidden path="subDomainName"
							id="subDomainName" />
				
							
				</c:if>
				<tr class="row">
					<td class="label2"><spring:message code="country" /></td>
					<td >
					<form:select path="country.countryId" id="countryId">
						<form:options items="${countryList}" itemLabel="countryName" itemValue="countryId" />				 
					</form:select> </td> 
				</tr>
				<tr class="row">
					<td class="label2"><spring:message code="state" /></td>
					<td >
						<form:select path="state.stateId"  id="stateId">
						    <form:option value="1">Select</form:option>	
					        <c:if test="${ not empty stateList}">
					          <form:options items="${stateList}" itemLabel="stateName" itemValue="stateId" />
					        </c:if>
						</form:select> 
					</td>  
				</tr>
				<tr class="row">
					<td class="label2"><spring:message code="city" /></td>
					<td ><form:input  path="cityName" 
							id="cityName" /></td> 
				</tr>
				<tr class="row">
					<td class="label2"><spring:message code="zipCode" /></td>
					<td ><form:input  path="zipCode" 
							id="zipCode" /></td> 
				</tr>
				<c:if test="${mode=='edit'}">
				<tr class="row">
					<td class="label2"><spring:message code="seller.picture" /></td>
					<td class="input-field">
						<input type="file" name="sellerPicture" />
					</td> 
				</tr>
				</c:if>
				<tr class="row">
					<td class="label2"><spring:message code="payPalEmail" /></td>
					<td ><form:input  path="paypalEmail" 
 							id="paypalEmail" /></td>  
				</tr>
				<tr >
					<td class="label2"><spring:message code="facebook.link" /></td>
					<td class="input-field">
						<form:input path="facebookLink" />
					</td> 
				</tr>
				
				<tr >
					<td class="label2"><spring:message code="twitter.link" /></td>
					<td class="input-field">
						<form:input path="twitterLink" />		
					</td> 
				</tr>
				
				<tr >
					<td class="label2"><spring:message code="linkedin.link" /></td>
					<td class="input-field">
						<form:input path="linkedinLink" />
					</td> 
				</tr>
               <tr class="row">
					<td class="label2"><spring:message code="store.overview" /></td>
					<td class="input-field"> <form:textarea path="storeDescription"
 							id="storeDescription" />
 							<script type="text/javascript">
    							CKEDITOR.replace( 'storeDescription' ,{
    								height: 200,
    						        width: 400
    							});
							</script>
					</td>
				</tr>	
				
				 <tr class="row">
					<td class="label2"><spring:message code="shipping.reFunding.policy" /></td>
					<td class="input-field"> <form:textarea path="shippingRefundPolicy"
 							id="shippingRefundPolicy" />
 							<script type="text/javascript">
    							CKEDITOR.replace( 'shippingRefundPolicy' ,{
    								height: 200,
    						        width: 400
    							});
							</script>
					</td>
				</tr>	
               

				<tr class="row">
					<td> </td>
					<td>
						<c:if test="${empty mode}">
							<form:button name="action" value="save" class="form-button">
									<spring:message code="save" />
								</form:button>
						</c:if>
						<c:if test="${mode=='edit'}">
							<form:button name="action" value="edit" class="form-button">
									<spring:message code="edit" />
								</form:button>
						</c:if>
						<form:button name="action" value="reset" onclick="return resetForm();" class="form-button">
								<spring:message code="reset" />
						</form:button>
					</td>

				</tr>
			</table>
		</div>
	
	</form:form>
	
</div>