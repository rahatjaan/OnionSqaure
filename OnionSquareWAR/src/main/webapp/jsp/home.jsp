<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<script type="text/javascript" >

  $(function(){
	  var a= '${showSignUp}' ;
	  if(a=="showSignUp")
	     {
		  $("#seller-signUp").removeClass("hideLogin");
	       $("#seller-signUp").addClass("showLogin");
	       $("#seller-Login").removeClass("showLogin");
		     $("#seller-Login").addClass("hideLogin");
		  
	     }
	  
  });
  function showSellerLogin( showDiv, hideDiv){
	
	  $(".error").text("");
	  $("#seller-form").find("input").val("");
	  $("#seller-form").find("select").val("0");
	  $("#seller-login-form").find("input[type=text]").val("");
	  $("#seller-login-form").find("input[type=password]").val("");


	 
	if ($('#'+showDiv).hasClass("showLogin"))
		{
			$('#'+showDiv).removeClass("showLogin");
			$('#'+showDiv).addClass("hideLogin");

		}
		else{
			$('#'+showDiv).removeClass("hideLogin");
			$('#'+showDiv).addClass("showLogin");		
		}
	if($("#"+hideDiv).hasClass("showLogin"))
	{
		$('#'+hideDiv).removeClass("showLogin");
	    $('#'+hideDiv).addClass("hideLogin");

		
	}

	}
</script>




<a href="#" onclick="showSellerLogin('seller-Login','seller-signUp');">Seller Login</a>
<label style=""> / </label>
<a href="#" id="sellerSignUp" onclick="showSellerLogin('seller-signUp','seller-Login');">Seller SignUp</a>
<br />
<jsp:include page="/jsp/common/message.jsp"></jsp:include>
<br />
<div id="seller-Login" class="showLogin">
	<jsp:include page="/jsp/seller/seller-login.jsp" />
</div>
<div id="seller-signUp" class="hideLogin">
	<jsp:include page="/jsp/seller/seller-form.jsp" />
</div>
<div>
 </div>




