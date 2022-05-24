<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<c:url value="/w-application-ozviewer/seach" var="contract_info_search_url" />

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=IE8">
<meta charset="utf-8" />

<!-- mobile meta (hooray!) -->
<meta name="HandheldFriendly" content="True">
<meta name="MobileOptimized" content="320">
<meta name="viewport" content="user-scalable=no, initial-scale=1, maximum-scale=1, minimum-scale=1, width=device-width">

<title>OZ e-Form View</title>
<!-- Load all CSS through interface because using LESS (lesscss.org) -->
<!-- <link href="./resources/css/kendo.mobile.exported.css"		rel="stylesheet" /> -->
<link href="./resources/css/kendo/kendo.common.min.css" 	rel="stylesheet" />
<link href="./resources/css/kendo/kendo.default.min.css" 	rel="stylesheet" />
<link href="./resources/css/kendo/kendo.dataviz.min.css" 	rel="stylesheet" />
<link href="./resources/css/kendo/kendo.dataviz.default.min.css" rel="stylesheet" />
<link href="./resources/css/kendo/kendo.mobile.all.min.css" rel="stylesheet" />
<link type="text/css" href="./resources/css/noti.css" 		rel="stylesheet" />

<!-- notification css -->
<link rel="stylesheet" href="./resources/css/kendo/kendo.common.min.css" />
<link rel="stylesheet" href="./resources/css/kendo/kendo.default.min.css" />
<link rel="stylesheet" href="./resources/css/kendo/kendo.dataviz.min.css" />
<link rel="stylesheet" href="./resources/css/kendo/kendo.dataviz.default.min.css" />
<!-- //notification css -->

	<!-- html5ivewer -->
<script src="http://code.jquery.com/jquery-2.0.3.min.js"></script>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css" type="text/css"/>
<script src="http://code.jquery.com/ui/1.12.1/jquery-ui.min.js"></script>
<link rel="stylesheet" href="./resources/html5viewer/ui.dynatree.css" type="text/css"/>
<script src="./resources/html5viewer/jquery.dynatree.js"></script>
<script src="./resources/html5viewer/OZJSViewer.js"></script>

<!-- font Spy -->
<script src="./resources/js/jQuery-FontSpy.js"></script>

<!-- conflict with jquery-2.0.3.min.js -->
<!-- <script src="./resources/js/jquery/jquery.min.js"></script> -->

<script src="./resources/js/jquery/jquery.form.min.js"></script>
<script src="./resources/js/kendo/kendo.all.min.js"></script>

<script src="./resources/js/date.js"></script> 
<script src="./resources/js/common.js"></script>
<script src="./resources/js/ozviewerparam.js"></script>

<!-- <script src="./resources/ozfviewer/AC_OETags.js"></script> -->
<!-- <script src="./resources/ozfviewer/ozutil.js"></script> -->
<!-- <script src="./resources/ozfviewer/ozjscript.js"></script> -->

<style type="text/css">
@font-face {
	font-family: 'Noto Sans';
	src: url('./resources/fonts/NotoSans.ttf') format('truetype'),
		url('./resources/fonts/NotoSans.woff2') format('woff2'),
		url('./resources/fonts/NotoSans.woff') format('woff');
		
	font-weight: normal;
	font-style: normal;
}
@font-face {
	font-family: 'NotoSans Bold';
	src: url('./resources/fonts/NotoSans-Bold.ttf') format('truetype'),
		url('./resources/fonts/NotoSans-Bold.woff2') format('woff2'),
		url('./resources/fonts/NotoSans-Bold.woff') format('woff');
		
	font-weight: normal;
	font-style: normal;
}
</style>
<script type="text/javascript">
//-----------------------------------------------------------------------------
//Globals
//Major version of Flash required
var requiredMajorVersion = 10;
//Minor version of Flash required
var requiredMinorVersion = 0;
//Minor version of Flash required
var requiredRevision = 0;
//-----------------------------------------------------------------------------

var login_user  = "${model.login_user}";
var user_role   = "${model.user_role}";
var reportName1 = "${model.report_name}";
var reportName2 = "";
var prod_no = "${model.prod_no}";
var customer_email = "";
var reportparam  = "";
var fileType = reportName1.split('.').pop();

