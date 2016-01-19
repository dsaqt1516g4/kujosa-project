var API_BASE_URL = "http://147.83.7.205:8080/kujosa";

var token = $.cookie('token');
var namecomp = $.cookie('userid');
var role = $.cookie('role');

$("#register_btn").on("click", function(e) {
	e.preventDefault();	
	console.log("estoy reg"); 
	createUser();
});

$("#btnlogout").on("click", function(e) {
	e.preventDefault();	
	console.log("estoy reg"); 
	logout();
});



$( "#login_btn" ).click(function(e) {
	event.preventDefault();
	login($("#username").val(), $("#password").val(), function(){
  	console.log("change");
  	window.location.replace('kujosa.html');	
  }); 	
  });

$( "#button_post_news" ).click(function(e) {
	event.preventDefault();
	createnews($("#headline").val(), $("#body").val(), function(){
  	console.log("change");
  	window.location.replace('news.html');	
  }); 	
  });

$( "#button_put_news" ).click(function(e) {
	event.preventDefault();
	editnews($("#headline").val(), $("#body").val(), function(){
  	console.log("change");
  	window.location.replace('news.html');	
  }); 	
  });
$( "#button_delete" ).click(function(e) {
	event.preventDefault();
	editnews($("#headline").val(), $("#body").val(), function(){
  	console.log("change");
  	window.location.replace('news.html');	
  }); 	
  });

$( "#create_btn" ).click(function(e) {
	event.preventDefault();
	createEvent function(){
  	console.log("Create Event");
  	window.location.replace('event.html');	
  }); 	
  });

$( "#button_register_modal" ).click(function(e) {
	event.preventDefault();
	createEvent function(){
  	console.log("Create Event");
  	window.location.replace('event.html');	
  }); 	
  });

$( "#post_comment" ).click(function(e) {
	event.preventDefault();
	createcoment function(){
  	console.log("Create Event");
  	window.location.replace('event.html');	
  }); 	
  });





function logout(){
	
	var url = API_BASE_URL + '/login';
	console.log(token);
	$.ajax({
    	type: 'DELETE',
   		url: url,
    	headers: {
        	"X-Auth-Token":token,
    	}
    }).done(function(data) { 
    	console.log("logout ");
		
  	}).fail(function(){});
}



function login(login, password, complete){
	
	loadAPI(function(){
		var api = JSON.parse(sessionStorage.api);
		var uri = API_BASE_URL+"login";
		//$("#nombre").text('');
		$.post(uri,
			{
				username: login,
				password: password
			}).done(function(authToken){
				console.log("estoy  login");
				authToken.links = linksToMap(authToken.links);
				sessionStorage["auth-token"] = JSON.stringify(authToken);
				complete();		
				
				$.cookie('token', authToken.token);				
				$.cookie('role', authToken.role);
				$.cookie('userid', authToken.userid);
				
				$.removeCookie('name');
				$.cookie('name', login);
				$.cookie('namelogin', login);
				
				//$('#nombre').text('Bienvenido, ' + $.cookie('name'));
														
				window.location.replace('kujosa.html');	

				
			}).fail(function(jqXHR, textStatus, errorThrown){
				var error = jqXHR.responseJSON;
				alert(error.reason);
			}
		);
	});
}


/*--------------------------------------------------------------------------------------------------------------*/

function createUser() {

	//$("#nombre").text('');
	
	formData.append("username",$('#username_1').val());
    formData.append("password",$('#password_1').val());
    formData.append("email",$('#email_1').val());
        formData.append("nombre",$('#nombre_1').val());
    formData.append("imagen",$("#inputFile")[0].files[0]);


	console.log(formData);

	//$("#result").text('');

	$.ajax({
		url : API_BASE_URL + '/users',
		type : 'POST',
		crossDomain : true,
		processData: false,
      //dataType : 'json',
	    //contentType: 'multipart/form-data',
	    contentType: false,
		data : formData,
	}).done(function(data, status, jqxhr) {
		console.log(data);
		$.cookie('token', data.token);
		$.cookie('userid', data.userid);
		$.cookie('role', data.role);
		
		$.removeCookie('name');
		$.removeCookie('password');
		$.removeCookie('email');
		
		
		$.cookie('namelogin', $('#name').val());
		$('#nombre').text('Bienvenido, ' + $.cookie('namelogin'));
		$.cookie('password', $('#password').val());
		$.cookie('email', $('#email').val());
		$.cookie('name', $('#name').val());
		
		
		window.location.replace('Eventos.html');

		
		$('<strong> Bienvenido, </strong>' + data.name).appendTo($('#result5'));

	}).fail(function(jqxhr) {
		if(jqxhr.status == 500){			
			alert('Debe rellenar todos los campos');
			}
		if(jqxhr.status == 409){			
			alert('Este usuario ya existe');
			}
	});

}



