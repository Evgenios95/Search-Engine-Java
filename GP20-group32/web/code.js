/* jshint esversion: 6 */
var input = document.getElementById("searchbox");
var button = document.getElementById("searchbutton");
var responsesize = document.getElementById("responsesize");
var urllist = document.getElementById("urllist");


button.onclick = () => {
    fetch("/search?q=" + input.value)
        .then((response) => response.json())
        .then((data) => {
            let message;
            if (data.length === 0) {
                message = "<p>No web page contains the query word.</p>";
            } else {
                message = "<p>" + data.length + " websites retrieved</p>";
            }
            responsesize.innerHTML = message;
            let results = data.map((page) =>
                `<li><a href="${page.url}">${page.title}</a></li>`)
                .join("\n");
            urllist.innerHTML = `<ul>${results}</ul>`;});
        };




