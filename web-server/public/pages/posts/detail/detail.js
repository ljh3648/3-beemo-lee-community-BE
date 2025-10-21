// URL에서 게시글 ID 추출
const postId = window.location.pathname.split('/posts/')[1];

// 게시글 데이터 로드
const fetchPostDetail = async () => {
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
            renderPost(post);
        } else {
            alert('게시글을 불러올 수 없습니다.');
            // window.location.href = '/home';
        }
    } catch (error) {
        console.error('게시글 로딩 실패:', error);
    }
};

// 게시글 렌더링
const renderPost = (post) => {
    document.getElementById('post-title').textContent = post.title;
    document.getElementById('author-name').textContent = post.user?.nickname || '익명';

    const createdAt = new Date(post.createdAt).toLocaleString('ko-KR', {
        year: 'numeric',
        month: '2-digit',
        day: '2-digit',
        hour: '2-digit',
        minute: '2-digit',
        second: '2-digit'
    });
    document.getElementById('post-date').textContent = createdAt;

    document.getElementById('post-content').textContent = post.body;

    // 이미지가 있으면 표시
    if (post.imageUrl) {
        const imageContainer = document.getElementById('post-image-container');
        imageContainer.innerHTML = `<img src="${post.imageUrl}" alt="게시글 이미지" class="post-image">`;
    }

    // 통계 업데이트
    document.getElementById('likes-count').textContent = post.likesCnt || 0;
    document.getElementById('views-count').textContent = post.viewsCnt || 0;
    document.getElementById('comments-count').textContent = post.commentCnt || 0;
};

// 댓글 등록
document.getElementById('comment-submit').addEventListener('click', async function() {
    const commentInput = document.getElementById('comment-input');
    const content = commentInput.value.trim();

    if (!content) {
        alert('댓글 내용을 입력해주세요.');
        return;
    }

    try {
        const response = await fetch(`/api/posts/${postId}/comments`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            credentials: 'include',
            body: JSON.stringify({
                content: content
            })
        });

        if (response.status === 201) {
            alert('댓글이 등록되었습니다.');
            commentInput.value = '';
            // TODO: 댓글 목록 새로고침
            fetchComments();
        } else if (response.status === 401) {
            alert('로그인이 필요합니다.');
            window.location.href = '/signin';
        } else {
            alert('댓글 등록에 실패했습니다.');
        }
    } catch (error) {
        console.error('댓글 등록 실패:', error);
    }
});

// 댓글 목록 로드
const fetchComments = async () => {
    try {
        const response = await fetch(`/api/posts/${postId}/comments`, {
            method: 'GET',
            credentials: 'include'
        });

        if (response.status === 200) {
            const comments = await response.json();
            renderComments(comments);
        }
    } catch (error) {
        console.error('댓글 로딩 실패:', error);
    }
};

// 댓글 렌더링
const renderComments = (comments) => {
    const commentsSection = document.getElementById('comments-section');

    if (!comments || comments.length === 0) {
        commentsSection.innerHTML = '<p style="text-align: center; color: #666; padding: 20px;">댓글이 없습니다.</p>';
        return;
    }

    commentsSection.innerHTML = comments.map(comment => {
        const createdAt = new Date(comment.createdAt).toLocaleString('ko-KR', {
            year: 'numeric',
            month: '2-digit',
            day: '2-digit',
            hour: '2-digit',
            minute: '2-digit',
            second: '2-digit'
        });

        return `
            <div class="comment-item">
                <div class="comment-header">
                    <div class="comment-author-profile"></div>
                    <div class="comment-author-info">
                        <span class="comment-author-name">${comment.user?.nickname || '익명'}</span>
                        <span class="comment-date">${createdAt}</span>
                    </div>
                    <div class="comment-actions">
                        <button class="action-btn">수정</button>
                        <button class="action-btn">삭제</button>
                    </div>
                </div>
                <div class="comment-content">${comment.content}</div>
            </div>
        `;
    }).join('');
};

// 페이지 로드시 게시글과 댓글 불러오기
document.addEventListener('DOMContentLoaded', () => {
    fetchPostDetail();
    fetchComments();
});
