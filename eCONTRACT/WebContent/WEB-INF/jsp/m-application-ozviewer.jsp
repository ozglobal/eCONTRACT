<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<c:url value="/m-application-ozviewer/seach" var="contract_info_search_url" />
<c:url value="/m-application-ozviewer/decline" var="contract_info_decline_url" />

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=IE8">
<meta charset="utf-8" />
<!-- mobile meta (hooray!) -->
<meta name="HandheldFriendly" content="True">
<meta name="MobileOptimized" content="320">
<meta name="viewport"
	content="user-scalable=no, initial-scale=1, maximum-scale=1, minimum-scale=1, width=device-width">

<title>OZ e-Form View</title>
<!-- Load all CSS through interface because using LESS (lesscss.org) -->
<link href="./resources/css/kendo/kendo.mobile.exported.css" rel="stylesheet" />
<link href="./resources/css/kendo/kendo.common.min.css" rel="stylesheet" />
<link href="./resources/css/kendo/kendo.default.min.css"
	rel="stylesheet" />
<link href="./resources/css/kendo/kendo.dataviz.min.css"
	rel="stylesheet" />
<link href="./resources/css/kendo/kendo.dataviz.default.min.css"
	rel="stylesheet" />
<link href="./resources/css/kendo/kendo.mobile.all.min.css"
	rel="stylesheet" />
<link type="text/css" href="./resources/css/noti.css" rel="stylesheet" />

<!-- notification css -->
<link rel="stylesheet" href="./resources/css/kendo/kendo.common.min.css" />
<link rel="stylesheet" href="./resources/css/kendo/kendo.default.min.css" />
<link rel="stylesheet" href="./resources/css/kendo/kendo.dataviz.min.css" />
<link rel="stylesheet" href="./resources/css/kendo/kendo.dataviz.default.min.css" />
<!-- //notification css -->

<script src="./resources/js/jquery/jquery.min.js"></script>
<script src="./resources/js/jquery/jquery.form.min.js"></script>
<script src="./resources/js/kendo/kendo.all.min.js"></script>


<script type="text/javascript">
var login_user  = "${model.login_user}";
var user_role   = "${model.user_role}";
var reportName = "${model.report_name}";
var prodNo = "";
var contractId = "";
var fileType 	= "";
var input_XML 	= "";
</script>

<script src="./resources/js/date.js"></script>
<script src="oztotoframework.js"></script> 
<script src="./resources/js/common.js"></script>
<script src="./resources/js/ozviewerparam.js"></script>

