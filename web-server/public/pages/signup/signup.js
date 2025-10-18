document.getElementById('form').addEventListener('submit', async function (e) {
    e.preventDefault();

    const nickname = document.getElementById('nickname').value;
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;

    try {
        const response = await fetch('/api/users', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                nickname: nickname,
                email: email,
                password: password,
                profileUrl: ''
            })
        });

        if (response.status === 201) {
            alert('회원가입 성공!');
            window.location.href = '/signin';
        } else {
            const error = await response.json();
            alert('회원가입 실패: ' + error.message);
        }
    } catch (error) {
        alert('서버 연결 실패: ' + error.message);
    }
});