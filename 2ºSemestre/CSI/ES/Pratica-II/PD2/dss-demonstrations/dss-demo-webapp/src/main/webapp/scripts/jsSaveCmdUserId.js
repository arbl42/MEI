function onDocumentReady() {
    // Variables corresponding to form inputs
    const $userId = $("#userId");
    const $saveUserId = $("#saveUserId");

    // Variables corresponding to localStorage saved preferences
    const savedUserId = localStorage.getItem("userId");

    // If the user has previously chosen to store his userId
    if(savedUserId) {
        // Restore user id from saved user id
        $userId.val(savedUserId);
        $saveUserId.prop("checked", true);
    } else {
        // If the user hasn't chosen to store his user id, or if he hasn't been on this page before

        // Se o controlador tiver definido o número de telemóvel quer dizer que este utilizador
        // já o editou na área pessoal e deve ser utilizado
        // Ou seja basta definir um valor predefinido caso o número de telemóvel não tenha sido fornecido 
        if($userId.value == ""){
            $userId.val("+351 ");
        }
    }

    // Attach a form submission event handler
    $userId.parents("form").submit(onFormSubmit);
}

function onFormSubmit(e) {
    // Variables corresponding to form inputs
    const $userId = $("#userId");
    const $saveUserId = $("#saveUserId");

    // Variables corresponding to localStorage saved preferences
    const savedUserId = localStorage.getItem("userId");

    // Check if the user wants to save his user id
    if($saveUserId.prop("checked")) {
        // If the user wants to store his current user id, then store it
        localStorage.setItem("userId", $userId.val());
    } else if(savedUserId != null && !$saveUserId.prop("checked")) {
        // If the user previously had a stored user id, but now the checkbox is not selected, then delete
        // the stored user id
        localStorage.removeItem("userId");
    }

    // If the user doesn't want to store his user id and he didn't previously have any stored id, then don't do anything
}

$(document).ready(onDocumentReady);
