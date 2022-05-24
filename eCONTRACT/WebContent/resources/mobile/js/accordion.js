
// Accordion

if (document.getElementById){
  document.write('<style type="text/css">\n')
  document.write('.row_menu{display: none;}\n')
  document.write('</style>\n')
  }
  function SwitchMenu(obj){
	  if(document.getElementById){
	  var el = document.getElementById(obj);
	  var ar = document.getElementById("accordion").getElementsByTagName("span"); 
		  if(el.style.display != "block"){ 
			  for (var i=0; i<ar.length; i++){
				  if (ar[i].className=="row_menu") 
				  ar[i].style.display = "none";
				  if (ar[i].className=="row_menu2") 
				  ar[i].style.display = "block";
			  }
			  el.style.display = "block";
		  }else{
			  el.style.display = "none";
		  }
	  }
  }