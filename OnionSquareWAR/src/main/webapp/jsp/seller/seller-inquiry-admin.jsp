<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<script type="text/javascript">
$(function(){
	 $("#sellerInquiryForm").validate({					
			rules : {								
				inquiryContent:{
					required: true	                    
	             }        
				
			},
			messages:{			
				inquiryContent:{
					required:  "Message is required"
	                    
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
	  $("#sellerInquiryForm").find("input").val("");
	  $("#sellerInquiryForm").find("textarea").val("");
	  return false;
}
</script>

<div id="seller-inquiry"  class="  store_home_container reset-margin inquiry">
	<h1><spring:message code="send.to.admin" /></h1>
		<div class="back-button"><a href="<c:url value='/seller/seller-inquiries'/>" data-icon="&#xe001;"></a></div>
	
	<form:form id="sellerInquiryForm" method="POST" action="send-seller-inquiry"
		modelAttribute="inquiry" cssStyle="width=100%"  cssClass="inquiry-form">	   
	    <div id="form_body">
	       <form:hidden path="inqueryId" id="inqueryId" />	
		   <form:hidden path="receiverUserId"/>	    				        
		   <form:hidden path="parentInquiry.inqueryId" /> 
		   <form:hidden path="receiverUserType"/>	
		   <form:hidden path="receiverName"/>					        
		   <form:hidden path="receiverEmail"/>			        
			<table>			
				<tr>
					<td colspan="2" align="center"><jsp:include
							page="/jsp/common/message.jsp"></jsp:include></td>
				</tr>
		
				<tr>				
					<td class="label2"><spring:message code="inquiry.message" /></td>
					<td class="input-field"><form:textarea path="inquiryContent"
							id="inquiryContent" /></td>
				</tr>
				<tr><td></td>
				  <security:authorize access="hasRole('ROLE_SELLER')">				 
					<td>
						<form:button name="action" value="save"  class="form-button">
										<spring:message code="send" />
						 </form:button>
						 <form:button name="action" value="reset" onclick="return resetForm();"  class="form-button">
									<spring:message code="reset" />
						 </form:button>
					 </td>
				  </security:authorize>
				</tr>
			</table>
		</div>
	</form:form>
</div>