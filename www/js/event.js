/* CREACIÓ d'ESDEVENIMENTS */

var map;
var eventsURL;
var markers =[];
$(document).ready(function(){
	initialize();
	loadRootAPI(function(rootAPI){
		eventsURL = rootAPI.getLink('create-event');
	});
});

$('#create_btn').click(function(e){
	e.preventDefault();
	var datePattern = /^\d{2,4}\-\d{1,2}\-\d{1,2}$/;
	
	$('#result_create').text('');
	if($('#event_title').val().length > 50 ||$('#event_description').val().length > 500){
		$('<div class="alert alert-danger">El títol o descripció son massa llargs</div>').appendTo($("#result_create"));
	}else if ($('#event_titol').val() == "" || $('#event_lat').val() == "" || $('#event_date').val() == ""	) {
		$('<div class="alert alert-danger">Emplena tots els camps</div>').appendTo($("#result_create"));
	}else if(!$('#event_date').val().match(datePattern)){
		$('<div class="alert alert-danger">El format de la data no és adequat (AAAA-MM-DD) </div>').appendTo($("#result_create"));
	}else{
		createEvent2();
	}
});

function initialize() {
  var mapOptions = {
    zoom: 15
  };
  map = new google.maps.Map(document.getElementById('map-canvas'),
      mapOptions);

  // Try HTML5 geolocation
  if(navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(function(position) {
      var pos = new google.maps.LatLng(position.coords.latitude,
                                       position.coords.longitude);

      var infowindow = new google.maps.InfoWindow({
        map: map,
        position: pos,
        content: 'Estàs aquí'
      });

      map.setCenter(pos);
    }, function() {
      handleNoGeolocation(true);
    });
  } else {
    // Browser doesn't support Geolocation
    handleNoGeolocation(false);
  }
  
google.maps.event.addListener(map, "rightclick", function(event) {
	    var lat = event.coordenades.lat();
	    var lon = event.coordenades.lon();
	    var myCoordinates = new google.maps.LatLng(lat,lon);
	    deleteMarkers();
	    addMarker(myLatlng);
	    
	    $('#event_lat').val(lat);
	    $('#event_lon').val(lon);
	});
}

function handleNoGeolocation(errorFlag) {
  if (errorFlag) {
    var content = 'Error: The Geolocation service failed.';
  } else {
    var content = 'Error: Your browser doesn\'t support geolocation.';
  }

  var options = {
    map: map,
    position: new google.maps.LatLng(60, 105),
    content: content
  };

  var infowindow = new google.maps.InfoWindow(options);
  map.setCenter(options.position);
}

function addMarker(myLatlng) {
	  markers.push(new google.maps.Marker({
	    position: myLatlng,
	    map: map,
	    draggable: false,
        title: 'Nou esdeveniment'
	  }));
	}

	function setAllMap(map) {
		  for (var i = 0; i < markers.length; i++) {
		    markers[i].setMap(map);
		  }
		}

	function clearMarkers() {
		  setAllMap(null);
		}

	function showMarkers() {
		  setAllMap(map);
		}

	function deleteMarkers() {
		  clearMarkers();
		  markers = [];
		}

//google.maps.event.addDomListener(window, 'load', initialize);
//function(user){
//      $("#username").text(user.fullname + ' ');
//}

function createEvent2(){
	var event = new Object();
	event.title = $('#event_title').val();
	event.lat = $('#event_lat').val();
	event.lon = $('#event_lon').val();
	event.text = $('#event_description').val();
	event.owner = user.userid;
	event.eventDate = $('#event_date').val();
	event.ratio = 0;
	createEvent(eventsURL.href, eventsURL.type, JSON.stringify(event), function(event){
		window.location.replace("home.html");
	});
} 

/* EDICIÓ d'ESDEVENIMENTS */

//var API_URL= "http://147.83.7.159:8080/kujosa-api/";

var eventURL;
var eventID;
var commentsURL;
var late;
var lnge;
var markers =[];

