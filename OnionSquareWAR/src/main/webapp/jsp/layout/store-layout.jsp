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
	href="<spring:url value="/static/css/pagination.css"/>">
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
<!-- <script src="http://gsgd.co.uk/sandbox/jquery/easing/jquery.easing.1.3.js"></script> -->
<script type="text/javascript"
	src="<spring:url value='/static/js/slides.min.jquery.js'/>"></script>	
<script type="text/javascript"
	src="<spring:url value='/static/js/script.js'/>"></script>
<script type="text/javascript" 
		src="<spring:url value="/static/ckeditor/ckeditor.js"/>"></script>
<script type="text/javascript" 
		src="<spring:url value="/static/js/jquery.pagination.js"/>"></script>
<script type="text/javascript"
		src="<spring:url value='/static/js/jTPS.js'/>"></script>
</head>
<body class="landing">
	<div id="fb-root"></div>
	<script>(function(d, s, id) {
		  var js, fjs = d.getElementsByTagName(s)[0];
		  if (d.getElementById(id)) return;
		  js = d.createElement(s); js.id = id;
		  js.src = "//connect.facebook.net/en_GB/all.js#xfbml=1";
		  fjs.parentNode.insertBefore(js, fjs);
		}(document, 'script', 'facebook-jssdk'));
	</script>
	<tiles:insertAttribute name="header" />
	<div class="clear"></div>

	<div id="content" class="store clearfix">
		<!--content starts-->
		<div id="container">

			<div class="store_categories">
				<!--store categories-->
				<div align="right"><h3>Categories</h3></div>
				
					<ul>
						 <li class='active'><a href="<c:url value='/${storeSubDomain}'></c:url>"
								onclick="displayCategory('${category.categoryName}');">All Category</a>
						 </li>
					   
						<c:forEach items="${categoryList}" var="category">
							<li class='active'><a href="<c:url value='/${storeSubDomain}/category/${category.categoryId}'></c:url>"
								onclick="displayCategory('${category.categoryName}');">${category.categoryName}</a>
							</li>
						</c:forEach>
						
						  
						 
					
					</ul>
					<security:authorize access="hasRole('ROLE_SELLER')">
						<div align="right">
							<h3>
								<a href="<c:url value='/seller/seller-home'></c:url>"> <spring:message
										code="seller.home" />
								</a>
							</h3>
						</div>
				   </security:authorize>
				   
				    <security:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')">
				     <div align="right">
							<h3>
								<a href="<c:url value='/admin/view-stores'></c:url>" ><spring:message code="admin.panel" /> </a>
							</h3>
						</div>	
           		
          		    </security:authorize>
					
					<security:authorize access="hasRole('ROLE_CUSTOMER')">
					<div align="right">
					       <h3>
					         <a href="<c:url value='/${storeSubDomain}/customer/customer-view-products'></c:url>">
								<spring:message code="viewed.products" /> 
							 </a>
							</h3>
					</div>
					 </security:authorize>
					 <div align="right"><h3><spring:message code="seller.network" /> </h3></div>
					<ul>
					   	<c:forEach items="${sellerNetworkList}" var="sellerNetwork">
							<li class='active'><a href="<c:url value='/${sellerNetwork.peerStoreUrl}'></c:url>">
								${sellerNetwork.displayName}</a>
							</li>
						</c:forEach>
					
					</ul>
					<c:if test="${empty facebookLink}">
						<div class="fb-like" data-href="${facebookLink}" data-send="true" data-width="10" data-show-faces="true" data-font="lucida grande" style="float:right"></div>		     
				   </c:if>	
			</div>

			<div class="store_products_container clearfix">
				<!-- all products will be within-->
				<div class="cartcontrol clearfix">
					<ul class="breadcrumb">
						<c:forEach var="navigation" items="${navigationMap}">
								<li><a href="<c:url value='${navigation.value}'/>">${navigation.key}</a><span>&rarr;</span></li>							
						</c:forEach>					
					</ul>
					<ul class="user_account clearfix">
						<security:authorize access="hasRole('ROLE_CUSTOMER')">
							<li class="dropdown">My account &#9660;
								<ul class="customer_dropdownMenu">
								  
									<!-- sub category-->
									<li><a href="<c:url value='/${storeSubDomain}/customer/customer-changePassword'></c:url>"><spring:message code="change.password" /></a></li>
									<li><a href="<c:url value='/${storeSubDomain}/customer/edit-customer'></c:url>"><spring:message code="update.profile" /></a></li>	                       						
									<li> <a href="<c:url value='/${storeSubDomain}/customer/customer-order'></c:url>"><spring:message code="view.orders" />  </a></li>
									<li><a href="<c:url value='/${storeSubDomain}/customer/customer-inquiries'></c:url>"><spring:message code="message.inbox" /></a></li>									
									
								 
								</ul>
							</li>
                       </security:authorize>
						<security:authorize
							ifNotGranted="ROLE_CUSTOMER,ROLE_ADMIN,ROLE_SELLER">
							<li class="dropdown" > <spring:message code="signUp" />
							   <ul class="dropdownMenu">
							     <li><a href="<c:url value='/${storeSubDomain}/customer-form' />"><spring:message code="signup.customer" /></a></li>
							     <li><a href="<c:url value='/${storeSubDomain}/seller-form' />"><spring:message code="signup.seller" /></a></li>							     
							   </ul>
							 </li>
							<li class="dropdown"> <spring:message code="login" />
							  <ul class="dropdownMenu">
							     <li><a href="<c:url value='/${storeSubDomain}/customer-login' />"><spring:message code="login.customer" /></a></li>
							     <li><a href="<c:url value='/${storeSubDomain}/seller-login' />"><spring:message code="login.seller" /></a></li>							     
							   </ul>
							
							</li>
						</security:authorize>

						<security:authorize access="hasRole('ROLE_ADMIN')">							
							<li>
								<security:authentication property="principal.username" />
								<span>|</span>
								<a href="<c:url value="/admin/j_spring_security_logout" />"><spring:message code="logout" /></a>
							</li>
						</security:authorize>
						<security:authorize access="hasRole('ROLE_SELLER')">
							<li>
								<security:authentication property="principal.username" />
								<span>|</span>
								<a href="<c:url value="/seller/j_spring_security_logout" />"><spring:message code="logout" /></a>
							</li>
						</security:authorize>
						<security:authorize access="hasRole('ROLE_CUSTOMER')">
							<li>
								<security:authentication property="principal.username" />
								<span>|</span>
								<a href="<c:url value="/customer/j_spring_security_logout?successUrl=/${storeSubDomain}" />"><spring:message code="logout" /></a>
							</li>
						</security:authorize>
						<security:authorize ifNotGranted="ROLE_ADMIN,ROLE_SELLER">
							<li><a href="<c:url value='/${storeSubDomain}/viewcart'/>"
								class="button">Cart (<label id="viewCartId">
								<c:if test="${empty noOfItems}">
									0
								</c:if>
								<c:if test="${not empty noOfItems}">
								  ${noOfItems}
								</c:if>
                               </label>
									)
							</a></li>
						</security:authorize>
					</ul>
				</div>
				<!--cart controls ends-->
				
				<tiles:insertAttribute name="body" />
				
			</div>
			<!-- store_products_container ends-->

		</div>
		<!-- contaienr ends-->

	</div>
	<!--cotnent ends-->

	<div class="clear"></div>

