function deleteBook(id){

  var url_string = window.location.href
  var url = new URL(url_string);
  var user = url.searchParams.get("user");


    var infoForm = {};
    var contentType = 'application/json; charset=utf-8';
    infoForm.bookId = id;
    console.log(id)
    console.log(user)
    infoForm.user = user;
    json = JSON.stringify(infoForm);
    console.log(json)
    var url = "http://127.0.0.1:8080/delete";
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.open("DELETE", url, true);
    xmlhttp.setRequestHeader('Content-type', contentType);
    xmlhttp.send(json);

}
