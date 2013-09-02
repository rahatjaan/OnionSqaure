<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page import="com.onionsquare.core.util.OnionSquareConstants"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>


<div id="header">
	<div id="container" class="clearfix">
		<!-- container starts-->
		<div class="logo">
			<img src="<spring:url value='/static/images/logo.png'/>" width="140" />
		</div>
		<ul class="nav">
		
			<li>			   
			    <a href="<c:url value='/${storeSubDomain}'/>"><spring:message	code="home" /></a>			    
			 </li>
			<li><a href="<c:url value='/${storeSubDomain}/seller-profile'/>"><spring:message	code="seller.profile" /></a></li>
			<li><a href="<c:url value='/seller-form'/>"><spring:message code="open.store" /></a></li>
		   	<li><a href="<c:url value='/${storeSubDomain}/store-inquiry'/>"><spring:message	code="contact.seller" /></a></li>
		   
		</ul>
		<div class="quick_call">

			<div></div>
		</div>
		<!-- quick call ends-->
	</div>
	<!-- container ends-->
</div>
<!-- header ends-->

