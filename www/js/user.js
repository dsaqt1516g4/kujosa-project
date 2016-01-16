var API_URL = "http://10.83.63.80:8080/kujosa/";
var AUTH_URL= "http://10.83.63.80:8080/kujosa-auth/ServletLogin";

/* EDITAR USUARI */

var userURL;
$(document).ready(function() {

	if($.cookie('username')==undefined){
		window.location.replace("index.html");
	}
	userURL = $.cookie('link-user');
	getUser(userURL, function(user){
		var userlog= new User(user);
		$('#name').val(user.nom);
		$('#password').val(user.password);
		$('#verify').val(user.password);
		$('#email').val(user.email);
	});
});

$("#save_btn").click(function(e) {
		e.preventDefault();
		$("#result_edit_process").text('');
		if($('#password').val()=="" || $('#verify').val()=="" || $('#name').val()=="" || $('#email').val()=="" ){
			$('<div class="alert alert-danger">Emplena tots els camps</div>').appendTo($("#result_edit_process"));
		}else if ($('#password').val() != $('#verify').val()) {
				$('<div class="alert alert-danger">La contrasenya no coincideix</div>').appendTo($("#result_edit_process"));
		}else {
				var user = new Object();
				user.username = $.cookie('username');
				user.password =  $('#password').val();
				user.nom = $('#name').val();
				user.email = $('#email').val();
				updateUser(userURL, 'application/vnd.kujosa.api.user+json', JSON	.stringify(user), function(user) {
							window.location.replace("user_profile.html");
				});
		}
}); 

/* REGISTRE D'USUARIS */

var userURL;

$(document).ready(function() {
	loadRootAPI(function(rootAPI) {
		userURL = rootAPI.getLink('create-user');
	});
});

$("#register_btn").click(function(e) {
		e.preventDefault();
		
		if($('#username_1').val().length > 50 || $('#nombre_1').val().length > 50 || $('#email_1').val().length > 50 || $('#password_1').val().length > 32){
			$('<div class="alert alert-danger">Algún camp és massa llarg:<br>Nom d\'usuari, nom i correu: 50 caràcters max. <br>Contrasenya: 32 caràcters màx.</div>').appendTo($("#result_register_process"));
		}else if ($('#username').val() == "" || $('#name').val() == "" || $('#email').val() == ""	|| $('#password').val() == "" || $('#verify').val() == "") {
				$('<div class="alert alert-danger">Per favor, emplena tots els camps</div>').appendTo($("#result_register_process"));
		}else if ($('#password').val() != $('#verify').val()) {
				$('<div class="alert alert-danger">La contrasenya no coincideix</div>').appendTo($("#result_register_process"));
		}else {
				
				var user = new Object();
				user.name = $('#nombre_1').val();
				user.username = $('#username_1').val();
				user.userpass = $('#password_1').val();
				user.email = $('#email_1').val();
				createUser(userURL.href, userURL.type, JSON.stringify(user), function(user) {
				window.location.replace("/index.html");
				});
		}
});

/* ENTRADA d'USUARIS */



$("#login_btn").click(function(e){
	console.log("Function")
	e.preventDefault();
	login($("#username").val(), $("#password").val() );
	
});

function linksToMap(links){
		console.log("Hfunction links");

	var map = {};
	$.each(links, function(i, link){
		$.each(link.rels, function(j, rel){
			map[rel] = link;
		});
	});

	return map;
}

function loadAPI(complete){
	console.log("Hfunction API");
	$.get(BASE_URI)
		.done(function(data){
			var api = linksToMap(data.links);
			sessionStorage["api"] = JSON.stringify(api);
			complete();
		})
		.fail(function(data){
		});
}

function login(loginid, password, complete){
	console.log("Hfunction login: "+loginid+" amb password: " +password+" complete: "+complete)
	loadAPI(function(){
		var api = JSON.parse(sessionStorage.api);
		var uri = API_URL+"login";
		console.log(uri);
		$.post(uri,
			{
				login: loginid,
				password: password
			}).done(function(authToken){
				authToken.links = linksToMap(authToken.links);
				sessionStorage["auth-token"] = JSON.stringify(authToken);
				complete();
			}).fail(function(jqXHR, textStatus, errorThrown){
				var error = jqXHR.responseJSON;
				alert(error.reason);
			}
		);
	});
}
/*
function Login(username, password){
	//$("#result_login").text('');


	Console.log( "Tryng Login with :"+username +"and pass : " +password);
	var credentials = 'username='+username+'password='+password+'';


	$.post({
		uri : API_URL+'login', {
		username : username,
		password: passsword
	}).done(console.log("Done").fail(console.log("FAIL"));



	$.ajax({
		
		url : API_URL+'login',
		type : 'POST',
		crossDomain : true,
		data: credentials,
		dataType: 'html',
		beforeSend: function (request)

		{
		request.setRequestHeader('content-type', 'application/x-www-form-urlencoded');
		},
		success : function(ata, status, jqxhr) {
		var response = jqxhr.responseText;

		if (response=="successadmin")
		{
			url = API_URL + 'users/'+ username;
			getUser(url, function(user){
				var userlog= new User(user);
					$.cookie('username', username);
					$.cookie('link-user', userlog.getLink('self').href);
					$.cookie('rol', 'admin');
					window.location.replace("/home.html");
			});

		}
		else if (response=="successusuario"){
		url = "http://147.83.7.159:8080/kujosa-api/" + 'users/'+ username;
		getUser(url, function(user){
			var userlog= new User(user);
				$.cookie('username', username);
				$.cookie('link-user', userlog.getLink('self').href);
				$.cookie('rol', 'registered');
				window.location.replace("/home.html");
		});

		}
		else if (response=="wrongpass"){
			$('<div class="alert alert-danger">Usuari i/o contrasenya incorrecta</div>').appendTo($("#result_login"));
		}
		else if (response==""){

			$('<div class="alert alert-danger">Usuari i/o contrasenya incorrecta</div>').appendTo($("#result_login"));
		}

		},

		});
} */

