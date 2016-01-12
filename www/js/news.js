var API_BASE_URL = "http://147.83.7.155:8080/kujosa-api";

var id = $.cookie('id');
var password = $.cookie('password')
var email = $.cookie('email')
var newsid =id;



$("#button_delete").click(function(e) {
	e.preventDefault();
	delete_news();
	
});

$("#button_update").click(function(e) {
	e.preventDefault();
	document.location.href = '/kujosa/editar_noticia.html';
}); 

$("#logout").click(function(e) {
    e.preventDefault();
    console.log("dsag");
   logout();
});

function logout() {
	$("#logout").hide();
    $("#perfil").hide();
    $("#addnews").hide();
		 $.removeCookie('email');
		 $.removeCookie('password');
         $.cookie('loggedin', "nologuejat");
        window.location = "http://147.83.7.155/kujosa/index.html";
}


$(document).ready(function() {
	
	if ($.cookie('loggedin')=='nologuejat'){
	  	  $("#logout").hide();
	      $("#perfil").hide();
	}

	if ($.cookie('loggedin')=='loguejat'){
	    $("#singup").hide();
	    $("#signin").hide();
	}
	
	
	$(button_delete).hide();
	$(button_update).hide();
	getnews();
	
});


function delete_news() {
	
	var url = API_BASE_URL + '/news/' + id;

	$.ajax({
		url : url,
		type : 'DELETE',
		crossDomain : true,
		
		dataType : 'json',
		beforeSend : function(request) {
			request.withCredentials = true;
			request.setRequestHeader("Authorization", "Basic "
					+ btoa($.cookie('email') + ':' + $.cookie('password')));
		},

	}).done(function(data, status, jqxhr) {
		window.location = "http://147.83.7.155/kujosa/index.html"

	}).fail(function(jqXHR, textStatus) {
		console.log(textStatus);
	});
}


function getnews() {

	console.log(id);
	console.log("geawgd");

	var url = API_BASE_URL + "/newss/"+id;

	$.ajax(
			{
				url : url,
				type : 'GET',
				crossDomain : true,
				dataType : 'json',
				beforeSend : function(request) {
					request.withCredentials = true;
					request.setRequestHeader("Authorization", "Basic "
							+ btoa($.cookie('email') + ':' + $.cookie('password')));
					
				},
				

			}).done(function(data, status, jqxhr) {
		var news = JSON.parse(jqxhr.responseText);
	
			destinatario=news.email;
			console.log=("El creador del news es:"+destinatario)
			foto_news1.src = news.imagenes[0].urlimagen;
			$("#title1").text(news.titol);
			$("#content1").text(news.content);
			
			
			if($.cookie('email') == (destinatario||"adminmail")){
				$(button_delete).show();
				$(button_update).show();
			}
		
			if(news.imagenes[1].urlimagen != undefined){
				foto_news2.src = news.imagenes[1].urlimagen;
			}
			
			else if (news.imagenes[1].urlimagen != undefined){
				foto_news3.src = news.imagenes[2].urlimagen;
			}
			

	}).fail(function() {
		$("#news_result").text("No hi ha notícies");
	});


}

