<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript">
	$(function() {

		$.validator.addMethod("valueNotEqual", function(value, element, arg) {
			return (arg != value);
		});

		$("#customer-form").validate({
			rules : {
				firstName : {
					required : true
				},

				lastName : {
					required : true
				},

				userName : {
					required : true
				},
				email : {
					required : true,
					email : true
				},

				password : {
					required : true,
					minlength : 5
				},

				password1 : {
					equalTo : "#password"
				},
				phone : {
					required : true
				},
				termsAndCondition : {
					required : true
				},
				"shippingAddress.cityName" : {
					required : true
				},
				"shippingAddress.country.countryId" : {
					valueNotEqual : '1'

				}

			},
			messages : {
				firstName : {
					required : "First name is required"
				},

				lastName : {
					required : "Last name is required"
				},

				userName : {
					required : "Username is required",
				},
				emailId : {
					required : "Email is required",
					email : "Please enter valid email"

				},

				password : {
					required : "Password is required",
					minlength : "Pasword Length should be greater than 5"
				},

				password1 : {
					equalTo : "Confirmation email does not match"
				},

				termsAndCondition : {
					required : "Please accept Terms and Conditions"
				},
				phone : {
					required : "Phone number is required"
				},
				"shippingAddress.cityName" : {
					required : "City name is required"
				},

				"shippingAddress.country.countryId" : {
					valueNotEqual : "Select a country"
				}

			},

			highlight : function(element, errorClass) {
				$(element).css({
					border : '1px solid #FF0000'
				});

			},
			unhighlight : function(element) {
				$(element).css({
					border : '1px solid #DDDDDD'
				});

			},
			onkeyup : false,
		});

		$("#shippingCountryId").bind('change', function() {
			countryChange("1");
		});

	});

	function resetForm() {

		$("#customer-form").find("input").val("");
		$("#customer-form").find("select").val("0");
		$(".error").text("");
		return false;
	}

	function countryChange(addressType) {
		var countryId;

		if (addressType == "1")
			countryId = $("#shippingCountryId").val();

		$.ajax({
			headers : {
				"Content-Type" : "application/json",
				"Accept" : "application/json"
			},
			type : "GET",
			url : "<c:url value='/stateList'/>",
			data : {
				"countryId" : countryId
			},
			dataType : "json",
			success : function(data) {

				var html = '';
				if (countryId != '1') {
					html = "<option value='1'>Select</option>";
				}
				if (data) {
					for (p in data) {

						html += "<option value='" + p + "'>" + data[p]
								+ "</option>";

					}
				}
				if (addressType == "1") {
					$("#shippingStateId option").remove();
					$("#shippingStateId").append(html).trigger("change");
				}

			},
			error : function(x, e) {

				alert("Ajax error");
			}
		});
	}
</script>