/* MOSTRAR ALTRES USUARIS */

var eventsURL;
var markers = [];
var iterator = 0;

var usersURL = API_URL + 'users';
$("#search_btn").click(function(e){
	e.preventDefault();
	loadUsersBy(usersURL, $('#search_user').val());
});


$('#logout_btn').click(function(e){
	deleteCookie('username');
});

$(document).ready(function(){

	if($.cookie('username')==undefined){
		window.location.replace("index.html");
	}
	$('<a id="username_logged">'+ $.cookie('username') +'</a>').appendTo($('#user_logged'));
	$('<h1>'+ $.cookie('username') +'</h1>').appendTo($('#username'));
	loadUsersBy(usersURL, "");
	
});

function loadUsersBy(url, username){
	$('#result_users').text('');
	if(username == ""){
		var urlSearch=url;
	}else{
		var urlSearch=url+'/search?username='+username;
	}
	var users = getUsers(urlSearch, function(userCollection){
		$.each(userCollection.users, function(index,item){
			var user = new User(item);

			//var link = $('<div class="well well-sm"><div class="media" ><a class="thumbnail pull-left"> <img class="media-object" src="./img/profile.png" height="70" width="70"></a><div class="media-body"><h4 class="media-heading">'+user.username+'</h4><p><a class="btn btn-xs btn-default" id="profile"><span class="glyphicon glyphicon-user"></span>Veure perfil/a></p></div></div></div>');
			//link.click(function(e){
			//	 $.cookie('link-friend',  user.getLink('self').href);
			//	 window.location.replace("/friend_profile.html");
			//});
			
			var div = $('<div></div>');
			div.append(link);
			$('#result_users').append(div);
		});
	});	

}

/* MOSTRAR ESDEVENIMENTS */

$(document).ready(function() {
	$('<a id="username_loged">'+ $.cookie('username') +'</a>').appendTo($('#user_loged'));
});



$('#edit_btn').click(function(e){
	e.preventDefault();
	window.location.replace("edit_profile.html");
});


$('#logout_btn').click(function(e){
	deleteCookie('username');
});

$(document).ready(function(){

	if($.cookie('username')==undefined){
		window.location.replace("index.html");
	}
	loadRootAPI(function(rootAPI){
		eventsURL = rootAPI.getLink('events').href;
	});
	;
	var followedEventsURL=$.cookie('link-user')+'/events/followed';
	var myEventsURL=$.cookie('link-user')+'/events';
	var myCommentsURL=$.cookie('link-user') +'/comments';
	var myURL =$.cookie('link-user');
	loadMyEvents(myEventsURL);
	loadMyProfile(myURL);
	loadMyComments(myCommentsURL);
});

function loadMyEvents(url){
	var events = getEvents(url, function(eventCollection){
		$.each(eventCollection.events, function(index,item){
			var event = new Event(item);
			var link = $('<div class="well well-sm"><div class="media" ><div class="media-body"><h4 class="media-heading">'+event.titol+'</h4><h6>Ràtio '+event.ratio+'</h6><p><a class="btn btn-xs btn-default"><span class="glyphicon glyphicon-map-marker"></span>Veure esdeveniment</a></p></div></div></div>');
			link.click(function(e){
				 $.cookie('link-event',  event.getLink("self").href);
				 window.location.replace("/event.html");
			});
			
			var div = $('<div></div>');
			div.append(link);
			$('#result_events').append(div);
		});
		
	});
	
}


function loadMyProfile(url){
	getUser(url, function(user){
		var date = new Date(user.registerDate);
		var day = date.getDate();
		var month = date.getMonth() + 1;
		var year = date.getFullYear();
		var register_date = day+'/'+month+'/'+year;
		$('<h1>'+ user.username +'</h1>').appendTo($('#username'));
		$('<li class="list-group-item text-right"><span class="pull-left"><strong>Data de registre</strong></span>' + register_date + '</li>').appendTo($('#result_profile'));
		$('<li class="list-group-item text-right"><span class="pull-left"><strong>Nom</strong></span>' + user.name + '</li>').appendTo($('#result_profile'));
		$('<li class="list-group-item text-right"><span class="pull-left"><strong>Correu electrònic</strong></span>' + user.email + '</li>').appendTo($('#result_profile'));
	});
	

}

function loadMyComments(url){
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
