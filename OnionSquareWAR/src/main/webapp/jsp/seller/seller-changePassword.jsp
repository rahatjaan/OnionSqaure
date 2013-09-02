<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	
<script type="text/javascript">


	$(function() {	
		
					$("#seller-changePassword-form").validate({					
						rules : {						
							
							oldPassword : {
								required : true
							},
							
							password : {
								required : true,
					            minlength: 5
							},
							
							password1 : {
					            equalTo: "#password"
							}
							
						},
						messages:{
							oldPassword : {
								required : "Old password is required."
							},
							
							password : {
								required : "Enter Password",
					            minlength: "Pasword Length should be greater than 5"
							},
							
							password1 : {
					            equalTo: "Confirmation password does not match"
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
		  $("#seller-changePassword-form").find("input").val("");
		  $("#seller-changePassword-form").find("select").val("0");
		  $(".error").text("");
		  return false;
	  }

	
	
	
</script>


<div id="seller-changePassword"  align="center" class="store_home_container seller_changePassword">
<h1><spring:message code="seller.change.password" /></h1>
	
	<form:form action="changePassword" id="seller-changePassword-form" method="POST"
		modelAttribute="seller" >
	
		<form:hidden path="sellerId" />
		<table>
			
			<tr class="row">
			    <td colspan="2" align="center">
			    <jsp:include page="/jsp/common/message.jsp"></jsp:include>
			    </td>
			</tr>			
		
<!-- 			<tr class="row"> -->
<%-- 				<td class="label2"><spring:message code="username" /></td> --%>
<%-- 				<td class="input-field"><form:input path='username' disabled="true" /></td> --%>
<!-- 			</tr> -->
			<tr class="row">
				<td class="label2"><spring:message code="oldPassword" /></td>
				<td class="input-field"><form:password path='oldPassword' /></td>
			</tr>	
			
			<tr class="row">
				<td class="label2"><spring:message code="password" /></td>
				<td class="input-field"><form:password path='password' /></td>
			</tr>

			<tr class="row">
				<td class="label2"><spring:message code="retypePassword" /></td>
				<td class="input-field"><input type="password"  id="password1" name="password1" /></td>
			</tr>
			
			
			<tr >			
			<td></td>		
						<td align="right"><form:button name="action" value="edit" class="form-button">
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
