var API_URL = "http://10.83.63.80:8080/kujosa/";

$("#btnlogout").click(function(e){
    e.preventDefault();
    logout(function(){
        window.location.replace('index.html');
    });
})

$("#register_btn").click(function(e){
    var username= $("#nombre_1").val();
    var password=$("#password_1").val();
    var fullname=$("#nombre_1").val();
    var email=$("#email_1").val();
    e.preventDefault();
    $('progress').toggle();
    var formData = new FormData();
    formData.append('username', username);
    formData.append('password', password);
    formData.append('email', email);
    formData.append('fullname', fullname);
    formData.append('image', $('#inputFile')[0].files[0]);    
    console.log(formData);
    registerUser(formData);
    console.log('Usuari creat');
});

function registerUser(formdata){
    loadAPI(function(){
        var api = JSON.parse(sessionStorage.api);
        var uri=api.user.uri;
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
            var lastfilename = response.filname;
            $('progress').toggle();
            window.location.replace('index.html');
        }).fail(function(jqXHR, textStatus) {
           var error = JSON.parse(jqXHR.responseText);
           console.log(error.reason);
        });
       
    });
}
