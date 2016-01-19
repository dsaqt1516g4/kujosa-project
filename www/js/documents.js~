var BASE_URL = "http://10.83.63.80:8080/kujosa";


function loadDocuments(uri, complete){
	// var authToken = JSON.parse(sessionStorage["auth-token"]);
	// var uri = authToken["links"]["current-documents"].uri;
	$.get(uri)
		.done(function(documents){
			documents.links = linksToMap(documents.links);
			complete(documents);
		})
		.fail(function(){});
}

function getDocument(uri, complete){
	$.get(uri)
		.done(function(document){
			complete(document);
		})
		.fail(function(data){
		});
}

$(function(){
   getCurrentUserProfile(function(user){
      $("#username").text(user.fullname);
      $("#username").append('<span class="caret"></span>');
   });

   var authToken = JSON.parse(sessionStorage["auth-token"]);
   var currentDocumentsUri = authToken["links"]["current-documents"].uri;
   loadDocuments(currentDocumentsUri, function(documents){
      $("#documents-list").empty();
      processDocumentCollection(documents);
   });
});

function previousDocuments(){
  loadDocuments($('#formPrevious').attr('action'), function(documents){
    processDocumentCollection(documents);
  });
}

function processDocumentCollection(documents){
  var lastIndex = documents["documents"].length - 1;
  $.each(documents["documents"], function(i,document){
      document.links=linksToMap(document.links);
      var edit = document.userid ==JSON.parse(sessionStorage["auth-token"]).userid;
      $("#documents-list").append(listItemHTML(document.links["self"].uri, document.subject, document.creator, edit));
      if(i==0)
        $("#buttonUpdate").click(function(){alert("I don't do anything, implement me!")});
      if(i==lastIndex){
        $('#formPrevious').attr('action', document["links"].previous.uri);
      }
  });

   $("#formPrevious").submit(function(e){
      e.preventDefault();
      e.stopImmediatePropagation();
      previousDocuments();
      $("#buttonPrevious").blur();
    });

  $("a.list-group-item").click(function(e){
    e.preventDefault();
    e.stopImmediatePropagation();
    var uri = $(this).attr("href");
    getDocument(uri, function(document){
      // In this example we only log the document
      console.log(document);
    });
  });
  $(".glyphicon-pencil").click(function(e){
    e.preventDefault();
    alert("This should open a document editor. But this is only an example.");});
}


function listItemHTML(uri, subject, creator, edit){
  var a = '<a class="list-group-item" href="'+ uri +'">';
  var p = '<p class="list-group-item-text unclickable">' + subject + '</p>';
  var h = (edit) ? '<h6 class="list-group-item-heading unclickable" align="right">'+creator+' <span class="glyphicon glyphicon-pencil clickable"></span></h6>' : '<h6 class="list-group-item-heading unclickable" align="right">'+creator+'</h6>';;
  return a + p +  h + '</a>';
} 


function post_documents (formdata){
    var authToken = JSON.parse(sessionStorage["auth-token"]);
    var uri=api.documents.uri;
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
}


function delete_document() {
	
        var authToken = JSON.parse(sessionStorage["auth-token"]);
        var uri = authToken["links"]["delete-documents"].uri;
        
        
	$.ajax({
		url : uri,
		type : 'DELETE',
		crossDomain : true,
		dataType : 'json',
		headers: {
        	"X-Auth-Token":authToken.token
                }

	}).done(function(data, status, jqxhr) {
		window.location = "documents.html"

	}).fail(function(jqXHR, textStatus) {
		console.log(textStatus);
	});
}


/* function getDocuments() {

	console.log(id);
        var authToken = JSON.parse(sessionStorage["auth-token"]);
        var uri = authToken["links"]["get-documents"].uri;
	$.ajax(
			{
				url : url,
				type : 'GET',
				crossDomain : true,
                                dataType: "json",
                                headers: {"X-Auth-Token" : authToken.token}
				},
				

			}).done(function(data, status, jqxhr) {
		var document = JSON.parse(jqxhr.responseText);

	}).fail(function() {
		console.log('Error');
	});
} */