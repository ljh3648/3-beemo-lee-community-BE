package com.kakao_tech.community.service;

import com.kakao_tech.community.dto.SessionDTO;
import com.kakao_tech.community.dto.UserDTO;
import com.kakao_tech.community.entity.Session;
import com.kakao_tech.community.entity.User;
import com.kakao_tech.community.exception.CustomErrorCode;
import com.kakao_tech.community.exception.RestApiException;
import com.kakao_tech.community.repository.SessionRepository;
import com.kakao_tech.community.repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;

    @Autowired
    public AuthService(UserRepository userRepository, SessionRepository sessionRepository) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
    }

    public User authenticateUser(UserDTO.SignInRequest userDTO) {
        User user = userRepository.findByEmail(userDTO.getEmail());

        if (user == null) {
//            throw new InvalidParameterException("그런 이메일 가진 유저 없음");
            throw new RestApiException(CustomErrorCode.ERROR_SIGN_IN);
        }

        if (!BCrypt.checkpw(userDTO.getPassword(), user.getPassword())) {
//            throw new InvalidParameterException("비밀번호 틀림");
            throw new RestApiException(CustomErrorCode.ERROR_SIGN_IN);
        }

        return user;
    }


    // 세션 등록 (로그인)
    public SessionDTO.ResponseCookie createSession(UserDTO.SignInRequest userDTO) {
        User user = authenticateUser(userDTO);

        // TODO : 세션값 제대로 만들기.
        // 세션 인증키 조건
        // 1. 그냥 비밀번호라고 생각하면 복잡해야함 추측이 안되어야함.
        // 2. 완전히 랜덤해야함.
        // 3. 적당한 길이를 가지고 있어야함.
        // 4. 많은 경우의 수 들이 있어서 브루트포스로 때려맞추려고 해도 가능성이 매우 낮아야함.

        Long number = new Random().nextLong(10000000000L);
        String sessionKey = String.valueOf(number);

        Session result = sessionRepository.save(new Session(sessionKey, user));

        return new SessionDTO.ResponseCookie(result.getSessionKey(), result.getExpiredAt());
    }

    // TODO : 세션 반환값 boolean 인데 개선해보기. void으로 바꾼다든지.
    // 세션 삭제 (로그아웃)
    public boolean deleteSession(SessionDTO.RequestCookie sessionDTO) {
         Session session = sessionRepository.findBySessionKey(sessionDTO.getSessionKey());
         if (session == null) {
             throw new RestApiException(CustomErrorCode.ERROR_SIGN_IN);
         }

         sessionRepository.delete(session);
        return true;
    }

    public void verificationSession(SessionDTO.RequestCookie sessionDTO) {
        Session session = sessionRepository.findBySessionKey(sessionDTO.getSessionKey());
        if (session == null) {
            throw new RestApiException(CustomErrorCode.ERROR_SIGN_IN);
        }
    }

    public User getCurrentUser(SessionDTO.RequestCookie request) {
        Session session = sessionRepository.findBySessionKey(request.getSessionKey());
        if (session == null) {
            throw new RestApiException(CustomErrorCode.ERROR_SIGN_IN);
        }
        User result = session.getUser();

        return result;
    }
}