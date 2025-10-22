/**
 * DOM 조작 유틸리티 함수 모음
 */

/**
 * 프로필 이미지 업로드 미리보기 설정
 * @param {string} inputId - file input 요소의 ID
 * @param {string} previewSelector - 미리보기를 표시할 요소의 선택자 (예: '.profile-circle')
 */

export const setupProfileImagePreview = (inputId, previewSelector) => {
    const input = document.getElementById(inputId);

    if (!input) {
        console.error(`Input element with id '${inputId}' not found`);
        return;
    }

    input.addEventListener('change', function(e) {
        const file = e.target.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = function(event) {
                const profileCircle = document.querySelector(previewSelector);

                if (!profileCircle) {
                    console.error(`Preview element '${previewSelector}' not found`);
                    return;
                }

                profileCircle.style.backgroundImage = `url(${event.target.result})`;
                profileCircle.style.backgroundSize = 'cover';
                profileCircle.style.backgroundPosition = 'center';

                const placeholder = profileCircle.querySelector('.profile-placeholder');
                if (placeholder) {
                    placeholder.style.display = 'none';
                }
            };
            reader.readAsDataURL(file);
        }
    });
};
