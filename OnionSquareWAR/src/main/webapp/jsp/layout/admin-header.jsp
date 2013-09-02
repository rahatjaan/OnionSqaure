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
			  activeMenu = 'inbox';
		$('#'+activeMenu).addClass("active");
	 });
	 
 });
    function changeActiveBar(activeBody){
		 var menuId= $(".active").attr("id");
		 $("#"+menuId).removeClass("active");
		  
    	
    	 if(activeBody=="createManager" || activeBody=="managerList"|| activeBody=="storesFinancialStat")
     		 $("#superAdmin").addClass("active");
    	 else if(activeBody=="guestInquiry" || activeBody=="customerInquiry" || activeBody=="sellerInquiry"|| activeBody=="inquiry"){
    		 $("#inbox").addClass("active");
    	 }else if(activeBody=="manageStores"){
    		 $("#stores").addClass("active");
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
    	<li class="user"><img src="<spring:url value='/static/images/profile.png'/>" />Welcome <security:authentication property="principal.username" />	
    		<ul>
   				<div class="wrap_">
  					<li><a>You are signed in as <span><security:authentication property="principal.username" /></span> </a></li>  					                    
                    <li class="bottomborder"><a href="<c:url value='/admin/admin-changePassword'></c:url>">Change Password</a></li>
                    <li class="bottomborder"><a href="<c:url value='/admin/admin-details'></c:url>">Edit Details</a></li>                    
                    <li><a href="<c:url value="/admin/j_spring_security_logout" />">Sign out</a></li>
    			</div>
   			 </ul>
   		 </li>
        <li class="settings">
           <span data-icon="&#xe008;"></span>
           <ul>
                <div class="wrap_">
<%--                    <li><a href="<c:url value='/seller/edit-store'></c:url>">Update My Store</a></li>						 --%>
<%-- 					<li><a href="<c:url value='/seller/seller-editForm'></c:url>">Update My Profile</a></li> --%>
                    
                </div>
            </ul>
        </li>
    
    
    </ul>
   
    
    
    </div><!--user welcome ends-->


 
 <div class="clear"></div>
 
 <div class="admin_nav clearfix">
      <ul>
         <li><a class="subNav" id="superAdmin"><spring:message	code="super.admin" /></a>         
            <ul>
               	<security:authorize access="hasRole('ROLE_ADMIN')">	                
	            <li><a href="<c:url value='/admin/manager-form'></c:url>"><spring:message	code="create.manager" /></a></li>
				<li><a href="<c:url value='/admin/manager-list'></c:url>"	id="productId"><spring:message	code="manager.list" /></a></li>
				<li><a href="<c:url value='/admin/view-financial-stat'></c:url>"  id="viewProductsId"><spring:message	code="finance.stat" /></a></li>
               </security:authorize>
            </ul>
         </li>
         <li><a class="active subNav" id="inbox"><spring:message	code="inbox" /></a>
         	<ul>
	            <li><a href="<c:url value='/admin/view-guest-inquiries'></c:url>"><spring:message	code="home.page.inquiry" /></a></li>
	            <li><a href="<c:url value='/admin/view-customer-inquiries'></c:url>"><spring:message	code="customer.inquiry" /></a></li>
	            <li><a href="<c:url value='/admin/view-seller-inquiries'></c:url>"><spring:message	code="seller.inquiry" /></a></li>
                <li><a href="<c:url value='/admin/view-admin-inquiries'></c:url>"><spring:message	code="admin.inquiry" /></a></li>
               
            </ul>
         </li>
         <li><a class="subNav" id="stores"><spring:message	code="stores" /></a>	
         	<ul>
          		<li><a href="<c:url value='/admin/view-stores'></c:url>" ><spring:message	code="manager.stores" /></a></li>
            </ul>
          </li>
         
         </ul>
 
 </div><!--admin nav ends-->
</div><!--header ends-->