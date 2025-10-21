// TOOD: 인증관련 처리 로직 구현해야함.
// 홈에서 로그인이 되어있는지 확인하는 로직 필요한데 쿠키 파싱안되니까 일단 정보 불러오면서 서버에서 인증 오류 보내면 그때 리 라우트 하는걸로

let currentPage = 0;
let isLoading = false;
let hasMore = true;

// 게시글 목록 가져오기
const fetchPosts = async (page) => {
    if (isLoading || !hasMore) return;

    isLoading = true;

    try {
        const response = await fetch(`/api/posts?page=${page}&size=10`, {
            method: 'GET',
            credentials: 'include'
        });

        if (response.status === 401) {
            // 인증 실패시 로그인 페이지로
            alert('로그인이 필요합니다.');
            window.location.href = '/signin';
            return;
        }

        if (response.status === 200) {
            const data = await response.json();

            if (data.posts && data.posts.length > 0) {
                renderPosts(data.posts);
                currentPage++;

                // 더 이상 데이터가 없으면
                if (data.posts.length < 10) {
                    hasMore = false;
                }
            } else {
                hasMore = false;
                // 첫 페이지인데 게시글이 없으면
                if (currentPage === 0) {
                    showNoPosts();
                }
            }
        }
    } catch (error) {
        console.error('게시글 로딩 실패:', error);
    } finally {
        isLoading = false;
    }
};

// 게시글 렌더링
const renderPosts = (posts) => {
    const postList = document.querySelector('.post-list');

    posts.forEach(post => {
        const postCard = document.createElement('div');
        postCard.className = 'post-card';

        const createdAt = new Date(post.createdAt).toLocaleString('ko-KR', {
            year: 'numeric',
            month: '2-digit',
            day: '2-digit',
            hour: '2-digit',
            minute: '2-digit',
            second: '2-digit'
        });

        postCard.innerHTML = `
            <div class="post-header">
                <h3 class="post-title">${post.title}</h3>
                <div class="post-meta">
                    <span class="meta-item">좋아요 ${post.likesCnt || 0}</span>
                    <span class="meta-item">댓글 ${post.commentCnt || 0}</span>
                    <span class="meta-item">조회수 ${post.viewsCnt || 0}</span>
                    <span class="post-date">${createdAt}</span>
                </div>
            </div>
            <div class="post-author">
                <img src="${post.user?.profileUrl || ''}" alt="프로필" class="author-profile">
                <span class="author-name">${post.user?.nickname || '익명'}</span>
            </div>
        `;

        postList.appendChild(postCard);
    });
};

// 게시글 없음 표시
const showNoPosts = () => {
    const postList = document.querySelector('.post-list');
    postList.innerHTML = '<p class="no-posts">게시물이 없습니다.</p>';
};

// 인피니티 스크롤
const handleScroll = () => {
    const { scrollTop, scrollHeight, clientHeight } = document.documentElement;

    // 스크롤이 바닥에서 100px 이내로 왔을 때
    if (scrollTop + clientHeight >= scrollHeight - 100) {
        fetchPosts(currentPage);
    }
};

// 초기 로드
document.addEventListener('DOMContentLoaded', () => {
    // 첫 페이지 로드
    fetchPosts(0);

    // 스크롤 이벤트 등록
    window.addEventListener('scroll', handleScroll);
});