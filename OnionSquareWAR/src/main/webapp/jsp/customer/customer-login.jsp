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
 });
			
			
</script>

<div id="customer-login-div" class="quote_home_container reset-margin login-form">
<h1><spring:message code="customer.login.header" /></h1>


<form name='f' action="<c:url value='/customer/j_spring_security_check?successUrl=/${storeSubDomain}/customer/customer-home&failureUrl=/${storeSubDomain}/customer-login/loginFailure' />"
	method='POST' id="customer-login-form">

	<table>
		<tr class="row">
		 <td colspan="2" align="center">
		 <jsp:include page="/jsp/common/message.jsp"></jsp:include>
		 
		 </td>
		</tr>
		<tr >
			<td ><spring:message code="login.username" /></td>
			<td ><input type='text' name='j_username' value=''></td>
		</tr>
		<tr >
			<td ><spring:message code="login.password" /></td>
			<td ><input type='password' name='j_password' /></td>
		</tr>
		<tr >
		    <td></td>
			<td ><input name="submit" type="submit" value="Login" class="form-button"/>
				<!-- ankur: Link for Forgot Password -->
				&nbsp;<a href='<c:url value="/${storeSubDomain}/customer-forgot-password"/>'>Forgot Password?</a>
			</td>
		</tr>
	</table>
 
</form>
</div>