var isCreateView = false;
//var onCreated = false;

function init(data) {
	ozviewer_init(data);
}

function OZProgressCommand_OZViewer(step, state, reportname) {
	if(step == 4 && state == 2){
		
		OZViewer.Script("home_report");
	}
}

function ozviewer_init(data){	
	
	if(data !== undefined && data !== ""){
		if(data.contract_type !== undefined && data.contract_type !== "" && data.contract_type !== null) {

			reportName1 = "";
			reportName2 = "";
			
			OZViewer.Script("closeall");
			//console.log(data.contract_type);
			if(data.contract_type == "ps_dugaar"){
				reportName1 = "ps_dugaar_new.ozr";
			}
			else if (data.contract_type == "gar_utas_new"){	// 2016.08.22 : added by ryu. (gar_utas => gar_utas_new)
				reportName1 = "gar_utas_new.ozr";
			}
			else if(data.contract_type == "owner_ship"){
			  
			   	// alert(data.contract_json);
				// var jsonString = data.contract_json // Json data get from billing system
				var jsonVal = JSON.parse(data.contract_json); // parse json data to json object
				var user = jsonVal.user; // get user data
				// alert(user);
				reportName1 = "owner_ship_new.ozr";
				
				if (user[0].SVC_TYPE == "PS"){
					reportName2 = "ps_dugaar_new.ozr";
				}
			}
			else if(data.contract_type == "business_dugaar_new") {	// 2016.08.22 : added by ryu. (business_dugaar => business_dugaar_new)
				reportName1 = "ps_dugaar_new.ozr";
				reportName2 = "business_dugaar_new.ozr";
			}
		   	else if(data.contract_type == "univision") {
				reportName1 = "univision_gurvalsan.ozr";
			}
			else if(data.contract_type == "contract_user") {
				reportName1 = "contract_user_new.ozr";
			}
			else if(data.contract_type == "deposit_refund") {
				reportName1 = "deposit_refund_new.ozr";
			}
			else if(data.contract_type == "device_sale") {	// 2016.08.22 : added by ryu. 
				reportName1 = "device_sale_new.ozr";
			}
			else if(data.contract_type == "aziin_dugaar") {	
				reportName1 = "aziin_dugaar.ozr";
			}
			else if(data.contract_type == "check_info"){
				reportName1 = "med_taaruulah_new.ozr";
			}
			else if(data.contract_type == "smart_chg"){
				reportName1 = "smart_bagts_hoorond_shiljih.ozr";
			}
			else if(data.contract_type == "bagts_rsv"){
					reportName1 = "bagts_rsv.ozr";
			}
			else if(data.contract_type == "8888_dugaar"){
					reportName1 = "8888_new.ozr";
			}
			else if(data.contract_type == "pps_3sar"){
					reportName1 = "pps_3_sar.ozr";
			}
			else if(data.contract_type == "selfcare") {	
				reportName1 = "selfcare.ozr";
				reportName2 = "selfcare_nemelt_zaalt.ozr";
			}
			else if(data.contract_type == "ger_internet"){
					reportName1 = "ger_internet.ozr";
			}
			else if(data.contract_type == "ger_internet_ps"){
					reportName1 = "ger_internet_ps.ozr";
			}
			else if(data.contract_type == "hosolson_tulburt"){
					reportName1 = "hosolson_tulburt.ozr";
			}else if(data.contract_type == "huuhdiin_tsag"){
					reportName1 = "huuhdiin_tsag.ozr";
			}else if(data.contract_type == "ps_dugaar_tusgai"){
					reportName1 = "ps_dugaar_tusgai.ozr";
			}else if(data.contract_type == "ger_internet_zuslan"){
					reportName1 = "ger_internet_zuslan.ozr";
			}else if(data.contract_type == "ger_internet_tusgai"){
					reportName1 = "ger_internet_tusgai.ozr";
			}
			else if(data.contract_type == "ger_internet_new"){
					reportName1 = "ger_internet_new.ozr";
			}
			else if(data.contract_type == "simple_device"){
					reportName1 = "simple_device.ozr";
			}
			else if(data.contract_type == "pps_customer"){
					reportName1 = "pps_customer.ozr";
			}
			else if(data.contract_type == "ps_expat"){
					reportName1 = "ps_expat.ozr";
			}else if(data.contract_type == "pps_expat"){
				reportName1 = "pps_expat.ozr";
			}
			else if(data.contract_type == "tur_salgalt"){
				reportName1 = "tur_salgalt.ozr";
			}
			else {
				reportName1 = "pro.ozr";
			}
			
			reportparam = ozbaseparam + "@oz@" + "connection.reportname=/gereenii form/" + reportName1;
			
			//console.log("jsondata :: " + data.contract_json);
			
			reportparam += "@oz@" + "connection.pcount=3";
			reportparam += "@oz@" + "connection.args1=user=" + data.contract_json;
			reportparam += "@oz@" + "connection.args2=login_user=" + login_user;
			reportparam += "@oz@" + "connection.args3=contract_type=" + data.contract_type;
			
			if (reportName2 != "") {					
				reportparam += "@oz@" + "viewer.childcount=1";
				reportparam += "@oz@" + "child1.viewer.progresscommand=true";
				reportparam += "@oz@" + "child1.connection.reportname=/gereenii form/" + reportName2;				
				reportparam += "@oz@" + "child1.connection.pcount=3";
				reportparam += "@oz@" + "child1.connection.args1=user=" + data.contract_json;
				reportparam += "@oz@" + "child1.connection.args2=login_user=" + login_user;
				reportparam += "@oz@" + "child1.connection.args3=contract_type=" + data.contract_type;	
			}
			
		} else {// Need to remove this block when new soap is ready
			OZViewer.Script("closeall");
			
			reportName1 = "";
			reportName2 = "";
			
			var jsonVal		 = JSON.parse(data.contract_json);
			var contractType = jsonVal.device[0].CONTRACT_TYPE;
			
			reportparam = "";
			if (fileType == "ozd") {
				reportparam = "connection.openfile=" + ozserverUrl + "/resources/upload/" + reportName1;
			} else {	
				
				if (contractType.toLowerCase() == "business_dugaar") {
					reportName1 = "ps_dugaar.ozr";
					reportName2 = "business_dugaar.ozr";
				}else if (contractType.toLowerCase() == "gar_utas") {
					reportName1 = "gar_utas.ozr";
				}else if (contractType.toLowerCase() == "ps_dugaar") {
					reportName1 = "ps_dugaar.ozr";
				}else {
					alert("There is no contract type for this prod_no");
				}
				
				reportparam = ozbaseparam + "@oz@" + "connection.reportname=/gereenii form/" + reportName1;
				
				if (data != '') {
					reportparam += "@oz@" + "connection.pcount=2";
					reportparam += "@oz@" + "connection.args1=contract_json=" + data.contract_json;
					reportparam += "@oz@" + "connection.args2=login_user=" + login_user;				
				}
				
				if (reportName2 != ""){
					reportparam += "@oz@" + "viewer.childcount=1";
					reportparam += "@oz@" + "child1.viewer.progresscommand=true";
					reportparam += "@oz@" + "child1.connection.reportname=/gereenii form/" + reportName2;				
					reportparam += "@oz@" + "child1.connection.pcount=2";
					reportparam += "@oz@" + "child1.connection.args1=contract_json=" + data.contract_json;
					reportparam += "@oz@" + "child1.connection.args2=login_user=" + login_user;
				}
			}
		}
	}else{
		reportparam = ozbaseparam + "@oz@" + "connection.reportname=/gereenii form/" + reportName1;
	}
	
	var params = "";
	params = reportparam + "@oz@" + p_oz.join("@oz@");
	
	OZViewer.CreateReportEx(params, "@oz@");
}

