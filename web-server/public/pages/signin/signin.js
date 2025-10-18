document.getElementById('form').addEventListener('submit', async function (e) {
    e.preventDefault();

    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;

    try {
        const response = await fetch('http://localhost:8080/auth/sessions', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            credentials: 'include',
            body: JSON.stringify({
                email: email,
                password: password
            })
        });

        if (response.status === 201) {
            window.location.href = '/';
        } else {
            alert("로그인 실패");
        }

    } catch (error) {
        alert('서버 연결 실패: ' + error.message);
    }
});