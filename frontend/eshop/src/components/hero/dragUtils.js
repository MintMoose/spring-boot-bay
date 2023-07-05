let dragging = false;

document.addEventListener("mousedown", () => {
  dragging = false;
});

document.addEventListener("mousemove", () => {
  dragging = true;
});

document.addEventListener("mouseup", (event) => {
  if (!dragging) {
    // Handle the click event here
    console.log("Clicked!");
  }
});