<script type="text/javascript">
	var isCreateView = false;
	//var onCreated = false;
	
	var ozviewer = new OZTotoFramework.OZViewer();
	
	function init(data) {

		ozviewer_init('bottom', data);
		ozviewer_visible();
		
		// 2016.11.16 (Ryu)
		if (reportName !== "" && reportName !== undefined && reportName !== null) {
			$("#btnDecline").show();
			$("#btnSubmit").show();
		}
		//$('div[id="tabstrip_loading"]').hide();
	}

	function ozviewer_dispose() {
		ozviewer.dispose();
		ozviewer.setVisible(false);
		isCreateView = false;
	}

	function ozviewer_init(target, data) {

		var reportparam = "";
		if (reportName !== "" && reportName !== undefined && reportName !== null) {
			reportparam = ozbaseparam + "&" +"connection.openfile=" + ozserverUrl + "/resources/upload/" + reportName;
			reportparam += "&" + "global.concatpage=true";
			reportparam += "&" + "global.inheritparameter=true";
			reportparam += "&" + "global.language=en/US";
			reportparam += "&" + "viewer.useprogressbar=false";
		} else {
			reportparam = ozbaseparam + "&" + "connection.reportname=/gereenii zaalt/Contract_all.ozr";
			/*reportparam = ozbaseparam + "&" + "connection.reportname=/gereenii zaalt/handset_lease_geree.ozr";
			
  			reportparam += "&" + "viewer.childcount=7";
  			reportparam += "&" + "child1.connection.servlet=" + ozserverUrl + "/server";
  			reportparam += "&" + "child1.connection.reportname=/gereenii zaalt/Bussines_dugaar_geree_12.ozr";
  			
  			reportparam += "&" + "child2.connection.servlet=" + ozserverUrl + "/server";
  			reportparam += "&" + "child2.connection.reportname=/gereenii zaalt/Bussines_dugaar_geree_24.ozr";
  			
  			reportparam += "&" + "child3.connection.servlet=" + ozserverUrl + "/server";
  			reportparam += "&" + "child3.connection.reportname=/gereenii zaalt/PS_geree.ozr";
  			
  			reportparam += "&" + "child4.connection.servlet=" + ozserverUrl + "/server";
  			reportparam += "&" + "child4.connection.reportname=/gereenii zaalt/tur_ashiglah_utas_contract.ozr";
  			
  			reportparam += "&" + "child5.connection.servlet=" + ozserverUrl + "/server";
  			reportparam += "&" + "child5.connection.reportname=/gereenii zaalt/selfcare-contract.ozr";
  			
			reportparam += "&" + "child6.connection.servlet=" + ozserverUrl + "/server";
			reportparam += "&" + "child6.connection.reportname=/gereenii zaalt/form_8888_new_contract.ozr";
			
			reportparam += "&" + "child7.connection.servlet=" + ozserverUrl + "/server";
			reportparam += "&" + "child7.connection.reportname=/gereenii zaalt/form_aziin_dugaar_contract.ozr";*/
			
			reportparam += "&" + "global.use_preview_progressbar=true";
			
			
		}
		
		// Mobile toolbar hide. 2016.05.12 (Ryu)
		reportparam += "&" + "viewer.usetoolbar=false";
		
		// only mobile. 2016.08.12 (Ryu)
		// commented out: no need anymore (John, 2022.04.22)
		// reportparam += "&" + "viewer.compatibility=!input_rendering_over_70";
		
		var params = "";
		params = reportparam + "&" + p_oz.join("&");

		// added to solve hidden header problem on android 11  (John, 2022.04.22)
		var targetDIV = document.getElementById('__oz__div__');
		if(targetDIV == undefined || targetDIV == null) {
			var elemDiv = document.createElement('div');
			elemDiv.id = '__oz__div__';
			document.body.appendChild(elemDiv);
		}
		
		ozviewer.createViewer(target, params, "&");
	}

	function ozviewer_visible() {
		ozviewer.setVisible(true);
	}

	function back_onClick() {
		ozviewer_dispose();
		history.back();
	}

	function ozviewer_setvisible() {
		var visible = ozviewer.isVisible();
		ozviewer.setVisible(!visible);
	}

	function prevReport() {		
		ozviewer.script("prev");
	}

	function nextReport() {	
		ozviewer.script("next");
	}

	function submitForm() {
		ozviewer.getInformation("REPORT_COUNT", function(reportCount) {
			getInformationExec(0, reportCount);
		});	
	}
	
	function getInformationExec(i, reportCount) {
		ozviewer.getInformation("INPUT_CHECK_VALIDITY_AT="+i, function(result2) {
			
			if(result2 == "valid"){
				bValid = true;
			}else{
				bValid = false;
				
				// Add OZTotoIndicator Stop. 2016.11.16 (Ryu)
				OZTotoFramework.indicator.stop();
			}

			var j = i + 1;
			if (bValid && j < reportCount) {
				getInformationExec(j, reportCount);
				return;
			}
			
			if (bValid && j >= reportCount) {
				contractSave();
			}
		});
	}
	
	// Add Indicator Start. 2016.11.16 (Ryu)
	function CallIndicator(msg){
		var options = {"backgroundAlpha":"80", "textSize":"20", "text":msg};
		OZTotoFramework.indicator.setOption(options);
		OZTotoFramework.indicator.start();
	}
	function StopIndicator(){
		OZTotoFramework.indicator.stop();
	}
	
	function contractSave(){
		startTime = getTimeStamp(); // For debugging
		
		var params = "";
		params += "\n" + "pdf.savecomment=true";
		params += "\n" + "pdf.fontembedding=true";
		params += "\n" + "font.fontnames=font1,font2";
		params += "\n" + "font.font1.url=res://NotoSans-Regular.ttf";
		params += "\n" + "font.font1.name=NotoSans-Regular";
		params += "\n" + "font.font2.url=res://NotoSans-Bold.ttf";
		params += "\n" + "font.font2.name=NotoSans-Bold";
		
		params += "\n" + "viewer.exportcommand=true";
		params += "\n" + "export.mode=silent";
		params += "\n" + "export.confirmsave=false";
		params += "\n" + "export.path=/sdcard";
		params += "\n" + "pdf.filename=export_test.pdf";
		params += "\n" + "ozd.filename=export_test.ozd";
		params += "\n" + "ozd.saveall=true";
		params += "\n" + "export.format=pdf,ozd";
		params += "\n" + "export.saveonefile=true";
		
		ozviewer.getInformation("INPUT_XML_ALL", function(inputXml) {
			input_XML = inputXml;
		});
		
		//ozviewer.getInformation("INPUT_JSON_ALL", function(inputJson){
			//var jsonObj = JSON.parse(inputJson.replace(/'/gi,"\'"));
			//customer_email = jsonObj.email;
			//customer_sign = jsonObj.customer_sign;

		//});
	
		// Add OZTotoIndicator. 2016.11.16 (Ryu)
		//CallIndicator("Export is Processing...");
		
		// Call OZExportMemoryStreamCallBack
		//exportedStart = getTimeStamp(); // For debugging
		ozviewer.scriptEx("save_memorystream", params, "\n",function(res){
		});
		
		//Show loading progress bar
		//$('div[id="tabstrip1"]').hide();	
		//$('div[id="tabstrip_loading"]').show();
	}
	
	function onDecline() {
		var params = "contract_id=" + contractId;
	
		$.ajax({
			type : "POST",
			url : "${contract_info_decline_url}",
			data : params,
			async : false,
			responseType : "application/json",
			success : function(data){
				alert("Амжилттай татгалзлаа.");
				/* alert("Decline is Success!"); */
				history.go(-1);
			},
			error : function(request, status, error) {
				if (request.status != '0') {
					//alert("code : " + request.status + "\r\nmessage : "	+ request.reponseText + "\r\nerror : " + error);
					alert("Татгалзах үйлдэл амжилтгүй боллоо.");
					/* alert("Decline is Failed!"); */
				}
			}
		});
	}
	 
	function onSearchContract() {
		
		var prodNoParam = $("input[id='searchStr']").val();
		//var contractTypeParam = $("input[id='contract_type']").val();
		var contractTypeParam = $("select[id='contract_type']").val();
		
		var requestParams = new FormData();
		requestParams.append("prod_no", prodNoParam);
		requestParams.append("contract_type", contractTypeParam);
		
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
				reportName = data.ozd_file_name;
				prodNo = data.prod_no;
				contractId = data.contract_id;
				init(data);
			},
			error : function(request, status, error) {
				if (request.status != '0') {
					//alert("code : " + request.status + "\r\nmessage : "	+ request.reponseText + "\r\nerror : " + error);				
					alert("[M002] " + request.responseText);
				}
			}
		});
	}
	 
	function close() {
		history.go(-1);
	}
	
	$(document).ready(function() {
		
		/* $("#contract_type").kendoComboBox({
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
				 {text: "Expat", value:"ps_expat"}
	         ],
	         height: 74,
	         filter: "contains",
	         suggest: true,
	         index: 0
	    }); */
		
	    var select = document.getElementById("contract_type");
	    var text = '{ "contractType" : [' +
	    '{ "text":"Гэрээний төрөл" ,			"value":"" },' +
	
	    '{ "text":"PS дугаар" ,					"value":"ps_dugaar" },' +
	    '{ "text":"Гар утасны лизинг" , 		"value":"gar_utas_new" },' +
	    '{ "text":"Нэр шилжүүлэх" , 			"value":"owner_ship" }, '+
		'{ "text":"Бизнес дугаар" , 			"value":"business_dugaar_new" },'+
		
		'{ "text":"Хэрэглэгчийн гэрээ" ,		"value":"contract_user" },' +
		'{ "text":"Барьцаа буцаах" ,			"value":"deposit_refund" },' +
		'{ "text":"Гар утас бэлнээр" ,			"value":"device_sale" },' +
		'{ "text":"Азын дугаар" ,				"value":"aziin_dugaar" },' +
	
		'{ "text":"Багц өөрчлөх PPS" ,	"value":"smart_chg" },' +
		'{ "text":"Багц өөрчлөх" ,				"value":"bagts_rsv" },' +
		
		'{ "text":"8888 дугаар" ,				"value":"8888_dugaar" },' +
		'{ "text":"PPS 3 сар" ,					"value":"pps_3sar" },' +
		'{ "text":"Self care" ,					"value":"selfcare" },'+
		'{ "text":"Univision гурвалсан" ,		"value":"univision" },'+
		'{ "text":"Гэр интернет" ,		"value":"ger_internet_new" },'+
		'{ "text":"Гэр интернет зуслан" ,		"value":"ger_internet_zuslan" },'+
		'{ "text":"Гэр интернет(ps)" ,		"value":"ger_internet_ps" },'+
		'{ "text":"Гэр интернет тусгай" ,		"value":"ger_internet_tusgai" },'+
		'{ "text":"Хосолсон төлбөрт" ,		"value":"hosolson_tulburt" },'+
		'{ "text":"Хүүхдийн цаг" ,		"value":"huuhdiin_tsag" },'+
		'{ "text":"PS дугаар тусгай" ,		"value":"ps_dugaar_tusgai" },'+
		'{ "text":"АШИД ББСБ" ,		"value":"simple_device" },'+
		'{ "text":"Урьдчилсан төлбөрт үйлчилгээний гэрээ" , "value":"pps_customer" },'+
		'{ "text":"PS Expat" , "value":"ps_expat" },'+
		'{ "text":"PPS Expat" , "value":"pps_expat" }'+
		' ]}';
		var obj = JSON.parse(text);
	    
		select.innerHTML = "";
		
		for(var i = 0; i < obj.contractType.length; i++) {
		    select.innerHTML += "<option value=\"" + obj.contractType[i].value + "\">" + obj.contractType[i].text+ "</option>";
		}
		
		initOZViewer();
	});
	
	$(window).load(function() {		
		ozviewer_init('bottom', '');
		ozviewer_visible();
	});
