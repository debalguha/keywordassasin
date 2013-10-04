<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@page import="java.util.Arrays"%>
<%@page import="java.util.TimeZone"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>Keyword Assasin - Registration</title>
<link rel="stylesheet" href="css/style.css" />
<script type="text/javascript">
	function validate(){
		var valid = false;
		if(checkEmail()){
			if(elementMatch('txtEmail','email_conf')){
				if(elementMatch('password','conf_password'))
					valid = true;
				else{
					var password = document.getElementById('password');
					var passwordConf = document.getElementById('conf_password');
					password.style.backgroundColor = 'yellow';
					passwordConf.style.backgroundColor = 'yellow';
				}
			}else{
				var email = document.getElementById('txtEmail');
				var emailConf = document.getElementById('email_conf');
				email.style.backgroundColor = 'red';
				emailConf.style.backgroundColor = 'red';
			}
		}
		return valid;
	}
	function checkEmail() {

	    var email = document.getElementById('txtEmail');
	    var filter = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;

	    if (!filter.test(email.value)) {
	    	alert('Please provide a valid email address');
	    	email.focus;
	    	return false;
	    }
	    return true;
	 }	
	 function elementMatch(srcElement, destElement){
		 var email = document.getElementById(srcElement).value;
		 var emailConf = document.getElementById(destElement).value;
		 alert(email+'--'+emailConf+'--'+(email!=emailConf));
		 if(email!=emailConf)
			 return false;
		 return true;
	 }
	 function validateFalse(){
		 return false;
	 }
</script>
</head>

<body>
      <div class="Paragraph-IndentBlack">
      <table width=518 border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF" class="Paragraph-IndentBlack">
        <tr bgcolor="#E5E5E5"> 
          <td height="22" colspan="3" align="left" valign="middle"><strong>&nbsp;Billing Information (required)</strong></td>
        </tr>
        <tr> 
          <td height="22" width="180" align="right" valign="middle">First Name:</td>
          <td colspan="2" align="left">
            <input name="firstName" type="text" size="50"/></td>
        </tr>
        <tr> 
          <td height="22" align="right" valign="middle">Last Name:</td>
          <td colspan="2" align="left">
            <input name="lastName" type="text" size="50"/></td>
        </tr>
        <tr>  
          <td height="22" align="right" valign="middle">Company (optional):</td>
          <td colspan="2" align="left">
            <input name="company" type="text" size="50"/></td>
        </tr>
              
        <tr> 
                
          <td height="22" align="right" valign="middle">Street Address:</td>
          <td colspan="2" align="left">
                <input name="address1" type="text" value="" size="50"/></td>
        </tr>
                     
        <tr> 
          <td height="22" align="right" valign="middle">Street Address (2):</td>
          <td colspan="2" align="left">
                <input name="address2" type="text" value="" size="50"></td>
        </tr>
           
        <tr> 
          <td height="22" align="right" valign="middle">City:</td>
          <td colspan="2" align="left">
                <input name="city" type="text" value="" size="50"></td>
        </tr>
         
        <tr> 
          <td height="22" align="right" valign="middle">State/Province:</td>
          <td colspan="2" align="left">
                <input name="state" type="text" value="" size="50"></td>
        </tr>
		<tr> 
          <td height="22" align="right" valign="middle">Zip/Postal Code:</td>
          <td colspan="2" align="left">
                <input name="zip" type="text" value="" size="50"></td>
        </tr>
        <tr> 
          <td height="22" align="right" valign="middle">Country:</td>
          <td colspan="2" align="left">
                <input name="country" type="text" value="" size="50"></td>
        </tr>
        <tr> 
          <td height="22" align="right" valign="middle">Phone:</td>
          <td colspan="2" align="left">
                <input name="phone" type="text" value="" size="50"></td>
        </tr>
        <tr> 
          <td height="22" colspan="3" align="left" valign="middle">&nbsp;</td>
        </tr>
        <tr bgcolor="#E5E5E5"> 
          <td height="22" colspan="3" align="left" valign="middle"><strong>&nbsp;Credit Card (required)</strong></td>
        </tr>
         <tr> 
          <td height="22" align="right" valign="middle">Credit Card Number:</td>
          <td colspan="2" align="left"><input name="CCNo" type="text" value="" size="19" maxlength="40">
		  </td>
        </tr>
        <tr> 
          <td height="22" align="right" valign="middle">Expiry Date:</td>
          <td colspan="2" align="left">
<SELECT NAME="CCExpiresMonth" >
<OPTION VALUE="" SELECTED>--Month--
<OPTION VALUE="01">January (01)
<OPTION VALUE="02">February (02)
<OPTION VALUE="03">March (03)
<OPTION VALUE="04">April (04)
<OPTION VALUE="05">May (05)
<OPTION VALUE="06">June (06)
<OPTION VALUE="07">July (07)
<OPTION VALUE="08">August (08)
<OPTION VALUE="09">September (09)
<OPTION VALUE="10">October (10)
<OPTION VALUE="11">November (11)
<OPTION VALUE="12">December (12)
</SELECT> /
<SELECT NAME="CCExpiresYear">
<OPTION VALUE="" SELECTED>--Year--
<OPTION VALUE="04">2004
<OPTION VALUE="05">2005
<OPTION VALUE="06">2006
<OPTION VALUE="07">2007
<OPTION VALUE="08">2008
<OPTION VALUE="09">2009
<OPTION VALUE="10">2010
<OPTION VALUE="11">2011
<OPTION VALUE="12">2012
<OPTION VALUE="13">2013
<OPTION VALUE="14">2014
<OPTION VALUE="15">2015
</SELECT>

			</td>
        </tr>
        <tr> 
          <td height="22" colspan="3" align="left" valign="middle"><!--DWLayoutEmptyCell-->&nbsp;</td>
        </tr>
        <tr bgcolor="#E5E5E5"> 
          <td height="22" colspan="3" align="left" valign="middle"><strong>&nbsp;Additional Information</strong></td>
        </tr>
        <tr> 
          <td height="22" align="right" valign="middle">Contact Email:</td>
          <td colspan="2" align="left">
                <input name="contactEmail" type="text" value="" size="50"></td>
        </tr>
        <tr> 
          <td height="22" colspan="3" align="left" valign="middle"><!--DWLayoutEmptyCell-->&nbsp;</td>
        </tr>
        <tr> 
          <td height="22" align="right" valign="top">Special Notes:</td>
          <td colspan="2" align="left"><textarea name="notes" cols="45" rows="4"></textarea>			</td>
        </tr>
        </table>
              </div>
</body>
</html>