function createnews(){

	objeto = {
        "headline" : $('#headline').val(),
        "body" : $('#body').val(),
        
       
    }



	var data = JSON.stringify(objeto);
	console.log(data);


	console.log($.cookie('token'));

	$.ajax({
		url : API_BASE_URL + '/news',
		type : 'POST',
		crossDomain : true,
		dataType : 'json',
		contentType:'application/vnd.dsa.kujosa.news+json',
		data : data,
		headers: {
        'X-Auth-Token':$.cookie('token'),
		},
	}).done(function(data, status, jqxhr) {
		console.log(data);
		
		window.location.replace('news.html');

	}).fail(function(jqxhr) {
		if(jqxhr.status == 500){			
			alert('Debe rellenar todos los campos');
			}
		if(jqxhr.status == 409){			
			alert('Esta News ya existe');
			}
	});


}

function editnews(){

	objeto = {
        "headline" : $('#headline').val(),
        "body" : $('#body').val(),
        
       
    }
	var data = JSON.stringify(objeto);
	console.log(data);


	console.log($.cookie('token'));

	$.ajax({
		url : API_BASE_URL + '/news/id',
		type : 'PUT',
		crossDomain : true,
		dataType : 'json',
		contentType:'application/vnd.dsa.kujosa.news+json',
		data : data,
		headers: {
        'X-Auth-Token':$.cookie('token'),
		},
	}).done(function(data, status, jqxhr) {
		console.log(data);
		
		window.location.replace('news.html');

	}).fail(function(jqxhr) {
		if(jqxhr.status == 500){			
			alert('Error');
			}
		
	});


}



function createEvent(){

	objeto = {
        "titol" : $('#event_title').val(),
        "text" : $('#event_description').val(),
        "ratio" : 0,
        "latitud" : $('#event_lat').val(),
		"longitud" : $('#event_lon').val(),
		"startdate":$('#event_date').val(),
		"enddate":$('#event_date').val(),
		"userid":$.cookie('userid'),
    }



	var data = JSON.stringify(objeto);
	console.log(data);

	$("#result").text('');

	console.log($.cookie('token'));

	$.ajax({
		url : API_BASE_URL + '/companies',
		type : 'POST',
		crossDomain : true,
		dataType : 'json',
		contentType:'application/vnd.dsa.eventsBCN.company+json',
		data : data,
		headers: {
        'X-Auth-Token':$.cookie('token'),
		},
	}).done(function(data, status, jqxhr) {
		console.log(data);
		
		window.location.replace('Eventos.html');

	}).fail(function(jqxhr) {
		if(jqxhr.status == 500){			
			alert('Debe rellenar todos los campos');
			}
		if(jqxhr.status == 409){			
			alert('Esta empresa ya existe');
			}
	});


}


function createcoment(){

	objeto = {
        "content" : $('#comment_text).val(),
		"userid":$.cookie('userid'),
    }



	var data = JSON.stringify(objeto);
	console.log(data);

	$("#result").text('');

	console.log($.cookie('token'));

	$.ajax({
		url : API_BASE_URL + '/companies',
		type : 'POST',
		crossDomain : true,
		dataType : 'json',
		contentType:'application/vnd.dsa.eventsBCN.company+json',
		data : data,
		headers: {
        'X-Auth-Token':$.cookie('token'),
		},
	}).done(function(data, status, jqxhr) {
		console.log(data);
		
		window.location.replace('Eventos.html');

	}).fail(function(jqxhr) {
		if(jqxhr.status == 500){			
			alert('Debe rellenar todos los campos');
			}
		if(jqxhr.status == 409){			
			alert('Esta empresa ya existe');
			}
	});


}
