<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<script type="text/javascript" >
  $(function(){	  
	  changeActiveBar("sellerNetwork");
	  
	   $('#demoTable').jTPS( {perPages:[5]} );

	  $.validator.addMethod("valueNotEqual", function(value, element,arg) {
			return (arg != value);
		}, "Please select Category.");
	  
	  
	  $("#addSellerToNetwork").validate({					
			rules : {
				productName : {
					required : true
				},
				
				unitPrice : {
					required : true,
				    number: true
				},				
				"category.categoryId":{
	                    valueNotEqual:'0'
	                    
	                }
				<c:if test="${empty mode}">
				,
	             "productImageFiles[0]":{
	                	required : true
	                }
	             </c:if>
				
			},
			messages:{
				productName : {
					required : "Product name is required"
				},
				
				unitPrice : {
					required : "Product price is required",
					number : "Price should be number"
				}
				<c:if test="${empty mode}">
				 , "productImageFiles[0]":{
	                	required : "Prodct image is required"
	                }
				  </c:if>
				
				
				
			},
			 highlight: function(element, errorClass) {
			        $(element).css({ border: '1px solid #FF0000'});
			 
			 },
			 unhighlight: function(element){
			        $(element).css({ border: '1px solid #DDDDDD'});

	         },
	        onkeyup: false
	  });
  });
  
  function resetForm(){
	  $("#addSellerToNetwork").find("input").val("");
	  $("#addSellerToNetwork").find("select").val("0");
	  return false;
  }

  </script>
  <br/><br/>
<div id="store-network-div"  class=" store-product store_home_container reset-margin ">
	<h1><spring:message code="seller.network.heading" /></h1>
	
	<form:form id="addSellerToNetwork" method="POST" action="updated-sellerNetwork"
		modelAttribute="sellerNetwork" cssStyle="width=100%"  cssClass="product-form">
	      <form:hidden path="seller.sellerId"
							id="sellerId" />
		  <form:hidden path="displayName"
							 />
		<div id="form_body">				        
			<table id="productTable">
			   
				<tr>
					<td colspan="2" align="center"><jsp:include
							page="/jsp/common/message.jsp"></jsp:include></td>
				</tr>
				<tr >
				    <form:hidden path="sellerNetworkId" id="sellerNetworkId" />
				   
					<td class="label2"><spring:message code="store.url" /></td>
					
					<c:if test="${ empty mode}">					
						<td> www.onionsquare.com/ <form:input path="peerStoreUrl"
							id="peerStoreUrl" style="width:90px"/></td>
					</c:if>
					<c:if test="${mode=='edit'}">
						<td>www.onionsquare.com/<form:input path="peerStoreUrl"
							id="peerStoreUrl" readonly="true" class="url-input-field"/></td>
					</c:if>
					
					
				</tr>

				<tr >
					<td class="label2"><spring:message code="store.status" /></td>
					<td class="input-field"><form:select id="status"
							path="status" cssClass="text_white">
							<form:option value="true" label="Active" />
					       <form:option value="false" label="Inactive" />
						</form:select></td>
				</tr>

				<tr >
				   <td></td>
				   <td >
				     <security:authorize access="hasRole('ROLE_SELLER')">
						<c:if test="${empty mode}">
							<form:button name="action" value="save"  class="form-button">
									<spring:message code="save" />
								</form:button>
						</c:if>
						<c:if test="${mode=='edit'}">
							<form:button name="action" value="edit"  class="form-button">
									<spring:message code="edit" />
								</form:button>
							
						</c:if>
					     <form:button name="action" value="reset" onclick="return resetForm();"  class="form-button">
								<spring:message code="reset" />
						</form:button>
						</security:authorize>
					</td>

				</tr>
			</table>
		</div>
	
	</form:form>
	
</div>

<br/> <br/>
<c:if test="${not empty sellerNetworkList}">
<div id="sellerNetworkListDiv" >
	<table id="demoTable" class="store_table">
       <thead>
			<tr>
			   <th><spring:message code="sn" /></th>
	<%-- 			<td><spring:message code="store.name" /></td> --%>
				<th sort="url"><spring:message code="store.url" /></th>
				<th sort="name"><spring:message code="store.name" /></th>
				<th sort="status"><spring:message code="status" /></th>
				<security:authorize access="hasRole('ROLE_SELLER')">
					<th>Update </th>
				</security:authorize>
	    	</tr>
       </thead>
        <tbody>
		<c:forEach items="${sellerNetworkList}" var="sellerNetwork" varStatus="status">
			<tr>
			    <td>${status.index+1}</td>			    
<%-- 				<td>${sellerNetwork.productName}</td> --%>
				<td>${sellerNetwork.peerStoreUrl}</td>
				<td>${sellerNetwork.displayName}</td>
				<c:if test="${sellerNetwork.status=='true'}">
				  <td><spring:message code="active" />
				</c:if>
				<c:if test="${sellerNetwork.status=='false'}">
				  <td><spring:message code="inactive" /></td>
				</c:if>
				<security:authorize access="hasRole('ROLE_SELLER')">
				<td><a class="store_link" href="<c:url value='/seller/edit-sellerNetwork'><c:param name='sellerNetworkId' value='${sellerNetwork.sellerNetworkId}'/></c:url>"> <spring:message code="edit"/> </a>
				<span>/</span><a class="store_link" onclick="return confirm('Are you sure you want to delete?');" href="<c:url value='/seller/delete-sellerNetwork'><c:param name='sellerNetworkId' value='${sellerNetwork.sellerNetworkId}'/></c:url>" ><spring:message code="delete" /></a>
				</td>
               </security:authorize>
			</tr>
		</c:forEach>
		</tbody>
		<tfoot class="nav">
			<tr>
				<td colspan="7">
					<div class="pagination"></div>
					<div class="paginationTitle">Page</div>
					<div class="selectPerPage"></div>
					<div class="status"></div>
				</td>
			</tr>
		 </tfoot>
	</table>
</div>
</c:if>
