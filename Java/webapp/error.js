$(document).on("pfAjaxError", function (event, xhr, options) {
    if (xhr.status == 403) {
        document.location = "../?session-expired";
    } else if (xhr.status == 404) {
        document.location = "../";
    } else {
        alert(xhr.responseText);
    }
});