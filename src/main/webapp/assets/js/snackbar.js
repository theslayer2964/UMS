function successSnackbar(inputString) {
    // Get the snackbar DIV
    var element = document.getElementById("snackbarSuccess");
    document.getElementById("msgSuccessReturn").textContent = inputString;

    // Add the "show" class to DIV
    element.className = "show";

    // After 4 seconds, remove the show class from DIV
    setTimeout(function(){ element.className = element.className.replace("show", ""); }, 4000);
}

function errorSnackbar(inputString) {
    // Get the snackbar DIV
    var element = document.getElementById("snackbarError");
    document.getElementById("msgErrorReturn").textContent = inputString;

    // Add the "show" class to DIV
    element.className = "show";

    // After 4 seconds, remove the show class from DIV
    setTimeout(function(){ element.className = element.className.replace("show", ""); }, 4000);
}