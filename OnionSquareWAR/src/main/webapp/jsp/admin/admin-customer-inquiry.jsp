<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" >
  $(function(){
	  changeActiveBar("customerInquiry");
  });

</script>

  <div id="view-message-div" align="center" class="admin-complain-container">
  <h1><spring:message code="customer.message" /></h1>
		<div class="back-button"><a href="<c:url value='/admin/view-customer-inquiries'/>" data-icon="&#xe001;" > </a></div>

	  <div  class="inquiries">  
	    <c:forEach items="${inquiryList}" var="inquiry">
	
	        <c:if test="${inquiry.senderUserType =='3' }">
	          <div  class="parentInquiry" >	        
	         	<h3>${newInquiry.customer.firstName}</h3> ${inquiry.postedDate}  
	           	<br/>
	            ${inquiry.inquiryContent}
	           
	          </div>
	       </c:if>
	       <c:if test="${inquiry.senderUserType =='2' }">
	         <div class="childInquiry">
	           <h3>You</h3>  ${inquiry.postedDate} 
	              <br/>
	               ${inquiry.inquiryContent}
	         </div>
	         </c:if>
	         <br/>
	         <br/>
	    </c:forEach>   

			 <c:if test="${sender == 'customer'}">
		   
		      <form:form id="adminInquiryForm" method="POST" action="send-reply-customer"
					modelAttribute="newInquiry" cssStyle="width=100%"  cssClass="inquiry-form">	 
 					<form:hidden path="parentInquiry.inqueryId"/>    
		    						 
 							<td class="textarea-field"><form:textarea   path="inquiryContent" class="inquiryContent"/> 
						<form:button name="action" value="save" class="replyButton"> 
												<spring:message code="reply" /> 
 							</form:button>
				</form:form> 
			</c:if>
 			     
	     </div>
	    
	</div>
	
