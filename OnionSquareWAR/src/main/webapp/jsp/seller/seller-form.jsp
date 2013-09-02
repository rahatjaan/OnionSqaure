<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	
<script type="text/javascript">
   

	$(function() {	
			 $.validator.addMethod("valueNotEqual", function(value, element,arg) {
					return (arg != value);
				});
		
		
			$("#seller-signup-form").validate({					
				rules : {
					firstName : {
						required : true
					},
					
					lastName : {
						required : true
					},
					
					username : {
						required : true,
						email :true
					},
// 					emailId : {
// 						required : true,
// 						equalTo: "#username"
// 					},
					
					password : {
						required : true,
			            minlength: 5
					},
					
					password1 : {
			            equalTo: "#password"
					},
					termsAndCondition : {
						required: true
					},
					"cityName": {
						required: true
					},
					"country.countryId":{
	                    valueNotEqual:'1'	                    
	                },
	                /* ankur: required rule for captcha*/
	                captcha: {
	                	required: true
	                }
				},
				messages:{
					firstName : {
						required : "First name is required"
					},
					
					lastName : {
						required : "Last name is required"
					},
					
					username : {
						required : "Email is required",
						email : "Please enter valid email"
					},
					
					password : {
						required : "Password is required",
			            minlength: "Pasword length should be greater than 5"
					},
					
					password1 : {
			            equalTo: "Confirm password does not match"
					},
					
					termsAndCondition : {
						required : "Please accept Terms and Conditions"
					},
					"cityName" : {
						required: "City name is required"
					},
					
					"country.countryId" : {
						valueNotEqual : "Select a country"
					},
					/* ankur: error message for captcha*/
					captcha: {
						required: "Please enter word displayed in image."
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
		  $("#seller-form").find("input").val("");
		  $("#seller-form").find("select").val("0");
		  $(".error").text("");
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
	 
	 function termsAndCondtions(){
		 var win=window.open("<c:url value='/seller-terms-conditions'/>", '_blank');
		  win.focus();
	 }
</script>


<div id="seller-signUp-div" class="quote_home_container reset-margin seller-signup">
	<div class="signup_header"><spring:message code="seller.signup.form" /></div>
	<form:form action="save-seller" id="seller-signup-form" method="POST"
		modelAttribute="seller"  >
		<form:hidden path="invitationId" value="${invitationId}"/>
		<form:hidden path="token" value="${invitationToken}"/>
		
			    <jsp:include page="/jsp/common/message.jsp"></jsp:include>
			   
			<table id="seller-form" width="800px">
				<tr class="row">
					<td width="150px" class="label2"><spring:message code="firstName" /></td>
					<td class="input-field"><form:input path="firstName" id="firstName" />
					</td>
				</tr>
	
				<tr class="row">
					<td class="label2"><spring:message code="middleName" /></td>
					<td class="input-field"><form:input path="middleName" /></td>
				</tr>
				<tr class="row">
					<td class="label2"><spring:message code="lastName" /> </td>
					<td class="input-field"><form:input path="lastName" /></td>
				</tr>
				<tr class="row">
					<td class="label2"><spring:message code="email" /></td>
					<td class="input-field"><form:input path="username" /></td>
				</tr>
				
<!-- 				<tr class="row"> -->
<%-- 					<td class="label2"><spring:message code="retypeEmail" /></td> --%>
<!-- 					<td class="input-field"><input type="text"  id="emailId" name="emailId" /></td> -->
<!-- 				</tr> -->
				
				<tr class="row">
					<td class="label2"><spring:message code="password" /></td>
					<td class="input-field"><form:password path="password" /></td>
				</tr>
	
				<tr class="row">
					<td class="label2"><spring:message code="retypePassword" /></td>
					<td class="input-field"><input type="password"  id="password1" name="password1" /></td>
				</tr>	
			
							
		
				
				<tr class="row">
				     <td colspan="2"><h3><spring:message code="address.detail" /> </h3></td>
				     <td></td>
				</tr>			
		
				
				<tr class="row">
					<td class="label2"><spring:message code="country" /></td>
					<td class="input-field">
					<form:select path="country.countryId"  id="countryId">
							<form:options items="${countryList}" itemLabel="countryName" itemValue="countryId" />					 
					</form:select> 
					</td>
				</tr>
				
				<tr class="row">
					<td class="label2"><spring:message code="state" /></td>
					<td class="input-field">
						<form:select path="state.stateId" id="stateId"  >
						    <form:option value="1">Select</form:option>							    
						     <c:if test="${ not empty stateList}">
						          <form:options items="${stateList}" itemLabel="stateName" itemValue="stateId" />
						      </c:if>									 
						</form:select> </td>
				</tr>
				
				<tr class="row">
					<td class="label2"><spring:message code="city" /></td>
					<td class="input-field"><form:input path='cityName' /></td>
				</tr>
				
								
				
				
				<tr class="row">
					<td class="label2">I agree to the &nbsp;<a onclick="termsAndCondtions()"> <spring:message code="termsAndCondition" /> </a></td>
					<td ><input type="checkbox" id="termsAndCondition" name="termsAndCondition" value="true" /></td>
	
				</tr>
				<!-- ankur: Added CAPTCHA -->	
				<tr class="row">
					<td></td>
					<td>
						<div style="float:left">
							<img src="<c:url value='/captcha.jpg' />" alt="Please wait image is loading.."/>
						</div>
						
					</td>
				</tr>
					<tr class="row">
					<td></td>
					<td>
					
						<div style="float:left">
							<input name="captcha" id="captcha" type="text" />
						</div>
					</td>
				</tr>
				<tr class="row">
					<td></td>
					<td>
						<form:button name="action" value="save" class="form-button"  >
									<spring:message code="signUp" />
						</form:button>
					</td>
		       </tr>	
				
								
			
	
				
			</table>
		
	</form:form>

</div>
