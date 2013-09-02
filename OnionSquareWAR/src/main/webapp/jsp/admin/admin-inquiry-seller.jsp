<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
$(function(){
	
	   changeActiveBar("manageStores");
	
	 $("#adminInquiryForm").validate({					
			rules : {
							
				inquiryContent:{
					required: true
	                    
	             }        
				
			},
			messages:{
			
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
	<h1 >Contact Seller</h1>
	<div class="back-button"><a href="<c:url value='/admin/view-stores'/>" data-icon="&#xe001;" > </a></div>
	<form:form id="adminInquiryForm" method="POST" action="send-inquiry-seller"
		modelAttribute="inquiry" cssStyle="width=100%"  cssClass="inquiry-form">	   
	    <div id="form_body">
	       <form:hidden path="receiverUserId"/>	    				        
			<table>			
				<tr>
					<td colspan="2" align="center"><jsp:include
							page="/jsp/common/message.jsp"></jsp:include></td>
				</tr>
			     <tr >
				    <form:hidden path="inqueryId" id="inqueryId" />
				
					<td class="label2"><spring:message code="inquiry.name" /></td>
					<td class="input-field"><form:input path="receiverName"
							id="receiverName" /></td>
				</tr>
				<tr >
					<td class="label2"><spring:message code="inquiry.email" /></td>
					<td class="input-field"><form:input path="receiverEmail"
							id="receiverEmail" /></td>
				</tr>
				<tr>				
					<td class="label2"><spring:message code="inquiry.message" /></td>
					<td class="input-field"><form:textarea path="inquiryContent"
							id="inquiryContent" /></td>
				</tr>
				<tr><td></td>
				   <td>
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