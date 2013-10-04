<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<!-- saved from url=(0049)http://2.s3.envato.com/files/51564135/index.html# -->
<%@page import="java.util.Arrays"%>
<%@page import="java.util.TimeZone"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

	<title>Tab Login &amp; Sign Up Forms</title>

	<link rel="stylesheet" type="text/css" href="css/blue.css"/>
	<script type="text/javascript" src="js/jquery-1.9.1.js"></script>
	<script type="text/javascript">
		$('#regSubmit').click(function(event){
			event.preventDefault();
			var oMyForm = new FormData();
			oMyForm.append("txtEmail", $('txtEmail').value);
			oMyForm.append("password", $('password').value);
			oMyForm.append("timezoneId", $('timezoneId').value);
			$.ajax({
				url : 'https://localhost:8443/registration/controller/register.do',
				data : oMyForm,
				dataType : 'text',
				processData : false,
				contentType : false,
				type : 'POST',
				success : function(data) {
					alert(data);
					if(data == 'success'){
						$('msgError').css('display', 'none');
						$('msgSuccess').css('display', 'block');
					}else{
						$('msgError').css('display', 'block');
						$('msgSuccess').css('display', 'none');
					}
				}
			});			
		});
	</script>
</head>
<body>
<div id="wrapper">
  <h1>Tab Login &amp; Sign Up Forms</h1>
  <div id="container">
	<!-- <section class="tabs"> -->
		<c:forEach items="plans" var="plan" varStatus="i">
			<input id="tab-"<c:out value="${i}"/> type="radio" name="radio-set" class="tab-selector-1" checked="checked"/>
			<span for='tab-<c:out value="${i}"/>' class='tab-label-<c:out value="${i}"/>'><c:out value="${plan.planName}"/></span>
	
			<!-- <input id="tab-2" type="radio" name="radio-set" class="tab-selector-2"/>
			<span for="tab-2" class="tab-label-2">Signup</span>
	
			<input id="tab-3" type="radio" name="radio-set" class="tab-selector-3"/>
			<span for="tab-3" class="tab-label-3">Forget Password</span> -->
		
		</c:forEach>
	
		<div class="clear-shadow"></div>
		
		<div id="content">
			<c:forEach items="plans" var="plan" varStatus="i">
				<div class='content-<c:out value="${i}"/>'>
					<table width="100%" cellpadding="5" cellspacing="10" border="0">
						<thead>
							<tr>
								<td colspan="2" align="left"><label>Subscription Plan Details</label></td>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td align="left">Plan Name</td>
								<td align="left"><c:out value="${plan.planName}"/></td>
							</tr>
							<tr>
								<td align="left">Susbscription Period</td>
								<td align="left"><c:out value="${plan.durationInDays}"/> Days</td>
							</tr>
							<tr>
								<td align="left">Keyword Limit per upload</td>
								<td align="left"><c:out value="${plan.uploadRestriction}"/></td>
							</tr>
							<tr>
								<td align="left">Monthly Keyword Limit</td>
								<td align="left"><c:out value="${plan.totalKeywordLimit}"/></td>
							</tr>
							<tr>
								<td align="left">Keyword Processing Subscription Cost</td>
								<td align="left"><c:out value="${plan.cost}"/></td>
							</tr>
							<tr>
								<td align="left">Discount</td>
								<td align="left"><c:out value="${plan.discount}"/></td>
							</tr>	
						</tbody>
						<script src="paypal-button.min.js?merchant=LD65XKKJ7YMQA" data-button="subscribe" data-name="Subscription Sell" data-amount='<c:out value="${plan.cost}"/>'
						    data-currency="USD" data-recurrence='<c:out value="${plan.recurrence}"/>' data-period='<c:out value="${plan.subscriptionPeriod}"/>' 
						    data-callback='https://localhost:8443/registration/controller/confirm.do?uuid=<c:out value="${uuid}"/>' data-env="sandbox"></script>						
					</table>
				</div>
			</c:forEach>
		</div>
	<!-- </section> -->
  </div>
</div>	
</body>
</html>