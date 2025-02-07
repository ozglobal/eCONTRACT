// For testing;
var startTime = "";
var exportedStart = "";
var exportedEnd = "";
var uploadStart = "";
var uploadEnd = "";
var endTime = "";

var jNotification;
var ozviewer = null;

$(document).ready(function(){	
	jNotification = $("#jNotification").kendoNotification({
		 position: {
		        top: 10,
		        right: 10
		    },
		 autoHideAfter: 0,
		 width: $(document).width()-20,
		stacking: "down"
	}).data("kendoNotification");
	
	/*OZTotoFramework.addEventListener("pushMsg", function(event){
		var msg = event.pushMsg.replace(/\n/gi,"<br/>");
		jNotification.show(msg, "info");
	});*/

});

$(window).on( "orientationchange", function() {
	jNotification = $("#jNotification").kendoNotification({
		position: {
	        top: 10,
	        right: 10
	    },
	 autoHideAfter: 0,
	 width: $(document).width()-20,
	stacking: "down"
	}).data("kendoNotification");
});

var isMobile = {
    Android: function() {
        return /Android/i.test(navigator.userAgent);
    },
    BlackBerry: function() {
        return /BlackBerry/i.test(navigator.userAgent);
    },
    iOS: function() {
        return /iPhone|iPad|iPod/i.test(navigator.userAgent);
    },
    Windows: function() { 
        return /IEMobile/i.test(navigator.userAgent);
    },
    any: function() {
        return (isMobile.Android() || isMobile.BlackBerry() || isMobile.iOS() || isMobile.Windows());
    }
};

function sleep(delay) {
    var start = new Date().getTime();
    while (new Date().getTime() < start + delay);
}

function shownotimsg(command, msg)
{
	//alert(event.message);
	jNotification.show(command + " "+ msg);
}

function initOZViewer(){
	
	if(isMobile.any()){
	//ozviewer = new OZTotoFramework.OZViewer();
	console.log("====== init ========");
	
	ozviewer.addEventListener("OZPostCommand", function(event){
	    //event.cmd, event.msg
	});
	ozviewer.addEventListener("OZPrintCommand", function(event){
	    //event.msg, event.code, event.reportname, event.printername, event.printcopy, event.printedpage, event.printrange, event.username, event.drivername, event.printpagesrange
	});
	ozviewer.addEventListener("OZExportCommand", function(event){
	    //event.code, event.path, event.filename, event.pagecount, event.filepaths
	});
	ozviewer.addEventListener("OZProgressCommand", function(event){
	
		console.log("event.step : "+event.step+"event.state :"+event.state);
		if(event.step == 4 && event.state == 2)
		{
			ozviewer.script("home_all");
			if(!isCreateView)
			{
				console.log("isCreateView false");
				isCreateView = true;
				//ozviewer_trigger();
			}
			
		}

	});
	ozviewer.addEventListener("OZCommand", function(event){
	    //event.code, event.args
	});
	ozviewer.addEventListener("OZErrorCommand", function(event){
	    //event.code, event.message, event.detailmessage, event.reportname
	});
	ozviewer.addEventListener("OZExitCommand", function(){
	});
	ozviewer.addEventListener("OZMailCommand", function(event){
	    //event.code
	});
	ozviewer.addEventListener("OZUserActionCommand", function(event){
  
	});
	ozviewer.addEventListener("OZLinkCommand", function(event){
	    //event.docindex, event.componentname, event.usertag, event.uservalue
	});
	ozviewer.addEventListener("OZBankBookPrintCommand", function(event){
	    //event.datas
	});
	ozviewer.addEventListener("OZPageChangeCommand", function(event){
	    //event.docindex
	});
	ozviewer.addEventListener("OZPageBindCommand", function(event){
	    //event.docindex, event.pagecount
	});
	ozviewer.addEventListener("OZReportChangeCommand", function(event){
	    //event.docindex
	});
	ozviewer.addEventListener("OZUserEvent", function(event){
	    //event.param1, event.param2, event.param3
	    event.result=event.param1+event.param2+event.param3; //userEvent sample
	});
	ozviewer.addEventListener("OZWillChangeIndex_Paging", function(event){
	    //event.newindex, event.oldindex
	    event.result=true; //OZWillChangeIndex_Paging sample
	});
	ozviewer.addEventListener("OZEFormInputEventCommand", function(event){
	    //event.docindex, event.formid, event.eventname
	});
	ozviewer.addEventListener("OZExportMemoryStreamCallBack", function(event){	
		//alert(getTimeStamp()+"\nIN OZExportMemoryStreamCallBack");

		if(event.outputdata == "{}" ){ 
			console.log("Fail to Export Memory Stream ");
		}else{
			console.log("Success to Export Memory Stream ");
			exportedEnd = getTimeStamp(); // For debugging
			
			var obj = eval('(' + event.outputdata + ')');
			//console.log(event.outputdata);
			var value = null;
			var formdata = new FormData();
			var index = 1;
			for(var key in obj){
				//file_name = key;
				value = obj[key];
				console.debug("file_name :: " + key);
				console.log(key.replace("/sdcard/",""));
				formdata.append("file_name_" + index, key.replace("/sdcard/",""));
				formdata.append("file_stream_" + index, value);
				index++;
			}						
			if(input_XML != null && input_XML != 'undefined') {
				formdata.append("contract_xml_data", input_XML);
			}
			if(prodNo != null && prodNo != 'undefined') {
				formdata.append("prod_no", prodNo);
			}
	
			// Add OZTotoIndicator. 2016.11.16 (Ryu)
			CallIndicator("Saving contract...");
			
			uploadStart = getTimeStamp(); // For debugging
			// Upload files (pdf + ozd)
			$.ajax({
				type : "POST",
				url : "./upload-file/ftp",
				data : formdata,
				dataType: 'text',
				processData: false,
				contentType: false,
				success : function(data){
					uploadEnd = getTimeStamp(); // For debugging
					endTime = getTimeStamp(); // For debugging
								
					alert("Гэрээ амжилтай хадгалагдлаа. \n" + "\n" +
						  "StartTime: " + startTime + "\n" +
						  "ExportStart: " + exportedStart + " || " + "ExportedEnd: " + exportedEnd + "\n" +
						  "UploadStart: " + uploadStart + " || " + "UploadEnd: " + uploadEnd + "\n" +
						  "EndTime: " + endTime);
					
					//alert("Гэрээ амжилтай хадгалагдлаа.");
					StopIndicator();
									
					if (data != "" && data != null) {
						CallIndicator("Sending email...");
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
								async: true,
								processData: false,
								contentType: false,
								success : function(data){
									alert("Email sent successfully.");
									console.log("Email sent successfully.");
									StopIndicator();
									ozviewer_dispose(); close();	
								},
								error : function(request, status, error) {
									alert("Failed to send the email.");
									console.log("Failed to send the email");
									StopIndicator();
									ozviewer_dispose(); close();	
								}
							});
						}
						
					}				
				},
				error : function(request, status, error) {
					if (status == 'error') {
						alert(request.responseText);	
					}
					StopIndicator();
					ozviewer_dispose(); close();
				}
			});
		}
	});
	
	OZTotoFramework.navigator.addEventListener("action",function(event) {
		switch(event.button) {
		case "home":
			console.debug("home onclick.....1");
			break;
		case "back":
			if(ozviewer.isVisible()) {
				event.handled = true;
				console.debug("back onclick.....2");	
				break;
			}
			console.debug("back onclick.....3");
			break;
		case "forward":
			console.debug("forward onclick.....1");
			break;
		case "refresh":
			console.debug("refresh onclick.....1");
			break;
		case "menu":
			kendoAlert("Hey Now!", "Important Message For You");			
			break;
		}
	});
	}
}

