<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<!-- saved from url=(0049)http://2.s3.envato.com/files/51564135/index.html# -->
<%@page import="java.util.Arrays"%>
<%@page import="java.util.TimeZone"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

	<title>Tab Login &amp; Sign Up Forms</title>

	<link rel="stylesheet" type="text/css" href="css/blue.css"/>
	<script type="text/javascript" src="js/jquery-1.9.1.js"></script>
	<script type="text/javascript">
		<c:set var="req" value="${pageContext.request}" />
		<c:set var="baseURL" value="${req.scheme}://${req.serverName}:${req.serverPort}/" />
		function validate(){
			var email = $('#txtEmail').val();
			var password = $('#password').val();
			var passwordConfirm = $('#password_confirm').val();
		    var filter = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;

		    if (!filter.test(email) || $.trim(email).length == 0) {
			    alert('Please provide a valid email address');
			    return false;
		    }
		    
		    if(password!=passwordConfirm){
		    	alert('Passwords does not match!!');
		    	return false;
		    }
		    return true;
		}		
		$(document).ready(function(){
			$('#regSubmit').click(function(event){
				var email = $('#txtEmail').val();
				var password = $('#password').val();
				var passwordConfirm = $('#password_confirm').val();
			    var filter = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;

			    if (!filter.test(email) || $.trim(email).length == 0) {
				    alert('Please provide a valid email address');
				    return false;
			    }
			    
			    if(password!=passwordConfirm){
			    	alert('Passwords do not match!!');
			    	return false;
			    }
				$.ajax({
					url : '/registration/controller/register.do',
					data : $('#regForm').serialize(),
					type : 'POST',
					success : function(data) {
						if(data == 'FAILED'){
							$('#msgError').show();
							$('#msgSuccess').hide();				
						}else{
							//var notifyURL = 'https://localhost:8443/registration/controller/confirm.do?uuid='+data;
							var notifyURL = '<c:out value="${baseURL}"/>registration/controller/confirm.do?uuid='+data;
							$('#notify_url').val(notifyURL);
							$('#msgError').hide();
							$('#tabs').hide();
							$('#msgSuccess').show();
						}
					}
				});			
			});	
			$('#msgError').hide();
			$('#msgSuccess').hide();  
		});
	</script>	
<style>
#msgSuccess{
    background: #fff;
	z-index: 6;
	color:#039BFC;
	position: relative;
	font-weight: normal; 
	color: green; 
	font-size: 11px;
	z-index: 100;
}
#msgError{
    background: #fff;
	z-index: 6;
	color:#039BFC;
	position: relative;
	font-weight: bold; 
	color: red; 
	font-size: 11px;
	z-index: 100;
}
</style>
</head>
<body>
<div id="wrapper">
  <h1>Tab Login &amp; Sign Up Forms</h1>
  <div id="container">
	<div id="tabs" class="tabs">
		<input id="tab-1" type="radio" name="radio-set" class="tab-selector-1" checked="checked"/>
		<span for="tab-1" class="tab-label-1">Registration</span>
		<div class="clear-shadow"></div>
		<div id="content">
			<div class="error"></div>
			<div class="content-1">
				<form id="regForm" action="" autocomplete="on">
				  <p>
					<label for="txtEmail" class="youmail"> Your email</label>
					<input id="txtEmail" class="field" name="txtEmail" required="required" type="email" placeholder="myusername@gmail.com"/>
				  </p>
				  <p>
					<label for="password" class="youpasswd">Your password </label>
					<input id="password" class="field" name="password" required="required" type="password" placeholder="mypassword"/>
				  </p>
				  <p>
					<label for="passwordsignup_confirm" class="youpasswd">Please confirm your password </label>
					<input class="field" name="password_confirm" required="required" type="password" placeholder="mypassword"/>
				  </p>
				  <p>
					<label for="gender" class="youpasswd">Your Timezone</label>
			            <select id="timezoneId" class="select-style timezone" name="timezoneId">
						<%
							String[] zones = TimeZone.getAvailableIDs();
							StringBuilder builder = new StringBuilder();
							Arrays.sort(zones);
							for (int i=0;i<zones.length;i++) {			            
				        %>    
							<option value="<%=zones[i]%>"><%=zones[i]%></option>
						<%} %>
				            <!-- <option value="m">Male</option>
				            <option value="f">Female</option>
				            <option value="others">Other</option> -->
			            </select>					
				  </p>				  
				  <p class="signin button">
					<input type="checkbox" required="required"/> I agree with terms and conditions 
					<input id="regSubmit" type="button" value="Sign up"/>
				  </p>
				</form>
			</div>
		</div>
	</div>
	<div id="msgError">
		Unable to process your registration request at this moment. Please notify the support team kwassasin-support@gmail.com.
	</div>	
	<div id="msgSuccess">
		Your registration request has been submitted successfully. Please complete the payment formality to activate it.
		<form id='paypalForm' action="https://www.sandbox.paypal.com/cgi-bin/webscr" method="post" target="_top">
			<input type="hidden" name="cmd" value="_s-xclick"/>
			<input type="hidden" name="hosted_button_id" value="6RJGGUVFPT8PU"/>
			<input id="notify_url" type="hidden" name="notify_url" value=""/>
			<table>
			<tr><td><input type="hidden" name="on0" value="Payment Options">Payment Options</td></tr><tr><td><select name="os0">
				<option value="Gold">Gold : $30.00 USD - monthly</option>
				<option value="Platinum">Platinum : $300.00 USD - yearly</option>
			</select> </td></tr>
			</table>
			<input type="hidden" name="currency_code" value="USD"/>
			<input type="image" src="https://www.sandbox.paypal.com/en_US/i/btn/btn_subscribeCC_LG.gif" border="0" name="submit" alt="PayPal - The safer, easier way to pay online!"/>
			<img alt="" border="0" src="https://www.sandbox.paypal.com/en_US/i/scr/pixel.gif" width="1" height="1"/>
		</form>					
	</div>	
  </div>
</div>	
</body>
</html>	