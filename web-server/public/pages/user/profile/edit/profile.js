// 사용자 정보 불러오기
const fetchUserProfile = async () => {
    try {
        const response = await fetch('/api/users/me', {
            method: 'GET',
            credentials: 'include'
        });

        if (response.status === 401) {
            alert('로그인이 필요합니다.');
            window.location.href = '/signin';
            return;
        }

        if (response.status === 200) {
            const user = await response.json();

            // 폼에 기존 데이터 채우기
            document.getElementById('email').textContent = user.email;
            document.getElementById('nickname').value = user.nickname;

            // 프로필 이미지 설정
            if (user.profileUrl) {
                const profileCircle = document.getElementById('profile-preview');
                profileCircle.style.backgroundImage = `url(${user.profileUrl})`;
            }
        }
    } catch (error) {
        console.error('사용자 정보 로딩 실패:', error);
    }
};

// 프로필 사진 변경
document.getElementById('profile-upload').addEventListener('change', function(e) {
    const file = e.target.files[0];
    if (file) {
        const reader = new FileReader();
        reader.onload = function(event) {
            const profileCircle = document.getElementById('profile-preview');
            profileCircle.style.backgroundImage = `url(${event.target.result})`;
        };
        reader.readAsDataURL(file);
    }
});

// 회원정보 수정 폼 제출
document.getElementById('profile-form').addEventListener('submit', async function(e) {
    e.preventDefault();

    const nickname = document.getElementById('nickname').value;
    const profileFile = document.getElementById('profile-upload').files[0];

    // TODO: 프로필 이미지 업로드 처리 필요

    try {
        const response = await fetch('/api/users/me', {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            credentials: 'include',
            body: JSON.stringify({
                nickname: nickname,
                profileUrl: profileFile ? profileFile.name : ''
            })
        });

        if (response.status === 200) {
            alert('수정완료');
            window.location.href = '/home';
        } else if (response.status === 401) {
            alert('로그인이 필요합니다.');
            window.location.href = '/signin';
        } else {
            const error = await response.json();
            alert('회원정보 수정 실패: ' + error.message);
        }
    } catch (error) {
        alert('서버 연결 실패: ' + error.message);
    }
});

// 회원 탈퇴 (텍스트 클릭)
document.getElementById('delete-account-btn').addEventListener('click', async function() {
    if (!confirm('정말로 탈퇴하시겠습니까? 이 작업은 되돌릴 수 없습니다.')) {
        return;
    }

    try {
        const response = await fetch('/api/users/me', {
            method: 'DELETE',
            credentials: 'include'
        });

        if (response.status === 204) {
            alert('회원 탈퇴가 완료되었습니다.');
            window.location.href = '/signin';
        } else if (response.status === 401) {
            alert('로그인이 필요합니다.');
            window.location.href = '/signin';
        } else {
            alert('회원 탈퇴 실패');
        }
    } catch (error) {
        console.error('회원 탈퇴 실패:', error);
        alert('회원 탈퇴 실패');
    }
});

// 페이지 로드시 사용자 정보 불러오기
document.addEventListener('DOMContentLoaded', () => {
    fetchUserProfile();
});
