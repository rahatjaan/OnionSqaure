<%@ taglib prefix="security"  uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript">

	function signUpForm(){
		window.location.replace("<c:url value='/seller-form'/>");
		return false;
		 
	 }

</script>
<div class="quote_home_container"><!-- only for home page concept-->
        <div id="container" class="index_middle_container">
       <div><jsp:include page="/jsp/common/message.jsp"></jsp:include></div> 
        <h1>Selling Online Made Easy & Affordable </h1>
        <span style="font-size:25px;">Try www.onionsquare.com/storename
	        <security:authorize ifNotGranted="ROLE_CUSTOMER,ROLE_ADMIN,ROLE_SELLER">
	             <input type="submit"  value="Open your store today" class="signUp_button" onclick="signUpForm();"/>     
	        </security:authorize>
        </span>
        </div><!-- container ends-->
 </div><!--quote_home ends-->