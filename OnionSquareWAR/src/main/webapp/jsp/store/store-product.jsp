<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://ckeditor.com" prefix="ckeditor" %>
<%@ taglib prefix="security"  uri="http://www.springframework.org/security/tags"%>
<script type="text/javascript" >
  $(function(){
	  

	  
	  changeActiveBar("manageProduct");
	  
	  $('#demoTable').jTPS( {scrollStep:1,scrollDelay:80
       });
	  
	  // reinstate sort and pagination if cookie exists
      var cookies = document.cookie.split(';');
      for (var ci = 0, cie = cookies.length; ci < cie; ci++) {
              var cookie = cookies[ci].split('=');
              if (cookie[0] == 'jTPS') {
                      var commands = cookie[1].split(',');
                      for (var cm = 0, cme = commands.length; cm < cme; cm++) {
                              var command = commands[cm].split(':');
                              if (command[0] == 'sortasc' && parseInt(command[1]) >= 0) {
                                      $('#demoTable .sortableHeader:eq(' + parseInt(command[1]) + ')').click();
                              } else if (command[0] == 'sortdesc' && parseInt(command[1]) >= 0) {
                                      $('#demoTable .sortableHeader:eq(' + parseInt(command[1]) + ')').click().click();
                              } else if (command[0] == 'page' && parseInt(command[1]) >= 0) {
                                      $('#demoTable .pageSelector:eq(' + parseInt(command[1]) + ')').click();
                              }
                      }
              }
      }
      

      // bind mouseover for each tbody row and change cell (td) hover style
      $('#demoTable tbody tr:not(.stubCell)').bind('mouseover mouseout',
              function (e) {
                      // hilight the row
                      e.type == 'mouseover' ? $(this).children('td').addClass('hilightRow') : $(this).children('td').removeClass('hilightRow');
              }
      );




		

	  $.validator.addMethod("valueNotEqual", function(value, element,arg) {
			return (arg != value);
		}, "Please select Category.");
	  
	  
	  $("#addProductForm").validate({					
			rules : {
				productName  : {
					required : true
				},
				tagLine : {
					required  : true,
					maxlength : 15
				},
				unitPrice : {
					required : true,
				    number   : true
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
					required  : "Product name is required"
					
					
				},
				tagLine : {
					required   : "Tagline is required",
					maxlength  : "Tagline should be less than 15 characters"
				},
				unitPrice : {
					required : "Product price is required",
					number   : "Price should be number"
				},
				"category.categoryId":{
                    valueNotEqual:"Select Category"
                    
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
	  $("#addProductForm").find("input").val("");
	  $("#addProductForm").find("select").val("0");
	  return false;
  }

  </script>
  <br/><br/>
<div id="store-product"  class=" store-product store_home_container reset-margin ">
	<h1 ><spring:message code="product.heading" /></h1>
	<security:authorize access="hasRole('ROLE_SELLER')">	
	<form:form id="addProductForm" method="POST" action="product"
		modelAttribute="Product" cssStyle="width=100%" enctype="multipart/form-data" cssClass="product-form">
	    <form:hidden path="store.storeId"/>	
	    
		<div id="form_body">				        
			<table id="productTable">
			
				<tr>
					<td colspan="2" align="center"><jsp:include
							page="/jsp/common/message.jsp"></jsp:include></td>
				</tr>
				<tr >
				    <form:hidden path="productId" id="productId" />
				
					<td class="label2"><spring:message code="product.name" /></td>
					<td class="input-field"><form:input path="productName"
							id="productName" /></td>
				</tr>
				<tr >
					<td class="label2"><spring:message code="product.price" />  $</td>
					<td class="input-field"><form:input path="unitPrice"
							id="unit_price" /></td>
				</tr>
				<c:if test="${empty mode}">
				<tr >
					<td class="label2"><spring:message code="product.image" /></td>
					<td class="input-field">
						<input type="file" name="productImageFiles[0]" />
					</td> 
				</tr>
				</c:if>
				<tr >
					<td class="label2"><spring:message code="product.tagline" />  </td>
					<td class="input-field"><form:textarea path="tagLine"
							id="tag_line" /></td>
				</tr>
				<tr >
					<td class="label2"><spring:message code="product.description" /></td>
					<td class="input-field"><form:textarea path="description"
 							id="description" />
 							<script type="text/javascript">
    							CKEDITOR.replace( 'description' ,{
    								height: 200,
    						        width: 350
    							});
							</script>
					</td>
					
				</tr>
				<tr >
					<td class="label2">Category</td>
					<td class="input-field"><form:select id="categoryId"
							path="category.categoryId" cssClass="text_white">
							<form:option value="0" label="Select" />
							<form:options items="${categoryList}" itemValue="categoryId"
								itemLabel="categoryName" />
						</form:select></td>
				</tr>

				<tr >
				   <td></td>
				   
				   
					   <td>
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
						</td>
                   
				</tr>
			</table>
		</div>
	
	</form:form>
   </security:authorize>
</div>

<br/> <br/>
<c:if test="${not empty productList}">
<div id="productListDiv" >
	<table id="demoTable" class="store_table">
      	<thead>
			<tr>
			    <th sort="sn"><spring:message code="sn" /></th>			   
				<th sort="name"><spring:message code="product.name" /></th>
				<th sort="price"><spring:message code="product.price" /></th>
				<th sort="category">Category</th>
				 <security:authorize access="hasRole('ROLE_SELLER')">				
					<th><spring:message code="action" />  </th>
					<th> <spring:message code="images" />  </th>
				</security:authorize>
	
			</tr>
       </thead>
       <tbody>
		<c:forEach items="${productList}" var="product" varStatus="status">
			<tr>
			    <td>${status.index+1}</td>
				<td>${product.productName}</td>
				<td>$ ${product.unitPrice}</td>
				<td>${product.category.categoryName}</td>
				<security:authorize access="hasRole('ROLE_SELLER')">				
					<td><a class="store_link" href="<c:url value='/seller/edit-product'><c:param name='productId' value='${product.productId}'/></c:url>"> <spring:message code="edit"/> </a>
					<span>/</span><a class="store_link" onclick="return confirm('Are you sure you want to delete?');" href="<c:url value='/seller/delete-product'><c:param name='productId' value='${product.productId}'/></c:url>" ><spring:message code="delete" /></a>
					</td>
					<td><a  class="store_link" href="<c:url value='/seller/store-product-images'><c:param name='productId' value='${product.productId}'/></c:url>"> <spring:message code="manage.images"/> </a></td>
                </security:authorize>
			</tr>
		</c:forEach>
		</tbody>
		<tfoot class="nav">
			<tr>
				<td colspan="6">
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
