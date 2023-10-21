function onClickHref(event) {
 let url = event.currentTarget.getAttribute("href");
 location.href = url;
}