<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>



<div id="storeViewDiv">

	<br /> <br />
	<div id="productListDiv">
	

		<c:forEach items="${productsList}" var="entry">
		<div style="clear:both">
		    <a href="<c:url value='/${entry.key}'/>" >${entry.key}</a>
		     <br/>
		     <c:forEach items="${entry.value}" var="product">
		        <div style="float: left;">		        
					<ul>
						<li><img src="<spring:url value="/uploads/${product.imageName}"/>" style="width:100px;height:100px;"/> </li>
						<li>${product.productName}</li>
						<li>${product.description}</li>
						<li>${product.unitPrice}</li>
					</ul>
				</div>
			</c:forEach> 
		</div>
			  
			<br/> <br/><br/><br/><br/><br/> 
		</c:forEach>
		<ul >
			
		</ul>
	</div>
</div>