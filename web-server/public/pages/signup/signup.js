// 프로필 사진 미리보기
document.getElementById('profile-upload').addEventListener('change', function(e) {
    const file = e.target.files[0];
    if (file) {
        const reader = new FileReader();
        reader.onload = function(event) {
            const profileCircle = document.querySelector('.profile-circle');
            profileCircle.style.backgroundImage = `url(${event.target.result})`;
            profileCircle.style.backgroundSize = 'cover';
            profileCircle.style.backgroundPosition = 'center';
            profileCircle.querySelector('.profile-placeholder').style.display = 'none';
        };
        reader.readAsDataURL(file);
    }
});

document.getElementById('form').addEventListener('submit', async function (e) {
    e.preventDefault();

    const nickname = document.getElementById('nickname').value;
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;
    const passwordRe = document.getElementById('password-re').value;
    
    // TODO : 패스워드 재확인 검증 기능 추가 필요.
    // 패스워드 재 확인 필요. - 클라이언트에서도 하고 서버에서도 하고
    // 클라이언트는 기본적으로 믿지 않는다! 클라이언트에서 확인하고, 서버에서도 확인한다.

    // TODO: 사용자 프로필도 같이 전송하는거 추가해야 함.

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