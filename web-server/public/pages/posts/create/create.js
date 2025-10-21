// 이미지 파일 선택 시 파일명 표시
document.getElementById('image-upload').addEventListener('change', function(e) {
    const file = e.target.files[0];
    const uploadText = document.getElementById('upload-text');

    if (file) {
        uploadText.textContent = file.name;
    } else {
        uploadText.textContent = '파일을 선택해주세요.';
    }
});

// 게시글 작성 폼 제출
document.getElementById('post-form').addEventListener('submit', async function(e) {
    e.preventDefault();

    const title = document.getElementById('title').value;
    const content = document.getElementById('content').value;
    const imageFile = document.getElementById('image-upload').files[0];

    // TODO: 이미지 업로드 처리 필요 (현재는 imageUrl만 전송)

    try {
        const response = await fetch('/api/posts', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            credentials: 'include',
            body: JSON.stringify({
                title: title,
                body: content,
                imageUrl: imageFile ? imageFile.name : ''
            })
        });

        if (response.status === 201) {
            alert('게시글 작성 성공!');
            window.location.href = '/home';
        } else if (response.status === 401) {
            alert('로그인이 필요합니다.');
            window.location.href = '/signin';
        } else {
            const error = await response.json();
            alert('게시글 작성 실패: ' + error.message);
        }
    } catch (error) {
        alert('서버 연결 실패: ' + error.message);
    }
});
