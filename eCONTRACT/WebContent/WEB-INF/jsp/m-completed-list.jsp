<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<c:url var="getCompletedList" value="/m-completed-list/read"/>
<c:url var="downloadFDF" value="/download-file/ftp"/>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=IE8">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	
    <!-- viewport -->
	<meta name="HandheldFriendly" content="True">
	<meta name="MobileOptimized" content="320">
	<meta name="viewport" content="user-scalable=no, initial-scale=1, maximum-scale=1, minimum-scale=1, width=device-width, target-densitydpi=medium-dpi">
	<!-- /viewport -->
    <title>Mobile Completed List</title>

    <link href="./resources/css/kendo/kendo.common.min.css" rel="stylesheet" />
    <link href="./resources/css/kendo/kendo.default.min.css" rel="stylesheet" />
    <link href="./resources/css/kendo/kendo.mobile.all.min.css" rel="stylesheet" />
    <link href="./resources/css/kendo/kendo.default.mobile.min.css" rel="stylesheet" />
    
    <!-- notification css -->
	<link rel="stylesheet" href="./resources/css/kendo/kendo.common.min.css" />
	<link rel="stylesheet" href="./resources/css/kendo/kendo.default.min.css" />
	<link rel="stylesheet" href="./resources/css/kendo/kendo.dataviz.min.css" />
	<link rel="stylesheet" href="./resources/css/kendo/kendo.dataviz.default.min.css" />
	<!-- //notification css -->
	
	<link rel="shortcut icon" href="./resources/images/favicon.ico" />
	<link rel="apple-touch-icon" href="/apple-touch-icon.png" />
	<!-- <link type="text/css" href="./resources/css/font.css" rel="stylesheet" /> -->
	<link type="text/css" href="./resources/css/style.css" rel="stylesheet" />
	<link type="text/css" href="./resources/css/style_mobile.css" rel="stylesheet" />
	
	
    <script src="./resources/js/kendo/jquery.min.js"></script>
    <script src="./resources/js/kendo/kendo.all.min.js"></script>
    <script src="./resources/js/kendo/console.js"></script>
    <script src="./resources/js/jquery-ui.js"></script>
   
 <script src="./resources/js/oztotoframework.js"></script>  <!-- commented by uugan -->
	
    <script src="./resources/js/common.js"></script>
    <script src="./resources/js/edit.js"></script>
</head>
<body>
<!-- push notificaion -->
<span id="hvacNotification"></span>
<!-- //push notificaion -->

<!-- modal progress -->
<div id="modal_loading" >
<div class="modal_load"></div>
</div>
<!-- //modal progress -->

<div data-role="view" id="jobList" data-title="Гэрээний жагсаалт" data-layout="databinding">
    <ul id="jobListview" data-template="listView-template"></ul>
</div>
<!-- header 영역  -->
<div data-role="layout" data-id="databinding">
    <header data-role="header">
    	<div style="margin: 10px">
		    <input type="number" id="searchStr" placeholder="Сонгосон дугаар ..." value="" class="k-textbox"/>
		    <button id="searchCustomer" name="searchCustomer" class="k-button" style="background-color: green; color: white" onclick="getList()" >Хайх</button>
		</div>
        <div data-role="navbar" style="background-color: #333A4D; height: 60px;">
            <span data-role="view-title" style="color: white; font-size: 20pt; line-height: 60px;"></span>
        <a data-align="left" style="opacity:100;width:33px;" data-icon="reply" class="km-button km-state-active" data-role="tab" onClick="back()"><span
					class="km-icon km-reply" style="cursor: default;color:white;"></span><span
					style="font-size:8px;color:white;">Буцах</span></a>
        </div>
        <div id="topBar" style="width: 100%; height: 40px; border-bottom:1px solid #DADADA; background-color: white;">
   			<div style="width: 100%">
   				<div id="topBarText" style="width: 50%; height: 40px; float: left; color: black; font-size: 12pt; line-height: 45px;"><span class="k-icon k-i-calendar" style="margin-left: 15px; margin-bottom: 3px;"></span><span id="nowdate" style="margin-left: 10px;"></span></div>
   				<div id="tech" style="width: 50%; height: 40px; float: left; line-height: 45px; color: black; padding-right: 10px;" align="right"></div>
   			</div>
   		</div>
    </header>
