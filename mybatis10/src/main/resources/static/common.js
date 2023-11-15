function createPageTd(no, label, url) {
    let td = document.createElement("td");
    let a = document.createElement("a");
    a.appendChild(document.createTextNode(label));
    a.setAttribute("href", url.replace(/pg=[0-9]+/, "pg=" + no));
    td.appendChild(a);
    return td;
}

function pagination() {
    let div = document.getElementsByClassName("pagination")[0];
    let recordCount = parseInt(div.getAttribute("data-record-count"));
    let pageSize = parseInt(div.getAttribute("data-page-size"));
    let currentPage = parseInt(div.getAttribute("data-current-page"));
    let pageCount = Math.ceil(recordCount / pageSize);
    if (currentPage > pageCount) currentPage = pageCount;
    let url = location.href;
    if (url.indexOf("pg=") < 0)
        url = url + (url.indexOf("?") < 0 ? "?" : "&") + "pg=1";
    let table = document.createElement("table");
    let tr = document.createElement("tr");
    div.appendChild(table);
    table.appendChild(tr);
    let baseNo = Math.floor((currentPage - 1) / 10) * 10;
    if (baseNo > 0) tr.appendChild(createPageTd(baseNo, "<", url));
    for (let i = 1; i <= 10; ++i) {
        let no = baseNo + i;
        if (no > pageCount) break;
        let td = createPageTd(no, String(no), url)
        if (no == currentPage) td.classList.add("active");
        tr.appendChild(td);   
    }
    let no = baseNo + 11;
    if (no <= pageCount) tr.appendChild(createPageTd(no, ">", url));
    div.appendChild(table);
}

function init() {
    if (document.querySelectorAll("div.pagination").length > 0)
        pagination();
    
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