<div id="customer-signUp-div"
	class="quote_home_container reset-margin customer_signup_index">

	<form:form action="save-customer" id="customer-form" method="POST"
		modelAttribute="customer">
		<form:hidden path="customerId" />
		<form:hidden path="shippingAddress.addressId" />
		<table>
			<tr>
				<td colspan="2">
					<h1>
						<c:if test="${empty mode}">
							<spring:message code="customer.signup.form" />
						</c:if>
						<c:if test="${mode =='edit'}">
							<spring:message code="customer.edit.form" />

						</c:if>
					</h1>
				</td>
			</tr>
			<tr class="row">
				<td colspan="2" align="center"><jsp:include
						page="/jsp/common/message.jsp"></jsp:include></td>
			</tr>
			<tr class="row">
				<td colspan="2"><h3>
						<spring:message code="personalDetails" />
					</h3></td>
				<td></td>
			</tr>
			<tr class="row">
				<td class="label2"><spring:message code="firstName" /></td>
				<td class="input-field"><form:input path="firstName"
						id="firstName" /> <form:errors path="firstName" cssClass="error" />
				</td>
			</tr>

			<tr class="row">
				<td class="label2"><spring:message code="middleName" /></td>
				<td class="input-field"><form:input path='middleName' /></td>
			</tr>
			<tr class="row">
				<td class="label2"><spring:message code="lastName" /></td>
				<td class="input-field"><form:input path='lastName' /></td>
			</tr>
			<c:if test="${empty mode}">
				<tr class="row">
					<td class="label2"><spring:message code="username" /></td>
					<td class="input-field"><form:input path='userName' /></td>
				</tr>
			</c:if>
			<c:if test="${not empty mode}">
				<tr class="row">
					<td class="label2"><spring:message code="username" /></td>
					<td class="input-field"><form:input path='userName'
							readonly="true" /></td>
				</tr>
			</c:if>
			<tr class="row">
				<td class="label2"><spring:message code="email" /></td>
				<td class="input-field"><form:input path='email' /></td>
			</tr>
			<c:if test="${empty mode}">
				<tr class="row">
					<td class="label2"><spring:message code="password" /></td>
					<td class="input-field"><form:password path='password' /></td>
				</tr>

				<tr class="row">
					<td class="label2"><spring:message code="retypePassword" /></td>
					<td class="input-field"><input type="password" id="password1"
						name="password1" /></td>
				</tr>
			</c:if>
			<tr class="row">
				<td colspan="2"><h3>
						<spring:message code="shippingDetails" />
					</h3></td>
				<td></td>
			</tr>
			<tr class="row">
				<td class="label2"><spring:message code="phoneNo" /></td>
				<td class="input-field"><form:input
						path='shippingAddress.phone' /></td>
			</tr>

			<tr class="row">
				<td class="label2"><spring:message code="country" /></td>
				<td class="input-field"><form:select
						path="shippingAddress.country.countryId" id="shippingCountryId">
						<form:option value="1">Select</form:option>
						<form:options items="${countryList}" itemLabel="countryName"
							itemValue="countryId" />
					</form:select></td>
			</tr>

			<tr class="row">
				<td class="label2"><spring:message code="state" /></td>
				<td class="input-field"><form:select
						path="shippingAddress.state.stateId" id="shippingStateId">
						<form:option value="1">Select</form:option>
						<c:if test="${ not empty shippingStateList}">
							<form:options items="${shippingStateList}" itemLabel="stateName"
								itemValue="stateId" />
						</c:if>
					</form:select></td>
			</tr>

			<tr class="row">
				<td class="label2"><spring:message code="city" /></td>
				<td class="input-field"><form:input
						path='shippingAddress.cityName' /></td>
			</tr>

			<tr class="row">
				<td class="label2"><spring:message code="street" /></td>
				<td class="input-field"><form:input
						path='shippingAddress.streetName' /></td>
			</tr>

			<tr class="row">
				<td class="label2"><spring:message code="apartmentAddress" /></td>
				<td class="input-field"><form:input
						path='shippingAddress.apartmentAddress' /></td>
			</tr>

			<tr class="row">
				<td class="label2"><spring:message code="zipCode" /></td>
				<td class="input-field"><form:input
						path='shippingAddress.zipCode' /></td>
			</tr>
			<c:if test="${empty mode}">
				<tr class="row">
					<td class="label2"><spring:message code="termsAndCondition" /></td>
					<td class="input-field"><input type="checkbox"
						id="termsAndCondition" name="termsAndCondition" value="true" /></td>

				</tr>
			</c:if>
			<tr class="row">
				<td></td>
				<td><c:if test="${empty mode}">
						<form:button name="action" value="save" class="form-button">
							<spring:message code="save" />
						</form:button>
					</c:if> <c:if test="${mode=='edit'}">
						<form:button name="action" value="edit" class="form-button">
							<spring:message code="edit" />
						</form:button>
					</c:if> <form:button name="action" value="reset" onclick="resetForm();"
						class="form-button">
						<spring:message code="reset" />
					</form:button></td>

			</tr>
		</table>
</form:form>

</div>

