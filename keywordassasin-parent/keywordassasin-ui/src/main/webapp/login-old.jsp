<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta charset="utf-8"/>
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>Keyword Assasin</title>
<link rel="stylesheet" href="css/style.css"/>
</head>

<body>
  <form method="post" action="<%= response.encodeURL("j_security_check") %>" class="login">
    <p>
      <label for="login">Email:</label>
      <input type="text" name="j_username" id="login"/>
    </p>

    <p>
      <label for="password">Password:</label>
      <input type="password" name="j_password" id="password"/>
    </p>

    <p class="login-submit">
      <button type="submit" class="login-button">Login</button>
    </p>

    <p class="forgot-password"><a href="https://localhost:8443/registration/forgotPassword.do">Forgot your password?</a></p>
    <p class="forgot-password"><a href="https://localhost:8443/registration/">Not yet registered? Please register yourself here</a></p>
  </form>
</body>
</html>
