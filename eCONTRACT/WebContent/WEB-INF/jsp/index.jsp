<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=IE8">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<title>Mobile Index</title>
<meta name="description" content="">
<meta name="keywords" content="">
<meta name="robots" content="index, follow">
<meta name="distribution" content="global">
<meta name="language" content="en">
<meta name="expires" content="never">

<link href="https://fonts.googleapis.com/css?family=Open+Sans:400,300" rel="stylesheet" type="text/css">
<link href="./resources/mobile/css/mobile.css" rel="stylesheet" />

<!-- viewport -->
<meta name="HandheldFriendly" content="True">
<meta name="MobileOptimized" content="320">
<meta name="viewport" content="user-scalable=no, initial-scale=1, maximum-scale=1, minimum-scale=1, width=device-width, target-densitydpi=medium-dpi">
<!-- /viewport -->

<!-- favicon -->
<link rel="address bar icon" type="image/x-icon" href="./resources/images/favicon.ico">
<link rel="shortcut icon" type="image/x-icon" href="./resources/images/favicon.ico">
<link rel="icon" type="image/png" href="./resources/images/login-logo.png">
<!-- /favicon -->

<!-- apple-mobile-web-app -->
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<!-- /apple-mobile-web-app -->

<!-- apple-touch-icons -->
<link rel="apple-touch-icon" sizes="144x144" href="./resources/images/login-logo.png">
<link rel="apple-touch-icon" sizes="114x114" href="./resources/images/login-logo.png">
<link rel="apple-touch-icon" sizes="72x72" href="./resources/images/login-logo.png">
<link rel="apple-touch-icon" href="./resources/images/login-logo.png">
<link rel="apple-touch-icon-precomposed" sizes="144x144" href="./resources/images/login-logo.png">
<link rel="apple-touch-icon-precomposed" sizes="114x114" href="./resources/images/login-logo.png">
<link rel="apple-touch-icon-precomposed" sizes="72x72" href="./resources/images/login-logo.png">
<link rel="apple-touch-icon-precomposed" href="./resources/images/login-logo.png">
<!-- notification css -->
<link rel="stylesheet" href="./resources/css/kendo/kendo.common.min.css" />
<link rel="stylesheet" href="./resources/css/kendo/kendo.default.min.css" />
<link rel="stylesheet" href="./resources/css/kendo/kendo.dataviz.min.css" />
<link rel="stylesheet" href="./resources/css/kendo/kendo.dataviz.default.min.css" />
<!-- //notification css -->

<script src="./resources/js/kendo/jquery.min.js"></script>
<script src="./resources/js/kendo/kendo.all.min.js"></script>

<script>
function showReport(){
	location.href = "w-application-ozviewer?prod_no=&reportname=";	
}

function openCompletedList(){
	location.href = "completed-list";	
}

</script>

<style scoped="scoped">

.index-logo {
	width: 250px;
	height: 110px;
	margin: auto;
	margin-top: 50px;
	margin-bottom: 5px;
	background: url(./resources/mobile/images/unitel_logo_250.png) no-repeat;
}

</style>	
</head>
<!-- <body background="./resources/mobile/images/unitel-bg.png"> -->
<body style="background-color: #ffffff;">
<!-- /apple-touch-icons -->
<div id="container">
	<div style="background-color: #5f5f5f">
		<table style="width:100%">
			<tr>
				<td style="color: white; padding-left: 10px; vertical-align: middle; font-size: 16px">
					Welcome ${model.accountName}
				</td>
				<td align="right">
					<a href="logout">
						<img style="float: right;" src="./resources/images/logout_btn.png" id="logout_btn"/>
					</a>
				</td>
			</tr>
		</table>
	</div>
	<div id="header" style="margin-top: 5%; height: 10%;">
		<div class="index-logo"></div>
	</div>
	<div style="padding-bottom:300px">
		<!-- <div style="border: 1px solid red; height:117px; width:100%;">&nbsp;</div> -->
		
		<!-- Main menu -->	
		<div class="p10 home_icons">&nbsp;</div>
		<div class="p35 home_icons" ><a href="#" onclick="showReport()">
		<img src="./resources/mobile/images/unitel_main_icon1.png" width="120px" height="120px" style="margin-top: 15%"></a><br>
		<b><font color="#66CC00">Гэрээний маягт</font></b></div>
	
		<div class="p10 home_icons">&nbsp;</div>
		<div class="p35 home_icons" ><a href="#" onclick="openCompletedList()">
		<img src="./resources/mobile/images/unitel_main_icon2.png" width="120px" height="120px" style="margin-top: 15%"></a><br>
		<b><font color="#66CC00">Хийгдсэн гэрээ</font></b></div>
		
		<!-- <div class="clear">&nbsp;</div>
			
		<div style="height:20px; width:100%;">&nbsp;</div> -->
	</div>
	<!-- <div id="footer" style="position: fixed; bottom: 0px; height:40%; margin-right: auto; margin-left: auto; background: url('./resources/mobile/images/unitel-bg.png') no-repeat; background-size: 100%; background-position: center bottom;"> -->
	<div id="footer" style="position: fixed; bottom: 0px; height: 300px;; margin-bottom: 0; margin-right: auto; margin-left: auto; background: url('./resources/mobile/images/unitel-bg.png') no-repeat; background-size: 100% 100%;">
		<div id="footerText" style="position: absolute; right: 0px; bottom: 0px; width: 100%;">
			<font color="white">Copyright @ FORCS Co.,LTD. All rights reserved.</font>
		</div>
	</div>
</div>
</body>
</html>
