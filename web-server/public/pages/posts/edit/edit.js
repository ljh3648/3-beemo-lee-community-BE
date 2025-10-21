// URL에서 게시글 ID 추출
const postId = window.location.pathname.split('/posts/')[1].split('/')[0];

// 기존 게시글 데이터 로드
const fetchPostData = async () => {
    try {
        const response = await fetch(`/api/posts/${postId}`, {
            method: 'GET',
            credentials: 'include'
        });

        if (response.status === 401) {
            alert('로그인이 필요합니다.');
            window.location.href = '/signin';
            return;
        }

        if (response.status === 200) {
            const post = await response.json();

            // 기존 데이터로 폼 채우기
            document.getElementById('title').value = post.title;
            document.getElementById('content').value = post.body;

            if (post.imageUrl) {
                document.getElementById('upload-text').textContent = post.imageUrl;
            }
        } else {
            alert('게시글을 불러올 수 없습니다.');
            // window.location.href = '/home';
        }
    } catch (error) {
        console.error('게시글 로딩 실패:', error);
    }
};

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

// 게시글 수정 폼 제출
document.getElementById('post-form').addEventListener('submit', async function(e) {
    e.preventDefault();

    const title = document.getElementById('title').value;
    const content = document.getElementById('content').value;
    const imageFile = document.getElementById('image-upload').files[0];

    // TODO: 이미지 업로드 처리 필요 (현재는 imageUrl만 전송)

    try {
        const response = await fetch(`/api/posts/${postId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            credentials: 'include',
            body: JSON.stringify({
                title: title,
                body: content,
                imageUrl: imageFile ? imageFile.name : document.getElementById('upload-text').textContent
            })
        });

        if (response.status === 200) {
            alert('게시글 수정 성공!');
            window.location.href = `/posts/${postId}`;
        } else if (response.status === 401) {
            alert('로그인이 필요합니다.');
            window.location.href = '/signin';
        } else {
            const error = await response.json();
            alert('게시글 수정 실패: ' + error.message);
        }
    } catch (error) {
        alert('서버 연결 실패: ' + error.message);
    }
});

// 페이지 로드시 기존 게시글 데이터 불러오기
document.addEventListener('DOMContentLoaded', () => {
    fetchPostData();
});
