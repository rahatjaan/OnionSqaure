<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<script type="text/javascript" >
  $(function(){	  
	  changeActiveBar("sellerProfile");	
	  
	  $( "#upload-image" ).dialog({
		  autoOpen: false,
		  height: 300,
		  width: 350
	  });
	  $( "#change_profile").click(function() {
	  $( "#upload-image" ).dialog( "open" );
	  });
	  
	  $('#upload-image').on('dialogclose', function( event, ui ){
		 	
		  window.location.reload(true);      
      });
  });
  
  function openLink(url){
	  var win=window.open(url, '_blank');
	  win.focus();
  }
 </script>

<div id="seller-profile" class="seller-profile" >
	<div style="padding-left:350px;"><h1><spring:message code="seller.profile" /></h1> </div>
	        			
		
			  <div style="float:left; 	margin-left: 100px;	margin-right: 100px;">
			  	
			   <img src="<spring:url value="/uploads/seller/${seller.sellerId}.jpg"/>"
											style="width: 150px; height: 150px;" />
				<br/>
				<security:authorize access="hasRole('ROLE_SELLER')">
				  <c:if test="${not empty seller_profile}">
					<a href="#" id="change_profile"><spring:message code="change.profile" /></a>
				</c:if>
				</security:authorize>
			  </div>
			  <ul class="social rightfloat">
			                <c:if test="${not empty store.facebookLink}" >       
							<li class="facebook"><a onclick="openLink('${store.facebookLink}');"><img src="<spring:url value='/static/images/facebook.jpg'/>" style="width: 40px; height: 40px;"/></a></li>
							</c:if>
							<c:if test="${not empty store.twitterLink}" >
							<li class="youtube"><a  onclick="openLink('${store.twitterLink}');"><img src="<spring:url value='/static/images/twitter.jpg'/>" style="width: 40px; height: 40px;"/></a></li>
							</c:if>
							<c:if test="${not empty store.linkedinLink}" >
							<li class="youtube"><a  onclick="openLink('${store.linkedinLink}');"><img src="<spring:url value='/static/images/linkedin.jpg'/>" style="width: 40px; height: 40px;"/></a></li>
							</c:if>
			 </ul>
			<table>
			  <tr class="row">				    
				    <td> <jsp:include page="/jsp/common/message.jsp"></jsp:include></td>
				    <td> </td>
			   </tr>
			   <tr class="row">
				    <td><h2><spring:message code="basic.info" /></h2> </td>
				    <td></td>
			    </tr>
				<tr class="row">
					<td  class="label3"><spring:message code="name" /></td>
					<td> ${seller.fullName} </td>
				</tr>
	
				
				<tr class="row">
					<td class="label3"><spring:message code="email" /></td>
					<td>${seller.username}</td>
				</tr>
										
				<tr class="row">
				     <td colspan="2"><h2><spring:message code="address.detail" /> </h2></td>
				     <td></td>
				</tr>		
				
				<tr class="row">
					<td class="label3"><spring:message code="country" /></td>
					<td >
					 <c:if test="${seller.country.countryId !=1}"> ${seller.country.countryName}</c:if>
					</td>
				</tr>
				
				<tr class="row">
					<td class="label3"><spring:message code="state" /></td>
					<td >
						<c:if test="${seller.state.stateId !=1}"> ${seller.state.stateName} </c:if></td>
				</tr>
				
				<tr class="row">
					<td class="label3"><spring:message code="city" /></td>
					<td >${seller.cityName}</td>
				</tr>
						
			</table>
			<div align="left" style="padding-left:330px;margin:15px;" >				
 			<br/><br/>		
			<h2><spring:message code="store.overview" /></h2>
			<div class="store_description">	${store.storeDescription}</div>
 			<br/><br/>				
			<h2><spring:message code="shipping.reFunding.policy" /></h2>
			<div class="store_description" >${store.shippingRefundPolicy}</div>
				
			</div>
								
			
		

</div>


	<div id="upload-image" title="<spring:message code='seller.picture'/>" style="display:none">
	
	   <form:form id="profilePictureForm" method="POST" action="save-profile-picture"
			modelAttribute="store" cssStyle="width=100%" enctype="multipart/form-data" cssClass="product-form">
			<form:hidden path="storeId"/> 
				<input type="file" name="sellerPicture" />
				<form:button name="action" value="upload" class="form-button"  >
								<spring:message code="upload" />
				 </form:button>
			</form:form>
	</div>
