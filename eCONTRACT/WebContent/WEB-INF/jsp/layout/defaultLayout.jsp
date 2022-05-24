<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=IE8">
<title><tiles:insertAttribute name="title" /></title>

<!-- kendo CSS -->
<link href="./resources/css/kendo/kendo.common.min.css" rel="stylesheet" />
<link href="./resources/css/kendo/kendo.default.min.css" rel="stylesheet" />
<link href="./resources/css/kendo/kendo.blue.css" rel="stylesheet" />

<!-- custom CSS -->
<link type="text/css" href="./resources/css/style_afterdelete.css" rel="stylesheet" />
<link type="text/css" href="./resources/css/style.css" rel="stylesheet" />

<link rel="shortcut icon" href="resources/images/favicon.ico" />
<link href='https://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css'>

<!-- JQEURY & KENDO JS -->
<script src="./resources/js/kendo/jquery.min.js"></script>
<script src="./resources/js/kendo/kendo.all.min.js"></script>
<!-- JQEURY UI JS -->
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>

<!-- custom JS -->
<script src="./resources/js/edit.js"></script>
</head>
<body>
	<tiles:insertAttribute name="body" />
</body>
</html>