<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<div id="gnb_left">
	<a href="m-index">
		<img id="admin_logo" src="./resources/images/unitel_logo_250.png"/>
	</a>
</div>
<div id="gnb_right">
	<a href="logout"><img src="./resources/images/logout_btn.png" id="logout_btn"/></a>
</div>


<script type="text/javascript">
$(document).ready(function() {
        
    $("#logout_btn").kendoTooltip({
        content: "Sign Out",
        position: "bottom"
    });
});
</script>
