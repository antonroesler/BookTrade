function getSchema(i) {
  // Choose the color scheme.
  if (i == 0) {
    // Scheme 0
    return ["#F55D3E", "#FFF", "#F7CB15", "#76BED0"];
  }
  // Standart scheme.
  return ["#2D3047", "#FFD100", "#333533", "#D6D6D6"];
}


function changeColor() {
  // Store the button in a variable.
  var button = document.getElementById("color-btn");
  var buttonState = button.innerText;

  // Choose color scheme depending on the button's text.
  if (buttonState == "Neue Farben") {
    schema = getSchema(0);
    button.innerText = "Zurück"; // Change button's text.
  } else {
    schema = getSchema(1);
    button.innerText = "Neue Farben";
  }

  for (i = 0; i < 50; i++) { // 50 = max number of elements on page.
    // Iterate through every book card on the page.
    card = document.getElementsByClassName("book-card")[i];
    card.style.color = "#202020"; // Change text to dark gray.
    if (i % 4 == 0) { // Every 4th card will be colored the same color.
      card.style.backgroundColor = schema[0];
      if (buttonState == "Zurück") {
        // If the color scheme is default the 0th's element text is  white.
        card.style.color = "#FFF";
      }
    } else if (i % 4 == 1) {
      card.style.backgroundColor = schema[1];
    } else if (i % 4 == 2) {
      card.style.backgroundColor = schema[2];
      if (buttonState == "Zurück") {
        // If the color scheme is default the 2nd's element text is  white.
        card.style.color = "#FFF";
      }
    } else {
      card.style.backgroundColor = schema[3];
    }
  }
}
