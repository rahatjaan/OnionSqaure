<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<script type="text/javascript">
$(function() {
	$("#customer-forgot-password-form").validate({
		rules : {
			username: {				
				required : true
			}
		},
		messages :{
			username:{
				required : "Username is required."
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

<div id="customer-forgot-password-div" class="quote_home_container reset-margin login-form">
	<h1><spring:message code="forgotpassword.header" /></h1>

	<form name='f' action="<c:url value='/${storeSubDomain}/get-customer-forgot-password' />" method='POST' id="customer-forgot-password-form">
		<table>
			<tr class="row">
		 		<td colspan="2" align="center">
		 			<jsp:include page="/jsp/common/message.jsp"></jsp:include>
		 		</td>
			</tr>
			<tr>
				<td><spring:message code="login.username" /></td>
				<td><input type='text' name='username' value=''></td>
			</tr>
			<tr>
		    	<td></td>
				<td>
					<input name="submit" type="submit" value="Submit" class="form-button"/>
				</td>
			</tr>
		</table>
	</form>
</div>