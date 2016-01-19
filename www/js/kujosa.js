/* NOSTRA API */

var cookies = document.cookie;
var username = getCookie("username");
var password = getCookie("password");

var eventURL;
var eventID;
var commentsURL;
var lat;
var lon;
var titol_event;
var link_owner;

var loginlogout;


$("#post_comment").click(function(e){
	e.preventDefault();
	$("#result_comments2").text('');
	if($('#comment_text').val().length > 200){
		$('<div class="alert alert-danger">El comentari és massa llarg, té més de 200 caràcters</div>').appendTo($("#result_comments2"));
	}else{
		postComment();
	}
});

$('#logout_btn').click(function(e){
	e.preventDefault();
	deleteCookie('username');
});

$('#delete_event_btn').click(function(e){
	e.preventDefault();
	deleteUser(eventURL, function(){
		window.location.replace("/home.html");
	});
});

$("#edit_event_btn").click(function(e){
	e.preventDefault();
	window.location.replace("/edit_event.html");
});

$(document).ready(function(){

	if($.cookie('username')==undefined){
		window.location.replace("index.html");
	}
	eventURL=$.cookie('link-event');
	commentsURL=$.cookie('link-comment');
	$('<a id="username_loged">'+ $.cookie('username') +'</a>').appendTo($('#user_loged'));
	$('<h1>'+ $.cookie('username') +'</h1>').appendTo($('#username'));
	loadRootAPI(function(rootAPI){
		eventsURL = rootAPI.getLink('events').href;
		loadEvent(eventURL);
	});
	

	var followedEventsURL=$.cookie('link-user')+'/events/followed';
	var myEventsURL=$.cookie('link-user')+'/events';
	var myURL =$.cookie('link-user');
	loadFollowers(eventURL+"/users");
	
});

function loadEvent(url){
	getEvent(url, function (event){
		var date = new Date(event.eventDate);
		var day = date.getDate();
		var month = date.getMonth() + 1;
		var year = date.getFullYear();
		var event_date = day+'/'+month+'/'+year;
		var eventID= event.id;
		$.cookie('ratio', event.ratio);
		titol_event=event.titol;
		var owner = event.owner;
		$('<h3>' + event.owner + '</h3><br><br><br><br>').appendTo($('#event_owner'));
		$('<h1>' + event.titol + '</h1>').appendTo($('#info_event'));
		$('<h4>' + event_date + '</h4>').appendTo($('#info_event'));
		$('<h6>' + event.text + '</h6>').appendTo($('#info_event'));
		if(event.owner.toUpperCase() ==  $.cookie('username').toUpperCase()){
			$('#event_settings').show();
		}

		if($.cookie('rol')=='admin' || $.cookie('username').toUpperCase()==owner.toUpperCase()){
			$('#delete_event').show();
		}
		lat = event.lat;
		lng = event.lon;
		initialize();
		loadComments(eventURL+'/comments');
	});
}

function initialize() {
	var myLatlon = new google.maps.LatLng(lat, lon);
	var mapOptions = {
		zoom: 8,
		center: myLatlng 
	};
	map = new google.maps.Map(document.getElementById('map-canvas'),
    mapOptions);
	
	
	var marker = new google.maps.Marker({
      position: myLatlon,
      map: map,
      title: 'Hello World!'
	});
}

google.maps.event.addDomListener(window, 'load', initialize);

function loadComments(url){
	var comments = getComments(url, function(commentCollection){
		$.each(commentCollection.comments, function(index, item){
			var comment = new Comment(item);
			var date = new Date(comment.lastModified);
			var hours = date.getHours();
			var minutes = date.getMinutes();
			var seconds = date.getSeconds();
			var day = date.getDate();
			var month = date.getMonth() + 1;
			var year = date.getFullYear();
			var date_format = day+'/'+month+'/'+year+' '+hours+':'+minutes+':'+seconds;
			$('<div class="well well-sm"><div class="media" ><div class="media-body"><class="media-heading">'+comment.comment+'<p><a class="btn btn-xs btn-default pull-right"><span class="glyphicon glyphicon-comment"></span> '+comment.username+'</a><a class="btn btn-xs btn-default pull-right"><span class="glyphicon glyphicon-dashboard"></span> '+date_format+'</a></p></div></div></div>').appendTo($('#result_comments'));
		});
	});
	
}

