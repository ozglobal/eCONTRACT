  function initialize() {

  // Create an array of styles.
  var pinkParksStyles = [
    {
      featureType: "all",
      stylers: [
        { saturation: -80 }
      ]
    },    
	{
      featureType: "road.highway",
      stylers: [
	    { hue: "#1f6ad1" },
        { saturation: 50 },
		{ lightness: -20 },
        { gamma: 1.51 },
      ]
    },	
	/*{
      featureType: "landscape",
      stylers: [
	    { hue: "#bbad98" },
        { saturation: 50 },
		{ lightness: 0 },
        
      ]
    }*/
  ];

  // Create a new StyledMapType object, passing it the array of styles,
  // as well as the name to be displayed on the map type control.
  var pinkMapType = new google.maps.StyledMapType(pinkParksStyles,
    {name: "j"});

  // Create a map object, and include the MapTypeId to add
  // to the map type control.
  
  var myLatlng = new google.maps.LatLng(-13.1635870, -72.5458610);
  
  var mapOptions = {
    zoom: 17,
	disableDefaultUI: true,
	zoomControl: true,
    zoomControlOptions: {
    style: google.maps.ZoomControlStyle.SMALL
    },
    mapTypeControl: true,
    center: myLatlng,
    mapTypeControlOptions: {
    mapTypeIds: [google.maps.MapTypeId.SATELLITE, 'j']
    }
  };
  var map = new google.maps.Map(document.getElementById('map_canvas'),
    mapOptions);
	
  var marker = new google.maps.Marker({
      position: myLatlng,
	  Icon:("/OZSMW/resources/mobile/images/j_place_icon.png"),
	  title:"J - yay!",
      animation: google.maps.Animation.DROP,	    
  });
  
  // To add the marker to the map, call setMap();
  marker.setMap(map); 	

  //Associate the styled map with the MapTypeId and set it to display.
  map.mapTypes.set('j', pinkMapType);
  map.setMapTypeId('j');
}