function onSearchContract() {
	var prodNoParam = $("input[id='searchStr']").val();
	var contractTypeParam = $("input[id='contract_type']").val();
		
	if (prodNoParam !== "") {
		
		var requestParams = new FormData();
		requestParams.append("prodNo", prodNoParam);
		requestParams.append("contractType", contractTypeParam);
		
		$.ajax({
			type : "POST",
			url : "${contract_info_search_url}",
			data : requestParams,
			dataType: 'json',
			processData: false,
			async : false,
			contentType: false,
			responseType : "application/json; charset=utf-8",
			success : function(data){
				//alert(data.contract_json);
				
				//Enable submit button
				$("#btnSubmit").show();
				
				prod_no = prodNoParam;
				init(data);// passing data to init function
			},
			error : function(request, status, error) {			
				if (request.status != '0') {
					//alert("code : " + request.status + "\r\nmessage : "	+ request.reponseText + "\r\nerror : " + error);
					$('div[id="tabstrip_loading"]').hide();
					alert("[W001] " + request.responseText);
				}
			}
		});
	} else {
		
		alert("Утасны дугаар болон гэрээний төрлийг оруулна уу.");
	}
}

function back_onClick() {
	history.back();
}

function prevReport() {
	
	OZViewer.Script("prev");
}

