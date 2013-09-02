<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" >
  $(function(){
	  
	  $('#addFile').click(function() {
	        var fileIndex = $('#productTable tr').length - 5;
	        $(    '<tr><td></td><td>'+
	                '   <input type="file" name="productImageFiles['+ fileIndex +']" />'+
	                '</td></tr>').insertBefore($('#uploadButtonId').parent());
	    });
	  
	  $(".item_image").mouseover(function(){
	        $(this).find(".cross").show();
	    });
	  
	  $(".item_image").mouseout(function(){
		  $(this).find(".cross").hide();
	    });
	  
	  
	    
    })
    </script>

<div id="addProductDiv" align="center">
	<h2><spring:message code="store.product.images" /></h2> 
	<div class="back-button"><a href="<c:url value='/seller/store-product'/>" data-icon="&#xe001;"></a></div>
	 
	
	<form:form id="addProductForm" method="POST" action="edit-product-images"
		modelAttribute="product" cssStyle="width=100%"
		enctype="multipart/form-data">
		<form:hidden path="store.storeId" />
		<form:hidden path="category.categoryId"/>

		<div id="form_body">
			<table id="productTable">

				<tr>
					<td colspan="2" align="center">
						<jsp:include page="/jsp/common/message.jsp"></jsp:include>
					</td>
				</tr>
				<tr>
					<form:hidden path="productId" id="productId" />
				</tr>
				<tr>				
					<td ><spring:message code="product.name" /></td>
					<td ><form:input path="productName"
							id="productName" disabled="true" /></td>
				</tr>
				<tr >
					<td ><spring:message code="product.price" /></td>
					<td ><form:input path="unitPrice"
							id="unit_price" disabled="true" /></td>
				</tr>
				<tr>
					<td><spring:message code="product.image" /></td>
					<td><input type="file" name="productImageFiles[0]" /><span><input id="addFile"
						type="button" value="Add File" class="form-button"/></span></td>
				</tr>
				<tr>
				   
					<td></td>
				
				    <td id="uploadButtonId"  align="center"><form:button name="action" value="save" class="form-button">
								<spring:message code="save" />
							</form:button>
					</td>
					
				</tr>

			</table>
		</div>
	</form:form>
	<div class="store_products_container clearfix">
		<div class="clear"></div>
		<c:forEach items="${product.productImagesName}" var="productImage" varStatus="imageStatus">
	
			<div class="item">
				<!-- full item-->
				   
					<div class="item_image">
					       <c:if test="${imageStatus.index!=0}">
					        <a class="cross" data-icon="&#xe005;" style="display:none;float:right;" href="<c:url value='/seller/delete-product-image'><c:param name='imageName' value='${productImage}'/><c:param name='productId' value='${product.productId}'/></c:url>"></a>
							</c:if>
							<img src="<spring:url value="/uploads/${productImage}"/>"
							style="width: 150px; height: 200px;" />
					</div>
					<br/>
					
				
			</div>
			<!-- full item ends-->
		</c:forEach>
		</div>
	</div>