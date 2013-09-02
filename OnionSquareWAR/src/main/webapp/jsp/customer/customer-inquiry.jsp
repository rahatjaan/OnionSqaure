<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
$(function(){
	 $("#customerInquiryForm").validate({					
			rules : {								
				inquiryContent:{
					required: true	                    
	             },
	             receiverName:{
	            	 required: true
	             },
	             receiverEmail:{
	            	 required: true
	             }
				
			},
			messages:{
			    receiverName:{
		            	 required: "Receiver name is required."
		             },
		        receiverEmail:{
		            	 required: "Receiver email is required."
		             },
				inquiryContent:{
					required:  "Sender message is required."
	                    
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
	  $("#inquiryContent").val("");
	  $("#inquiryContent").css({ border: '1px solid #DDDDDD'});
	  $(".error").html("");
	  return false;
}
</script>

<div id="customer-inquiry"  class="store_home_container reset-margin inquiry">
	<h1 ><spring:message code="message" /></h1>
	<div class="back-button"><a href="<c:url value='/${storeSubDomain}/customer/customer-inquiries'/>" data-icon="&#xe001;"></a></div>
	
	<form:form id="customerInquiryForm" method="POST" action="send-customer-inquiry"
		modelAttribute="inquiry" cssStyle="width=100%"  cssClass="inquiry-form">
		  <form:hidden path="inqueryId" id="inqueryId" />	
		  <form:hidden path="receiverUserId"/>	    				        
		  <form:hidden path="parentInquiry.inqueryId" /> 
		   <form:hidden path="receiverUserType"/>	    				        
		  	   
	      <div id="form_body">				        
			<table>			
				<tr>
					<td colspan="2" align="center"><jsp:include
							page="/jsp/common/message.jsp"></jsp:include></td>
				</tr>
				<tr>
					<td class="label2"><spring:message code="receiverName" /></td>
					<td class="input-field"><form:input path="receiverName"
							id="receiverName"  readonly="true"/></td>
				</tr>
				<tr >
					<td class="label2"><spring:message code="receiverEmail" /></td>
					<td class="input-field"><form:input path="receiverEmail"
							id="receiverEmail" readonly="true"/></td>
				</tr>
				<tr>				
					<td class="label2"><spring:message code="inquiry.message" /></td>
					<td class="input-field"><form:textarea path="inquiryContent"
							id="inquiryContent" /></td>
				</tr>
				<tr><td></td><td>
					<form:button name="action" value="save"  class="form-button">
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