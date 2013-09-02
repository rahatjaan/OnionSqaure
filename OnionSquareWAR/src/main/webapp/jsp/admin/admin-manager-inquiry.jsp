<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
$(function(){
	
	changeActiveBar("guestInquiry");
	
	  $.validator.addMethod("valueNotEqual", function(value, element,arg) {
			return (arg != value);
		}, "Please select Category.");
	
	 $("#adminInquiryForm").validate({					
			rules : {
			
				
				receiverUserId : {
					valueNotEqual:'0'
				},				
				inquiryContent:{
					required: true
	                    
	             }        
				
			},
			messages:{
				
				
				receiverUserId : {
					required : "Select Receiver"
				},
				inquiryContent:{
					required:  "Sender message is required"
	                    
	             }
			},
				 highlight: function(element, errorClass) {
				        $(element).css({ border: '1px solid #FF0000'});
				 
				 },
				 unhighlight: function(element){
				        $(element).css({ border: '1px solid #DDDDDD'});

		         },
		        onkeyup: false
				
			
	 });
	
	
	
});

function resetForm(){
	  $("#adminInquiryForm").find("input").val("");
	  $("#adminInquiryForm").find("textarea").val("");
	  return false;
}
</script>

<div id="admin-inquiry"  class="  store_home_container reset-margin inquiry">
	<h1 >Contact Admin</h1>
	<div class="back-button"><a href="<c:url value='/admin/view-admin-inquiries'/>" data-icon="&#xe001;"></a></div>
	
	<form:form id="adminInquiryForm" method="POST" action="send-admin-inquiry"
		modelAttribute="inquiry" cssStyle="width=100%"  cssClass="inquiry-form">
	      <form:hidden path="inqueryId" id="inqueryId" />
	      <form:hidden path="senderUserType" />		
	      <form:hidden path="senderUserId"  />
	      <form:hidden path="senderName" />		      		
	      <form:hidden path="senderEmail" />		      		
			   
	    <div id="form_body">				        
			<table>			
				<tr>
					<td colspan="2" align="center"><jsp:include
							page="/jsp/common/message.jsp"></jsp:include></td>
				</tr>
				<tr >			
					<td class="label2"><spring:message code="receiver" /></td>
					<td class="input-field">
						<form:select id="receiverId"
							path="receiverUserId" cssClass="text_white">
							<form:option value="0" label="Select" />
							<form:options items="${adminList}" itemValue="adminId"
								itemLabel="username" />
						</form:select>
					</td>
				</tr>
				<tr>				
					<td class="label2"><spring:message code="inquiry.message" /></td>
					<td class="input-field"><form:textarea path="inquiryContent"
							id="inquiryContent" /></td>
				</tr>
				<tr><td></td><td>
					<form:button name="action" value="send"  class="form-button">
									<spring:message code="send" />
					 </form:button>
					 <form:button name="action" value="reset" onclick="return resetForm();"  class="form-button">
								<spring:message code="reset" />
					 </form:button>
					 </td>
				</tr>
			</table>
		</div>
	</form:form>
</div>