<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>


  <div id="view-message-div" align="center" class="admin-complain-container">
  <h1><spring:message code="messages" /></h1>
  <div class="back-button"><a href="<c:url value='/seller/seller-inquiries'/>" data-icon="&#xe001;"></a></div>
   <div class="message"><jsp:include page="/jsp/common/message.jsp"></jsp:include></div>
  
	  <div  class="inquiries">  
	    <c:forEach items="${inquiryList}" var="inquiry">
	       <c:if test="${inquiry.senderUserType =='1' }">
	          <div class="parentInquiry" >	        
	         	<div class="name"> ${inquiry.senderName}</div><div><fmt:formatDate value="${inquiry.postedDate}" type="both"/></div>
	           	<br/>
	            ${inquiry.inquiryContent}
	           
	          </div>
	       </c:if>	      
	       <c:if test="${inquiry.senderUserType !='1' }">
	         <div class="childInquiry">
	           <div class="name">${newInquiry.receiverName}</div><div> <fmt:formatDate value="${inquiry.postedDate}" type="both"/></div>
	              <br/>
	               ${inquiry.inquiryContent}
	         </div>
	         </c:if>
	         <br/>
	         <br/>
	    </c:forEach>      
	  
		   <security:authorize access="hasRole('ROLE_SELLER')">	 
		      <form:form id="adminInquiryForm" method="POST" action="send-reply"
					modelAttribute="newInquiry" cssStyle="width=100%"  cssClass="inquiry-form">	 
 					<form:hidden path="parentInquiry.inqueryId"/>   
 					<form:hidden path="receiverUserId"/> 
 					<form:hidden path="receiverUserType"/> 
 					<form:hidden path="receiverEmail"/>     
		    		 <form:hidden path="receiverName"/>     
		    				  		 
 							<td class="textarea-field"><form:textarea   path="inquiryContent" class="inquiryContent"/> 
						<form:button name="action" value="save" class="replyButton"> 
												<spring:message code="reply" /> 
 							</form:button>
				</form:form> 
		   </security:authorize>
 			     
	     </div>
	    
	</div>