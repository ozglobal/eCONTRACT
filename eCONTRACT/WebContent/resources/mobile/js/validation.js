/*
@Author: Adrian "yEnS" Mato Gondelle & Ivan Guardado Castro
@website: www.yensdesign.com
http://yensdesign.com/2009/01/how-validate-forms-both-sides-using-php-jquery/
*/

$(document).ready(function(){
	//global vars
	var form = $("#customForm");
	var name = $("#name");
	var nameInfo = $("#nameInfo");
	var email = $("#email");
	var emailInfo = $("#emailInfo");
	var message = $("#message");
	
	//On blur
	name.blur(validateName);
	email.blur(validateEmail);
	//On key press
	name.keyup(validateName);
	email.keyup(validateEmail);
	message.keyup(validateMessage);
	//On Submitting
	form.submit(function(){
		if(validateName() & validateEmail() & validateMessage())
			return true
		else
			return false;
	});
	
	//validation functions
	function validateName(){
		//if it's NOT valid
		if(name.val().length < 4){
			name.addClass("ff_error");
			nameInfo.text("We want names with more than 3 letters!");
			nameInfo.addClass("f_error");
			return false;
		}
		//if it's valid
		else{
			name.removeClass("ff_error");
			nameInfo.text("");
			nameInfo.removeClass("f_error");
			return true;
		}
	}
	function validateEmail(){
		//if it's NOT valid
		if(email.val().length < 8){
			email.addClass("ff_error");
			emailInfo.text("Type a valid e-mail please");
			emailInfo.addClass("f_error");
			return false;
		}
		//if it's valid
		else{
			email.removeClass("ff_error");
			emailInfo.text("");
			emailInfo.removeClass("f_error");
			return true;
		}
	}
	function validateMessage(){
		//it's NOT valid
		if(message.val().length < 2){
			message.addClass("ff_error");
			return false;
		}
		//it's valid
		else{			
			message.removeClass("ff_error");
			return true;
		}
	}
});