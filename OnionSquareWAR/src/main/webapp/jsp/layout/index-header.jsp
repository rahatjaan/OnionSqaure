<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
	
<div id="header"> <!-- header is far full width -->
	<div id="container" class="clearfix"><!-- container deciding width for page, for mobile devices , we can make it adjust as per our need,
    clearfix is important-->
        <div class="logo"><img src="<spring:url value='/static/images/logo.png'/>" width="140" /></div><!-- logo is floating , and code in CSS are in groups for each page-->
       <!-- as per client demand, you can make any heading tag including wording with leftfloat-->
     
     <!-- u can make ul to appear if you want-->
       <ul class="nav">
        <li><a href="<c:url value="/" />">Home</a></li>
    
        <li><a href="<c:url value="/onionsquare-description" />">what is onionsquare? </a></li>
      
        <li><a href="<c:url value="/onionsquare-work" />">how it works</a></li>
		
       </ul>
        <div class="quick_call">
          
           			 <security:authorize ifNotGranted="ROLE_CUSTOMER,ROLE_ADMIN,ROLE_SELLER,ROLE_MANAGER">
           			          <c:if test="${empty sellerLogin}">
            					<span><a href="<c:url value='/seller-login'></c:url>">Log In</a></span>
            				  </c:if>
                    </security:authorize>
                   	<security:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')">							
						<security:authentication
									property="principal.username" />
						<span>|</span>
						<a href="<c:url value="/admin/j_spring_security_logout" />">Logout</a>
					</security:authorize>
					<security:authorize access="hasRole('ROLE_SELLER')">
						<security:authentication
									property="principal.username" />
						<span>|</span>
						<a href="<c:url value="/seller/j_spring_security_logout" />">Logout</a>
					</security:authorize>
					<security:authorize access="hasRole('ROLE_CUSTOMER')">
						<security:authentication
									property="principal.username" />
						<span>|</span>
						<a href="<c:url value="/customer/j_spring_security_logout?successUrl=/${storeSubDomain}" />">Logout</a>
					</security:authorize>
					<a href="<c:url value='/admin-inquiry'/>">Feedback</a>
<%-- 					<a href="<c:url value='/customer-form'/>">Customer Signup</a> --%>
					
                 </p>
                 
                 
        </div>
    </div><!-- container ends-->
</div><!-- header ends-->