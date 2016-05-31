var API_URL = "http://147.83.7.205:8080/kujosa/"

/* EDITAR USUARI v2 */

/* $(document).ready(function(){
    try{
    var authToken = JSON.parse(sessionStorage["auth-token"]);
    var currentGrupoUri = authToken["links"]["create-group"].uri;
    console.log(currentGrupoUri);
    console.log("hola");
    loadGru(currentGrupoUri);
    getUser();
    }catch(e){
        window.location.replace('index.html');
    }
});*/


$("#btnlogout").click(function(e){
    e.preventDefault();
    logout(function(){
        window.location.replace('index.html');
    });
})

/* EDITAR USUARI */

/* var userURL;
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
}); */

$("#save_btn").click(function(e) {
		e.preventDefault();
		$("#result_edit_process").text('');
		if($('#password_1').val()=="" || $('#verify').val()=="" || $('#nombre_1').val()=="" || $('#email_1').val()=="" ){
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
							window.location.replace("perfil.html");
				});
		}
}); 

/* REGISTRE D'USUARIS */

var userURL;

/* $("#register_btn").click(function(e) {
		e.preventDefault();
		
		if($('#username_1').val().length > 50 || $('#nombre_1').val().length > 50 || $('#email_1').val().length > 50 || $('#password_1').val().length > 32){
			$('<div class="alert alert-danger">Algún camp és massa llarg:<br>Nom d\'usuari, nom i correu: 50 caràcters max. <br>Contrasenya: 32 caràcters màx.</div>').appendTo($("#result_register_process"));
		}else if ($('#username_1').val() == "" || $('#nombre_1').val() == "" || $('#email_1').val() == ""	|| $('#password_1').val() == "" || $('#verify').val() == "") {
				$('<div class="alert alert-danger">Si us plau, emplena tots els camps</div>').appendTo($("#result_register_process"));
		}else if ($('#password_1').val() != $('#verify').val()) {
				$('<div class="alert alert-danger">La contrasenya no coincideix</div>').appendTo($("#result_register_process"));
		}else {
						
                    
                    
                    
                    
                                                var api = JSON.parse(sessionStorage.api);
						var uri = API_URL+"users";
						console.log("uri : "+uri);
						$.post(uri,
						{
							username: $('#username_1').val(),
							password: $('#password_1').val(),
							email: $('#email_1').val(),
							nombre: $('#nombre_1').val(),
							image: "chapuza.png"
						}).done(function(authToken){
							authToken.links = linksToMap(authToken.links);
							sessionStorage["auth-token"] = JSON.stringify(authToken);
							complete();
						}).fail(function(jqXHR, textStatus, errorThrown){
							var error = jqXHR.responseJSON;
							alert(error.reason);
						}
					);
				var user = new Object();
				user.name = $('#nombre_1').val();
				user.username = $('#username_1').val();
				user.userpass = $('#password_1').val();
				user.email = $('#email_1').val();
				createUser(userURL.href, userURL.type, JSON.stringify(user), function(user) {
				window.location.replace("/index.html");
				});
		}
});*/
/* $("#register_btn").click(function(event){
    event.preventDefault();
register($("#username").val(),
$("#password").val(), 
$("#fullname").val(), 
$("#email").val(), 
$("#name").val(), 
$("#image").val(), 
function()
{  	console.log("register");
	var formData = new FormData();
	formData.append('
}

}); */



$("#register_btn").click(function(e){
    var username= $("#username_1").val();
    var password=$("#password_1").val();
    var nombre=$("#nombre_1").val();
    var email=$("#email_1").val();
    e.preventDefault();
    $('progress').toggle();
    var formData = new FormData();
    formData.append('username', username);
    formData.append('password', password);
    formData.append('email', email);
    formData.append('nombre', nombre);
    formData.append('imagen', $('#inputFile')[0].files[0]);    
    console.log(formData);
    registerUser(formData);
    console.log('Usuari creat');
});

function registerUser(formdata){
    loadAPI(function(){
        var api = JSON.parse(sessionStorage.api);
        var uri = API_URL+'users';
        $.ajax({
            url: uri,
		    type: 'POST',
            xhr: function(){
                var myXhr=$.ajaxSettings.xhr();
                if(myXhr.upload){
                    myXhr.upload.addEventListener('progress',progressHandlingFunction,false);
                }
                return myXhr;
            },
            crossDomain: true,
            data: formdata,
            cache: false,
            contentType: false,
            processData: false
        }).done(function(data, status,jqxhr){
            var response = $.parseJSON(jqxhr.responseText);
            var lastfilename = response.filename;
            $('progress').toggle();
            window.location.replace('index.html');
        }).fail(function(jqXHR, textStatus) {
           var error = JSON.parse(jqXHR.responseText);
           console.log(error.reason);
        });
       
    });
}

/* ENTRADA d'USUARIS */



$("#login_btn").click(function(e){
	console.log("Function")
	e.preventDefault();
	login($("#username").val(), $("#password").val(),function(){
            console.log("TODO CORRECTO");
            window.location.replace('kujosa.html');
            });
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
	$.get(API_URL)
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
		console.log("uri : "+uri);
		$.post(uri,
			{
				username: loginid,
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
		url = "http://147.83.7.205:8080/kujosa-api/" + 'users/'+ username;
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

/* MOSTRAR ESDEVENIMENTS */

//$(document).ready(function(user){
//      $("#username").text(user.fullname);
//      $("#username").append('<span class="caret"></span>');
//}

$(document).ready(function(){

	var myEventsURL=null;
	var myCommentsURL=null;
	var myURL =null;
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

/* $(document).ready(function(){
    try{
    var authToken = JSON.parse(sessionStorage["auth-token"]);
    console.log("hola");
    var profile = JSON.parse(sessionStorage["profile"]);
    console.log(profile);
    var filename = profile.image;
    $("result_profile").append('<p>Nom complet: '+ profile.fullname +'<br>Login ID: ' + profile.loginid + '<br>Correu: ' + profile.email + '</p>' + '<br>Imatge de perfil: <img src="img/' + filename + '" class="img-rounded img-responsive" />');
    }catch(e){
        window.location.replace('index.html');

} */

/* function loadMyComments(url){
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
	
} */
