import { userService } from '/services/userService.js';
import { setupProfileImagePreview } from '/utils/dom.js';
import { CSS, loadMultipleCSS, PAGE, JS } from '/constants/paths.js';

// CSS 로드
// 이렇게 한 이유가 css 위치 한번 바뀌면 모두 바꿔줘야하는 거. ㅐ문에 바꿈
loadMultipleCSS([
    CSS.GLOBAL,
    CSS.SIGN
]);

// 프로필 사진 미리보기 설정
setupProfileImagePreview('profile-upload', '.profile-circle');

document.getElementById('form').addEventListener('submit', async function (e) {
    e.preventDefault();

    const nickname = document.getElementById('nickname').value;
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;
    const passwordRe = document.getElementById('password-re').value;

    // TODO : 패스워드 재확인 검증 기능 추가 필요.
    // 패스워드 재 확인 필요. - 클라이언트에서도 하고 서버에서도 하고
    // 클라이언트는 기본적으로 믿지 않는다! 클라이언트에서 확인하고, 서버에서도 확인한다.

    // TODO : 사용자 프로필도 같이 전송하는거 추가해야 함.

    try {
        // userService ->>>> API 호출 로직을 서비스 계층에 위임
        const response = await userService.signup({
            nickname: nickname,
            email: email,
            password: password,
            profileUrl: ''
        });

        if (response.status === 201) {
            alert('회원가입 성공!');
            window.location.href = PAGE.SIGNIN;
        } else {
            const error = await response.json();
            alert('회원가입 실패: ' + error.message);
        }
    } catch (error) {
        alert('서버 연결 실패: ' + error.message);
    }
});