function nextReport() {

	OZViewer.Script("next");
}

function close() {
	history.go(-1);
}

function submitForm() {
	
	/* var TOTAL_PAGE = OZViewer.GetInformation("TOTAL_PAGE") ;
	 
    for (var i=1; i<=TOTAL_PAGE; i++) {
    	if(OZViewer.GetInformation("INPUT_CHECK_VALIDITY_PAGE_AT="+i) == "valid"){
    		bValid = true;
    	}else{
    		bValid = false;
    		break;
    	}
    }
    
    if (bValid){ */
    	var params = "";
		params += "\n" + "viewer.exportcommand=true";
		params += "\n" + "export.mode=silent";
		params += "\n" + "export.confirmsave=false";
		params += "\n" + "export.path=/sdcard";
		params += "\n" + "ozd.filename=export_test.ozd";
		params += "\n" + "ozd.saveall=true";
		params += "\n" + "export.format=ozd";
		params += "\n" + "export.saveonefile=true";
		
	 	// Call OZExportMemoryStreamCallBack
		OZViewer.ScriptEx("save_memorystream", params, "\n");
    //}
    
	//Show loading progress bar
	$('div[id="tabstrip1"]').hide();	
	$('div[id="tabstrip_loading"]').show();
}

function OZExportMemoryStreamCallBack_OZViewer(outputdata) {
	
	if(outputdata == "{}" ){
		console.log("Fail to Export Memory Stream ");
	}else{
		console.log("Success to Export Memory Stream ");

		var obj = eval('(' + outputdata + ')');
		//console.log(event.outputdata);
		var value = null;
		var formdata = new FormData();
		var index = 1;
		
		for(var key in obj){
			//file_name = key;
			value = obj[key];
			//console.debug("file_name="+key);
			console.log(key.replace("/sdcard/",""));
			formdata.append("file_name_" + index, key.replace("/sdcard/",""));
			formdata.append("file_stream_" + index, value);
			index++;
		}						
				
		if(prod_no != null && prod_no != 'undefined') {
			formdata.append("prod_no", prod_no);
		}
		
		$.ajax({
			type : "POST",
			url : "./upload-file-w",
			data : formdata,
			dataType: 'text',
			processData: false,
			contentType: false,
			success : function(data){
				var jsonObj = JSON.parse(data);
				var return_filename = jsonObj.return_filename;
				
				var inputXML = OZViewer.GetInformation("INPUT_XML_ALL");
				var xmlformdata = new FormData();
				if(inputXML != null && inputXML != 'undefined') {
					xmlformdata.append("contract_xml_data", inputXML);
					xmlformdata.append("ozd_filename", return_filename);
					xmlformdata.append("file_type", fileType);
					
					//alert("inputXML:"+inputXML);
					
					$.ajax({
						type : "POST",
						url : "./w-application-ozviewer/submit",
						data : xmlformdata,
						dataType: 'text',
						processData: false,
						contentType: false,
						success : function(data){
							alert("Гэрээ амжилтай хадгалагдлаа.");
							/* alert("Submit completed"); */
							if (data != "" && data != null) {
								var dataObj = JSON.parse(data);
								if(dataObj.contract_type != "device_sale" && 
										dataObj.contract_type != "pps" && 
										dataObj.contract_type != "deposit_refund" &&
										dataObj.contract_type != "owner_ship") {
									// Send email
									$.ajax({
										type : "POST",
										url : "./send-email/" + dataObj.id,
										data : "",
										dataType: 'text',
										processData: false,
										contentType: false,
										success : function(data){
											console.log("Success to send email");
										},
										error : function(request, status, error) {
											console.log("Failed to send email");
										}
									});
								}
							}

							close();
						},
						error : function(request, status, error) {
							if (status == 'error') {
								//alert("code : " + request.status + "\r\nmessage : "	+ request.reponseText + "\r\nerror : " + error);
								
								
								alert("[W003] " + request.responseText);
								close();
							}
						}
					});
				} else {
					alert("InputXMLData is null");
				}
			},
			error : function(request, status, error) {
				if (status == 'error') {
					//alert("code : " + request.status + "\r\nmessage : "	+ request.reponseText + "\r\nerror : " + error);
					$('div[id="tabstrip_loading"]').hide();
					$('div[id="tabstrip1"]').show();
								
					alert("[W002] " + request.responseText);
				}
			}
		});
	}
}

