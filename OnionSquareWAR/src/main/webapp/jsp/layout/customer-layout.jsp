<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<link rel="stylesheet" type="text/css"
	href="<spring:url value="/static/css/ui-smoothness/jquery-ui.css"/>" />
<link rel="stylesheet" type="text/css"
	href="<spring:url value="/static/css/main.css"/>">
<link rel="stylesheet" type="text/css"
		href="<spring:url value="/static/css/jTPS.css"/>">	
<link rel="stylesheet" type="text/css"
	href="<spring:url value="/static/css/style.css"/>">
<script type="text/javascript"
	src="<spring:url value="/static/js/jquery-1.9.1.js"/>"></script>
<script type="text/javascript"
	src="<spring:url value="/static/js/jquery-ui-1.10.2.js"/>"></script>
<script type="text/javascript"
	src="<spring:url value="/static/js/jquery.validate.js"/>"></script>
<script type="text/javascript"
	src="<spring:url value="/static/js/jquery.qtip.js"/>"></script>
<script type="text/javascript"
	src="<spring:url value="/static/js/main.js"/>"></script>
<script type="text/javascript"
		src="<spring:url value='/static/js/jTPS.js'/>"></script>

<title>Onion Square</title>
</head>
<body>
	<tiles:insertAttribute name="header" />
	<hr />
	<div class="main_cont">
		<div id="left">
			<ul>
			
				<li><span>Control Panel</span>
					<ul>
						<li><a href="#">Customer Home</a></li>
						<li><a href="<c:url value='/customer/customer-changePassword'><c:param name='customerId' value='${customer.customerId}'/></c:url>">Change Password</a></li>
						<li><a href="<c:url value='/customer/edit-customer'><c:param name='customerId' value='${customer.customerId}'/></c:url>">Edit Profile</a></li>							
                        						
						<li> <a href="<c:url value='/store'></c:url>">View Store </a></li>
											
					</ul>
				</li>
				<li><span>Notification</span>
					<ul>
						<li><a href="">New Products</a></li>
						<li><a href="">Order Placed</a></li>
						<li><a href="">Products View</a></li>
                        						
						
					</ul>
				</li>
			</ul>
		</div>
		<div id="content">

			<tiles:insertAttribute name="body" />
		</div>
	</div>


	<hr />
	<tiles:insertAttribute name="footer" />
</body>
</html>