<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<script type="text/javascript">
$(function() {
	
	changeActiveBar("invitaionProgram");
	
	$("#invitation-form").validate({					
		rules : {
			inviteeEmail : {
				required : true,
				email : true
			}
		},
		messages:{
			inviteeEmail : {
				required : "Invitee email required",
				email : "Please enter valid email"
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
	  $("#invitation-form").find("input").val("");
	  $(".error").text("");
	  $("#invitation-form").find("input").css({ border: '1px solid #DDDDDD' });
	  return false;
}
		

</script>
<br/><br/>
<div   align="center" class="store_home_container invitation-program" >
	<h1 > <spring:message code="seller.invite.friend" /></h1>
	<form:form action="add-invitee" id="invitation-form" method="POST"
		modelAttribute="invitation" >
		<table id="invitaion-program">	
		<tr >
		  <td colspan="2"><jsp:include page="/jsp/common/message.jsp"></jsp:include> </td>
		</tr>		
			<tr class="row">
				<td class="label2"><spring:message code="email" /></td>
				<td class="input-field1"><form:input path='inviteeEmail' /></td>
			</tr>			
			<tr>
			   <td></td>
				<td><form:button name="action" value="save" class="form-button">
							<spring:message code="save" />
						</form:button>
				
				<form:button name="action" value="reset" onclick="return resetForm();" class="form-button">
						<spring:message code="reset" />
					</form:button>
				</td>
			</tr>

			
		</table>
	 </form:form>

</div>