$( "#form-signin" ).submit(function( event ) {
  event.preventDefault();
  console.log('Inici de sessió');
  login($("#inputLoginid").val(), $("#inputPassword").val(), function(){
  	console.log("change");
  	window.location.replace('kujosa.html');
  });
});

$("#register_btn").click(function(event){
    event.preventDefault();
    //window.location.replace('register.html');
})

$("#imageForm").submit(function(e){
    var login= $("#usernamesignup").val();
    var password=$("#passwordsignup").val();
    var fullname=$("#fullnamesignup").val();
    var email=$("#emailsignup").val();
    e.preventDefault();
    $('progress').toggle();
    //var formData = new FormData($('imageForm')[0]);
    var formData = new FormData();
    formData.append('login', login);
    formData.append('password', password);
    formData.append('email', email);
    formData.append('fullname', fullname);
    formData.append('image', $('#inputFile')[0].files[0]);    
    console.log(formData);
    registrarUsuario(formData);
    console.log('Usuari registrat');
});
