<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="clear"></div>

<div id="footer" class="clearfix">
    <div id="container">
    <div class="leftfloat sixty_width">
     <ul class="nav">
    			<li class="first"><a href="<c:url value="/${storeSubDomain}" />"> Home</a></li>
  			    <li><a href="<c:url value='/${storeSubDomain}/onionsquare-terms-of-use'   />">Terms of Use</a></li>
  			    <li><a href="<c:url value='/${storeSubDomain}/onionsquare-privacy-policy' />">Privacy Policy</a></li>  			    
      </ul>
    </div>
    <div class="rightfloat forty_width">
            <div class="logo"><img src="<spring:url value='/static/images/logo.png'/>" width="90" />
            </div>            
            <div class="clear"></div>
            <strong class="copyrights">Produced and Operatd by Onion Square</strong>
		</div>
    <div class="clear"></div>
    
    </div><!--container ends-->
</div><!--footer ends-->