</div>
<!-- PHAM updated 20151230: Add address, time_planned, status_name -->
<script type="text/x-kendo-template" id="listView-template">
	<div id="open_pdf" name="open_pdf" class="test" onclick="openPDF2('#:doc_name #');" style="cursor:pointer;">
		<input type="hidden" id="prod_no" name="prod_no" value="#:prod_no #"/>
		 	<div class="header" style="background-color:rgb(50,205,50)">			
				<p class="header-user">#:index_no #</p>
		 	</div>
			<div class="contents">
				<div class="contents-docname">#:doc_name #</div>
			</div>
	</div>
</script>

<script type="text/x-kendo-template" id="nodata-template">
	<div style="text-align:center; display: table-cell;vertical-align: middle;margin-left: auto; margin-right:auto;line-height: 50px;">#:name#
	</div>
</script>
<style scoped="scoped">

.test{
	overflow: auto;
}
.header{
	float:left;   
	width:120px; 
	text-align:center;
	display: table-cell;
	vertical-align: middle;
	height: 50px;
}

.header .header-user{
	line-height: 1.8;
	font-size: 1.5em; 
	color: white;
}

.contents {
	float:left; 
	/* background-color: yellow; */ 
	width:80%; 
	line-height: 50px;
	/* max-height: 190px; */
}
.contents-docname{
	height: 50px;
	margin-left: 20px;
	font-size: 12pt;
	color:#5D5D5D;
	padding-bottom: 10px;
	/* background-color: green; */
}

.km-ios7 .km-list>li {
	padding: 0 0 0 0;
}
.km-view-title{
	height: 60px;
}
.k-dropdown-wrap.k-state-default, .k-dropdown-wrap.k-state-default, .k-dropdown-wrap{
	background-color: white;
	border: none;
}
span.k-widget.k-dropdown.k-header.km-widget.k-state-border-down{
	border: none;
}
span.k-dropdown-wrap.k-state-default.k-state-hover.k-state-focused.k-state-active.k-state-border-down{
	border: none;
}
.k-header
{
	border-color: white;
}
</style>
<script>
var app;
var listView = $("#jobListview").kendoMobileListView({ 
	dataSource: new kendo.data.DataSource({ data:[ {name : "No Completed List"} ]}),
	template : $("#nodata-template").html()
	});

function go(report_name) {
	location.href = "m-ozviewer?reportname=" + report_name;
}

function openPDF2(pdf_filename) {
	var _prod_no = $(prod_no).val();
	
	var webapproot = "<%=request.getContextPath()%>";
	var serverRootUrl = location.protocol + "//" + location.hostname +(location.port ? ':'+location.port: '') + webapproot;
	var pdfUrl = "/m-pdfviewer?file_name=" + pdf_filename + "&prod_no=" + _prod_no;
    
	alert("Please wait seconds ...");
	
	OZTotoFramework.addEventListener("NativeTOJS_btnClick_PdfShow", function(event){});
	OZTotoFramework.dispatchEvent("JSTONative_btnClick_PdfShow", { path: serverRootUrl + pdfUrl});
	OZTotoFramework.removeEventListener("NativeTOJS_btnClick_PdfShow", function(event){});
}

function back() {
	history.back();
}

function getList() {
	var params = "prod_no="+$(searchStr).val();
	
	$.ajax({
        type: "POST",
        url: "${getCompletedList}",
        data: params,
        async: true,
        success : function(data) {            
              setList(data);
        },
        complete : function(data) {
        },
        error : function(xhr, status, error) {
        	alert(error);
        }
	});
}

function setList(datas) {
	if(datas == null || datas.length == 0) {
		console.log("size 0");
		$("#jobListview").kendoMobileListView({ 
			dataSource: new kendo.data.DataSource({ data:[ {name : "No Completed List"} ]}),
			template : $("#nodata-template").html()
			});
	}
	else {
		$("#jobListview").kendoMobileListView({ 
			dataSource: new kendo.data.DataSource({ data: datas}),
			template : $("#listView-template").html()
			});	
	}
	
}

$(document).ready(function(){
	//getList();
	app = new kendo.mobile.Application($(document.body), {
	    platform: "ios7"
	});
});

</script>
</body>
</html>
