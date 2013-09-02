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
<script type="text/javascript"
	src="<spring:url value="/static/js/jquery-1.9.1.js"/>"></script>
 <script type="text/javascript" 
	src="<spring:url value="/static/js/jquery-ui-1.10.2.js"/>"></script> 
<script type="text/javascript"
	src="<spring:url value="/static/js/jquery.validate.js"/>"></script>
 <script type="text/javascript" 
	src="<spring:url value="/static/js/main.js"/>"></script> 
<!-- <script src="http://gsgd.co.uk/sandbox/jquery/easing/jquery.easing.1.3.js"></script> -->
<script type="text/javascript"
	src="<spring:url value='/static/js/slides.min.jquery.js'/>"></script>	
<script type="text/javascript"
	src="<spring:url value='/static/js/script.js'/>"></script>
<script type="text/javascript" 
		src="<spring:url value="/static/ckeditor/ckeditor.js"/>"></script>
</head>
	<body class="landing">  
		<tiles:insertAttribute name="header" />
		<div class="clear"></div><!-- clearing requires because content is floating, -->
		
		<div id="content" class="drawing"><!-- content for content fo all pages-->
			<tiles:insertAttribute name="body" />
		</div>
			<tiles:insertAttribute name="footer" />		
	</body>
</html>
	