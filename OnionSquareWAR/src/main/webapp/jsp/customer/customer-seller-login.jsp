<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<script type="text/javascript">
$(function() {	
	
	$("#customer-login-form").validate({					
		rules : {
			j_username : {
				required : true
			},
			
			j_password : {
				required : true
			}
		},
		messages:{
			j_username : {
				required : "Username is required"
			},
			
			j_password : {
				required : "Password is required"
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
	
	$("#seller-login-form").validate({					
		rules : {
			j_username : {
				required : true
			},
			
			j_password : {
				required : true
			}
		},
		messages:{
			j_username : {
				required : "Username is required"
			},
			
			j_password : {
				required : "Password is required"
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
 });

			
			
</script>

<div id="customer-login-div" class="customer_login_index" >
<h1><spring:message code="customer.login.header" /></h1>

  <jsp:include page="/jsp/common/message.jsp"></jsp:include>


<form name='f' action="<c:url value='/customer/j_spring_security_check?successUrl=/customer/customer-home&failureUrl=/customer-login/loginFailure' />"
	method='POST' id="customer-login-form">

	<table>
	
		<tr>
			<td ><spring:message code="login.username" /></td>
			<td ><input type='text' name='j_username' value=''></td>
		</tr>
		<tr>
			<td ><spring:message code="login.password" /></td>
			<td ><input type='password' name='j_password' /></td>
		</tr>
		<tr >
		    <td></td>
			<td ><input name="submit" type="submit" value="Login" class="form-button"/>			
			</td>
		</tr>
	</table>
 
</form>
</div>
<div id="blue-box"></div>
<div class="seller_login_index"  >
<h1><spring:message code="seller.login.header" /></h1>

<c:if test="${not empty error}">
	<div class="errorblock">Please enter correct username/password.</div>
</c:if>

<form name='f' action="<c:url value='/seller/j_spring_security_check' />"
	method='POST' id="seller-login-form">

	<table id="seller-login" class="seller_table">
	
		<tr>
			<td><spring:message code="login.username" /></td>
			<td><input type='text' name='j_username' value=''></td>
		</tr>
		<tr>
			<td><spring:message code="login.password" /></td>
			<td><input type='password' name='j_password' /></td>
		</tr>
		<tr>
			<td ></td><td><input name="submit" type="submit" value="Login" class="form-button" />
			
			</td>
		</tr>
	</table>

</form>
</div>
