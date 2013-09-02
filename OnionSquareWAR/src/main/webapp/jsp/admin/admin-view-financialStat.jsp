<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<script type="text/javascript">
   $(function(){
	   changeActiveBar("storesFinancialStat");
	   document.getElementById("filterId").value='${selectedIndex}';
	   
	   $('#demoTable').jTPS( {perPages:[5],scrollStep:1,scrollDelay:80} );

	   
	   $( "#fromDate" ).datepicker();
	   $( "#toDate" ).datepicker();
	 
	   
	   $("#search").click(function(){
		  
		   var fromDate = $("#fromDate").val();
		   var toDate = $("#toDate").val();
	
		   var url = "<c:url value='/admin/view-financial-stat?fromDate="+fromDate+"&toDate="+toDate+"'/>";
		   window.location.replace(url);
		   
	   });
	   
   });
   
   function filterStore(){
	  var filterValue= document.getElementById("filterId").value;
	   var url = "<c:url value='/admin/view-financial-stat?filterCriteria="+filterValue+"'/>";
	   window.location.replace(url);
   }
</script>

<div id="view-store" > 
  <div class="admin_search_criteria">
    <h1><spring:message code="stores.revenue" /></h1>
   
  <table>
    <tr>
      <td> <spring:message code="from.date" /> </td> <td><input id="fromDate" value="${fromDate}"/></td>
      <td> <spring:message code="to.date" /> </td> <td><input id="toDate" value="${toDate}"/></td>
      <td>  </td> <td><button  id="search" class="form-button"><spring:message code="search" /></button></td>
    </tr>
    
  </table>
 
      <spring:message code="search.criteria" />
	   <select id="filterId" onchange="filterStore();">
	     <option value="0"> Select </option>
	     <option value="2"> Last 7 days  </option>
	     <option value="3"> This Month   </option>
	     <option value="4"> Previous Month   </option>
	   </select>
 </div>
<div id="view-store">
  
	<table id="demoTable" class="store_table">
	   <thead>
		<tr>
		    <th><spring:message code="sn" /></th>		
			<th><spring:message code="store.name" /></th>		
			<th><spring:message code="store.url" /></th>
			<th><spring:message code="revenue" /></th>	
			<th><spring:message code="profit" /> </th>
		</tr>
	   </thead>
       <tbody>
			<c:forEach items="${storeList}" var="store" varStatus="status">
				<tr>
				    <td>${status.index+1}</td>
					<td>${store.storeName}</td>		
					<td>www.onionsquare.revenuecom/onionsquare/${store.subDomainName}</td>	
					<td>$${store.revenue}</td>	
					<td>$${store.onionsquareProfit}</td>	
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
</div>