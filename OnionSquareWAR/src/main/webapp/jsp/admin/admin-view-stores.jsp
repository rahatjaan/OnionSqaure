<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<script type="text/javascript">
   $(function(){
	   changeActiveBar("manageStores");	  
	   $('#demoTable').jTPS( {perPages:[5],scrollStep:1,scrollDelay:80} );

   });
</script>

<div id="view-store" class="admin-view-stores">
 <h1><spring:message code="stores" /></h1>
  <div align="center"><jsp:include page="/jsp/common/message.jsp"></jsp:include></div> 

<div id="view-store">
  
	<table id="demoTable" class="store_table">
		<thead>
			<tr>
				<th><spring:message code="sn" /></th>			
				<th><spring:message code="store.name" /></th>		
				<th><spring:message code="view.panel" /></th>
				<th><spring:message code="view.store" /></th>	
				<th><spring:message code="contact.seller" /> </th>
			</tr>
	     </thead>
	     <tbody>
			<c:forEach items="${storeList}" var="store" varStatus="status">
				<tr>
				    <td>${status.index+1}</td>
					<td>${store.storeName}</td>		
					<td><a class="store_link" href="<c:url value='/admin/store-home'><c:param name='storeId' value='${store.storeId}'/></c:url>" > <spring:message code="view.panel"/> </a></td>	
					<td><a class="store_link" href="<c:url value='/${store.subDomainName}'></c:url>" > <spring:message code="view.store"/> </a></td>	
												
					<td><a class="store_link" href="<c:url value='/admin/contact-seller'><c:param name='storeId' value='${store.storeId}'/></c:url>" > <spring:message code="contact.seller"/> </a></td>	
				</tr>
			</c:forEach>
		  </tbody>
		 <tfoot class="nav">
				<tr>
					<td colspan="5">
						<div class="pagination"></div>
						<div class="paginationTitle">Page</div>
						<div class="selectPerPage"></div>
						<div class="status"></div>
					</td>
				</tr>
		  </tfoot>
	</table>
</div>
</div>