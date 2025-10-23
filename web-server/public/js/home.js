// DOM 요소
const postsContainer = document.getElementById('postsContainer');
const createPostButton = document.getElementById('createPostButton');
const loading = document.getElementById('loading');
const profileImage = document.getElementById('profileImage');

// 상태
let currentPage = 0;
let isLoading = false;
let hasMore = true;

// 세션 확인 및 초기화
async function checkSession() {
    try {
        const response = await fetch('/api/auth/sessions', {
            method: 'GET',
            credentials: 'include'
        });

        if (response.status !== 200) {
            // 로그인되지 않은 경우 로그인 페이지로 리다이렉트
            window.location.href = '/signin';
            return false;
        }

        return true;
    } catch (error) {
        console.error('세션 확인 오류:', error);
        window.location.href = '/signin';
        return false;
    }
}

// 더미 데이터 생성
function generateDummyPosts(page) {
    const posts = [];
    const startId = page * 10 + 1;

    for (let i = 0; i < 10; i++) {
        const id = startId + i;
        posts.push({
            id: id,
            title: `제목 ${id}`,
            author: `더미 작성자 ${id}`,
            likes: Math.floor(Math.random() * 150000),
            comments: Math.floor(Math.random() * 1000),
            views: Math.floor(Math.random() * 50000),
            createdAt: '2021-01-01 00:00:00'
        });
    }

    return posts;
}

// 숫자 포맷팅 (1k, 10k, 100k)
function formatNumber(num) {
    if (num >= 100000) {
        return Math.floor(num / 1000) + 'k';
    } else if (num >= 10000) {
        return Math.floor(num / 1000) + 'k';
    } else if (num >= 1000) {
        return Math.floor(num / 1000) + 'k';
    }
    return num.toString();
}

// 게시글 카드 생성
function createPostCard(post) {
    const card = document.createElement('div');
    card.className = 'post-card';
    card.onclick = () => {
        window.location.href = `/posts/${post.id}`;
    };

    // 제목이 26자를 초과하면 잘라냄 (글자 수 제한)
    const displayTitle = post.title.length > 26 ? post.title.substring(0, 26) : post.title;

    card.innerHTML = `
        <div class="post-header">
            <h3 class="post-title">${displayTitle}</h3>
            <span class="post-date">${post.createdAt}</span>
        </div>
        <div class="post-stats">
            <span class="stat-item">좋아요 ${formatNumber(post.likes)}</span>
            <span class="stat-item">댓글 ${formatNumber(post.comments)}</span>
            <span class="stat-item">조회수 ${formatNumber(post.views)}</span>
        </div>
        <div class="post-footer">
            <div class="author-avatar"></div>
            <span class="author-name">${post.author}</span>
        </div>
    `;

    return card;
}

// 게시글 로드
async function loadPosts() {
    if (isLoading || !hasMore) return;

    isLoading = true;
    loading.style.display = 'block';

    // 더미 데이터 로드 (API가 없으므로)
    setTimeout(() => {
        const posts = generateDummyPosts(currentPage);

        // 5페이지까지만 로드 (총 50개 게시글)
        if (currentPage >= 4) {
            hasMore = false;
        }

        posts.forEach(post => {
            const card = createPostCard(post);
            postsContainer.appendChild(card);
        });

        currentPage++;
        isLoading = false;
        loading.style.display = 'none';
    }, 500);
}

// 인피니티 스크롤
function handleScroll() {
    const scrollTop = window.pageYOffset || document.documentElement.scrollTop;
    const windowHeight = window.innerHeight;
    const documentHeight = document.documentElement.scrollHeight;

    // 스크롤이 바닥에서 100px 이내에 도달하면 다음 페이지 로드
    if (scrollTop + windowHeight >= documentHeight - 100) {
        loadPosts();
    }
}

// 게시글 작성 버튼
createPostButton.addEventListener('click', () => {
    window.location.href = '/posts/create';
});

// 프로필 드롭다운
const profileDropdown = document.getElementById('profileDropdown');
const logoutButton = document.getElementById('logoutButton');

profileImage.addEventListener('click', (e) => {
    e.stopPropagation();
    profileDropdown.style.display = profileDropdown.style.display === 'none' ? 'block' : 'none';
});

// 드롭다운 외부 클릭 시 닫기
document.addEventListener('click', (e) => {
    if (!profileImage.contains(e.target) && !profileDropdown.contains(e.target)) {
        profileDropdown.style.display = 'none';
    }
});

// 로그아웃
logoutButton.addEventListener('click', async () => {
    try {
        const response = await fetch('/api/auth/sessions', {
            method: 'DELETE',
            credentials: 'include'
        });

        // 성공 여부와 관계없이 로그인 페이지로 이동
        window.location.href = '/signin';
    } catch (error) {
        console.error('로그아웃 오류:', error);
        window.location.href = '/signin';
    }
});

// 초기화
async function init() {
    // 세션 확인
    const isAuthenticated = await checkSession();
    if (!isAuthenticated) return;

    // 프로필 이미지 설정 (기본 회색 원 - CSS로 처리)
    // profileImage.src는 설정하지 않음 (CSS background로 처리)

    // 첫 페이지 로드
    await loadPosts();

    // 스크롤 이벤트 등록
    window.addEventListener('scroll', handleScroll);
}

// 페이지 로드 시 실행
init();