$(document).ready(function() {

	if(prod_no !== null && prod_no !== "undefined" && prod_no !== "") {
		$("#btnSubmit").show();
	}
	
    $("#contract_type").kendoComboBox({
         dataTextField: "text",
         dataValueField: "value",
         dataSource: [
             {text: "Гэрээний төрөл", value:""},
			
             {text: "PS дугаар", value:"ps_dugaar"},
             {text: "Гар утасны лизинг", value:"gar_utas_new"},
			 {text: "Нэр шилжүүлэх", value:"owner_ship"},
			 {text: "Бизнес дугаар", value:"business_dugaar_new"},
			 {text: "Хэрэглэгчийн гэрээ", value:"contract_user"},
			 {text: "Барьцаа буцаах", value:"deposit_refund"},
			 {text: "Гар утас бэлнээр", value:"device_sale"},
			 {text: "Азын дугаар", value:"aziin_dugaar"},
			 {text: "Багц өөрчлөх PPS", value:"smart_chg"},
			 {text: "Багц өөрчлөх", value:"bagts_rsv"},
			 {text: "8888 дугаар", value:"8888_dugaar"},
			 {text: "PPS 3 сар", value:"pps_3sar"},
			 {text: "Self care", value:"selfcare"},
			 {text:"Univision гурвалсан", value:"univision"},
			 {text:"Гэр интернет", value:"ger_internet_new"},
			 {text:"Гэр интернет зуслан", value:"ger_internet_zuslan"},
			 {text:"Гэр интернет(ps)", value:"ger_internet_ps"},
			 {text:"Гэр интернет тусгай", value:"ger_internet_tusgai"},
			 {text:"Хосолсон төлбөрт", value:"hosolson_tulburt"},
			 {text:"Хүүхдийн цаг", value:"huuhdiin_tsag"},
			 {text:"PS дугаар тусгай", value:"ps_dugaar_tusgai"},
			 {text:"АШИД ББСБ", value:"simple_device"},
			 {text:"Урьдчилсан төлбөрт үйлчилгээний гэрээ", value:"pps_customer"},
			 {text:"PS Expat", value:"ps_expat"},
			 {text:"PPS Expat", value:"pps_expat"},
			  {text:"Түр салгалт", value:"tur_salgalt"}
			 
         ],
         height: 250,
         filter: "contains",
         suggest: true,
         index: 0
    });
    
    $("#searchStr").keyup(function(event){
        if(event.keyCode == 13){
            $("#searchContract").click();
        }
    });
});

