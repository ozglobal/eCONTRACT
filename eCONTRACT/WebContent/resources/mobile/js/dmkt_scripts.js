/*
// Hide the address bar! (Android only, iPhone not)
// When ready... 
window.addEventListener("load",function() {
  // Set a timeout...
  setTimeout(function(){
    // Hide the address bar!
    window.scrollTo(0, 1);
  }, 0);
});
*/

// Hide the address bar! jQuery (iPhone only, Android not)
if( !window.location.hash && window.addEventListener ){
    window.addEventListener( "load",function() {
        setTimeout(function(){
            window.scrollTo(0, 1);
        }, 0);
    });
    window.addEventListener( "orientationchange",function() {
        setTimeout(function(){
            window.scrollTo(0, 1);
        }, 0);
    });
}

// Show SubMenu
 $(document).ready(function (){
    $('#menu').click(function() {
      $('#submenu').slideToggle('slow', function() {
      });
    });
    $('#menu_home').click(function() {
      $('#submenu').slideToggle('slow', function() {
      });
    });
    $('.submenu_close').click(function() {
      $('#submenu').slideUp('slow', function() {
      });
    });
});

//go to top
// http://webdesignerwall.com/tutorials/animated-scroll-to-top
$(document).ready(function(){
	// hide #back-top first
	$("#back-top").hide();
	// fade in #back-top
	$(function () {
		$(window).scroll(function () {
			if ($(this).scrollTop() > 100) {
				$('#back-top').fadeIn();
			} else {
				$('#back-top').fadeOut();
			}
		});
		// scroll body to 0px on click
		$('#back-top a').click(function () {
			$('body,html').animate({
				scrollTop: 0
			}, 800);
			return false;
		});
	});
});