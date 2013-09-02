<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">

$(function(){
	
	changeActiveBar("guestInquiry");
	
	 $("#adminInquiryForm").validate({					
			rules : {
				senderName : {
					required : true
				},				
				senderEmail : {
					required : true
				},				
				inquiryContent:{
					required: true	                    
	            }  			
			},
			messages:{
				senderName : {
					required : "Sender name  is required"
				},				
				senderEmail : {
					required : "Sender email is required"
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
	  $("#message").html("");
	  return false;
}
</script>

<div id="admin-inquiry"  class="quote_home_container seller_login">
	<h1 >Contact Admin</h1>
	
	<c:if test="${ not empty guest}">	
		<div class="back-button"><a href="<c:url value='/admin/view-guest-inquiries'/>" data-icon="&#xe001;"></a></div>
	</c:if>
		
	<form:form id="adminInquiryForm" method="POST" action="send-admin-inquiry" modelAttribute="inquiry" cssStyle="width=100%"  cssClass="inquiry-form">	   
			<table class="seller_table">			
				<tr>
					<td colspan="2" align="center" id="message"><jsp:include
							page="/jsp/common/message.jsp"></jsp:include></td>
				</tr>
				<c:if test="${mode!='sender_email_disabled'}">
				
				<tr>
				    <form:hidden path="inqueryId" id="inqueryId" />
				
					<td class="label2"><spring:message code="inquiry.name" /></td>
					<td class="input-field"><form:input path="senderName"
							id="senderName" /></td>
				</tr>
				<tr >
					<td class="label2"><spring:message code="inquiry.email" /></td>
					<td class="input-field"><form:input path="senderEmail"
							id="senderEmail" /></td>
				</tr>
			</c:if>
				
				<tr>				
					<td class="label2"><spring:message code="inquiry.message" /></td>
					<td class="input-field"><form:textarea path="inquiryContent"
							id="inquiryContent" /></td>
				</tr>
				<tr><td></td><td>
				
				<c:if test="${empty guest &&  mode!='button_disabled' }">
					<form:button name="action" value="send"  class="form-button">
									<spring:message code="send" />
					 </form:button>
					 <form:button name="action" value="reset" onclick="return resetForm();"  class="form-button">
								<spring:message code="reset" />
					 </form:button>
			   </c:if>
					 </td>
				</tr>
			</table>
	</form:form>
</div>