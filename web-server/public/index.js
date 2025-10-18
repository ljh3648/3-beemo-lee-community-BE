const init = async () => {
    try {
        // api서버에 쿠키에 있는 세션값 유효한지 확인 및 저장
        const res = await fetch('http://localhost:8080/auth/sessions', {
            method: 'GET',
            credentials: 'include'
        });

        // 인증된 세션인지 확인
        if (res.status === 200) {
            // HOME으로 가자
            window.location.replace("/home");
        } else {
            // 세션이 유효하지 않으면 로그인 페이지로
            window.location.replace("/signin");
        }
    } catch (error) {
        window.location.replace("/signin");
    }
}

init();