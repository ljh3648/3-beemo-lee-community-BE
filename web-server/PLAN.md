> 10월 21일
전체적으로 코드 아키텍처 디자인 패턴을 효율적으로 적용하고자 함.
html을 컴포넌트 하는 과정이 지금 애매한 부분이 있는거 같음.

이것을 먼저 피그마로 디자인을 먼저 실시하고 공통된 디자인 요소를 선별하는건 어떠할까?

그리고 css가 이름이 겹치면 오버라이드 되는것때문에 뭔가 자유롭게 css를 적용하지 못하는 문제가 있는데
뭔가 독립적으로 필요한 해당 부분만 css를 적용하는 방법이 있을까?

지금 공용된 컴포넌트를 적용하기 위해서 html, css, js가 한 세트인데
그냥 컴포넌트는 UI설계의 공통 부분만 전달하는 거라면
html을 보내줘야하나? 그냥 css에서 버튼의 스타일... js로 자동으로 이 컴포넌트가 필요한 html에 주입하는 그런 기능으로 채우면 되지 않을까 싶은데

보고 할 만한 git이 있을까? 
  ⎿  Interrupted · What should Claude do instead?

> 이 고민에 대한 todo 리스트를 추천해줘 

⏺ 좋습니다! 당신의 고민을 분석하고 체계적으로 해결할 수 있는 TODO 리스트를 만들어드리겠습니다.

