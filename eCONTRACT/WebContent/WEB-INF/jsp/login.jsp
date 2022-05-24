<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link type="text/css" href="//netdna.bootstrapcdn.com/font-awesome/3.2.1/css/font-awesome.css" rel="stylesheet" />

<script>
function submitform(){
	checkId();
	checkPw();
	
	document.frmlogin.submit();
}

function checkId() {
	var id = $('#j_username').val();
	
	if ($('#chkId').is(":checked"))
		localStorage.setItem("saveId", id);
}

function checkPw() {
	var pwd = $('#j_password').val();
	
	if ($('#chkPw').is(":checked"))
		localStorage.setItem("savePw", pwd);
}

function getLogin() {

	var id =  localStorage.getItem("saveId");
	var pwd = localStorage.getItem("savePw");

	if (id.length) {
		$('#j_username').val(id);
		$('#chkId').attr('checked','true');
	}
	if (pwd.length) {
		$('#j_password').val(pwd);
		$('#chkPw').attr('checked','true');
	}
}


$(document).ready(function() {
	getLogin();
});
</script>

<div id="login-holder">
	<div id="login-box">
		<div class="card-section">
			<div class="login-logo"></div>
			<c:url value="/j_spring_security_check" var="security_check_action"/>
			<form name="frmlogin" method="POST" action="${security_check_action}" novalidate="novalidate">

				<div class="form-joined">
					<div class="form-item icon">
						<i class="icon-user"></i>
						<div class="form-text large">
							<input type="text" id="j_username" name="j_username" value=""
								class="username" placeholder="Нэр" required="required" autofocus />
						</div>
					</div>
					<div class="form-item icon">
						<i class="icon-key"></i>
						<div class="form-text large">
							<input type="password" id="j_password" name="j_password" value=""
								class="password" placeholder="Нууц үг" required="required" />
						</div>
					</div>
				</div>
				
				<c:if test="${error}">  
       				<font color="red">Хэрэглэгчийн нэр эсвэл нууц үг буруу байна!</font><br/><br/>  
   				</c:if>
   				<c:if test="${expired}">  
       				<font color="red">Үйлчилгээний хугацаа дууссан байна.</font><br/><br/>  
   				</c:if>
     			<c:if test="${missingrole}">  
       				<font color="red">Таньд нэвтрэх эрх байхгүй байна. Системийн админтай холбогдоно уу.</font><br/><br/>  
   				</c:if>
				<button id="loginbtn" onclick="submitform()" class="button-kit xlarge wide green" value="Login">
					Нэвтрэх<span></span>
				</button>
				
				<div style="margin: 10px 0px 10px 0px; font-family:'Malgun Gothic'; font-size:11pt; text-align: center;">
						<input name="chkId" id="chkId" type="checkbox"> Логин хадгалах &nbsp; &nbsp;
						<input name="chkPw" id="chkPw" type="checkbox"> Нууц үг хадгалах
				</div>
					
			</form>
		</div>			
	</div>
</div>

<style scoped="scoped">
body{
	/* background-image : url(./resources/images/bg_01.jpg); */
	padding-top : 100px;
	background-color: #e5e6e0;
    background-repeat: no-repeat;
    background-position: top right;
}

#login-holder {
	margin: 0 auto 0;
	width: 420px;
	padding-bottom: 20px;
}

.card-section {
	padding: 50px 50px 60px;
	background: #FFFFFF;
}

.login-logo {
	width: 250px;
	height: 110px;
	margin: auto;
	margin-top: 25px;
	margin-bottom: 5px;
	background: url(./resources/images/unitel_logo_250.png) no-repeat;
}

.form-joined {
	margin-bottom: 20px;
}

.check_auto {	
	/* margin-left:45px; */
	margin-top:6.25%;
	max-height: 80px;
}

.check_auto .check_left{	
	float: left;
	width: 48%;
	text-align: center;
}
.check_auto .split{
	float: left;
}
.check_auto .check_right{	
	float: left;
	width: 48%;
	text-align: center;
}

.check_auto .check_right a{	
	text-decoration:none;
}

.check_auto span{	
	font-size:15px;
}
</style>