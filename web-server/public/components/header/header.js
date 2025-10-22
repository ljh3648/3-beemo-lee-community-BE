/**
 * Header Component
 * 인증 상태에 따라 다른 헤더를 렌더링하는 통합 헤더 컴포넌트
 */

import { CSS, loadCSS, API, PAGE } from '/constants/paths.js';

class Header {
    constructor() {
        this.isAuthenticated = false;
        this.user = null;
    }

    /**
     * 인증 상태 확인
     */
    async checkAuth() {
        try {
            const response = await fetch(API.AUTH_SESSIONS, {
                method: 'GET',
                credentials: 'include'
            });

            if (response.status === 200) {
                this.isAuthenticated = true;
                // 사용자 정보 가져오기 (선택)
                // this.user = await this.fetchUserProfile();
            }
        } catch (error) {
            console.error('인증 확인 실패:', error);
            this.isAuthenticated = false;
        }
    }

    /**
     * 사용자 프로필 정보 가져오기
     */
    async fetchUserProfile() {
        try {
            const response = await fetch(API.USERS_ME, {
                method: 'GET',
                credentials: 'include'
            });

            if (response.status === 200) {
                return await response.json();
            }
        } catch (error) {
            console.error('프로필 정보 로딩 실패:', error);
        }
        return null;
    }

    /**
     * 헤더 HTML 렌더링
     */
    renderHTML() {
        return `
            <div class="header__container">
                <h1 class="header__title" onclick="window.location.href='/home'">아무말 대잔치</h1>
                ${this.isAuthenticated ? this.renderAuthMenu() : this.renderGuestMenu()}
            </div>
        `;
    }

    /**
     * 비인증 유저 메뉴 렌더링
     */
    renderGuestMenu() {
        return `
            <nav class="header__nav">
                <a href="${PAGE.SIGNIN}" class="header__link">로그인</a>
                <a href="${PAGE.SIGNUP}" class="header__link header__link--primary">회원가입</a>
            </nav>
        `;
    }

    /**
     * 인증된 유저 메뉴 렌더링
     */
    renderAuthMenu() {
        return `
            <div class="header__profile">
                <div class="header__profile-image" id="header-profile"></div>
                <div class="header__dropdown" id="header-dropdown">
                    <a href="${PAGE.PROFILE_EDIT}" class="header__dropdown-item">회원정보수정</a>
                    <a href="/user/password/edit" class="header__dropdown-item">비밀번호수정</a>
                    <a href="#" class="header__dropdown-item" id="header-logout-btn">로그아웃</a>
                </div>
            </div>
        `;
    }

    /**
     * 드롭다운 토글
     */
    toggleDropdown() {
        const dropdown = document.getElementById('header-dropdown');
        if (dropdown) {
            dropdown.classList.toggle('header__dropdown--show');
        }
    }

    /**
     * 드롭다운 외부 클릭 시 닫기
     */
    handleOutsideClick(e) {
        const profile = document.querySelector('.header__profile');
        const dropdown = document.getElementById('header-dropdown');

        if (profile && dropdown && !profile.contains(e.target)) {
            dropdown.classList.remove('header__dropdown--show');
        }
    }

    /**
     * 로그아웃 처리
     */
    async logout() {
        try {
            const response = await fetch(API.AUTH_SESSIONS, {
                method: 'DELETE',
                credentials: 'include'
            });

            if (response.status === 204) {
                alert('로그아웃 되었습니다.');
                window.location.href = PAGE.SIGNIN;
            } else {
                alert('로그아웃 실패');
            }
        } catch (error) {
            console.error('로그아웃 실패:', error);
            alert('로그아웃 실패');
        }
    }

    /**
     * 이벤트 리스너 등록
     */
    attachEventListeners() {
        if (!this.isAuthenticated) return;

        // 프로필 이미지 클릭
        const profileImage = document.getElementById('header-profile');
        if (profileImage) {
            profileImage.addEventListener('click', () => this.toggleDropdown());
        }

        // 외부 클릭
        document.addEventListener('click', (e) => this.handleOutsideClick(e));

        // 로그아웃 버튼
        const logoutBtn = document.getElementById('header-logout-btn');
        if (logoutBtn) {
            logoutBtn.addEventListener('click', (e) => {
                e.preventDefault();
                this.logout();
            });
        }

        // TODO: 프로필 이미지 설정
        if (this.user && this.user.profileUrl) {
            profileImage.style.backgroundImage = `url(${this.user.profileUrl})`;
        }
    }

    /**
     * 헤더 초기화 및 렌더링
     */
    async init() {
        // CSS 로드
        await loadCSS(CSS.HEADER);

        // 인증 확인
        await this.checkAuth();

        // HTML 렌더링
        const headerElement = document.querySelector('header');
        if (headerElement) {
            headerElement.innerHTML = this.renderHTML();
        }

        // 이벤트 리스너 등록
        this.attachEventListeners();
    }
}

// 자동 초기화
document.addEventListener('DOMContentLoaded', async () => {
    const header = new Header();
    await header.init();
});
