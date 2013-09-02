<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	
<script type="text/javascript">


	$(function() {	
		
					$("#admin-editForm").validate({					
						rules : {						
							
							username : {
								required : true
							},
							
							adminEmail : {
								required : true,
								email:true
							}
							
						},
						messages:{
							username : {
								required : "Username is required."
								
							},						
							adminEmail : {
								required : "Admin email is required.",
								email: "Admin email is incorrect"
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
	 function resetForm(){
		  $("#admin-editForm").find("input").val("");
		  $("#admin-editForm").find("select").val("0");
		  $(".error").text("");
		  return false;
	  }

	
	
	
</script>


<div id="admin-edit-div"  align="center" class="admin_home_container ">
    <h1>Admin Details</h1>
	
	<form:form action="edit-details" id="admin-editForm" method="POST"
		modelAttribute="admin" >
	
		<form:hidden path="adminId" />
		<table class="admin_table">
			
			<tr class="row">
			    <td colspan="2" align="center">
			    <jsp:include page="/jsp/common/message.jsp"></jsp:include>
			    </td>
			</tr>			
		
			<tr class="row">
				<td class="label2"><spring:message code="username" /></td>
				<td class="input-field"><form:input path='username'/></td>
			</tr>
			<c:if test="${admin.roleName=='ROLE_ADMIN'}">
			<tr class="row">
				<td class="label2"><spring:message code="payPalEmail" /></td>
				<td class="input-field"><form:input path='paypalEmail' /></td>
			</tr>
			<tr class="row">
				<td class="label2"><spring:message code="paypalUsername" /></td>
				<td class="input-field"><form:input path='paypalUsername' /></td>
			</tr>	
			<tr class="row">
				<td class="label2"><spring:message code="paypalPassword" /></td>
				<td class="input-field"><form:input path='paypalPassword' /></td>
			</tr>
			<tr class="row">
				<td class="label2"><spring:message code="paypalSignature" /></td>
				<td class="input-field"><form:input path='paypalSignature' /></td>
			</tr>
			<tr class="row">
				<td class="label2"><spring:message code="paypalAppId" /></td>
				<td class="input-field"><form:input path='paypalAppId' /></td>
			</tr>
			<tr class="row">
				<td class="label2"><spring:message code="paypalMode" /></td>
				<td class="input-field">
				<form:select path='paypalMode' >
					<form:option value="LIVE">LIVE</form:option>
					<form:option value="TEST">TEST</form:option>
				</form:select>
				</td>
			</tr>
				
			</c:if>
			<tr class="row">
				<td class="label2"><spring:message code="email" /></td>
				<td class="input-field"><form:input path='adminEmail' /></td>
			</tr>

						
			<tr >			
			<td></td>		
						
			<td align="right">
				<form:button name="action" value="edit" class="form-button">
					<spring:message code="edit" />
				</form:button>
				<form:button name="action" value="reset" onclick="return resetForm();" class="form-button">
						<spring:message code="reset" />
				</form:button>
			</td>

		</tr>

			
	</table>
	</form:form>

</div>
