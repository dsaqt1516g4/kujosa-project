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
      $("#aProfile").text(user.fullname + ' ');
      $("#aProfile").append('<span class="caret"></span>');
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
