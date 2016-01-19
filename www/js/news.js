var BASE_URL = "http://10.83.63.80:8080/kujosa";


$(function(){
   getCurrentUserProfile(function(user){
      $("#username").text(user.fullname);
      $("#username").append('<span class="caret"></span>');
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

/* function processNewCollection(news){
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
} */

/*function listItemHTML(uri, subject, creator, edit){
  var a = '<a class="list-group-item" href="'+ uri +'">';
  var p = '<p class="list-group-item-text unclickable">' + subject + '</p>';
  var h = (edit) ? '<h6 class="list-group-item-heading unclickable" align="right">'+creator+' <span class="glyphicon glyphicon-pencil clickable"></span></h6>' : '<h6 class="list-group-item-heading unclickable" align="right">'+creator+'</h6>';;
  return a + p +  h + '</a>';
}
*/






/* NEWS OLD */

var API_BASE_URL = "http://10.83.63.80:8080/kujosa";


$("#button_delete").click(function(e) {
	e.preventDefault();
	delete_news();
	
});

$("#button_post_news").click(function(e){
    e.preventDefault();
    post_news(formdata);
});
/*$("#logout").click(function(e) {
    e.preventDefault();
    console.log("dsag");
   logout();
}); */

/* function post_news (formdata){
    var authToken = JSON.parse(sessionStorage["auth-token"]);
    var uri=api.news.uri;
    $.ajax({
        url: uri,
        type: 'POST',
        crossDomain: true,
        dataType: "json",
        data:formdata,
        headers: {"X-Auth-Token":authToken.token}
        }).done(function(data, status, jqxhr){
        data.links=linksToMap(data.links);
        window.location.reload();
    }).fail(function(){
        console.log('Error');
    });
} */
    

/* function loadNews(uri, complete){
	// var authToken = JSON.parse(sessionStorage["auth-token"]);
	// var uri = authToken["links"]["current-news"].uri;
	$.get(uri)
		.done(function(news){
			news.links = linksToMap(news.links);
			complete(news);
		})
		.fail(function(){});
}
*/
/* function getNew(uri, complete){
	$.get(uri)
		.done(function(new){
			complete(new);
		})
		.fail(function(data){
		});
} */


/* $(document).ready(function() {
	getnews();
	
}); */

/* function delete_news() {
	
        var authToken = JSON.parse(sessionStorage["auth-token"]);
        var uri = authToken["links"]["delete-news"].uri;
        
        
	$.ajax({
		url : uri,
		type : 'DELETE',
		crossDomain : true,
		dataType : 'json',
		headers: {
        	"X-Auth-Token":authToken.token
                }

	}).done(function(data, status, jqxhr) {
		window.location = "index.html"

	}).fail(function(jqXHR, textStatus) {
		console.log(textStatus);
	});
} */


/* function getnews() {

	console.log(id);
        var authToken = JSON.parse(sessionStorage["auth-token"]);
        var uri = authToken["links"]["get-news"].uri;
	$.ajax(
			{
				url : url,
				type : 'GET',
				crossDomain : true,
                                dataType: "json",
                                headers: {"X-Auth-Token" : authToken.token}
				},
				

			}).done(function(data, status, jqxhr) {
		var news = JSON.parse(jqxhr.responseText);
	
			creator=news.userid;
			console.log=("El creador del news es:"+creator)
			$("#title1").text(news.titol);
			$("#content1").text(news.text);
	}).fail(function() {
		$("#news_result").text("No hi ha notícies");
	});
} */

