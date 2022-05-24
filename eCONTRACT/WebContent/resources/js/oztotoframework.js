var loadOZTotoFramework = typeof OZTotoFramework != "undefined";
var OZTotoFramework=loadOZTotoFramework ? OZTotoFramework : null;
OZTotoFramework=loadOZTotoFramework ? OZTotoFramework : function(){
OZTotoFramework=function(){return OZTotoFramework;};
var $runtime = 0;
var _res = null;
var _args = null;
var $exec = function (opcode, args) {
    if (/iPhone|iPad|iPod/i.test(navigator.userAgent)) {
        try {
            eval(webkit.messageHandlers._toto_interface_.postMessage([$runtime,opcode,JSON.stringify(args)]));
            var result = undefined;
            try {
                result = prompt("OZTotoFramework.$exec");
            } catch (e) {}
            return eval(result);
        } catch(e) {
            return undefined;
        }
    }
	else if (/Android/i.test(navigator.userAgent)) {
        return eval(_toto_interface_.exec($runtime,opcode,JSON.stringify(args)));
	}
	else if (/WebView/i.test(navigator.userAgent)) {
        return eval(_toto_interface_.exec($runtime, opcode, JSON.stringify(args)));
	}
}

var $bind=function(obj,className){obj.__class__=className;$exec("bind",{__class__:className,__id__:obj.__id__});}
var $$=OZTotoFramework.prototype;
$$.__ozexec_result__=undefined;
$$.__version__ = "2.0.0.3";
$$.__id__=0;
$$.__ref__={};
$$.__ar__={};
$$.__types__={};
$$._dispatchCallback = {};
$$._dispatchCallback[0] = null;  // OZTotoFramework.util.confirm
$$._dispatchCallback[1] = null;  // OZTotoFramework.storage.putHTTP
$$._dispatchCallback[2] = null;  // OZTotoFramework.PDFViewer.createViewer
$$._dispatchCallback[3] = null;  // OZTotoFramework.location.start
$$._dispatchCallback[4] = null;  // OZTotoFramework.util.alert
$$._dispatchCallback[5] = null;  // OZTotoFramework.speach.startListening.event

$$.__call__=function(obj,name,args){return $exec("call",{__class__:obj.__class__,__id__:obj.__id__,__name__:name,__args__:args});}
$$.__addCallbackListener__=function(name, callback)
{
    OZTotoObject.prototype.addEventListener(name, callback);
}
$$.__removeCallbackListener__=function(name, callback)
{
    OZTotoObject.prototype.removeEventListener(name, callback);
}
$$.__dispatchCallback__=function(name, event, index, isremove)
{
	if(isremove == null) {
		isremove = true;
	}
    setTimeout(function(){
       if ($$._dispatchCallback[index] != null) {
           OZTotoObject.prototype.dispatchEvent(name, event);
           if (index != 2 && isremove) {
               OZTotoObject.prototype.removeEventListener(name, $$._dispatchCallback[index]);
               $$._dispatchCallback[index] = null;
           }
       }
   });
}
    
$$.__setDivContentRect__=function(divID)
{
    if (document.getElementById(divID) != undefined && document.getElementById(divID) != null) {
        var __left__   = document.getElementById(divID).getBoundingClientRect().left;
        var __top__    = document.getElementById(divID).getBoundingClientRect().top;
        var __width__  = document.getElementById(divID).getBoundingClientRect().width;
        var __height__ = document.getElementById(divID).getBoundingClientRect().height;
        return __left__+","+__top__+","+__width__+","+__height__;
    }
    else {
        console.debug('invalid oztotoframework.js error. setDivContentRect()');
    }
    
    return "";
}

    
OZTotoFramework.__proto__=$$;
OZTotoFramework.__ref__[0]=OZTotoFramework;

$$.__applyResult=function(res) {
	_res=res;
}

$$.__getArgs=function () {
    return _args;
}

$$.__getUserAgent=function() {
	return navigator.userAgent;
}

var $doInit=function(obj,proto,args){
	if(!proto)return;
	var superProto=proto.__proto__;
	if(superProto){
		var superInit=superProto.__init__;
		if(superInit && superInit.length==0)$doInit(obj,superProto);
	}
	if(proto.__init__)proto.__init__.apply(obj,args);
}
$$.__init__=function(obj,$class,args){$doInit(obj,$class.prototype,args);}
$$.__defineClass__ = function(className, superClass){
	if($$.__types__[className]!=undefined)
		throw new Error("already exist class: "+className);
	var $super = superClass?superClass:$$.Object;
	var classObject = eval("var "+className+"=function(){$doInit(this,this.__proto__,arguments);}; "+className);
	classObject.prototype.__proto__ = $super.prototype;
	classObject.prototype.__class__ = className;
    if (superClass) {
        classObject.prototype.__init__=function(){
            this.__id__=OZTotoObject.__seq__++;
            $$.__ref__[this.__id__]=this;
            if(this.__class__)
                $bind(this,this.__class__);
        };
    }
    else {
        classObject.prototype.__init__ = null;
    }
	$$.__types__[className]=classObject;
	$exec("defineClass",{name:className});
	return classObject;
}

$$.__seqArid__ = 0;
$$.createAsyncReturn=function(return_callback) {
	var arid = "_AR"+(++$$.__seqArid__)+"_";
	var onResult = function(event) {
		this.removeEventListener(arid, onResult);
		if(return_callback) return_callback(event["result"]);
	};
	$$.addEventListener(arid, onResult);
	$$.__ar__[arid]=onResult;
	return arid;
};
$$.cancelAsyncReturn=function(arid) {
	var handler=$$.__ar__[arid];
	$$.removeEventListener(arid, handler);
	delete $$.__ar__[arid];
};
$$.applyAsyncReturn=function(arid,returnValue) {
	$$.dispatchEvent(arid, {"result":returnValue});
	$$.cancelAsyncReturn(arid);
};
$$.execute=function(opcode) {
    $exec(opcode);
};

var OZTotoObject=function(){$doInit(this,this.__proto__,arguments);};
OZTotoObject.prototype.__init__=function(){
	this.__id__=OZTotoObject.__seq__++;$$.__ref__[this.__id__]=this;
	if(this.__class__)
		$bind(this,this.__class__);
};
OZTotoObject.__seq__=1;
OZTotoObject.prototype.addEventListener=function(eventName,callback){
	if(!this.__events__)this.__events__={};
	var events=this.__events__[eventName];
	if(events==null){
		events=[];
		this.__events__[eventName]=events;
	}
	events.push(callback);
};
OZTotoObject.prototype.removeEventListener=function(eventName,callback){
    if(!this.__events__)return;
    var events=this.__events__[eventName];
    if(events){
        if (callback != null) { // remove one..
            var i=events.indexOf(callback);
            if(i>=0){
                var handler=events.splice(i, 1)[0];
                if(typeof handler=='number')$exec('event.remove',{handler:handler});
            }
            if(!events.length)delete this.__events__[eventName];
        }
        else { // remove all..
            for(var i=0;i<events.length;i++){
                var handler=events[i];
                if(typeof handler=='number')$exec('event.remove',{handler:handler});
            }
            delete this.__events__[eventName];
        }
    }
};
OZTotoObject.prototype.dispatchEvent=function(eventName,args){
	var event=args?args:({});
	event.__eventName__=eventName;
	try{
		if(!this.__events__) return event;
		var events=this.__events__[eventName];
	
		if (events) {
			for(var i=0;i<events.length;i++){
				var handler=events[i];
				switch(typeof handler){
					case 'function':handler(event);break;
					case 'number':{
						var event2=$exec('event.callback',{handler:handler,event:event});
						for(var k in event2)event[k]=event2[k];
						break;
					}
					default:(0,eval)(handler);break;
				}
			}
		}
		return event;
	}finally{
		$$.__lastevent__ = event;
		$exec("setLastEvent",{event:event});
	}
};
$$.Object=OZTotoObject;
$$.__proto__ = $$.Object.prototype;

$runtime=$exec("init",{location:location.href,version:$$.__version__});
$exec("defineClass",{name:"OZTotoFramework"});
$bind($$,"OZTotoFramework");
window.addEventListener("load",function(){window.setTimeout(function(){$exec("pageLoaded");});});
window.addEventListener("unload",function(){$exec("pageUnload");});
return OZTotoFramework;
}();

