
// Orientation (iphone)

  var metas = document.getElementsByTagName('meta');
  var i;
  if (navigator.userAgent.match(/iPhone/i)) {
    for (i=0; i<metas.length; i++) {
      if (metas[i].name == "viewport") {
        metas[i].content = "width=device-width, minimum-scale=1.0, maximum-scale=1.0";
      }
    }
    document.getElementsByTagName('body')[0].addEventListener("gesturestart", gestureStart, false);
  }
  function gestureStart() {
    //$('meta[name="viewport"]').attr('content', 'width=device-width, minimum-scale=0.25, maximum-scale=1.6');
    for (i=0; i<metas.length; i++) {
      if (metas[i].name == "viewport") {
        metas[i].content = "width=device-width, minimum-scale=0.25, maximum-scale=1.0";
      }
    }
  }