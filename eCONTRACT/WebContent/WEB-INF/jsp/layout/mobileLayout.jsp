<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=IE8">
<title><tiles:insertAttribute name="title" /></title>
<style>
html {
	font-size: 12px;
	font-family: Arial, Helvetica, sans-serif;
}
</style>
<link type="text/css" href="./resources/css/font.css" rel="stylesheet" />
<link type="text/css" href="//netdna.bootstrapcdn.com/font-awesome/3.2.1/css/font-awesome.css" rel="stylesheet" />
<link type="text/css" href="./resources/css/style.css" rel="stylesheet" />
<link type="text/css" href="./resources/css/login.css" rel="stylesheet" />

<link type="text/css" href="./resources/css/style2.css" rel="stylesheet" />
<link href='https://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css'>


<link rel="shortcut icon" href="resources/images/favicon.ico" />
<link rel="apple-touch-icon" href="/apple-touch-icon.png" />

<link href="./resources/css/kendo/kendo.common.min.css" rel="stylesheet" />
<link href="./resources/css/kendo/kendo.default.min.css" rel="stylesheet" />
<link href="./resources/css/test.css" type="text/css" rel="stylesheet" />

<script src="./resources/js/kendo/jquery.min.js"></script>
<script src="./resources/js/kendo/kendo.all.min.js"></script>
<!-- <script src="./resources/js/edit.js"></script> -->

</head>
<body>
	<tiles:insertAttribute name="header" />
	<tiles:insertAttribute name="body" />
<%-- 	<tiles:insertAttribute name="footer" /> --%>
</body>
</html>