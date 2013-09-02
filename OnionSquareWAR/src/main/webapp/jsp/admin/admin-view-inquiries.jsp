<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
 $(function(){
	 changeActiveBar("sellerInquiry");
 })
</script>
  <div id="view-message-div" align="center" class="admin-complain-container">
  <h1><spring:message code="message" /></h1>
  <c:if test="${newInquiry.receiverUserType==1}">
	<div class="back-button"><a href="<c:url value='/admin/view-seller-inquiries'/>" data-icon="&#xe001;"></a></div>
  </c:if>
   <c:if test="${newInquiry.receiverUserType==3}">
	<div class="back-button"><a href="<c:url value='/admin/view-customer-inquiries'/>" data-icon="&#xe001;"></a></div>
  </c:if>
   <c:if test="${newInquiry.receiverUserType==4}">
	<div class="back-button"><a href="<c:url value='/admin/view-guest-inquiries'/>" data-icon="&#xe001;"></a></div>
  </c:if>
   <c:if test="${newInquiry.receiverUserType==5 && newInquiry.senderUserType==2}">
	<div class="back-button"><a href="<c:url value='/admin/view-admin-inquiries'/>" data-icon="&#xe001;"></a></div>
  </c:if>
   <c:if test="${newInquiry.receiverUserType==2 && newInquiry.senderUserType==5}">
	<div class="back-button"><a href="<c:url value='/admin/view-admin-inquiries'/>" data-icon="&#xe001;"></a></div>
  </c:if>
	  <div  class="inquiries">  
	    <c:forEach items="${inquiryList}" var="inquiry">
	       <c:if test="${inquiry.receiverUserType != newInquiry.receiverUserType }">
	          <div class="parentInquiry" >	        
	         	<h3>${inquiry.senderName}</h3> ${inquiry.postedDate}  
	           	<br/>
	            ${inquiry.inquiryContent}
	           
	          </div>
	       </c:if>	      
	       <c:if test="${inquiry.receiverUserType == newInquiry.receiverUserType}">
	         <div class="childInquiry">
	           <h3>${inquiry.senderName}</h3>  ${inquiry.postedDate} 
	              <br/>
	               ${inquiry.inquiryContent}
	         </div>
	         </c:if>
	         <br/>
	         <br/>
	    </c:forEach>   
	    
		   
		      <form:form id="adminInquiryForm" method="POST" action="admin-reply"
					modelAttribute="newInquiry" cssStyle="width=100%"  cssClass="inquiry-form">	 
					<form:hidden path="parentInquiry.inqueryId"/>   
 					<form:hidden path="receiverUserId"/> 
 					<form:hidden path="receiverUserType"/> 
 					<form:hidden path="senderUserType"/> 
 					<form:hidden path="receiverEmail"/>     
		    		 <form:hidden path="receiverName"/>   		    						 
 							<td class="textarea-field"><form:textarea   path="inquiryContent" class="inquiryContent"/> 
						<form:button name="action" value="save" class="replyButton"> 
												<spring:message code="reply" /> 
 							</form:button>
				</form:form> 
		
 			     
	     </div>
	    
	</div>
	