</script>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="">
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
			    <!-- <input type="number" id="searchStr" placeholder="Сонгосон дугаар ..." value="" class="k-textbox"/> -->
			    <!-- <input id="contract_type" style="width: 200px;" /> -->
			    <input type="number" id="searchStr" placeholder="Сонгосон дугаар ..." value="" style="height:34px; font-size:16px;"/>
			    <select id="contract_type" style="height:34px; font-size:16px;"></select>
			    <button id="searchContract" name="searchContract" class="k-button" style="background-color: green; color: white" onclick="onSearchContract()" >Хайх</button>
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
				<a data-icon="action" id="btnDecline"
					href="javascript:onDecline()" class="km-button km-state-active"
					data-role="tab" style="display: none"><span 
					class="km-icon km-action" style="cursor: default;"></span><span
					class="km-text">Татгалзах</span></a>
				<a data-icon="action" id="btnSubmit"
					href="javascript:submitForm()" class="km-button km-state-active"
					data-role="tab" style="display: none"><span
					class="km-icon km-action" style="cursor: default;"></span><span
					class="km-text">Баталгаажуулах</span></a>
			</div>
			<!-- <div data-role="tabstrip" class="km-widget km-tabstrip" style="word-spacing:normal; display:none;" id="tabstrip_loading">
		       <img src="./resources/images/loading.gif" style="height:30px;">
		       <div class="km-text"><b>It will take seconds depend on speed of network. Please Wait ...</b></div> 
		    </div> -->
		</footer>
	</div>
	<form>
		<input type="hidden" id="strPDF" name="strPDF">
	</form>
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
