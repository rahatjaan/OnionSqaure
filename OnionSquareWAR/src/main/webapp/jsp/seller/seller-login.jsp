<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<script type="text/javascript">
$(function() {	
	
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
<c:if test="${empty storeSubDomain}">
	<div class="quote_home_container seller_login">
	<h1><spring:message code="seller.login.header" /></h1>
		<form name='f' action="<c:url value='/seller/j_spring_security_check?failureUrl=/seller/login/loginFailure' />"
			method='POST' id="seller-login-form">
		
			<table id="seller-login" class="seller_table">
				<tr>
				 <td colspan="2">
				 <jsp:include page="/jsp/common/message.jsp"></jsp:include>
				 
				 </td>
				</tr>
				<tr>
					<td class="label2"><spring:message code="login.username" /></td>
					<td class="input-field"><input type='text' name='j_username' value=''></td>
				</tr>
				<tr>
					<td class="label2"><spring:message code="login.password" /></td>
					<td class="input-field"><input type='password' name='j_password' /></td>
				</tr>
				<tr>
					<td ></td><td><input name="submit" type="submit" value="Login" class="form-button" />
					<!-- ankur: Link for Forgot Password -->
					&nbsp;<a href="<c:url value='/seller-forgot-password'/>">Forgot Password?</a>
					</td>
				</tr>
			</table>
		
		</form>
	</div>
</c:if>
<c:if test="${not empty storeSubDomain}">
<div id="customer-login-div" class="quote_home_container reset-margin login-form">
	<h1><spring:message code="seller.login.header" /></h1>
	<form name='f' action="<c:url value='/seller/j_spring_security_check?failureUrl=/${storeSubDomain}/seller/login/loginFailure' />"
		method='POST' id="seller-login-form">
	
		<table id="seller-login" class="seller_table">
			<tr>
			 <td colspan="2">
			 <jsp:include page="/jsp/common/message.jsp"></jsp:include>
			 
			 </td>
			</tr>
			<tr>
				<td><spring:message code="login.username" /></td>
				<td><input type='text' name='j_username' value=''></td>
			</tr>
			<tr>
				<td><spring:message code="login.password" /></td>
				<td><input type='password' name='j_password' /></td>
			</tr>
			<tr>
				<td></td>
				<td>
					<input name="submit" type="submit" value="Login" class="form-button" />
					<!-- ankur: Link for Forgot Password -->
					&nbsp;<a href="<c:url value='/${storeSubDomain}/seller-forgot-password'/>">Forgot Password?</a>
				</td>
			</tr>
		</table>
	
	</form>
</div>
</c:if>
