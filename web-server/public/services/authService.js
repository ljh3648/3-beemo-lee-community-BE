/**
 * Auth Service
 * 인증 관련 API 호출을 담당하는 서비스 계층
 *
 * API 엔드포인트:
 * - GET    /api/auth/sessions 세션 유효성 판단.
 * - POST   /api/auth/sessions 세션 등록 (로그인)
 * - DELETE /api/auth/sessions 세션 파괴 (로그아웃)
 */
import { API } from '/constants/paths.js';

export const authService = {

    // 세션 인증 API 연결
    async verificationSession() {
        const response = await fetch(API.AUTH_SESSIONS, {
            method: 'GET',
            credentials: 'include'
        })

        return response;
    }


};
