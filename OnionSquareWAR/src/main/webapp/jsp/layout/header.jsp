<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.onionsquare.core.util.OnionSquareConstants"%>

<div id="header">
	<div id="pageTitle">
		<h1>Onion Square</h1>
	</div>
	<div id="banner"></div>
	<div >
		<ul class="floatR">
			<security:authorize access="hasRole('ROLE_ADMIN')">
				<span>Logged in as: <strong><security:authentication property="principal.username" /></strong></span>
				<span>&nbsp;|&nbsp;</span>
				<a href="<c:url value="/admin/j_spring_security_logout" />">Logout</a>
			</security:authorize>
			<security:authorize access="hasRole('ROLE_SELLER')">
				<span>Logged in as: <strong><security:authentication property="principal.username" /></strong></span>
				<span>&nbsp;|&nbsp;</span>
				<a href="<c:url value="/seller/j_spring_security_logout" />">Logout</a>
			</security:authorize>
			<security:authorize access="hasRole('ROLE_CUSTOMER')">
				<span>Logged in as: <strong><security:authentication property="principal.username" /></strong></span>
				<span>&nbsp;|&nbsp;</span>
				<a href="<c:url value="/customer/j_spring_security_logout" />">Logout</a>
			</security:authorize>			
		</ul>
	</div>
</div>