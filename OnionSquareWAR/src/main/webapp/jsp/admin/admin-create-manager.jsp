<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" >
  $(function(){
	  changeActiveBar("createManager");
	  
	  $("#manager-form").validate({					
			rules : {
				username : {
					required : true
				},
				password : {
					required : true,
		            minlength: 5
				},				
				password1 : {
		            equalTo: "#password"
				},					
				adminEmail :{
					required : true,
					email : true

				}
			},	
				
			messages:{
				username : {
					required : "Username is required"
				},
			
				
				password : {
					required : "Password is required",
		            minlength: "Pasword length should be greater than 5"
				},
				
				password1 : {
		            equalTo: "Confirm password does not match"
				},	
				adminEmail: {
					required : "Email is required",
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
	  $("#manager-form").find("input").val("");
	  $("#manager-form").find("select").val("0");
	  return false;
  }

  </script>


<div id="manager-form-div" class="admin_home_container ">
    <c:if test="${empty mode}">
	<h1><spring:message	code="create.manager" /></h1>
	</c:if>
	<c:if test="${mode=='edit'}">
	<h1><spring:message	code="edit.manager" /></h1>
	</c:if>
	<c:if test="${mode=='edit'}">
	<div class="back-button"><a href="<c:url value='/admin/manager-list'/>" data-icon="&#xe001;" > </a></div>
	</c:if>
	<form:form id="manager-form" method="POST" action="save-manager" modelAttribute="manager" cssStyle="width:100%">
	    <form:hidden path="adminId"/>	   
						        
		<table id="manager-table"  class="admin_table">
			<tr class="row">
			    <td colspan="2" align="center">
			    <jsp:include page="/jsp/common/message.jsp"></jsp:include>
			    </td>
			</tr>
			<tr class="row">
				<td class="label2"><spring:message code="username" /></td>							
				<td class="input-field">
				    <c:if test="${empty mode}">	
				    	<form:input path="username" id="username" />
				    </c:if>	
				    <c:if test="${mode=='edit'}">
				       	<form:input path="username" id="username"  readonly="true"/>
				       
				    </c:if>
				 </td>
				
				
			</tr>

			<tr class="row">
				<td class="label2"><spring:message code="email" /></td>
				<td class="input-field"><form:input path='adminEmail' /></td>
			</tr>		
			<c:if test="${empty mode}">
			<tr class="row">
				<td class="label2"><spring:message code="password" /></td>
				<td class="input-field"><form:password path='password' /></td>
			</tr>

			<tr class="row">
				<td class="label2"><spring:message code="retypePassword" /></td>
				<td class="input-field"><input type="password"  id="password1" name="password1" /></td>
			</tr>
			</c:if>
		
			<tr class="row">
			<td></td>
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
	
	</form:form>
	
</div>