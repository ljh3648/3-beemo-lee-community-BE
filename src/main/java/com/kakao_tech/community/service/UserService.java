package com.kakao_tech.community.service;

import com.kakao_tech.community.dto.user.SignUpDTO;
import com.kakao_tech.community.entity.RefreshToken;
import com.kakao_tech.community.entity.User;
import com.kakao_tech.community.exception.AuthErrorCode;
import com.kakao_tech.community.exception.RestApiException;
import com.kakao_tech.community.provider.JwtProvider;
import com.kakao_tech.community.repository.RefreshTokenRepository;
import com.kakao_tech.community.repository.UserRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ImageService imageService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    private static final int ACCESS_TOKEN_EXPIRATION = 15 * 60; // 15분
    private static final int REFRESH_TOKEN_EXPIRATION = 14 * 24 * 3600; // 14일

    @Transactional
    public User getUser(Integer userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Transactional
    public String signInUser(String email, String password, HttpServletResponse response) {
        User user = userRepository.findByEmail(email).orElse(null);

        if (user == null || !checkPassword(user, password)) {
            return null;
        }

        // 기존 리프레시 토큰 무효화
        refreshTokenRepository.deleteByUserId(user.getId());

        // 새로운 토큰 발급 및 저장
        var tokenResponse = generateAndSaveTokens(user);

        // 쿠키 추가
        addTokenCookies(response, tokenResponse);

        return tokenResponse.accessToken();
    }

    @Transactional
    public TokenResponse refreshTokens(String refreshToken, HttpServletResponse response) {
        var parsedRefreshToken = jwtProvider.parse(refreshToken);

        // 여기서 만료처리 확인함
        RefreshToken entity = refreshTokenRepository.findByTokenAndRevokedFalse(refreshToken).orElse(null);

        if (entity == null || entity.getExpiresAt().isBefore(Instant.now())) {
            return null;
        }

        Integer userId = Integer.valueOf(parsedRefreshToken.getBody().getSubject());
        User user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            return null;
        }

        // refresh token은 유지하고 access token만 새로 발급
        String newAccessToken = jwtProvider.createAccessToken(user.getId(), user.getEmail());

        // access token 쿠키만 갱신
        addTokenCookie(response, "accessToken", newAccessToken, ACCESS_TOKEN_EXPIRATION);

        return new TokenResponse(newAccessToken, refreshToken);
    }

    @Transactional
    public void signOutUser(HttpServletResponse response, String refreshToken) {
        // 쿠키 즉시 만료
        addTokenCookie(response, "accessToken", null, 0);
        addTokenCookie(response, "refreshToken", null, 0);

        // 해당 리프레시 토큰 만료 처리
        System.out.println("revoking refresh token: " + refreshToken);
        revokedRefreshToken(refreshToken);
    }

    // 데이터베이스에서 리프레쉬 토큰을 만료처리함.
    public void revokedRefreshToken(String refreshToken) {
        RefreshToken tokenEntity = refreshTokenRepository.findByTokenAndRevokedFalse(refreshToken).orElse(null);
        if (tokenEntity != null) {
            tokenEntity.setRevoked(true);
            refreshTokenRepository.save(tokenEntity);
        }
    }

    /** Access / Refresh 토큰을 새로 발급하고 DB에 저장 */
    private TokenResponse generateAndSaveTokens(User user) {
        String accessToken = jwtProvider.createAccessToken(user.getId(), user.getEmail());
        String refreshToken = jwtProvider.createRefreshToken(user.getId());

        RefreshToken refreshEntity = new RefreshToken();
        refreshEntity.setUserId(user.getId());
        refreshEntity.setToken(refreshToken);
        refreshEntity.setExpiresAt(Instant.now().plusSeconds(REFRESH_TOKEN_EXPIRATION));
        refreshEntity.setRevoked(false);
        refreshTokenRepository.save(refreshEntity);

        return new TokenResponse(accessToken, refreshToken);
    }

    /** AccessToken + RefreshToken 쿠키를 한번에 추가 */
    private void addTokenCookies(HttpServletResponse response, TokenResponse tokenResponse) {
        addTokenCookie(response, "accessToken", tokenResponse.accessToken(), ACCESS_TOKEN_EXPIRATION);
        addTokenCookie(response, "refreshToken", tokenResponse.refreshToken(), REFRESH_TOKEN_EXPIRATION);
    }

    /** 공통 쿠키 생성 로직 */
    private void addTokenCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    private boolean checkPassword(User user, String rawPassword) {
        return passwordEncoder.matches(rawPassword, user.getPassword());
    }

    public record TokenResponse(String accessToken, String refreshToken) {
    }

    @Transactional
    public SignUpDTO.Response createUser(SignUpDTO.Request request, MultipartFile profileImage) {

        // 닉네임 중복 검사
        if (userRepository.existsByNickname(request.getNickname())) {
            throw new RestApiException(AuthErrorCode.DUPLICATE_NICKNAME);
        }

        // 이메일 중복 검사
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RestApiException(AuthErrorCode.DUPLICATE_EMAIL);
        }

        User user = new User(
                request.getNickname(),
                request.getEmail(),
                BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));

        // 먼저 user를 DB에 저장하여 ID를 할당받음
        user = userRepository.save(user);

        if (profileImage != null) {
            // 파일 확장자 추출
            String originalFilename = profileImage.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));

            String imageUrl = imageService.uploadImage(
                    profileImage,
                    "users/" + user.getId() + "/",
                    "profile" + extension);
            user.setProfileUrl(imageUrl);
        }

        return new SignUpDTO.Response(
                user.getId(),
                user.getNickname(),
                user.getEmail(),
                user.getProfileUrl());
    }
}