(function(){if(loadOZTotoFramework){console.log("OZTotoFramework already loaded... skip...");return;} var $=OZTotoFramework.prototype;var $$=OZTotoFramework;

var OZTotoEnvironment = OZTotoFramework.__defineClass__("OZTotoEnvironment");
OZTotoEnvironment.prototype.isOnline=function(){return $$.__call__(this,"isOnline", []);};
OZTotoEnvironment.prototype.getScriptVersion=function(){return $$.__call__(this,"getScriptVersion", []);}
OZTotoEnvironment.prototype.getEngineVersion=function(){return $$.__call__(this,"getEngineVersion", []);}
OZTotoEnvironment.prototype.getAppVersion=function(){return $$.__call__(this,"getAppVersion", []);}
OZTotoEnvironment.prototype.getAppInfo=function(){return $$.__call__(this,"getAppInfo", []);}
OZTotoEnvironment.prototype.getPackageName=function(){return $$.__call__(this,"getPackageName", []);}
OZTotoEnvironment.prototype.getArchivePath=function(){return $$.__call__(this,"getArchivePath", []);}
OZTotoEnvironment.prototype.isNetworkAlive=function(){return $$.__call__(this,"isNetworkAlive", []);};

var OZTotoDevice = OZTotoFramework.__defineClass__("OZTotoDevice");
OZTotoDevice.prototype.getOSName=function(){return $$.__call__(this,"getOSName", []);};
OZTotoDevice.prototype.getOSVersion=function(){return $$.__call__(this,"getOSVersion", []);};
OZTotoDevice.prototype.getModelName=function(){return $$.__call__(this,"getModelName", []);};
OZTotoDevice.prototype.getUUID=function(){return $$.__call__(this,"getUUID", []);};
OZTotoDevice.prototype.getDeviceName=function(){return $$.__call__(this,"getDeviceName", []);};
OZTotoDevice.prototype.getManufacturer=function(){return $$.__call__(this,"getManufacturer", []);};
OZTotoDevice.prototype.getBrand=function(){return $$.__call__(this,"getBrand", []);};

var OZTotoUtil = OZTotoFramework.__defineClass__("OZTotoUtil");
OZTotoUtil.prototype.trace=function(string) {
$$.__call__(this,"trace",[string]);

};
OZTotoUtil.prototype.alert = function (message, title, callback) {
    if (message == null) message = "";
    if (title == null) title = "";
	if (callback != null) {
		$$._dispatchCallback[4] = callback;
		$$.__addCallbackListener__("OZTotoUtil.alert.event", $$._dispatchCallback[4]);
	}
    $$.__call__(this,"alert",[message,title]);
};
OZTotoUtil.prototype.messageService = function (numbers, message) {
    if (numbers == null) numbers = "";
    if (message == null) message = "";
    $$.__call__(this,"messageService",[numbers,message]);
};
OZTotoUtil.prototype.callService = function (number) {
    if (number == null) number = "";
    $$.__call__(this,"callService",[number]);
};
OZTotoUtil.prototype.getPhoneNumber = function () {
    return $$.__call__(this,"getPhoneNumber",[]);
};
OZTotoUtil.prototype.confirm=function(message,title,button1,button2,callback) {
	if ($$._dispatchCallback[0] == null) {
        $$._dispatchCallback[0] = callback;
        if (callback != null)
            $$.__addCallbackListener__("OZTotoUtil.confirm.event", $$._dispatchCallback[0]);

        if (/WebView/i.test(navigator.userAgent)) {
            if (button1 == null || button1 == "") button1 = "OK";
            if (button2 == null) button2 = "";
        }
        $$.__call__(this,"confirm",[message,title,button1,button2]);
    }
};
 
var OZTotoIndicator = OZTotoFramework.__defineClass__("OZTotoIndicator");
OZTotoIndicator.prototype.setOption=function(option) {
    if (option == null)
        return;
$$.__call__(this,"setOption",[option]);
};
OZTotoIndicator.prototype.start=function(width, height) {
    if (width == null)
        width = 0;
    if (height == null)
        height = 0;
$$.__call__(this,"start",[width, height]);
};
OZTotoIndicator.prototype.stop=function(interval) {
    if (interval == null)
        interval = 0;
$$.__call__(this,"stop",[interval]);
};

var OZTotoLocation = OZTotoFramework.__defineClass__("OZTotoLocation");
OZTotoLocation.prototype.start=function(updateType, getAddress, callBack) {
	if(updateType == null || getAddress == null || callBack == null) {
		return;
	}
	var lower = updateType.toLowerCase();
	if(!(lower == "once" || lower == "tracking")) {
		return;
	}
	if(typeof getAddress != "boolean") {
		return;
	}
	if(!(callBack instanceof Function)) {
		return;
	}
	$$.__removeCallbackListener__("OZTotoLocation.start.event", $$._dispatchCallback[3]);
	$$._dispatchCallback[3] = callBack;
	$$.__addCallbackListener__("OZTotoLocation.start.event", $$._dispatchCallback[3]);
	$$.__call__(this,"start",[updateType, getAddress]);
};
OZTotoLocation.prototype.stop=function() {
	$$.__call__(this,"stop",[]);
};

var OZTotoSpeach = OZTotoFramework.__defineClass__("OZTotoSpeach");
OZTotoSpeach.prototype.startListening=function(bShowUI, bListening) {
    if(bShowUI == null || bListening == null) {
        return;
    }
    $$.__call__(this,"startListening",[bShowUI, bListening]);
};
OZTotoSpeach.prototype.addListener=function(callback) {
    if(callback == null || !(callback instanceof Function))
        return;
    $$.__removeCallbackListener__("OZTotoSpeach.startListening.event", $$._dispatchCallback[5]);
    $$._dispatchCallback[5] = callback;
    $$.__addCallbackListener__("OZTotoSpeach.startListening.event", $$._dispatchCallback[5]);
};
OZTotoSpeach.prototype.setOption=function(option) {
    if (option == null)
        return;
    $$.__call__(this,"setOption",[option]);
};
OZTotoSpeach.prototype.speak=function(text) {
    if (text == "" || text == null)
        return;
    $$.__call__(this,"speak",[text]);
};
OZTotoSpeach.prototype.stop=function() {
    $$.__call__(this,"stop",[]);
};
OZTotoSpeach.prototype.isListening=function(){return $$.__call__(this,"isListening", []);};
OZTotoSpeach.prototype.isSpeaking=function(){return $$.__call__(this,"isSpeaking", []);};

var OZTotoHttpClient = OZTotoFramework.__defineClass__("OZTotoHttpClient");
OZTotoHttpClient.prototype.send=function(options) {
	var ar_error = $$.createAsyncReturn(function(event){
		if(options.error != null) options.error(event);
		$$.cancelAsyncReturn(ar_success);
	});
	var ar_success = $$.createAsyncReturn(function(event){
		if(options.success != null) options.success(event);
		$$.cancelAsyncReturn(ar_error);
	});
	try {
		return $$.__call__(this,"send", [options, ar_success, ar_error]);
	}catch(e){
		$$.cancelAsyncReturn(ar_success);
		$$.cancelAsyncReturn(ar_error);
	}
};

//OZTotoStorageData is OZTotoStorage data wrapper class.
var OZTotoStorageData = OZTotoFramework.__defineClass__("OZTotoStorageData");
 OZTotoStorageData.prototype.__init__=function(obj, type){ this.type = type; this.category = obj.category; this.key = obj.key; };
OZTotoStorageData.prototype.size = function(){ return $$.__call__(this,"size",[this.type,this.category,this.key]);}
OZTotoStorageData.prototype.append = function(value){ $$.__call__(this,"append",[this.type,this.category,this.key,value]); }
OZTotoStorageData.prototype.get = function(){ return $$.__call__(this,"get",[this.type,this.category,this.key]); }

var OZTotoStorage = OZTotoFramework.__defineClass__("OZTotoStorage");
OZTotoStorage.prototype.get=function(categoryName,key){return $$.__call__(this,"get",[categoryName,key]);};
OZTotoStorage.prototype.data=function(categoryName,key){	var obj = $$.__call__(this,"data",[categoryName,key]); if (obj == null){ return null; }else { return new OZTotoStorageData(obj, 100); } };
OZTotoStorage.prototype.put=function(categoryName,key,value){return $$.__call__(this,"put",[categoryName,key,value]);};
OZTotoStorage.prototype.putHTTP=function(categoryName,key,url,callback) {
    if ($$._dispatchCallback[1] == null) {
        $$._dispatchCallback[1] = callback;
        if (callback != null)
            $$.__addCallbackListener__("OZTotoStorage.putHTTP.event", $$._dispatchCallback[1]);
        $$.__call__(this,"putHTTP",[categoryName,key,url]);
    }
};
OZTotoStorage.prototype.getFilePath=function(categoryName,key){return $$.__call__(this,"getFilePath",[categoryName,key]);};
OZTotoStorage.prototype.remove=function(categoryName,key){return $$.__call__(this,"remove",[categoryName,key]);};
OZTotoStorage.prototype.contains=function(categoryName,key){return $$.__call__(this,"contains",[categoryName,key]);};
OZTotoStorage.prototype.listKeys=function(categoryName){return $$.__call__(this,"listKeys",[categoryName]);};
OZTotoStorage.prototype.containsCategory=function(categoryName){return $$.__call__(this,"containsCategory",[categoryName]);};
OZTotoStorage.prototype.removeCategory=function(categoryName){return $$.__call__(this,"removeCategory",[categoryName]);};
OZTotoStorage.prototype.listCategories=function(){return $$.__call__(this,"listCategories",[]);};

var OZTotoSecStorage = OZTotoFramework.__defineClass__("OZTotoSecStorage", OZTotoStorage);
OZTotoSecStorage.prototype.data=function(categoryName,key){	var obj = $$.__call__(this,"data",[categoryName,key]); if (obj == null){ return null; }else { return new OZTotoStorageData(obj, 200); } };
 
var OZTotoNavigator = OZTotoFramework.__defineClass__("OZTotoNavigator");
//OZTotoNavigator.prototype.goHome=function(){return $$.__call__(this,"goHome",[]);};
OZTotoNavigator.prototype.setVisible=function(visible){ $$.__call__(this,"setVisible",[visible]);};
OZTotoNavigator.prototype.isVisible=function(){return $$.__call__(this,"isVisible",[]);};
OZTotoNavigator.prototype.setMenuButtonVisible=function(visible){ $$.__call__(this,"setMenuButtonVisible",[visible]);};
OZTotoNavigator.prototype.isMenuButtonVisible=function(){return $$.__call__(this,"isMenuButtonVisible",[]);};

$$.device=new OZTotoDevice();
$$.environment=new OZTotoEnvironment();
$$.http=new OZTotoHttpClient();
$$.storage=new OZTotoStorage();
$$.secstorage=new OZTotoSecStorage();
$$.navigator=new OZTotoNavigator();
$$.util=new OZTotoUtil();
$$.indicator=new OZTotoIndicator();
$$.location=new OZTotoLocation();
$$.speach=new OZTotoSpeach();
}());

