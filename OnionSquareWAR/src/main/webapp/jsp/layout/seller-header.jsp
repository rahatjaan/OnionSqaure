<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<script type="text/javascript">
 var activeMenu ;
 $(function(){
	 $('.subNav').click(function(){
         var loadUrl= $(this).next().find("a:first").attr("href");
		 window.location.replace(loadUrl);	 
	 });
	 
	 $('.subNav').mouseover(function(){
		$(".active").removeClass("active");
		 $(this).addClass("active");
	 });
	 
	 $('.admin_nav').mouseleave(function(){
		 var menuId= $(".active").attr("id");
		 $("#"+menuId).removeClass("active");
		 if(activeMenu =='')
			  activeMenu = 'storeDesk';
		$('#'+activeMenu).addClass("active");
	 });

     
 });
    function changeActiveBar(activeBody){
		 var menuId= $(".active").attr("id");
		 $("#"+menuId).removeClass("active");
		  
    	
    	 if(activeBody=="invitaionProgram" || activeBody=="invitaion list" || activeBody == "sellerNetwork")
     		 $("#sellerNetwork").addClass("active");
    	 else if(activeBody=="orderHistory" || activeBody=="orderDetail" || activeBody=="finance"){
    		 $("#orderCustomer").addClass("active");
    	 }else if(activeBody=="manageProduct" || activeBody=="manageCategory"||activeBody=="stores"|| activeBody =="sellerProfile"){
    		 $("#storeDesk").addClass("active");
    	 }
    	
		 $(".admin_leftpane").find("ul:first").empty();
		 $(".active").next().find("li").each(function(index){
		 $(".admin_leftpane").find("ul:first").append("<li>"+$(this).html()+"</li>");
		 });
		 activeMenu = $(".active").attr("id");
   }
</script>
<div id="header">
 <div class="logo"><img src="<spring:url value='/static/images/logo.png'/>" width="110" /></div>
    
    <div class="user_welcome">
    <ul>
    	<li class="user"><img src="<spring:url value='/static/images/profile.png'/>" />Welcome  <security:authorize access="hasRole('ROLE_SELLER')"> ${sellerName}</security:authorize><security:authorize access="hasAnyRole('ROLE_ADMIN' ,'ROLE_MANAGER')"><security:authentication property="principal.username" /></security:authorize>	
    		<ul>
   				<div class="wrap_">
  					<li><a>You are signed in as <span><security:authentication property="principal.username" /></span> </a></li> 
  					 <security:authorize access="hasRole('ROLE_SELLER')">	 					                    
                    	<li class="bottomborder"><a href="<c:url value='/seller/seller-changePassword'></c:url>" id="productId"><spring:message code="change.password" /></a></li>
                    	<li><a href="<c:url value="/seller/j_spring_security_logout" />"><spring:message code="logout" /></a></li>
                    </security:authorize>
    			</div>
   			 </ul>
   		 </li>
        <li class="settings">
           <span data-icon="&#xe008;"></span>
           <ul>
                <div class="wrap_">
                 <security:authorize access="hasRole('ROLE_SELLER')">	
                    <li><a href="<c:url value='/seller/edit-store'></c:url>"><spring:message code="update.store" /></a></li>	
                   	<li><a href="<c:url value='/seller/seller-editForm'></c:url>"><spring:message code="seller.edit.form" /></a></li>
                  </security:authorize>                   					
                   <li><a href="<c:url value='/seller/seller-inquiries'></c:url>"><spring:message code="message.inbox" /></a></li>
                    
                </div>
            </ul>
        </li>
    
    
    </ul>
   
    
    
    </div><!--user welcome ends-->


 
 <div class="clear"></div>

 <div class="admin_nav clearfix">
      <ul>
         <li><a class=" active subNav" id="storeDesk"><spring:message code="store.desk" /></a>         
            <ul>
	            <li><a href="<c:url value='/seller/store-category'></c:url>"><spring:message code="manage.categories" /></a></li>
				<li><a href="<c:url value='/seller/store-product'></c:url>"	id="productId"><spring:message code="manage.products" /></a></li>
<%-- 				<c:if test="${not empty storeSubDomain}"> --%>
					<li><a href="<c:url value='/${storeSubDomain}'></c:url>"  id="viewProductsId"><spring:message code="my.store" /></a></li>
<%-- 				</c:if> --%>
				<li><a href="<c:url value='/seller/seller-profile' ></c:url>" id="sellerProfileId"><spring:message code="seller.profile" /></a></li>
				
            </ul>
         </li>
         <li><a class="subNav" id="orderCustomer"><spring:message code="orders.customers" /></a>
         	<ul>
	            <li><a href="<c:url value='/seller/store-order'></c:url>"><spring:message code="order.histories" /></a></li>
	            <li><a href="<c:url value='/seller/store-current-order'></c:url>"><spring:message code="current.orders" /></a></li>
	            <li><a href="<c:url value='/seller/view-financial-stat'></c:url>"><spring:message code="seller.finance" /></a></li>  
            </ul>
         </li>
         <li><a class="subNav" id="sellerNetwork"><spring:message code="seller.network" /> </a>	
         	<ul>
         	    <security:authorize access="hasRole('ROLE_SELLER')">	
          		   <li><a href="<c:url value='/seller/invitation-program'></c:url>" id="inviteId"><spring:message code="invite.friends" /></a></li>
				</security:authorize>
				<li><a href="<c:url value='/seller/invitation-list'></c:url>" id="inviteListId"><spring:message code="invitation.list" /></a></li>
				<li><a href="<c:url value='/seller/sellerNetwork'></c:url>" id="sellerNetworkId"><spring:message code="my.square" /></a></li>
            </ul>
          </li>
            <security:authorize access="hasAnyRole('ROLE_ADMIN' ,'ROLE_MANAGER')">	
           		<li><a href="<c:url value='/admin/view-stores'></c:url>"  class="subNav" id="adminPanel" ><spring:message code="admin.panel" /> </a> </li>	
           </security:authorize>
          
         </ul>
 
 </div><!--admin nav ends-->
</div><!--header ends-->