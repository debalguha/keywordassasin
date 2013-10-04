<!DOCTYPE html>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.TimeZone"%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/w2ui-1.2.css" />
<script src="js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="js/w2ui-1.2.js"></script>
</head>
<body style="margin-left: 100px; margin-right: 100px; overflow: scroll;">
	<div style="width: 100%; height: 150px;">
		<img alt="logo" src="img/keyword_assassin.jpg" width="100%"
			height="100%">
	</div>
	<div id="form" style="height: 80%">
		<form action="controller/request.do" method="post" autocomplete="on"
			spellcheck="false">
			<div class="w2ui-page page-0">
				<div style="width: 50%; height: 200px; float: left;">
					<div style="padding: 3px; font-weight: bold; color: #777;">Registration
						Information</div>
					<div class="w2ui-group">
						<div class="w2ui-label">Name:</div>
						<div class="w2ui-field">
							<input name="name" type="text" maxlength="100" style="width: 50%" />
						</div>
						<div class="w2ui-label">E-Mail:</div>
						<div class="w2ui-field">
							<input name="txtEmail" type="email" maxlength="100"
								style="width: 80%" />
						</div>
						<div class="w2ui-label">Confirm E-Mail:</div>
						<div class="w2ui-field">
							<input name="confEmail" type="email" maxlength="100"
								style="width: 80%" />
						</div>
						<div class="w2ui-label">Password:</div>
						<div class="w2ui-field">
							<input name="password" type="password" maxlength="100"
								style="width: 40%;" />
						</div>
						<div class="w2ui-label">Confirm Password:</div>
						<div class="w2ui-field">
							<input name="confPassword" type="password" maxlength="100"
								style="width: 40%;" />
						</div>
						<div class="w2ui-label">Subscription Plan:</div>
						<div class="w2ui-field">
							<select id="plan" name="plan"></select>
						</div>
						<div class="w2ui-label">Timezone:</div>
						<div class="w2ui-field">
							<select id="timezoneId" name="timezoneId"></select>
						</div>						
					</div>
				</div>
				<div style="margin-left: 53%; height: 200px;">
					<div style="padding: 3px; font-weight: bold; color: #777;">Billing
						Information</div>
					<div class="w2ui-group">
						<div class="w2ui-label w2ui-span5">Address Line 1:</div>
						<div class="w2ui-field w2ui-span5">
							<input name="address1" type="text" maxlength="100"
								style="width: 100%" />
						</div>
						<div class="w2ui-label w2ui-span5">Address Line 2:</div>
						<div class="w2ui-field w2ui-span5">
							<input name="address2" type="text" maxlength="100"
								style="width: 100%" />
						</div>
						<div class="w2ui-label w2ui-span5">Country:</div>
						<div class="w2ui-field w2ui-span5">
							<input id="country" name="country" type="text" maxlength="50"
								size="50" value="US" />
						</div>
						<div class="w2ui-label w2ui-span5">State:</div>
						<div class="w2ui-field w2ui-span5">
							<input name="state" type="text" maxlength="2" size="2" />
						</div>
						<div class="w2ui-label w2ui-span5">City:</div>
						<div class="w2ui-field w2ui-span5">
							<input name="city" type="text" maxlength="50" size="25" />
						</div>
						<div class="w2ui-label w2ui-span5">Zip:</div>
						<div class="w2ui-field w2ui-span5">
							<input name="zip" type="text" maxlength="10" size="10" />
						</div>
					</div>
				</div>
				<div style="Width: 50%; clear: both; padding-top: 35px;">
					<div style="padding: 3px; font-weight: bold; color: #777;">Payment
						Information</div>
					<div class="w2ui-group">
						<div class="w2ui-label">Card Type:</div>
						<div class="w2ui-field">
							<select id="cardSelect" name="cardType"></select>
						</div>
						<div class="w2ui-label">Card Number</div>
						<div class="w2ui-field">
							<input name="cardNumber" type="text" maxlength="16"
								style="width: 40%;" />
						</div>
						<div class="w2ui-label">Confirm Card Number:</div>
						<div class="w2ui-field">
							<input name="confCardNumber" type="text" maxlength="16"
								style="width: 40%;" />
						</div>
						<div class="w2ui-label">CVV2:</div>
						<div class="w2ui-field">
							<input name="cvv2" type="int" maxlength="3" style="width: 10%;" />
						</div>
						<div class="w2ui-label">Amount:</div>
						<div class="w2ui-field">
							<input name="amount" type="float" maxlength="7"
								style="width: 17%;" />
						</div>
						<div class="w2ui-label">Expiry:</div>
						<div class="w2ui-field">
							<input name="month" type="int" maxlength="2" style="width: 7%;" />
							/ <input name="year" type="int" maxlength="4" style="width: 10%;" />
						</div>
					</div>
				</div>
			</div>
			<div class="w2ui-buttons">
				<input type="button" value="Reset" name="reset"> <input
					type="submit" value="Submit" name="save">
			</div>
		</form>
	</div>
</body>
<script type="text/javascript">
	$('#form').w2form({
		name : 'form',
		header : 'Subscription Form',
		url : 'controller/request.do',
		fields : [ {
			name : 'name',
			type : 'text',
			required : true
		}, {
			name : 'txtEmail',
			type : 'text',
			required : true
		}, {
			name : 'confEmail',
			type : 'text',
			required : true
		}, {
			name : 'password',
			type : 'text',
			required : true
		}, {
			name : 'confPassword',
			type : 'text',
			required : true
		}, {
			name : 'plan',
			type : 'list',
			required : true,
			options : {
				items : [ 'Monthly', 'Quarterly', 'Yearly' ]
			}
		}, {
			name : 'timezoneId',
			type : 'list',
			required : true,
			<%
				String[] zones = TimeZone.getAvailableIDs();
				StringBuilder builder = new StringBuilder();
				Arrays.sort(zones);
				for (int i=0;i<zones.length;i++) {
					builder.append("'"+zones[i]+"'");
					if(i!=zones.length-1)
						builder.append(",");
				}
			%>
			options : {
				items : [ <%=builder.toString()%> ]
			}
		}, {
			name : 'address1',
			type : 'text',
			required : true
		}, {
			name : 'address2',
			type : 'text'
		}, {
			name : 'city',
			type : 'text',
			required : true
		}, {
			name : 'state',
			type : 'text',
			required : true
		}, {
			name : 'country',
			type : 'text',
			required : true
		}, {
			name : 'zip',
			type : 'int',
			required : true
		}, {
			name : 'cardType',
			type : 'list',
			required : true,
			options : {
				items : [ 'VISA', 'MasterCard' ]
			}
		}, {
			name : 'cardNumber',
			type : 'text',
			required : true
		}, {
			name : 'confCardNumber',
			type : 'text',
			required : true
		}, {
			name : 'cvv2',
			type : 'int',
			required : true
		}, {
			name : 'amount',
			type : 'float',
			required : true
		}, {
			name : 'month',
			type : 'int',
			required : true
		}, {
			name : 'year',
			type : 'int',
			required : true
		} ],
		actions : {
			reset : function() {
				this.clear();
			}/* ,
						save : function() {
							this.save(showStatus());
						} */
		}
		
	});
</script>
</html>
