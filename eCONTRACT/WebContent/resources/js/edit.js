// 쿠키값 가져오기
function getCookie(key) {
	var cook = document.cookie + ";";
	var idx = cook.indexOf(key, 0);
	var val = "";
	if (idx != -1) {
		cook = cook.substring(idx, cook.length);
		begin = cook.indexOf("=", 0) + 1;
		end = cook.indexOf(";", begin);
		val = unescape(cook.substring(begin, end));
	}
	return val;
}
// 쿠키값 설정
function setCookie(name, value, expiredays) {
	var today = new Date();
	today.setDate(today.getDate() + expiredays);

	document.cookie = name + "=" + escape(value) + "; path=/; expires="
			+ today.toGMTString() + ";";
}

// 쿠키삭제관리
function delLogCookies() {
	OZTotoFramework.storage.put("loginInfo","checkVal","N");
	location.href="logout";
}

// 자동 로그인 체킹
function chkAuto(v) {
	if(v == 1){
		var id = $('#username').val();
		var pwd = $('#password').val();
		OZTotoFramework.storage.put("loginInfo","userName",id);
		OZTotoFramework.storage.put("loginInfo","password",pwd);
	}
	
	if ($('#autoChk').is(":checked")) {
		OZTotoFramework.storage.put("loginInfo","checkVal","Y");
	} else {
		OZTotoFramework.storage.put("loginInfo","checkVal","N");
	}
}

function getTemplate(id, data) {
	var fullId= '#' + id;
	
	var gridData = $(fullId).getKendoGrid().dataSource;
	var currentPage = gridData.page();
	var pageSize = gridData.pageSize();
	var num = gridData.indexOf(data)+1;
	
    /*return (pageSize*currentPage) - (pageSize - num);*/
	return num;
}

// trim 처리
String.prototype.trim = function() {
    return this.replace(/(^\s*)|(\s*$)/gi, "");
}


var tooltip_arr = [];
tooltip_arr.push(".k-grid-add");
tooltip_arr.push(".k-grid-edit");
tooltip_arr.push(".k-grid-delete");
tooltip_arr.push(".k-grid-update");
tooltip_arr.push(".k-grid-cancel");
tooltip_arr.push(".k-scheduler-update");
tooltip_arr.push(".k-scheduler-cancel");
tooltip_arr.push(".k-scheduler-delete");
tooltip_arr.push("#auto_Btn");
tooltip_arr.push(".pdfdown");
tooltip_arr.push(".print");
tooltip_arr.push(".k-plus");
tooltip_arr.push(".k-grid-Export");
tooltip_arr.push(".k-upload-button");
tooltip_arr.push(".k-grid-Contract");
tooltip_arr.push(".k-grid-JobHistory");
tooltip_arr.push("#new_Btn");
tooltip_arr.push("#pdf_Btn");
tooltip_arr.push(".k-grid-NewContract");
tooltip_arr.push("#submit");
tooltip_arr.push("#teamBtn");







var tooltip_msg = [];
tooltip_msg.push("New");
tooltip_msg.push("Edit");
tooltip_msg.push("Delete");
tooltip_msg.push("Save");
tooltip_msg.push("Cancle");
tooltip_msg.push("Update");
tooltip_msg.push("Cancle");
tooltip_msg.push("Delete");
tooltip_msg.push("Auto Schedule");
tooltip_msg.push("Download");
tooltip_msg.push("Print");
tooltip_msg.push("Detail");
tooltip_msg.push("Export");
tooltip_msg.push("Upload");
tooltip_msg.push("Contract Details");
tooltip_msg.push("Payment History");
tooltip_msg.push("New");
tooltip_msg.push("Download");
tooltip_msg.push("Edit");
tooltip_msg.push("Submit");
tooltip_msg.push("Team");

$.each(tooltip_arr, function(idx, val) {
	$(document).kendoTooltip({
	    filter: val, // if we filter as td it shows text present in each td of the table
	    content: function (e) {
	        return tooltip_msg[idx];
	    }, //kendo.template($("#template").html()),
	    width: 80,
	    position: "top"
	}).data("kendoTooltip");
});

$( document ).ajaxSend(function() {
	if($('#modal_loading') != null)
	{
		$("#modal_loading").addClass("loading");
	}
});

$( document ).ajaxStop(function() {
	if($('#modal_loading') != null)
	{
		$("#modal_loading").removeClass("loading");
	}
});