(function(){if(loadOZTotoFramework){return;}var $$=OZTotoFramework; /* OZViewer */
//OZExportData is oz.export data wrapper class.
var OZExportData = OZTotoFramework.__defineClass__("OZExportData");
OZExportData.prototype.__init__=function(obj){ this.filename = obj.filename; };
OZExportData.prototype.size = function(){ return $$.__call__(this,"size",[this.filename]);}
OZExportData.prototype.data = function(){ return $$.__call__(this,"data",[this.filename]);}

var Document = $$.__defineClass__("OZViewerDocument");
var OZViewerDocument = Document.prototype;
OZViewerDocument.__init__=function(viewerId){return $$.__call__(this,"__init__",[viewerId]);}
OZViewerDocument.setChartStyle=function(style){return $$.__call__(this,"SetChartStyle",[style]);};
OZViewerDocument.pingOZServer = function (ipOrUrl, port)
{
    if (/WebView/i.test(navigator.userAgent)) {
        if (port == null) port = -1;
    }
    return $$.__call__(this, "PingOZServer", [ipOrUrl, port]);
};
OZViewerDocument.getGlobal = function (key, nIndex)
{
    if (/WebView/i.test(navigator.userAgent)) {
        if (nIndex == null) nIndex = 0;
    }
    return $$.__call__(this, "GetGlobal", [key, nIndex]);
};
OZViewerDocument.setGlobal = function (key, varValue, nIndex)
{
    if (/WebView/i.test(navigator.userAgent)) {
        if (nIndex == null) nIndex = 0;
    }
    return $$.__call__(this, "SetGlobal", [key, varValue, nIndex]);
};
OZViewerDocument.getTitle=function(){return $$.__call__(this,"GetTitle",[]);};
OZViewerDocument.getPaperWidth=function(){return $$.__call__(this,"GetPaperWidth",[]);};
OZViewerDocument.getPaperHeight=function(){return $$.__call__(this,"GetPaperHeight",[]);};
OZViewerDocument.triggerExternalEvent = function (param1, param2, param3, param4, callback)
{
    var arid = "";
    if (param1 == null) param1 = "";
    if (param2 == null) param2 = "";
    if (param3 == null) param3 = "";
    if (param4 == null) param4 = "";
	param1 = param1 + "";
	param2 = param2 + "";
	param3 = param3 + "";
	param4 = param4 + "";
    if (callback != null) {
        arid = $$.createAsyncReturn(callback);
    }

    return $$.__call__(this, "TriggerExternalEvent", [param1, param2, param3, param4, arid]);
};
OZViewerDocument.triggerExternalEventByDocIndex = function (docIndex, param1, param2, param3, param4, callback)
{
    var arid = "";
    if (param1 == null) param1 = "";
    if (param2 == null) param2 = "";
    if (param3 == null) param3 = "";
    if (param4 == null) param4 = "";
	param1 = param1 + "";
	param2 = param2 + "";
	param3 = param3 + "";
	param4 = param4 + "";
    if (callback != null) {
        arid = $$.createAsyncReturn(callback);
    }

    return $$.__call__(this, "TriggerExternalEventByDocIndex", [docIndex, param1, param2, param3, param4, arid]);
};
OZViewerDocument.triggerLocationUpdated = function (locationInfo, addressInfo, callback)
{
    var arid = "";
	if(locationInfo == null) {
		locationInfo = "";
	} else if(locationInfo instanceof Object) {
		locationInfo = JSON.stringify(locationInfo);
	} else {
		try {
			JSON.parse(locationInfo);
		} catch(e) {
			locationInfo = "";
		}
	}
	if(addressInfo == null) {
		addressInfo = "";
	} else if(addressInfo instanceof Object) {
		addressInfo = JSON.stringify(addressInfo);
	} else {
		try {
			JSON.parse(addressInfo);
		} catch(e) {
			addressInfo = "";
		}
	}
    if (callback != null) {
        arid = $$.createAsyncReturn(callback);
    }

    return $$.__call__(this, "TriggerLocationUpdated", [locationInfo, addressInfo, arid]);
};
OZViewerDocument.triggerLocationUpdatedByDocIndex = function (docIndex, locationInfo, addressInfo, callback)
{
    var arid = "";
	if(locationInfo == null) {
		locationInfo = "";
	} else if(locationInfo instanceof Object) {
		locationInfo = JSON.stringify(locationInfo);
	} else {
		try {
			JSON.parse(locationInfo);
		} catch(e) {
			locationInfo = "";
		}
	}
	if(addressInfo == null) {
		addressInfo = "";
	} else if(addressInfo instanceof Object) {
		addressInfo = JSON.stringify(addressInfo);
	} else {
		try {
			JSON.parse(addressInfo);
		} catch(e) {
			addressInfo = "";
		}
	}
    if (callback != null) {
        arid = $$.createAsyncReturn(callback);
    }

    return $$.__call__(this, "TriggerLocationUpdatedByDocIndex", [docIndex, locationInfo, addressInfo, arid]);
};
 
 
 
var $class3 = $$.__defineClass__("OZEFormComponent");
$$.OZEFormComponent = $class3;
var OZEFormComponent = $class3.prototype;
OZEFormComponent.create=function(viewerTarget,compType,option)
{
 if (option == null) option = new Object();
 return $$.__call__(this,"create",[viewerTarget,compType,option]);
};
OZEFormComponent.script=function(command, return_callback){return $$.__call__(this,"script",[command, $$.createAsyncReturn(return_callback)]);};
OZEFormComponent.updateLayout=function(){return $$.__call__(this,"updateLayout",[]);};
OZEFormComponent.setVisible=function(isVisible){return $$.__call__(this,"setVisible",[isVisible]);};
OZEFormComponent.isVisible=function(){return $$.__call__(this,"isVisible",[]);};
OZEFormComponent.dispose=function()
{
 return $$.__call__(this,"Dispose",[]);
};
 
 

var $class2 = $$.__defineClass__("OZPDFViewer");
$$.OZPDFViewer = $class2;
var OZPDFViewer = $class2.prototype;
OZPDFViewer.createViewer=function(viewerTarget,filePath,option,callback)
{
    var isCallback = false;
    $$._dispatchCallback[2] = callback;
    if ($$._dispatchCallback[2] != null) {
        $$.__addCallbackListener__("OZPDFViewer.createViewer.callback", $$._dispatchCallback[2]);
        isCallback = true;
    }
 
    if (option == null) option = new Object();
    return $$.__call__(this,"createViewer",["OZPDFViewer",viewerTarget,filePath,option,isCallback]);
};
OZPDFViewer.setVisible=function(isVisible){return $$.__call__(this,"setVisible",[isVisible]);};
OZPDFViewer.isVisible=function(){return $$.__call__(this,"isVisible",[]);};
OZPDFViewer.dispose=function(){
 $$.__removeCallbackListener__("OZPDFViewer.createViewer.callback", $$._dispatchCallback[2]);
 $$._dispatchCallback[2] = null;
 return $$.__call__(this,"Dispose",[]);
};
 
 

var $class = $$.__defineClass__("OZViewer");
$$.OZViewer = $class;
var OZViewer = $class.prototype;
OZViewer.__init__=function(){
	this.document = new Document(this.__id__);
}
OZViewer.createViewer=function(viewerTarget,param,delimiter,closemessage)
{
	if (closemessage == null) closemessage = "";
	return $$.__call__(this,"createViewer",["OZViewer",viewerTarget,param,delimiter,closemessage]);
};
OZViewer.createReport = function (param, delimiter)
{
    if (/WebView/i.test(navigator.userAgent)) {
        if (delimiter == null) delimiter = "\n";
    }
    return $$.__call__(this, "CreateReport", [param, delimiter]);
};
OZViewer.newReport=function(param,delimiter){return $$.__call__(this,"NewReport",[param,delimiter]);};
OZViewer.setCloseMessage=function(msg){return $$.__call__(this,"setCloseMessage",[msg]);};
OZViewer.setTarget=function(target){return $$.__call__(this,"setTarget",[target]);};
OZViewer.setVisible=function(isVisible){return $$.__call__(this,"setVisible",[isVisible]);};
OZViewer.isVisible=function(){return $$.__call__(this,"isVisible",[]);};
OZViewer.dispose=function(){return $$.__call__(this,"Dispose",[]);};
OZViewer.rebind = function (nIndex, type, param, delimiter, keepediting)
{
    if (/WebView/i.test(navigator.userAgent) || /Android/i.test(navigator.userAgent)) {
        if (param == null) param = "";
        if (delimiter == null) delimiter = "\n";
        if (keepediting == null) keepediting = true;
    }
    return $$.__call__(this, "ReBind", [nIndex, type, param, delimiter, keepediting]);
};
OZViewer.getInformation=function (item,return_callback){return $$.__call__(this,"GetInformation",[item, $$.createAsyncReturn(return_callback)]);};
OZViewer.script=function(str){return $$.__call__(this,"Script",[str]);};
OZViewer.scriptEx=function(cmd,param,delimiter,return_callback){return $$.__call__(this,"ScriptEx",[cmd,param,delimiter, $$.createAsyncReturn(return_callback)]);};
OZViewer.setHelpURL=function(helpURL){return $$.__call__(this,"setHelpURL",[helpURL]);};
OZViewer.updateLayout=function(){return $$.__call__(this,"updateLayout",[]);};
OZViewer.getExportPath=function(){return $$.__call__(this,"getExportPath",[]);};
OZViewer.getExportData=function(filename){var obj = $$.__call__(this,"getExportData",[filename]); if (obj==null){ return null; }else { return new OZExportData(obj); } };
OZViewer.removeExportData=function(filename){var obj = $$.__call__(this,"removeExportData",[filename]);};
}());