⏺ 완벽합니다! 이제 각 TODO 항목에 대한 구체적인 가이드를 드리겠습니다.

  ---
  📋 TODO 리스트 상세 가이드

  ✅ TODO 1: Figma로 전체 UI 디자인 스케치 및 공통 요소 선별하기

  목표: 디자인부터 시작해서 컴포넌트 정의

  작업 단계:
  1. 모든 페이지 Figma로 스케치
  2. 반복되는 UI 요소 표시 (버튼, 인풋, 카드 등)
  3. 공통 요소 목록 작성
    - 예: Primary Button, Secondary Button, Input Field, Profile Circle, Post Card 등

  결과물:
  공통 컴포넌트 목록.md
  - Button (Primary, Secondary, Danger)
  - Input (Text, Email, Password)
  - Card (Post Card, User Card)
  - Profile Circle (Small, Medium, Large)

  ---
  ✅ TODO 2: CSS 이름 충돌 해결 방법 조사

  당신의 문제: "CSS가 이름이 겹치면 오버라이드 되는것때문에 자유롭게 css를 적용하지 못함"

  해결 방법 3가지:

  방법 1: BEM (Block Element Modifier) ⭐ 추천

  /* components/button/button.css */
  .btn { }                    /* Block */
  .btn__icon { }              /* Element */
  .btn--primary { }           /* Modifier */
  .btn--secondary { }

  /* 충돌 없음! */
  .card { }
  .card__title { }
  .card__body { }

  장점:
  - Vanilla CSS 그대로 사용
  - 빌드 도구 불필요
  - 명확한 네이밍으로 충돌 방지

  방법 2: CSS Modules (빌드 필요)

  // button.module.css
  .button { color: blue; }

  // button.js
  import styles from './button.module.css';
  element.className = styles.button; // → button_abc123

  장점: 자동으로 고유한 클래스명 생성단점: Webpack/Vite 같은 빌드 도구 필요

  방법 3: Scoped CSS with Shadow DOM

  class Button extends HTMLElement {
      constructor() {
          super();
          const shadow = this.attachShadow({mode: 'open'});
          shadow.innerHTML = `
              <style>
                  /* 이 스타일은 이 컴포넌트 안에서만 유효 */
                  .button { color: blue; }
              </style>
              <button class="button">Click</button>
          `;
      }
  }

  장점: 완전한 스타일 격리단점: Web Components 학습 필요

  → 추천: 지금 프로젝트엔 BEM 방식이 가장 적합!

  ---
  ✅ TODO 3: 컴포넌트 구조 재설계 - HTML vs CSS vs JS 역할 정의

  당신의 질문: "html을 보내줘야하나? 그냥 css와 js로만 해결?"

  두 가지 접근 방식:

  접근 A: HTML + CSS + JS (현재 방식)

  /components/header/
    header.html       ← HTML 구조
    header.css        ← 스타일
    header.js         ← fetch로 HTML 로드

  사용 시:
  const res = await fetch('/components/header/header.html');
  const html = await res.text();
  document.querySelector('header').innerHTML = html;

  장점: 구조가 명확, 수정 쉬움단점: 네트워크 요청 필요

  ---
  접근 B: JS에서 HTML 생성 (추천!)

  /components/button/
    button.css        ← 스타일만
    button.js         ← HTML 생성 + 로직

  // components/button/button.js
  export class Button {
      constructor(text, variant = 'primary') {
          this.text = text;
          this.variant = variant;
      }

      render() {
          const button = document.createElement('button');
          button.className = `btn btn--${this.variant}`;
          button.textContent = this.text;
          return button;
      }
  }

  // 사용
  import { Button } from '/components/button/button.js';
  const btn = new Button('회원가입', 'primary');
  document.body.appendChild(btn.render());

  장점:
  - HTML 파일 불필요
  - 파라미터로 동적 생성 가능
  - 네트워크 요청 없음

  단점:
  - 복잡한 HTML은 코드가 길어짐

  ---
  접근 C: Template Literal (절충안)

  // components/button/button.js
  export const Button = (text, variant = 'primary') => {
      return `
          <button class="btn btn--${variant}">
              ${text}
          </button>
      `;
  };

  // 사용
  import { Button } from '/components/button/button.js';
  document.body.innerHTML += Button('회원가입', 'primary');

  장점:
  - 간단한 문법
  - HTML처럼 보임

  ---
  ✅ TODO 4: 참고할 만한 Vanilla JS 컴포넌트 시스템 GitHub 조사

  추천 저장소:

  1. lit-html (Google)
    - https://github.com/lit/lit
    - Template literal 기반 컴포넌트
  2. VanJS (Vanilla JavaScript Framework)
    - https://github.com/vanjs-org/van
    - 초경량 (1KB) Reactive UI 프레임워크
  3. hyperapp
    - https://github.com/jorgebucaran/hyperapp
    - 1KB Functional UI 라이브러리
  4. petite-vue
    - https://github.com/vuejs/petite-vue
    - Vue의 경량 버전 (6KB)

  ---
  ✅ TODO 5: 프로토타입 컴포넌트 1개 구현 (Button 추천)

  구현 예시:

  // components/button/button.js
  export class Button {
      constructor(options = {}) {
          this.text = options.text || 'Button';
          this.variant = options.variant || 'primary'; // primary, secondary, danger
          this.onClick = options.onClick || (() => {});
          this.disabled = options.disabled || false;
      }

      render() {
          const button = document.createElement('button');
          button.className = `btn btn--${this.variant}`;
          button.textContent = this.text;
          button.disabled = this.disabled;
          button.addEventListener('click', this.onClick);
          return button;
      }
  }

  /* components/button/button.css */
  .btn {
      display: inline-block;
      padding: 10px 20px;
      border-radius: 6px;
      font-size: 16px;
      font-weight: 600;
      border: none;
      cursor: pointer;
      transition: all 0.3s;
  }

  .btn--primary {
      background-color: #0348C9;
      color: white;
  }

  .btn--secondary {
      background-color: #6C63FF;
      color: white;
  }

  .btn--danger {
      background-color: #DC3545;
      color: white;
  }

  .btn:disabled {
      opacity: 0.5;
      cursor: not-allowed;
  }

  사용:
  import { Button } from '/components/button/button.js';

  const submitBtn = new Button({
      text: '회원가입',
      variant: 'primary',
      onClick: () => console.log('clicked!')
  });

  document.body.appendChild(submitBtn.render());

  ---
  ✅ TODO 6: 기존 컴포넌트 새 패턴으로 리팩토링

  대상: header, profile-uploader

  예시 - Header 리팩토링:
  // components/header/header.js
  export class Header {
      constructor(options = {}) {
          this.isAuthenticated = options.isAuthenticated || false;
          this.user = options.user || null;
      }

      render() {
          const header = document.createElement('header');
          header.className = 'header';

          if (this.isAuthenticated) {
              header.innerHTML = `
                  <div class="header__container">
                      <h1 class="header__title">아무말 대잔치</h1>
                      <div class="header__profile">
                          <img src="${this.user.profileUrl}" alt="profile">
                      </div>
                  </div>
              `;
          } else {
              header.innerHTML = `
                  <div class="header__container">
                      <h1 class="header__title">아무말 대잔치</h1>
                      <a href="/signin">로그인</a>
                      <a href="/signup">회원가입</a>
                  </div>
              `;
          }

          return header;
      }
  }

  ---
  🎯 추천 작업 순서

  1. TODO 2 먼저 → BEM 방식 결정하고 문서화
  2. TODO 3 → 컴포넌트 패턴 결정 (접근 B 또는 C 추천)
  3. TODO 5 → Button 프로토타입 구현
  4. TODO 1 → Figma로 공통 요소 정리
  5. TODO 6 → 기존 컴포넌트 리팩토링
  6. TODO 4 → 참고 자료 조사 (병행)

  ---
  어떤 TODO부터 시작하고 싶으세요? 구체적인 구현을 도와드릴 수 있습니다!