var BASE_URL = "http://10.83.63.80:8080/kujosa";

/* API de BEETER */

function linksToMap(links){
	var map = {};
	$.each(links, function(i, link){
		$.each(link.rels, function(j, rel){
			map[rel] = link;
		});
	});

	return map;
}

function loadAPI(complete){
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
	loadAPI(function(){
		var api = JSON.parse(sessionStorage.api);
		var uri = api.login.uri;
		$.post(uri,
			{
				login: loginid,
				password: password
			}).done(function(authToken){
				authToken.links = linksToMap(authToken.links);
				sessionStorage["auth-token"] = JSON.stringify(authToken);
                                window.location.replace("kujosa.html");
				complete();
			}).fail(function(jqXHR, textStatus, errorThrown){
				var error = jqXHR.responseJSON;
				alert(error.reason);
			}
		);
	});
}

function logout(complete){
	var authToken = JSON.parse(sessionStorage["auth-token"]);
	var uri = authToken["links"]["logout"].uri;
	console.log(authToken.token);
	$.ajax({
    	type: 'DELETE',
   		url: uri,
    	headers: {
        	"X-Auth-Token":authToken.token
    	}
    }).done(function(data) { 
    	sessionStorage.removeItem("api");
    	sessionStorage.removeItem("auth-token");
    	complete();
  	}).fail(function(){});
}

/* function getCurrentUserProfile(complete){
	var authToken = JSON.parse(sessionStorage["auth-token"]);
	var uri = authToken["links"]["user-profile"].uri;
	$.get(uri)
		.done(function(user){
			user.links = linksToMap(user.links);
			complete(user);
		})
		.fail(function(){});
} */

function progressHandlingFunction(e){
    if(e.lengthComputable){
        $('progress').attr({value:e.loaded,max:e.total});
    }
} 

/* BEETER.js */

/* $(function(){
   getCurrentUserProfile(function(user){
      $("#aProfile").text(user.fullname + ' ');
      $("#aProfile").append('<span class="caret"></span>');
   });
}); */

function getUser(){
    var authToken = JSON.parse(sessionStorage["auth-token"]);
    var uri = authToken["links"]["user-profile"].uri;
    var userid = authToken.userid;
    var getuserURI = uri;
    $.ajax({
        url: getuserURI,
        type: 'GET',
        crossDomain: true,
        dataType: "json",
        headers: {"X-Auth-Token" : authToken.token}
    }).done(function(data, status, jqxhr){
        data.links=linksToMap(data.links);
        var filename=data.filename;
        $("#img_src").text('');
        $("#img_src").append('<img src="images/'+filename+'" class="img-rounded img-responsive" />');
    }).fail(function(){
         console.log("ERROR");
    });
}

function getProfile(uri){
    var authToken = JSON.parse(sessionStorage["auth-token"]);
    $.ajax({
        url: uri,
        type: 'GET',
        crossDomain: true,
        dataType: "json",
        headers: {"X-Auth-Token" : authToken.token}
    }).done(function(data, status, jqxhr){
        data.links=linksToMap(data.links);
        sessionStorage["profile"]=JSON.stringify(data);
        window.location.replace("myProfile.html")
    }).fail(function(){
         console.log("ERROR");
    });
}

/* function changePassword(newPass, oldPass){
    var authToken = JSON.parse(sessionStorage["auth-token"]);
    var uri = authToken["links"]["user-profile"].uri;
    var changepassUri = uri+'-password';
    var userid = authToken.userid;
      objeto = {
          "id": userid,
          "oldPassword": oldPass,
          "password" : newPass
      }
      var data = JSON.stringify(objeto);
    $.ajax({
        url: changepassUri,
        type: 'PUT',
        crossDomain: true,
        contentType: "application/vnd.dsa.flatmates.user+json",
        dataType: "json",
        data: data, /*{
            id: userid,
            oldPassword: oldPass,
            password: newPass
        },
        headers: {"X-Auth-Token" : authToken.token
                  //"Content-Type" : application/vnd.dsa.flatmates.user+json
                 }
    }).done(function(data, status, jqxhr){
        data.links=linksToMap(data.links);
        $("#culebrilla").text("");   
        $("#culebrilla").append("<div class='alert alert-block alert-info'><p><span style='color:green'>Password changed</span></p></div>");
        $("#InputOldPass").val("");
        $("#InputNewPass").val("");
        $("#InputNewPass2").val("");
    }).fail(function(){
        $("#culebrilla").text("");   
         $("#culebrilla").append("<div class='alert alert-block alert-info'><p><span style='color:red'>Your actual password is not this</span></p></div>");
    });
}*/


function listItemHTML(uri, subject, creator, edit){
  var a = '<a class="list-group-item" href="'+ uri +'">';
  var p = '<p class="list-group-item-text unclickable">' + subject + '</p>';
  var h = (edit) ? '<h6 class="list-group-item-heading unclickable" align="right">'+creator+' <span class="glyphicon glyphicon-pencil clickable"></span></h6>' : '<h6 class="list-group-item-heading unclickable" align="right">'+creator+'</h6>';;
  return a + p +  h + '</a>';
}