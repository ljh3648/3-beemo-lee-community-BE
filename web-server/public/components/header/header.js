const fetchHeader = async () => {
    const res = await fetch('/header/header.html');
    const header = await res.text();
    return header;
}

document.addEventListener('DOMContentLoaded', async () => {
    const header = await fetchHeader();
    document.querySelector('header').innerHTML = header;
});