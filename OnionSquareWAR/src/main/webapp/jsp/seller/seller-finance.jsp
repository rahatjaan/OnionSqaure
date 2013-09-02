<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script type="text/javascript">
   $(function(){
	   changeActiveBar("finance");
	   document.getElementById("filterId").value='${selectedIndex}';
	   
	   $( "#fromDate" ).datepicker();
	   $( "#toDate" ).datepicker();
	 
	   
	   $("#search").click(function(){
		  
		   var fromDate = $("#fromDate").val();
		   var toDate = $("#toDate").val();
	
		   var url = "<c:url value='/seller/view-financial-stat?fromDate="+fromDate+"&toDate="+toDate+"'/>";
		   window.location.replace(url);
		   
	   });
	   
   });
   
   function filterStore(){
	  var filterValue= document.getElementById("filterId").value;
	   var url = "<c:url value='/seller/view-financial-stat?filterCriteria="+filterValue+"'/>";
	   window.location.replace(url);
   }
</script>

<div >
 <h1><spring:message code="seller.finance.detail" /></h1>
  <div class="admin_search_criteria">
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
<div id="view-store"  class="seller_finance">
  
	<table>
		<tr>		
			<td style="font-weight: bold;"><spring:message code="revenue" /></td>
			 <td>$<fmt:formatNumber maxFractionDigits="2" value="${store.revenue}"></fmt:formatNumber> </td>		
		</tr>
		<tr>		
			<td><spring:message code="seller.cost" /></td>
			 <td>$<fmt:formatNumber maxFractionDigits="2" value="${store.onionsquareProfit}"></fmt:formatNumber></td>		
		</tr>
		<tr>		
			<td><spring:message code="seller.profit" /></td>
			 <td>$<fmt:formatNumber maxFractionDigits="2" value="${store.profit}"></fmt:formatNumber></td>		
		</tr>

			
	</table>
</div>
</div>