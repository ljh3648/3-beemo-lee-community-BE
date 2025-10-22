# Header Component - BEM 방식 통합 헤더

## 📋 개요

인증 상태에 따라 자동으로 다른 UI를 렌더링하는 통합 헤더 컴포넌트입니다.

**주요 특징**:
- ✅ BEM 방식으로 CSS 이름 충돌 방지
- ✅ 단일 파일로 관리 (header-unified.js, header-unified.css)
- ✅ 인증 상태 자동 감지
- ✅ 경로 상수 사용

---

## 🎯 BEM 구조

### Block
- `.header`

### Elements (구조적 부분)
- `.header__container` - 전체 컨테이너
- `.header__title` - 제목 ("아무말 대잔치")
- `.header__nav` - 비인증 유저 네비게이션
- `.header__link` - 로그인/회원가입 링크
- `.header__profile` - 프로필 섹션 (인증 유저)
- `.header__profile-image` - 프로필 이미지
- `.header__dropdown` - 드롭다운 메뉴
- `.header__dropdown-item` - 드롭다운 메뉴 아이템

### Modifiers (상태/변형)
- `.header__link--primary` - Primary 스타일 링크
- `.header__dropdown--show` - 드롭다운 보이기

---

## 🚀 사용 방법

### HTML
```html
<!DOCTYPE html>
<html>
<head>
    <title>페이지 제목</title>
</head>
<body>
    <!-- 헤더 컨테이너 -->
    <header></header>

    <!-- 통합 헤더 스크립트 로드 -->
    <script type="module" src="/components/header/header-unified.js"></script>

    <!-- 페이지 컨텐츠 -->
    <div>...</div>
</body>
</html>
```

**주의**: `type="module"` 필수!

---

## 📊 렌더링 결과

### 비인증 유저 (로그인 전)
```html
<div class="header__container">
    <h1 class="header__title">아무말 대잔치</h1>
    <nav class="header__nav">
        <a href="/signin" class="header__link">로그인</a>
        <a href="/signup" class="header__link header__link--primary">회원가입</a>
    </nav>
</div>
```

### 인증된 유저 (로그인 후)
```html
<div class="header__container">
    <h1 class="header__title">아무말 대잔치</h1>
    <div class="header__profile">
        <div class="header__profile-image" id="header-profile"></div>
        <div class="header__dropdown" id="header-dropdown">
            <a href="/user/profile/edit" class="header__dropdown-item">회원정보수정</a>
            <a href="/user/password/edit" class="header__dropdown-item">비밀번호수정</a>
            <a href="#" class="header__dropdown-item" id="header-logout-btn">로그아웃</a>
        </div>
    </div>
</div>
```

---

## 🔧 작동 원리

1. **CSS 로드**: `constants/paths.js`에서 CSS 경로 가져와서 동적 로드
2. **인증 확인**: `/api/auth/sessions` GET 요청으로 세션 체크
3. **조건부 렌더링**:
   - 인증됨 → `renderAuthMenu()` 호출
   - 비인증 → `renderGuestMenu()` 호출
4. **이벤트 등록**: 프로필 클릭, 드롭다운, 로그아웃 이벤트 리스너 등록

---

## 💡 기존 헤더와 비교

### Before (2개 파일)
```
/components/header/
  header.html           ← 비인증용
  header.css
  header.js
  header-auth.html      ← 인증용
  header-auth.css
  header-auth.js
```

**문제점**:
- 중복 코드 (컨테이너, 제목 스타일 등)
- CSS 이름 충돌 가능성
- 수정 시 2곳 모두 변경 필요

### After (1개 파일)
```
/components/header/
  header-unified.css    ← BEM 적용
  header-unified.js     ← 통합 로직
```

**개선점**:
- ✅ 코드 중복 제거
- ✅ BEM으로 CSS 충돌 방지
- ✅ 한 곳에서 관리

---

## 🎨 CSS 커스터마이징

### 색상 변경
```css
.header__title {
    color: #0348C9;  /* ← 원하는 색상으로 변경 */
}

.header__link--primary {
    background-color: #0348C9;  /* ← Primary 버튼 색상 */
}
```

### 크기 조정
```css
.header__profile-image {
    width: 48px;   /* ← 프로필 이미지 크기 */
    height: 48px;
}
```

---

## 📝 마이그레이션 가이드

### 기존 페이지 업데이트

**Before**:
```html
<header></header>
<script src="/components/header/header.js"></script>
<!-- 또는 -->
<script src="/components/header/header-auth.js"></script>
```

**After**:
```html
<header></header>
<script type="module" src="/components/header/header-unified.js"></script>
```

**주의**: `type="module"` 꼭 추가!

---

## 🐛 트러블슈팅

### Q: 헤더가 안 보여요!
A: 브라우저 콘솔 확인. `type="module"` 누락 확인.

### Q: CSS가 안 먹혀요!
A: `/constants/paths.js`의 `CSS.HEADER` 경로 확인.

### Q: 로그아웃이 안 돼요!
A: API 서버 `/api/auth/sessions` DELETE 엔드포인트 확인.

### Q: 프로필 이미지가 안 보여요!
A: `header-unified.js` 72번 라인 TODO 부분 구현 필요.

---

## 🔄 향후 개선 사항

- [ ] 프로필 이미지 자동 로드
- [ ] 알림 기능 추가
- [ ] 다크모드 지원
- [ ] 모바일 반응형