<!-- 	<div class="product_container"> -->

<!-- 		<div id="container"> -->
<!-- 			<div class="features_section clearfix"> -->
<!-- 				<ul class="type_slides"> -->
<!-- 					<li class="clearfix"><a class="first item"> -->
<!-- 							<div class="feature_image"></div> <strong class="clearfix">one -->
<!-- 								suitable line for this feature</strong> -->
<!-- 					</a> <a class="item"> -->
<!-- 							<div class="feature_image"></div> <strong class="clearfix">one -->
<!-- 								suitable line for this feature</strong> -->
<!-- 					</a> <a class="item"> -->
<!-- 							<div class="feature_image"></div> <strong class="clearfix">one -->
<!-- 								suitable line for this feature</strong> -->
<!-- 					</a> <a class="last item"> -->
<!-- 							<div class="feature_image"></div> <strong class="clearfix">one -->
<!-- 								suitable line for this feature</strong> -->
<!-- 					</a></li> -->
<!-- 					<li class="clearfix"><a class="first item"> -->
<!-- 							<div class="feature_image"></div> <strong class="clearfix">one -->
<!-- 								suitable line for this feature</strong> -->
<!-- 					</a> <a class="item"> -->
<!-- 							<div class="feature_image"></div> <strong class="clearfix">one -->
<!-- 								suitable line for this feature</strong> -->
<!-- 					</a> <a class="item"> -->
<!-- 							<div class="feature_image"></div> <strong class="clearfix">one -->
<!-- 								suitable line for this feature</strong> -->
<!-- 					</a> <a class="last item"> -->
<!-- 							<div class="feature_image"></div> <strong class="clearfix">one -->
<!-- 								suitable line for this feature</strong> -->
<!-- 					</a></li> -->

<!-- 				</ul> -->


<!-- 			</div> -->
<!-- 			<!--featrue section ends--> 


<!-- 		</div> -->
<!-- 		<!-- container ends--> 
<!-- 	</div> -->
	<!--products container ends-->
	<tiles:insertAttribute name="footer" />
</body>
</html>