$('#save_settings').click(function(e){
	e.preventDefault();
	var datePattern = /^\d{2,4}\-\d{1,2}\-\d{1,2}$/;
	$('#result_edit').text('');

	if($('#event_title').val().length > 50 ||$('#event_description').val().length > 500){
		$('<div class="alert alert-danger">El títol o descripció són massa llargs</div>').appendTo($("#result_edit"));
	}else if ($('#event_title').val() == "" || $('#event_coordX').val() == "" || $('#event_date').val() == ""	) {
		$('<div class="alert alert-danger">Emplena tots els camps obligatoris</div>').appendTo($("#result_edit"));
	}else if(!$('#event_date').val().match(datePattern)){
		$('<div class="alert alert-danger">El format de la data no és correcte (AAAA-MM-DD)</div>').appendTo($("#result_edit"));
	}else{
		var event = new Object();
		event.title = $('#event_title').val();
		event.description = $('#event_description').val();
		event.lat = $('#event_lat').val();
		event.lon = $('#event_lon').val();
		event.eventDate = $('#event_date').val();
		event.ratio = $.cookie('ratio');
		var type = 'application/vnd.kujosa.api.event+json';
		updateEvent(eventURL, type, JSON.stringify(event), function(event){
			window.location.replace("event.html");
		});
	}
});

$(document).ready(function(){
	if($.cookie('username')==undefined){
		window.location.replace("index.html");
	}
	eventURL=$.cookie('link-event');
	$('<a id="username_logged">'+ $.cookie('username') +'</a>').appendTo($('#user_logged'));

	
	loadRootAPI(function(rootAPI){
		eventsURL = rootAPI.getLink('events').href;
		loadEvent(eventURL);
	});
	
});

function loadEvent(url){
	getEvent(url, function (event){
		var eventID= event.id;
		$('#event_title').val(event.title);
		$('#event_description').text(event.text);
		$('#event_coordX').val(event.lat);
		$('#event_coordY').val(event.lon);
		$('#event_date').val(event.eventDate);
		$.cookie('popularity', event.ratio);
		late = event.lat;
		lnge = event.lon;
		initialize();
	});
}


/* function initialize() {
	  var mapOptions = {
	    zoom: 15
	  };
	  map = new google.maps.Map(document.getElementById('map-canvas'),
	      mapOptions);

	  // Try HTML5 geolocation
	  if(navigator.geolocation) {
	    navigator.geolocation.getCurrentPosition(function(position) {
	      var pos = new google.maps.LatLng(late,
	                                       lnge);

	      var infowindow = new google.maps.InfoWindow({
	        map: map,
	        position: pos,
	        content: 'Coordenades actuals'
	      });

	      map.setCenter(pos);
	    }, function() {
	      handleNoGeolocation(true);
	    });
	  } else {
	    // Browser doesn't support Geolocation
	    handleNoGeolocation(false);
	  }
	  
	  google.maps.event.addListener(map, "rightclick", function(event) {
		    var lat = event.latLng.lat();
		    var lng = event.latLng.lng();
		    var myLatlng = new google.maps.LatLng(lat,lng);
		    deleteMarkers();
		    addMarker(myLatlng);
		    
		    $('#event_coordX').val(lat);
		    $('#event_coordY').val(lng);
		});
	}

	function handleNoGeolocation(errorFlag) {
	  if (errorFlag) {
	    var content = 'Error: The Geolocation service failed.';
	  } else {
	    var content = 'Error: Your browser doesn\'t support geolocation.';
	  }

	  var options = {
	    map: map,
	    position: new google.maps.LatLng(60, 105),
	    content: content
	  };

	  var infowindow = new google.maps.InfoWindow(options);
	  map.setCenter(options.position);
	}

	function addMarker(myLatlng) {
		  markers.push(new google.maps.Marker({
		    position: myLatlng,
		    map: map,
		    draggable: false,
	        title: 'New event'
		  }));
        }

		function setAllMap(map) {
			  for (var i = 0; i < markers.length; i++) {
			    markers[i].setMap(map);
			  }
			}

		function clearMarkers() {
			  setAllMap(null);
			}

		function showMarkers() {
			  setAllMap(map);
			}

		function deleteMarkers() {
			  clearMarkers();
			  markers = [];
			}

	google.maps.event.addDomListener(window, 'load', initialize);

*/
