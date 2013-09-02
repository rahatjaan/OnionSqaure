<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<script type="text/javascript">
  $(function(){
	  changeActiveBar("manageCategory");
	  $('#demoTable').jTPS( {perPages:[5],scrollStep:1,scrollDelay:80} );
	  
	  $("#add-category-form").validate({					
			rules : {
				categoryName : {
					required : true
				},
				
				description : {
					required : true
				}			
			},
			messages:{
				categoryName : {
					required : "Category name is required"
				},
				
				description : {
					required : "Description is required"
				}	
				
				
			},
			 highlight: function(element, errorClass) {
			        $(element).css({ border: '1px solid #FF0000' });
			 
			 },
			 unhighlight: function(element){
			        $(element).css({ border: '1px solid #DDDDDD' });

	         },
	        onkeyup: false
	  });
  });  
  
  
  function resetForm(){
	  $("#add-category-form").find("input").val("");
	  return false;
  }

</script>

<div id="add-category-div" class=" store-product store_home_container">
 <h1><spring:message code="store.category.heading" /></h1>
    <security:authorize access="hasRole('ROLE_SELLER')">	
	<form:form id="add-category-form" method="POST" action="category"
		modelAttribute="category">
		    <form:hidden path="store.storeId"/>
			<div id="form_body">
				<table id="categoryTable">
				
				<tr>
					<td colspan="2" align="center"><jsp:include page="/jsp/common/message.jsp"></jsp:include>
					</td>
				</tr>
				<tr class="row">
				       <form:hidden path="categoryId" id="categoryId" />
						<td class="label2"><spring:message code="category.name" /></td>
						<td class="input-field"><form:input path="categoryName"
								id="productName" /></td>
					</tr>
					<tr class="row">
						<td class="label2"><spring:message code="category.description" /></td>
						<td class="input-field"><form:textarea path="description"
								id="unit_price" /></td>
					</tr>
					
					<tr class="row">
					<td></td>
									
						<td>	
							<c:if test="${empty mode}">				  
							    <form:button name="action"  value="save"  class="form-button"><spring:message code="save" /></form:button>
							</c:if>
							<c:if test="${mode=='edit'}">	
		     					<form:button name="action" value="edit"  class="form-button"> <spring:message code="edit" /></form:button>	
		     				</c:if>
								<form:button  onclick="return resetForm();" class="form-button"> <spring:message code="reset" /></form:button>	
						</td>
								
										
                     </tr>
				</table>
			</div>
	
	</form:form>
  </security:authorize>	
</div>
<br/>
<br/>
<c:if test="${not empty categoryList}">

<div id="categoryListDiv" >
	<table id="demoTable" class="store_table">
	  <thead>
		<tr>
		    <th sort="sn"><spring:message code="sn" /></th>
			<th sort="name"><spring:message code="category.name" /></th>
			<th sort="description"><spring:message code="category.description" /></th>
			 <security:authorize access="hasRole('ROLE_SELLER')">				
				<th ><spring:message code="action" />  </th>
			</security:authorize>
		</tr>
      </thead>
      <tbody>
		<c:forEach items="${categoryList}" var="category" varStatus="status">
			<tr>
			    <td>${status.index+1}</td>
				<td>${category.categoryName}</td>
				<td>${category.description}</td>				
				  <security:authorize access="hasRole('ROLE_SELLER')">	
				
					<td>
						<a class="store_link" href="<c:url value='/seller/edit-category'> <c:param  name='categoryId' value='${category.categoryId}'/> </c:url>">
								<spring:message code="edit" />
						</a> <span>/ </span>
						<a class="store_link" onclick="return confirm('Are you sure you want to delete?');"
						href="<c:url value='/seller/delete-category'><c:param name='categoryId' value='${category.categoryId}'/> </c:url>"><spring:message code="delete" /> </a>
				    </td>
				 </security:authorize>
					
				
			</tr>
		</c:forEach>
      </tbody>
      <tfoot class="nav">
			<tr>
				<td colspan="4">
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