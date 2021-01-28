function getUser() {
  // Read the user information (session id) from the url.
  var current_url_string = window.location.href;
  var current_url = new URL(current_url_string);
  var user = current_url.searchParams.get("user");
  return user;
}

function createInfoForm(user, bookId) {
  // Create info form object to send user and book info to server.
  var infoForm = {};
  // Id was passed to function as parameter.
  infoForm.bookId = bookId;
  // User info (session id) was read from url.
  infoForm.user = user;
  // Turn in JSON OBject
  json = JSON.stringify(infoForm);
  //console.log(json)
  return json;
}

function deleteBook(id) {
  var user = getUser();
  var contentType = 'application/json; charset=utf-8';
  var json = createInfoForm(user, id);
  var url = "http://127.0.0.1:8080/delete";
  var xmlhttp = new XMLHttpRequest();

  // send delete request
  xmlhttp.open("DELETE", url, true);
  xmlhttp.setRequestHeader('Content-type', contentType);
  xmlhttp.send(json);

  setTimeout(function() {
    // relaod page affter 50 ms.
    location.reload()
  }, 50);

}


function add(id) {
	var user = getUser();
	var json = createInfoForm(user, id);
	var contentType = 'application/json; charset=utf-8';
	// URL to send request to.
	var url = "http://127.0.0.1:8080/search/results";
	var xmlhttp = new XMLHttpRequest();

	// Adding a book is a POST request
	xmlhttp.open("POST", url, true);
	xmlhttp.setRequestHeader('Content-type', contentType);

	// Send request.
	xmlhttp.send(json);

	/*
	TODO: Refactor to properly use AJAX. 
	*/

}
