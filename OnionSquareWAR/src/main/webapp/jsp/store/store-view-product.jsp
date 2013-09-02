<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<script type="text/javascript">
	function addToCart(productName, productId){
		$.ajax({
	        url:"<c:url value='/shoppingCart/additem?productId="+productId+"'/>",
	        type:"GET",
	        data: '',
	        success: function(data){   
	               $("#viewCartId").html(data);
	        },
	        error:function(x,e){
	        
	            if(x.status==0){
	                alert('You are offline!!\n Please Check Your Network.');
	            }else if(x.status==404){
	                alert('Requested URL not found.');
	            }else if(x.status==500){
	                alert('Internel Server Error.');
	            }else if(e=='parsererror'){
	                alert('Error.\nParsing JSON Request failed.');
	            }else if(e=='timeout'){
	                alert('Request Time out.');
	            }else {
	                alert('Unknow Error.\n'+x.responseText);
	            }
	        }
	    });		
	
}
</script>
	<div class="clearfix" id="viewProduct">
	   <h1> ${product.productName}</h1>		  
	   
		<div class="clear"></div>
		<c:forEach items="${product.productImagesName}" var="productImage" varStatus="imageStatus">	
			<div class="item">
				<!-- full item-->
				   
					<div class="item_image">					      
							<img src="<spring:url value="/uploads/${productImage}"/>"
							style="width: 150px; height: 200px;" />
					</div>
					<br/>		
			</div>
			<!-- full item ends-->
		</c:forEach>				
		</div>
		 <a href="<c:url value="/${storeSubDomain}/additem?productId=${product.productId}"></c:url>"  class="add_cart_button">Add To Cart</a>
		 
		<div class="product_description">
		  <h2>$${product.unitPrice}</h2>
		   <br/> 
		  <p> ${product.description} </p> <br/> 
		  	
		</div>