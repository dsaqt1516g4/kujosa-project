$(function(){
   getCurrentUserProfile(function(user){
      $("#aProfile").text(user.fullname + ' ');
      $("#aProfile").append('<span class="caret"></span>');
   });

   var authToken = JSON.parse(sessionStorage["auth-token"]);
   var currentNewsUri = authToken["links"]["current-news"].uri;
   loadNews(currentNewsUri, function(news){
      $("#news-list").empty();
      processNewCollection(news);
   });
});

function previousNews(){
  loadNews($('#formPrevious').attr('action'), function(news){
    processNewCollection(news);
  });
}

function processNewCollection(news){
  var lastIndex = news["news"].length - 1;
  $.each(news["news"], function(i,new){
      new.links=linksToMap(new.links);
      var edit = new.userid ==JSON.parse(sessionStorage["auth-token"]).userid;
      $("#news-list").append(listItemHTML(new.links["self"].uri, new.headline, new.body, edit));
      if(i==0)
        $("#buttonUpdate").click(function(){alert("I don't do anything, implement me!")});
      if(i==lastIndex){
        $('#formPrevious').attr('action', new["links"].previous.uri);
      }
  });

   $("#formPrevious").submit(function(e){
      e.preventDefault();
      e.stopImmediatePropagation();
      previousNews();
      $("#buttonPrevious").blur();
    });

  $("a.list-group-item").click(function(e){
    e.preventDefault();
    e.stopImmediatePropagation();
    var uri = $(this).attr("href");
    getNew(uri, function(new){
      // In this example we only log the new
      console.log(new);
    });
  });
  $(".glyphicon-pencil").click(function(e){
    e.preventDefault();
    alert("This should open a new editor. But this is only an example.");});
}

$("#aCloseSession").click(function(e){
  e.preventDefault();
  logout(function(){
    window.location.replace('login.html');
  });
});





function listItemHTML(uri, subject, creator, edit){
  var a = '<a class="list-group-item" href="'+ uri +'">';
  var p = '<p class="list-group-item-text unclickable">' + subject + '</p>';
  var h = (edit) ? '<h6 class="list-group-item-heading unclickable" align="right">'+creator+' <span class="glyphicon glyphicon-pencil clickable"></span></h6>' : '<h6 class="list-group-item-heading unclickable" align="right">'+creator+'</h6>';;
  return a + p +  h + '</a>';
}






/* NEWS OLD */

var API_BASE_URL = "http://147.83.7.155:8080/kujosa";

//var id = $.cookie('id');
//var password = $.cookie('password')
//var email = $.cookie('email')
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

function loadNews(uri, complete){
	// var authToken = JSON.parse(sessionStorage["auth-token"]);
	// var uri = authToken["links"]["current-news"].uri;
	$.get(uri)
		.done(function(news){
			news.links = linksToMap(news.links);
			complete(news);
		})
		.fail(function(){});
}

function getNew(uri, complete){
	$.get(uri)
		.done(function(new){
			complete(new);
		})
		.fail(function(data){
		});
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

