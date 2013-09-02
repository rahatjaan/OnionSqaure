<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript">
  $(function(){
	  changeActiveBar("stores");

  });

</script>

<div id="storeViewDiv">
<%-- 	<form:form id="storeViewForm" method="GET" action="viewProduct" --%>
<%-- 		modelAttribute="Category" cssStyle="width=100%"> --%>
<!-- 		<table> -->
<!-- 			<tr class="row"> -->
<!-- 				<td class="label2">Category</td> -->
<%-- 				<td class="input-field"><form:select id="categoryId" --%>
<%-- 						path="categoryId" cssClass="text_white" --%>
<%-- 						onchange="this.form.submit()"> --%>
<%-- 						<form:option value="0" label="Select" /> --%>
<%-- 						<form:options items="${categoryList}" itemValue="categoryId" --%>
<%-- 							itemLabel="categoryName" /> --%>
<%-- 					</form:select></td> --%>
<!-- 			</tr> -->
<!-- 		</table> -->
<%-- 	</form:form> --%>

	<br /> <br />
	<div id="productListDiv" class="CSSTableGenerator">
		<ul >
			<c:forEach items="${productList}" var="product">
				<li >
					<ul>
					
						<li><img src="<spring:url value="/uploads/${product.imageName}"/>" style="width:100px;height:100px;"/> </li>
						<li>${product.productName}</li>
						<li>${product.description}</li>
						<li>${product.unitPrice}</li>
						
					</ul>
				</li>

			</c:forEach>
		</ul>
	</div>
</div>