$(window).load(function(){
	if (fileType == "ozd") {
		document.getElementById("searchContract").disabled = true;
		document.getElementById("searchStr").disabled = true;
	}
});
</script>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onload="" style="height:100%; overflow:hidden;">
	<!-- push notificaion -->
	<span id="jNotification"></span>
	<!-- //push notificaion -->
	
	<div id="ozdiv">
		<div data-role="view" id="m-ozview" data-title="Mobile OZView">
		</div>

		<span id="notification" style="display: none;"></span>
		<span id="notification"></span>
		<c:if test="${model.user_role eq 'ROLE_TELLER' || model.user_role eq 'ROLE_REPORTER'}">
			<div style="margin: 10px">
			    <input id="searchStr" placeholder="Сонгосон дугаар ..." value="" class="k-textbox"/>
			    <input id="contract_type" style="width: 200px;" />
			    <button id="searchContract" name="searchContract" class="k-button" onclick="onSearchContract()" >Хайх</button>
			    	
				<a class="k-button" href="Logout" id="logOut" >Гарах</a>
				<!-- style="background-color: green; color: white" -->
			</div>
		</c:if>
		<footer data-role="footer" class="km-footer">
			<div data-role="tabstrip" class="km-widget km-tabstrip"
				style="word-spacing: normal" id="tabstrip1">
				<a data-icon="reply" href="javascript:back_onClick(); "
					class="km-button km-state-active" data-role="tab"><span
					class="km-icon km-reply" style="cursor: default;"></span><span
					class="km-text">Буцах</span></a>
				<a data-icon="revind"
					href="javascript:prevReport()" class="km-button km-state-active"
					data-role="tab"><span class="km-icon km-rewind"
					style="cursor: default;"></span><span class="km-text">Өмнөх</span></a> <a
					data-icon="arrow-e" href="javascript:nextReport()"
					class="km-button km-state-active" data-role="tab"><span
					class="km-icon km-fastforward"></span><span class="km-text">Дараагийх</span></a>
				<a data-icon="action" id="btnSubmit"
					href="javascript:submitForm()" class="km-button km-state-active"
					data-role="tab" style="display: none"><span
					class="km-icon km-action" style="cursor: default;"></span><span
					class="km-text">Баталгаажуулах</span></a>
			</div>
			<div data-role="tabstrip" class="km-widget km-tabstrip" style="word-spacing:normal; display:none;" id="tabstrip_loading">
		       <img src="./resources/images/loading.gif" style="height:30px;">
		       <div class="km-text"><b>Сүлжээний хурднаас хамаарч удах магадлалтай тул түр хүлээнэ үү ...</b></div> 
		   	</div>
		   	
			<div id="OZViewer"></div>
		  
		</footer>
	</div>
	<form>
		<input type="hidden" id="strPDF" name="strPDF">
	</form>
	
	<script>
			
	
		function SetOZParamters_OZViewer() {
			var oz;
			if(navigator.appName.indexOf("Microsoft") != -1) {
				oz = window["OZViewer"];
			} else {
				oz = document.getElementById("OZViewer")
			}
			
			if(fileType == "ozd"){
				oz.sendToActionScript("connection.openfile", 	ozserverUrl + "/resources/upload/" + reportName1);
			}else{
				oz.sendToActionScript("global.use_preview_progressbar",	"true");
				
				oz.sendToActionScript("connection.servlet", 			ozserverUrl + "/server");
				oz.sendToActionScript("connection.reportname", 			"/gereenii zaalt/handset_lease_geree.ozr");
				
				
				oz.sendToActionScript("viewer.childcount", 				"7");
				oz.sendToActionScript("child1.connection.servlet", 		ozserverUrl + "/server");
				oz.sendToActionScript("child1.connection.reportname", 	"/gereenii zaalt/Bussines_dugaar_geree_12.ozr");
				
				oz.sendToActionScript("child2.connection.servlet", 		ozserverUrl + "/server");
				oz.sendToActionScript("child2.connection.reportname", 	"/gereenii zaalt/Bussines_dugaar_geree_24.ozr");
				
				oz.sendToActionScript("child3.connection.servlet", 		ozserverUrl + "/server");
				oz.sendToActionScript("child3.connection.reportname", 	"/gereenii zaalt/PS_geree.ozr");
				
				oz.sendToActionScript("child4.connection.servlet", 		ozserverUrl + "/server");
				oz.sendToActionScript("child4.connection.reportname", 	"/gereenii zaalt/tur_ashiglah_utas_contract.ozr");
				
				oz.sendToActionScript("child5.connection.servlet", 		ozserverUrl + "/server");
				oz.sendToActionScript("child5.connection.reportname", 	"/gereenii zaalt/selfcare-contract.ozr");
				
				oz.sendToActionScript("child6.connection.servlet", 		ozserverUrl + "/server");
				oz.sendToActionScript("child6.connection.reportname", 	"/gereenii zaalt/form_8888_new_contract.ozr");
				
				oz.sendToActionScript("child7.connection.servlet", 		ozserverUrl + "/server");
				oz.sendToActionScript("child7.connection.reportname", 	"/gereenii zaalt/form_aziin_dugaar_contract.ozr");
			}

			// 2016.08.12 : added by ryu. start
			oz.sendToActionScript("global.concatpage",				"true");
			oz.sendToActionScript("global.inheritparameter",		"true");
			oz.sendToActionScript("global.language",				"en/US");
			/* oz.sendToActionScript("global.use_preview_progressbar",	"true"); */
			oz.sendToActionScript("information.debug",				"true");
			// 2016.08.12 : added by ryu. end
		
			oz.sendToActionScript("viewer.showtree", 		"true");
			oz.sendToActionScript("viewer.isframe", 		"false");
			oz.sendToActionScript("viewer.progresscommand", "true");
			oz.sendToActionScript("viewer.bgcolor", 		"ffffff");
			oz.sendToActionScript("viewer.usestatusbar", 	"false");
			
			oz.sendToActionScript("toolbar.save", 			"false");
			oz.sendToActionScript("toolbar.savedm", 		"false");
			
			oz.sendToActionScript("repository_agent.clientcachetype", 		"memory");
		}
		//start_ozjs("OZViewer",ozserverUrl+"/resources/html5viewer/");
		
		var NotoSans = false;
		var NotoSansBold = false;
		
		fontSpy('Noto Sans', {
			success: function() {
				NotoSans = true;								
			},
			failure: function() {
				console.log("Noto Sans loading failed");
			}
		});

		
		fontSpy('NotoSans Bold', {
			success: function() {
				NotoSansBold = true;		
				if((NotoSans == true) && (NotoSansBold == true)) {
					start_ozjs("OZViewer",ozserverUrl+"/resources/html5viewer/");
				} else {
					console.log('fonts loading failed. Noto Sans == ' + NotoSans + ', Noto Sans Bold == ' + NotoSansBold );
				}
				
			},
			failure: function() {
				console.log("Noto Sans Bold loading failed");
			}
		});

	</script>
