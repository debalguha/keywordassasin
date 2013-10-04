<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>Registration Successfull</title>
<link rel="stylesheet" href="/css/style.css"/>
</head>

<body>
  <form method="post" action="/controller/request.do" class="login">
    <p class="forgot-password">Your registration has failed. Please go back and try again. Error message is</p>
    <c:out value="${errMsg}"></c:out>
  </form>
</body>
</html>
