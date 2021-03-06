<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	
	<link rel="stylesheet" type="text/css"
		href="<spring:url value="/static/css/ui-smoothness/jquery-ui.css"/>" />
	<link rel="stylesheet" type="text/css"
		href="<spring:url value='/static/css/style.css'/>">
	<link rel="stylesheet" type="text/css"
		href="<spring:url value="/static/css/main.css"/>">
	<link rel="stylesheet" type="text/css"
		href="<spring:url value="/static/css/jTPS.css"/>">	
	<script type="text/javascript"
		src="<spring:url value="/static/js/jquery-1.9.1.js"/>"></script>
	<script type="text/javascript"
		src="<spring:url value="/static/js/jquery-ui-1.10.2.js"/>"></script>
	<script type="text/javascript"
		src="<spring:url value="/static/js/jquery.validate.js"/>"></script>
	<script type="text/javascript"
		src="<spring:url value="/static/js/main.js"/>"></script>
<!-- 	<script src="http://gsgd.co.uk/sandbox/jquery/easing/jquery.easing.1.3.js"></script> -->
	<script type="text/javascript"
		src="<spring:url value='/static/js/slides.min.jquery.js'/>"></script>
	<script type="text/javascript"
		src="<spring:url value='/static/js/script.js'/>"></script>
    <script type="text/javascript" 
		src="<spring:url value="/static/ckeditor/ckeditor.js"/>"></script>
	<script type="text/javascript"
		src="<spring:url value='/static/js/jTPS.js'/>"></script>
</head>
<body class="admin">
	<tiles:insertAttribute name="header" />
	<div class="subnav_area"></div>

	<div class="clear"></div>
	<div id="content">
    
	    <div class="admin_leftpane">
	        
	        <ul>
	        	 <li><a href="<c:url value='/admin/view-guest-inquiries'></c:url>"><spring:message	code="home.page.inquiry" /></a></li>
	            <li><a href="<c:url value='/admin/view-customer-inquiries'></c:url>"><spring:message	code="customer.inquiry" /></a></li>
	            <li><a href="<c:url value='/admin/view-seller-inquiries'></c:url>"><spring:message	code="seller.inquiry" /></a></li>
                <li><a href="<c:url value='/admin/view-admin-inquiries'></c:url>"><spring:message	code="admin.inquiry" /></a></li>
               
	        </ul>
	    
	    </div>
	    <div class="admin_content">
	        <tiles:insertAttribute name="body" />
		</div>	 
		<div style="height:500px"></div>   
		

	</div><!--content ends-->		
	
	<tiles:insertAttribute name="footer" />
</body>
</html>