</body>

<style scoped>
.km-ios #popover .km-view-title, .km-ios #popover-people .km-view-title
	{
	color: #fff;
	text-shadow: 0 -1px rgba(0, 0, 0, .5);
}

.km-ios #popover .km-navbar, .km-root>*>#popover>.km-content {
	background: -webkit-gradient(linear, 50% 0, 50% 100%, color-stop(0, rgba(255, 255,
		255, 0.35)), color-stop(0.5, rgba(255, 255, 255, 0.1)),
		color-stop(0.5, rgba(255, 255, 255, 0)),
		color-stop(1, rgba(255, 255, 255, 0))) #000;
}

.km-ios #popover .km-navbar .km-button {
	background-color: #000;
}

.km-localsave:after, .km-localsave:before {
	content: "\e096";
}

.km-send:after, .km-send:before {
	content: "\e0c6";
}

.km-human:after, .km-human:before {
	content: "\e0bc";
}

.km-feedemail:after, .km-feedemail:before {
	content: "\e099";
}

.km-tabstrip .km-button .km-icon {
	width: 2.5rem;
	height: 1.7rem;
	font-size: 1.8rem;
}

.km-icon:after, .km-icon:before, .km-contactadd:after, .km-contactadd:before,
	.km-rowdelete:after, .km-rowdelete:before, .km-rowinsert:after,
	.km-rowinsert:before, .km-detaildisclose:after, .km-detaildisclose:before,
	.km-loading:after, .km-filter-wrap:before {
	text-align: center;
}

.km-tabstrip .km-text {
	font-size: 0.7em
}
</style>
</html>
