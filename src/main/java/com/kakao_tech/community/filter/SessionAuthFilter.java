// package com.kakao_tech.community.filter;

// import jakarta.servlet.FilterChain;
// import jakarta.servlet.ServletException;
// import jakarta.servlet.http.Cookie;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;

// import com.kakao_tech.community.exception.RestApiException;
// import com.kakao_tech.community.provider.SessionProvider;
// import lombok.NonNull;
// import lombok.RequiredArgsConstructor;
// import org.springframework.stereotype.Component;
// import org.springframework.web.filter.OncePerRequestFilter;

// import java.io.IOException;
// import java.util.Arrays;
// import java.util.Optional;

// @Component
// @RequiredArgsConstructor
// public class SessionAuthFilter extends OncePerRequestFilter {
//     private final SessionProvider sessionProvider;

//     // 필터 제외 경로 목록
//     private static final String[] EXCLUDED_PATHS = {
//             "/api/users", "/api/signIn"
//     };

//     // 필터 제외 경로 설정
//     @Override
//     protected boolean shouldNotFilter(HttpServletRequest request) {
//         String path = request.getRequestURI();
//         return Arrays.stream(EXCLUDED_PATHS).anyMatch(path::startsWith);
//     }

//     // 실제 필터링 로직
//     @Override
//     protected void doFilterInternal(
//             @NonNull HttpServletRequest request,
//             @NonNull HttpServletResponse response,
//             @NonNull FilterChain chain) throws IOException, ServletException {

//         Optional<String> sid = extractSid(request);

//         // 요청 쿠키에 sid 없는 경우에 접근권한 없음 처리
//         if (sid.isEmpty()) {
//             response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//             return;
//         }

//         try {
//             sessionProvider.validate(sid.get());
//         } catch (RestApiException e) {
//             response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//             return; 
//         }

//         chain.doFilter(request, response);
//     }

//     // 루트에 온 인덱스인지 확인하는 메서드인데 API 서버라서 사용하지 않는다.
//     // private boolean isIndexRequest(HttpServletRequest request) {
//     // String uri = request.getRequestURI();
//     // return "/".equals(uri) || "/index".equals(uri);
//     // }

//     // 세션 추출 (쿠키에서)
//     private Optional<String> extractSid(HttpServletRequest request) {
//         return extractSidFromCookie(request);
//     }

//     // 쿠키에서 토큰 추출
//     private Optional<String> extractSidFromCookie(HttpServletRequest request) {
//         return Optional.ofNullable(request.getCookies())
//                 .stream()
//                 .flatMap(Arrays::stream)
//                 .filter(cookie -> "sid".equals(cookie.getName()))
//                 .map(Cookie::getValue)
//                 .findFirst();
//     }
// }