<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<script type="text/javascript">
$(function() {	
	
	$("#admin-forgot-password-form").validate({					
		rules : {
			username : {
				required : true
			}
		},
		messages:{
			username : {
				required : "Username is required"
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

<div class="quote_home_container admin_login" >
	<h1>Admin Forgot Password</h1>
	
	<form name='f' action="<c:url value='/admin-forgot-password' />" method='POST' id="admin-forgot-password-form">
		<table class="admin_table">
			<tr>
				<td colspan="2"><jsp:include page="/jsp/common/message.jsp"></jsp:include></td>
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