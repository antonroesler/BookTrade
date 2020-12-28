function color(e) {
  e.style.backgroundColor = "#FFAC23";
}

function searchById() {
  document.getElementById('search-field').style.backgroundColor = "#FFAC23"
  var searchForm = {};
  var contentType = 'application/json; charset=utf-8';
  searchForm.input = document.getElementById('search-field').value;
  json = JSON.stringify(searchForm);
  console.log(json)
  var url = "http://127.0.0.1:8080/search0/id";
  var xmlhttp = new XMLHttpRequest();
  xmlhttp.open("POST", url, true);
  xmlhttp.setRequestHeader('Content-type', contentType);
  xmlhttp.send(json);

}
