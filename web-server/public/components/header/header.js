const fetchHeader = async () => {
    // CSS 로드
    const link = document.createElement('link');
    link.rel = 'stylesheet';
    link.href = '/components/header/header.css';
    document.head.appendChild(link);

    // HTML 로드
    const res = await fetch('/components/header/header.html');
    const header = await res.text();
    return header;
}

document.addEventListener('DOMContentLoaded', async () => {
    const header = await fetchHeader();
    document.querySelector('header').innerHTML = header;
});