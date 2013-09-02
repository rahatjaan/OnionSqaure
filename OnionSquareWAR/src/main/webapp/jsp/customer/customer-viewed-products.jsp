<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<div class="clear"></div>
<div align="center"><jsp:include page="/jsp/common/message.jsp"></jsp:include></div>
 <c:if test="${empty productList}">
 	<div class="item" align="center">Store Owner has not added any product to this store.
 	</div>
 </c:if>
<c:forEach items="${productList}" var="product">

	<div class="item">
		<!-- full item-->
		<a>
			<div class="item_image">
				<img src="<spring:url value="/uploads/${product.imageName}"/>"
					style="width: 100px; height: 100px;" />
			</div>
			<h3 class="item_name">${product.productName}</h3>
			<ul class="item_specs">
				<li>${product.description}</li>
				<li><a href="#"
					onclick="addToCart('${product.productName}','${product.productId}')"
					style="text-decoration: underline">Add To Cart</a></li>
			</ul>
			<p class="item_price">${product.unitPrice}</p>
		</a>
	</div>
	<!-- full item ends-->
</c:forEach>
