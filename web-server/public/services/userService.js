/**
 * User Service
 * 사용자 관련 API 호출을 담당하는 서비스 계층
 *
 * API 엔드포인트:
 * - POST   /api/users        회원가입
 * - PATCH  /api/users/:id    회원 정보 수정 (백엔드 아직 미구현)
 * - DELETE /api/users/:id    회원 탈퇴 (백엔드 아직 미구현)
 */

import { API } from '/constants/paths.js';

export const userService = {
    /**
     * 회원가입
     * @param {Object} userData - 회원가입 데이터
     * @param {string} userData.nickname - 닉네임 (최대 10자)
     * @param {string} userData.email - 이메일
     * @param {string} userData.password - 비밀번호
     * @param {string} [userData.profileUrl] - 프로필 이미지 URL (선택)
     * @returns {Promise<Response>}
     */
    async signup(userData) {
        const response = await fetch(API.USERS, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            credentials: 'include',
            body: JSON.stringify(userData)
        });
        return response;
    }
};