//****Ryu test...//
	function getTimeStamp() {
	  var d = new Date();
	  var s =
		leadingZeros(d.getFullYear(), 4) + '-' +
		leadingZeros(d.getMonth() + 1, 2) + '-' +
		leadingZeros(d.getDate(), 2) + ' ' +

		leadingZeros(d.getHours(), 2) + ':' +
		leadingZeros(d.getMinutes(), 2) + ':' +
		leadingZeros(d.getSeconds(), 2);

	  return s;
	}

	function leadingZeros(n, digits) {
	  var zero = '';
	  n = n.toString();

	  if (n.length < digits) {
		for (i = 0; i < digits - n.length; i++)
		  zero += '0';
	  }
	  return zero + n;
	}
//****Ryu test...//
	
function b64(s) {
	  var key = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=';

	  var i = 0, len = s.length,
	      c1, c2, c3,
	      e1, e2, e3, e4,
	      result = [];
	 
	  while (i < len) {
	    c1 = s.charCodeAt(i++);
	    c2 = s.charCodeAt(i++);
	    c3 = s.charCodeAt(i++);
	   
	    e1 = c1 >> 2;
	    e2 = ((c1 & 3) << 4) | (c2 >> 4);
	    e3 = ((c2 & 15) << 2) | (c3 >> 6);
	    e4 = c3 & 63;
	   
	    if (isNaN(c2)) {
	      e3 = e4 = 64;
	    } else if (isNaN(c3)) {
	      e4 = 64;
	    }
	   
	    result.push(e1, e2, e3, e4);
	  }
	 
	  return result.map(function (e) { return key.charAt(e); }).join('');
}

/* category setting */
var category_root = "/econtract";
var category_acceptcase = category_root + "/caserepository";
var category_submitozd = category_root + "/submitozdrepository";

/* local repository */
var localrepository = "local://localrepository/";
var repository_catetory = "econtract/";
var aprolocalrepository = localrepository + repository_catetory;

function escapejson (val) {
    if (typeof(val)!="string") return val;
    return val      
        .replace(/[\\]/g, '\\\\')
        .replace(/[\/]/g, '\\/')
        .replace(/[\b]/g, '\\b')
        .replace(/[\f]/g, '\\f')
        .replace(/[\n]/g, '\\n')
        .replace(/[\r]/g, '\\r')
        .replace(/[\t]/g, '\\t')
        .replace(/[\"]/g, '\\"')
        .replace(/\\'/g, "\\'"); 
}

Date.prototype.format = function(f) {
    if (!this.valueOf()) return " ";
 
    var weekName = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];
    var d = this;
     
    return f.replace(/(yyyy|yy|MM|dd|E|hh|mm|ss|a\/p)/gi, function($1) {
        switch ($1) {
            case "yyyy": return d.getFullYear();
            case "yy": return (d.getFullYear() % 1000).zf(2);
            case "MM": return (d.getMonth() + 1).zf(2);
            case "dd": return d.getDate().zf(2);
            case "E": return weekName[d.getDay()];
            case "HH": return d.getHours().zf(2);
            case "hh": return ((h = d.getHours() % 12) ? h : 12).zf(2);
            case "mm": return d.getMinutes().zf(2);
            case "ss": return d.getSeconds().zf(2);
            case "a/p": return d.getHours() < 12 ? "AM" : "PM";
            default: return $1;
        }
    });
};
 
String.prototype.string = function(len){var s = '', i = 0; while (i++ < len) { s += this; } return s;};
String.prototype.zf = function(len){return "0".string(len - this.length) + this;};
Number.prototype.zf = function(len){return this.toString().zf(len);};