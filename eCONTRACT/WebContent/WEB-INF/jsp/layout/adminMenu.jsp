<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>


<span id="notification" style="display: none;"></span>

<div id="admin_gnb">
	<ul id="main-menu">
		<li><a href="dashboard" class="dashboard" id="Dashboard" ></a></li>
		<li><a href="scheduler-task" class="scheduler" id="Scheduler"></a>
			<ul>
				<li><a href="scheduler-task" class="scheduler_sub">
					<img src="./resources/images/sub_btn.png">&nbsp;Scheduler</a></li>
				<li><a href="timeline" class="scheduler_sub2">
					<img src="./resources/images/sub_btn.png">&nbsp;TimeLine</a></li>
				<li><a href="joblist" class="scheduler_sub2">
					<img src="./resources/images/sub_btn.png">&nbsp;Job List</a></li>
			</ul>
		</li>
		<sec:authorize access="hasAnyRole('ROLE_ADMIN')">
			<li><a href="customer" class="customer" id="Customer"></a></li>
			<li><a href="contract" class="contract" id="Contract"></a>
				<ul>
					<li><a href="contract" class="contract_sub"><img src="./resources/images/sub_btn.png">&nbsp;Contract List</a></li>
					<li><a href="standby_contract" class="contract_sub2"><img src="./resources/images/sub_btn.png">&nbsp;Waiting Contract</a></li>
				</ul>
			</li>
<!-- 			<li><a href="part" class="part" id="Part"></a></li> -->
<!-- 			<li><a href="checklist" class="check" id="Check"></a></li> -->
<!-- 			<li><a href="user" class="usermanage" id="User"></a></li> -->
			<li><a href="#" class="report" id="Report"></a>
				<ul>
					<li><a href="#" class=report_sub>
						<img src="./resources/images/sub_btn.png">&nbsp;Job History</a></li>
					<li><a href="#" class=report_sub>
						<img src="./resources/images/sub_btn.png">&nbsp;Billing/payment</a></li>
					<li><a href="#" class=report_sub>
						<img src="./resources/images/sub_btn.png">&nbsp;Replaced Parts</a></li>
					<li><a href="#" class=report_sub>
						<img src="./resources/images/sub_btn.png">&nbsp;TOP 10 Parts</a></li>
					<li><a href="#" class=report_sub>
						<img src="./resources/images/sub_btn.png">&nbsp;Renewal List</a></li>
					<li><a href="#" class=report_sub>
						<img src="./resources/images/sub_btn.png">&nbsp;Technician Performance</a></li>
					<li><a href="#" class=report_sub>
						<img src="./resources/images/sub_btn.png">&nbsp;Contract Trends</a></li>									
				</ul>
			</li>
			<li><a href="configuration" class="configuration" id="Settings"></a></li>
		</sec:authorize>
	</ul>
</div>

<script id="contractTemplate" type="text/x-kendo-template">
<div class="new-contract">
	<img src="http://demos.telerik.com/kendo-ui/content/web/notification/envelope.png" />
	<h3>#= title #</h3>
	#= message #
</div>
</script>
<script>
	var notification;
	
	function longPollingTest() {
		console.log("admin chk! standby contract 1min");
		var standby = $("#standby_flag").val();
		var repeat = true;
		$.ajax({
			type : "GET",
			url : "./standby/push?timeOutMin=1",
			async : true,
			cache : false,
			success : function(data) {
				if(standby == "true"){
					console.log(standby);
					var stand_flag = getCookie("stand_flag");
					if(stand_flag == "N"){
						notification.hide();
						setCookie("stand_flag", "Y", 365);
					}
					$("#standby_flag").val("false");
				} else {
					if(data.standbyPush.length){
						notification.show({
							title : "New Contract",
							message : "You have new Contract!"
						}, "info");
						
						setCookie("stand_flag", "N", 365);
						
						var param="";
						for(var i = 0 ; i < data.standbyPush.length; i++){
							param+=data.standbyPush[i]+",";
						}
						// flag를 0으로
						$.ajax({
							type : "GET",
							url : "./standby/push/flag?keys="+param,
							async : false,
							cache : false,
							success : function(data) {
								console.log("success");
								setCookie("keys", param, 365);
							}
						});
					}
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				console.log("error");
				repeat = false;
			},
			complete : function() {				
				if(repeat)
				{
					setTimeout(longPollingTest(),5000);
				}
				
			}
		});
	}
	
	function onHide(e) {
		setCookie("stand_flag", "Y", 365);
		location.href = "./standby_contract";
	}
	
	$(document).ready(function() {
		$("#main-menu ul").css('visibility', 'visible');
		$("#main-menu").menu();
		
		// 선택된 것 계속 색 변해있도록 처리
		// main-menu 선택 시
		var admin_title = $("#admin_title").text();
		if(admin_title == "Job List"){
			admin_title = "Scheduler";
		} else if(admin_title == "Waiting Contract"){
			admin_title = "Contract";
		}
		
		var menu_id = "#"+admin_title;
		$(menu_id).css("background-color","#29b3e1");
		
		$( "#main-menu" ).find("li ul").mouseenter(function() {
			$(this).parent().css("background-color","#29b3e1");
		}).mouseleave(function() {
			$(this).parent().css("background-color","#333a4d");
			$(this).css("display", "none");
		});
		
		$( "#main-menu" ).find("li").mouseenter(function() {
			 $( this ).find("ul").css("display", "block").css("left","124px");
		}).mouseleave(function() {
			$(this).find("ul").css("display", "none");
		});
		
		
		notification = $("#notification").kendoNotification({
			position : {
				pinned : true,
				top : 30,
				right : 30
			},
			hide : onHide,
			autoHideAfter : 0,
			stacking : "down",
			templates : [ {
				type : "info",
				template : $("#contractTemplate").html()
			}]
		}).data("kendoNotification");
		
		// flag가 N이면 notification show
		var flag = getCookie("stand_flag");
		if(flag == "N"){
			notification.show({
// 				title : "New Contract",
				message : "You have new Contract!"
			}, "info");
		}

		//longPollingTest();
	});
</script>

<style type="text/css">
.k-menu .k-item, .k-widget.k-menu-horizontal>.k-item{
 border-width: 0 0 0 0;
}

.k-notification-info.k-group {
	background: rgba(0%, 0%, 0%, .7);
	color: #fff;
}

.new-contract {
	width: 300px;
	height: 100px;
}

.new-contract h3 {
	font-size: 1em;
	padding: 32px 10px 5px;
}

.new-contract img {
	float: left;
	margin: 30px 15px 30px 30px;
}
</style>
