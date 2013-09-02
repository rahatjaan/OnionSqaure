<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>


<script type="text/javascript">
$(function(){
	if('${topSeller}'=='1')
		$("#seller").val('1');
	$("#seller").bind('change', function() {
	    var categoryId = 0;
		if($("#seller").val()=='1'){
			<c:if test="${not empty categoryId}">
				categoryId ='${categoryId}';
			</c:if>
			  window.location.replace("<c:url value='/${storeSubDomain}/topSeller/"+categoryId+"'/>");
		}
	});	
    $(".page_numbers").pagination('${productListSize}', {
    	items_per_page:8,
    	num_display_entries:8,
    	callback: loadProducts
    });
    
    


});

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
	function loadProducts(pageNo, jq){
       var pageSize = 8;
       var categoryId =0;
       if('${categoryId}'!='')
    	   categoryId = '${categoryId}';
    	   
    		   
			    $.ajax({
					   headers: { "Content-Type": "application/json", "Accept": "application/json" },
					   type:"GET",
					   url: "<c:url value='/products/"+pageNo+"/"+pageSize+"/${store.storeId}/"+categoryId+"'/>",
					   data: {"topSeller":$("#seller").val()},
					   dataType: "json",
					   success: function(productList){				  
							if (productList) {
								productPaginationResult(productList,0,productList.length,pageNo,pageSize);
							}  			   
			           
					   } ,
					     error:function(x,e){
					         
// 					        alert("Ajax error"+x+e);
					        }
					   });
    	   
	   
	    // Prevent click eventpropagation
	    return false;
	}

function displayCategory(categoryName){
	
	$("#categoryId").html(categoryName);
}

 function productPaginationResult(productList,startIndex,endIndex,pageNo,pageSize){
	 var newcontent ='';
	 for (var i=startIndex;i<endIndex;i++) {	
			var product = productList[i];
	        newcontent += '<div class="item">';	   
	        newcontent += '<a href="<c:url value="/${storeSubDomain}/product/'+product['productId']+'"></c:url>">';
	        newcontent += '<div class="item_image">';
	        newcontent += '<img src="<spring:url value="/uploads/'+product['imageName']+'"/>" style="width: 100px; height: 100px;" />';
	        newcontent += '</div>';
	        newcontent += '</a>';
	        newcontent += '<h3 class="item_name">'+product['productName']+'</h3>';
	        newcontent += '<ul class="item_specs">';
	        newcontent += '<li>'+product['tagLine']+'</li>';
	        newcontent += '</ul>';
	        newcontent += '<p class="item_price">$'+product['unitPrice']+'</p>';
	        newcontent += '<span style="padding-left:10px;	padding-bottom: 5px;">';
	        newcontent += '<a href="<c:url value="/${storeSubDomain}/additem?productId='+product['productId']+'"></c:url>"  class="add_cart_button">Add To Cart</a>';		
	        newcontent += '</span>';
	        newcontent += '</div>';							
		}
	    pageNo = pageNo + 1;
        var totalPages = Math.ceil('${productListSize}'/ pageSize);
		$("#productContainer").html(newcontent);
		$(".range").empty();
		$(".range").html(pageNo+"-"+pageNo*pageSize);
		$(".value").empty();
		$(".value").html(totalPages);  
 }

</script>


<h1>
	<label id="categoryId">${categoryName}</label>
</h1>
<div class="store_pages clearfix bottomborder">
	<!-- pagination for store-->
	<form>
		<label>Sort By: <select id="seller"><option value="0">Select</option><option value="1">Top Sellers</option></select>
		</label>
	</form>
	
	<ul class="page_numbers">
		<span class="leftfloat">Pages:</span>

	</ul>
	<div class="page_viewing">
		<span>Viewing</span>
		<!-- i keep them different for changing colors-->
		<span class="range">1-4</span> <span>Of</span> <span class="value"></span>
		<span>Results</span>

	</div>
</div>
<!-- paginations ends-->


<div class="clear"></div>
<div align="center"><jsp:include page="/jsp/common/message.jsp"></jsp:include></div>
 <c:if test="${empty productList}">
 	<div class="item" align="center">Store Owner has not added any product to this store.
 	</div>
 </c:if>
 <div id="productContainer">
	<c:forEach items="${productList}" var="product">
		<div class="item">
			<!-- full item-->
			
			<a href="<c:url value='/${storeSubDomain}/product/${product.productId}'></c:url>">
				<div class="item_image">
					<img src="<spring:url value="/uploads/${product.imageName}"/>"
						style="width: 100px; height: 100px;" />
				</div>
			</a>
			<h3 class="item_name">${product.productName}</h3>
			<ul class="item_specs">
				<li>${product.tagLine}</li>							
			</ul>
			<p class="item_price">$ ${product.unitPrice}</p>
			
			<a href="<c:url value="/${storeSubDomain}/additem?productId='+product['productId']+'"></c:url>"  class="add_cart_button">Add To Cart</a>
		</div>
		<!-- full item ends-->
	</c:forEach>
</div>

<div class="clear"></div>

<div class="store_pages clearfix topborder">
	<ul class="page_numbers">
		<span class="leftfloat">Pages:</span>
		
	</ul>

	<div class="page_viewing">
		<span>Viewing</span> <span class="range">1-8</span> <span>Of</span> <span
			class="value">10</span> <span>Results</span>
	</div>

</div>
<!-- store pages ends-->

<div class="clear"></div>

</div>







