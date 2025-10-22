# Constants - 프로젝트 경로 상수 관리

## 📁 개요

프로젝트 내 모든 경로(CSS, JS, API, Page 등)를 한 곳에서 관리하는 상수 파일입니다.

**장점**:
- 경로 변경 시 한 파일만 수정
- 오타 방지 (IDE 자동완성)
- 코드 가독성 향상

---

## 📖 사용법

### 1. CSS 경로 사용

```javascript
import { CSS, loadCSS, loadMultipleCSS } from '/constants/paths.js';

// 단일 CSS 로드
loadCSS(CSS.GLOBAL);

// 여러 CSS 한번에 로드
loadMultipleCSS([
    CSS.GLOBAL,
    CSS.SIGN
]);
```

### 2. API 경로 사용

```javascript
import { API } from '/constants/paths.js';

// 회원가입 API 호출
const response = await fetch(API.USERS, {
    method: 'POST',
    body: JSON.stringify(userData)
});

// 프로필 조회
const response = await fetch(API.USERS_ME, {
    method: 'GET',
    credentials: 'include'
});
```

### 3. 페이지 리다이렉트

```javascript
import { PAGE } from '/constants/paths.js';

// 로그인 페이지로 이동
window.location.href = PAGE.SIGNIN;

// 게시물 상세 페이지로 이동 (동적 ID)
window.location.href = PAGE.POST_DETAIL(123);
// → /posts/123
```

---

## 📋 전체 상수 목록

### CSS 경로
- `CSS.GLOBAL` - 전역 CSS
- `CSS.SIGN` - 로그인/회원가입 공통 CSS
- `CSS.BUTTON` - 버튼 컴포넌트 CSS
- `CSS.HEADER` - 헤더 CSS
- `CSS.HEADER_AUTH` - 인증된 헤더 CSS

### API 경로
- `API.BASE` - `/api`
- `API.USERS` - `/api/users`
- `API.USERS_ME` - `/api/users/me`
- `API.AUTH_SESSIONS` - `/api/auth/sessions`
- `API.POSTS` - `/api/posts`

### 페이지 경로
- `PAGE.HOME` - `/home`
- `PAGE.SIGNIN` - `/signin`
- `PAGE.SIGNUP` - `/signup`
- `PAGE.PROFILE_EDIT` - `/user/profile/edit`
- `PAGE.POST_CREATE` - `/posts/create`
- `PAGE.POST_DETAIL(id)` - `/posts/:id`

---

## 🔧 경로 추가 방법

새로운 경로가 필요할 때:

```javascript
// paths.js
export const CSS = {
    GLOBAL: '/assets/css/global.css',
    SIGN: '/components/styles/sign.css',
    NEW_STYLE: '/components/new/new.css'  // ← 추가
};

export const API = {
    // ...기존 코드
    NEW_ENDPOINT: '/api/new'  // ← 추가
};
```

---

## ⚠️ 주의사항

1. **절대경로 사용**: 모든 경로는 `/`로 시작 (상대경로 금지)
2. **import 필수**: 사용하려는 파일에서 반드시 import
3. **HTML에서는 사용 불가**: JavaScript에서만 사용 가능

---

## 💡 실전 예시

### 회원가입 페이지 (pages/signup/signup.js)

```javascript
import { userService } from '/services/userService.js';
import { CSS, loadMultipleCSS, PAGE } from '/constants/paths.js';

// CSS 로드
loadMultipleCSS([CSS.GLOBAL, CSS.SIGN]);

// 회원가입 성공 후 리다이렉트
if (response.status === 201) {
    window.location.href = PAGE.SIGNIN;
}
```

### 서비스 레이어 (services/userService.js)

```javascript
import { API } from '/constants/paths.js';

export const userService = {
    async signup(userData) {
        return await fetch(API.USERS, {
            method: 'POST',
            body: JSON.stringify(userData)
        });
    }
};
```

---

## 🎯 Before vs After

### Before (경로 하드코딩)
```javascript
// signup.js
const response = await fetch('/api/users', { ... });
window.location.href = '/signin';

// profile.js
const response = await fetch('/api/users', { ... });
window.location.href = '/signin';

// 문제: /api/users 경로 변경 시 모든 파일 수정 필요!
```

### After (상수 사용)
```javascript
// signup.js
import { API, PAGE } from '/constants/paths.js';
const response = await fetch(API.USERS, { ... });
window.location.href = PAGE.SIGNIN;

// profile.js
import { API, PAGE } from '/constants/paths.js';
const response = await fetch(API.USERS, { ... });
window.location.href = PAGE.SIGNIN;

// 해결: paths.js만 수정하면 모든 곳에 반영!
```
