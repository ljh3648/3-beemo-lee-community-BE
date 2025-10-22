/**
 * 프로젝트 경로 상수
 * 경로 변경 시 이 파일만 수정하면 됨
 */

// CSS 경로
export const CSS = {
    GLOBAL: '/assets/css/global.css',
    SIGN: '/components/styles/sign.css',
    BUTTON: '/components/button/button.css',
    HEADER_OLD: '/components/header/header.css',
};

// JS 경로
export const JS = {
    HEADER: '/components/header/header.js',
    HEADER_AUTH: '/components/header/header-auth.js',
    BUTTON: '/components/button/button.js'
};

// HTML 경로 (컴포넌트)
export const HTML = {
    HEADER: '/components/header/header.html',
    HEADER_AUTH: '/components/header/header-auth.html'
};

// API 경로
export const API = {
    BASE: '/api',
    USERS: '/api/users',
    USERS_ME: '/api/users/me',
    AUTH_SESSIONS: '/api/auth/sessions',
    POSTS: '/api/posts'
};

// 페이지 경로
export const PAGE = {
    HOME: '/home',
    SIGNIN: '/signin',
    SIGNUP: '/signup',
    PROFILE_EDIT: '/user/profile/edit',
    POST_CREATE: '/posts/create',
    POST_EDIT: '/posts/edit',
    POST_DETAIL: (id) => `/posts/${id}`
};

/**
 * CSS 파일 동적 로드 헬퍼
 * @param {string} href - CSS 파일 경로
 * @returns {Promise<void>}
 */
export const loadCSS = (href) => {
    // 이미 로드됐는지 확인
    if (document.querySelector(`link[href="${href}"]`)) {
        return Promise.resolve();
    }
    
    return new Promise((resolve, reject) => {
        const link = document.createElement('link');
        link.rel = 'stylesheet';
        link.href = href;
        link.onload = () => resolve();
        link.onerror = () => reject(new Error(`Failed to load CSS: ${href}`));
        document.head.appendChild(link);
    });
};

/**
 * 여러 CSS 파일 한번에 로드
 * @param {string[]} hrefs - CSS 파일 경로 배열
 * @returns {Promise<void[]>}
 */
export const loadMultipleCSS = (hrefs) => {
    return Promise.all(hrefs.map(href => loadCSS(href)));
};
