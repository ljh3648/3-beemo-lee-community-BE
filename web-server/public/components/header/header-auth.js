const fetchHeaderAuth = async () => {
    // CSS 로드
    const link = document.createElement('link');
    link.rel = 'stylesheet';
    link.href = '/components/header/header-auth.css';
    document.head.appendChild(link);

    // HTML 로드
    const res = await fetch('/components/header/header-auth.html');
    const header = await res.text();
    return header;
}

// 드롭다운 토글
const toggleDropdown = () => {
    const dropdown = document.getElementById('dropdown-menu');
    dropdown.classList.toggle('show');
};

// 드롭다운 외부 클릭시 닫기
const closeDropdown = (e) => {
    const profileSection = document.querySelector('.profile-section');
    const dropdown = document.getElementById('dropdown-menu');

    if (profileSection && !profileSection.contains(e.target)) {
        dropdown.classList.remove('show');
    }
};

// 로그아웃
const logout = async () => {
    try {
        const response = await fetch('/api/auth/sessions', {
            method: 'DELETE',
            credentials: 'include'
        });

        if (response.status === 204) {
            alert('로그아웃 되었습니다.');
            window.location.href = '/signin';
        } else {
            alert('로그아웃 실패');
        }
    } catch (error) {
        console.error('로그아웃 실패:', error);
        alert('로그아웃 실패');
    }
};

document.addEventListener('DOMContentLoaded', async () => {
    const header = await fetchHeaderAuth();
    document.querySelector('header').innerHTML = header;

    // 프로필 클릭 이벤트
    const profileImage = document.getElementById('header-profile');
    if (profileImage) {
        profileImage.addEventListener('click', toggleDropdown);
    }

    // 외부 클릭시 드롭다운 닫기
    document.addEventListener('click', closeDropdown);

    // 로그아웃 버튼 이벤트
    const logoutBtn = document.getElementById('logout-btn');
    if (logoutBtn) {
        logoutBtn.addEventListener('click', (e) => {
            e.preventDefault();
            logout();
        });
    }

    // TODO: 사용자 정보 가져와서 프로필 이미지 설정
    // const userData = await fetchUserProfile();
    // if (userData.profileUrl) {
    //     document.getElementById('header-profile').style.backgroundImage = `url(${userData.profileUrl})`;
    // }
});
