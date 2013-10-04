<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<!-- saved from url=(0049)http://2.s3.envato.com/files/51564135/index.html# -->
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<c:set var="req" value="${pageContext.request}" />
<c:set var="baseURL" value="${req.scheme}://${req.serverName}:${req.serverPort}/" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

	<title>Tab Login &amp; Sign Up Forms</title>

	<link rel="stylesheet" type="text/css" href="css/blue.css"/>
</head>
<body>
<div id="wrapper">
  <h1>Tab Login &amp; Sign Up Forms</h1>
  <div id="container">
	<section class="tabs">
		<input id="tab-1" type="radio" name="radio-set" class="tab-selector-1" checked="checked"/>
		<span for="tab-1" class="tab-label-1">Login</span>

		<input id="tab-2" type="radio" name="radio-set" class="tab-selector-2"/>
		<span for="tab-2" class="tab-label-2">Forget Password</span>
	
		<div class="clear-shadow"></div>
		
		<div id="content">
			<div class="content-1">
				<form action="<%= response.encodeURL("j_security_check") %>" autocomplete="on">
				  <p>
					<label for="j_username" class="uname"> Your email or username </label>
					<input class="field" name="j_username" required="required" type="text" placeholder="bill.gates@gmail.com"/>
				  </p>
				  <p>
					<label for="password" class="j_password"> Your password </label>
					<input class="field" name="j_password" required="required" type="password" placeholder="mypassword"/>
				  </p>
				  <p class="signin button">
					<input type="submit" value="Sign in"/>
				  </p>				  
				</form>
				<span class="signin">Don't have a login? <a href='<c:out value="${baseURL}"/>registration/registration.jsp'>register here</a></span>
			</div>
			<div class="content-2">
				<form action="" autocomplete="on">
				  <p>
					<label for="emailsignup" class="youmail"> Your email</label>
					<input class="field" name="emailsignup" required="required" type="email" placeholder="myusername@gmail.com"/>
				  </p>
				  <p class="signin button">
					<input type="submit" value="Get New Password"/>
				  </p>
				</form>
			</div>
			
		</div>
	</section>
  </div>
</div>	
</body>
</html>