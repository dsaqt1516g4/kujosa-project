$( "#form-signin" ).submit(function( event ) {
  event.preventDefault();
  console.log('Inici de sessió');
  login($("#inputLoginid").val(), $("#inputPassword").val(), function(){
  	console.log("change");
  	window.location.replace('kujosa.html');
  });
});
