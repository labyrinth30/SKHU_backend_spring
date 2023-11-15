function init() {
    for (let tr of document.querySelectorAll("tr[href]"))
        tr.addEventListener("click", function (event) {
            let url = event.currentTarget.getAttribute("href");
            location.href = url;    
        });
        
    for (let button of document.querySelectorAll("[data-confirm]"))
        button.addEventListener("click", function (event) {
            let message = event.target.getAttribute("data-confirm");
            if (confirm(message) == false)
                event.preventDefault();
        });
}

window.addEventListener('DOMContentLoaded', init, false);

