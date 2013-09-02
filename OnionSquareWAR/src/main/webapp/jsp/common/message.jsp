<%-- Error Messages --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:if test="${not empty errors}">
    <div class="errorMessages" >
        <div class="error">
           <c:out value="${errors}" />          
        </div>
    </div>
    <c:remove var="errors"/>
</c:if>

<%-- Success Messages --%>
<c:if test="${not empty successMessages}">
     <div class="successMessages" >
         <c:out value="${successMessages}" />
     </div>
    <c:remove var="successMessages" scope="session"/>
</c:if>