function loadMyProfile(url){
	getUser(url, function(user){
            });
}

function postComment(){
		
		var comment = new Object();
		comment.username= $.cookie('username');
		comment.comment = $('#comment_text').val();
		comment.eventId = eventID;
		var type = 'application/vnd.kujosa.api.comment+json';
		createComment(eventURL+'/comments', type, JSON.stringify(comment), function(comment){
			window.location.reload();
		});
}

function joinEvent(){
	var user = new Object();
	user.username = $.cookie('username');
	url = $.cookie('link-event')+'/users';
	type = 'application/vnd.kujosa.api.user+json';
	followEvent(url, type, JSON.stringify(user), function(user){
		var event = new Object();
		event.ratio = parseInt($.cookie('ratio')) + 1;
		updateEvent($.cookie('link-event'), 'application/vnd.kujosa.api.event+json', JSON.stringify(event), function(event){
			window.location.reload();
		});
	});
	
	
}

function leaveEvent(){
	var user = new Object();
	user.username = $.cookie('username');
	url = $.cookie('link-event')+'/users';
	type = 'application/vnd.kujosa.api.user+json';
	unfollowEvent(url, type, JSON.stringify(user), function(user){
		var event = new Object();
		event.ratio = parseInt($.cookie('ratio')) - 1;
		updateEvent($.cookie('link-event'), 'application/vnd.kujosa.api.event+json', JSON.stringify(event), function(event){
			window.location.reload();
		});
	});
}


/* FOTOSHARE */

$("#button_getnews").click(function(e) {
	e.preventDefault();
	getNews();
});

$("#button_login").click(function(e) {
	e.preventDefault();
   
	if(getCookie('username')=="")
	{
		var newLogin = new Object();
		newLogin.username = $("#username").val()
		newLogin.userpass= $("#password").val()
		loginlogout = "1";				 
		Login(newLogin);
	}
	else{
		 $('<div class="alert alert-danger"> <strong>Error!</strong> Abans has de tancar sessió en el compte actual. </div>').appendTo($("#loginform"));
	}
		
});

$("#button_logout").click(function(e) {
	e.preventDefault();
	
	if(getCookie('username')=="")
	{
		 $('<div class="alert alert-danger"> <strong>Error!</strong> Per a poder tancar sessió has d&#39;haver iniciat sessió... Prova a registrar-te i després inicia sessió!</div>').appendTo($("#loginform"));
	}
	else
	{
		Logout();
		window.location = "index.html";
	}
});

/********* Otras funciones *************/

$("#button_registrarse").click(function(e) {
    e.preventDefault();
                         
    var newSignin = new Object();
    newSignin.username = $("#username").val();
    newSignin.password = $("#password").val();
	newSignin.name = $("#name").val();
	newSignin.email = $("#mail").val();
	valor = document.getElementById("mail").value;
	if (document.getElementById("mail").value.indexOf('@') == -1) 
	{
		alert('Introdueixi un correu electrònic vàlid, ha oblidat l\'@\'?');
		return false;	 
	}
	else if (document.getElementById("mail").value.indexOf('.') == -1) 
	{
		alert('Introdueix una direcció de correu electrònic vàlida, ha oblidat el punt \'.\'?');
		return false;
	}
	
	Signup(newSignin);
	                        
    
});

/*********** Ordenar ******************/

$("#button_score").click(function(e) {
	e.preventDefault();  
	$("#foto_result").text(' ');
	var ordenarpor = "byscore";
	 getVideosByX(ordenarpor);
});

$("#button_date").click(function(e) {
	e.preventDefault();  
	$("#foto_result").text(' ');
	 var ordenarpor = "bydate";
	 getVideosByX(ordenarpor);
});


$("#button_username").click(function(e) {
	e.preventDefault();  
	$("#foto_result").text(' ');
	 var ordenarpor = "byusername";
	 getVideosByX(ordenarpor);
});

