package com.kakao_tech.community.service;

import com.kakao_tech.community.dto.SessionDTO;
import com.kakao_tech.community.entity.Session;
import com.kakao_tech.community.entity.User;
import com.kakao_tech.community.repository.SessionRepository;
import com.kakao_tech.community.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;

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

    public User authenticateUser(String email, String password) {
        // TODO : 인자로 넘겨받은 패스워드랑 비밀번호가 NULL이거나 공백이면 어떻게 되는건지 실험해봐야곘다.
        // 일단 이메일 있는지 찾자 v
        User user = userRepository.findByEmail(email);

        // 이메일로 유저를 찾았는데 없을 수도 있음 그럼 확인해봐야지 v
        if (user == null) {
            throw new InvalidParameterException("그런 이메일 가진 유저 없음");
        }

        // 2. 그리고 그 유저의 비밀번호를 입력받은 비밀번호랑 비교하기 물론 나중에 비크립트로 둘이 비교해야함 v
        // 3. 위에 아무튼 뭐가 잘못됐으면 뭐가 이상하다고 쓰로잉해야지 v

        // 여기에서는 찾은 유저의 이메일에서 비밀번호랑 파라미터로 넘겨받은 비밀번호랑 비교해야함. v
        if (!user.getPassword().equals(password)) {
            throw new InvalidParameterException("비밀번호 틀림");
        }

        return user;
    }

    // TODO : 세션 제대로 만들기
    // 여기에 세션 등록하는 서비스 만들어야함
    // 근데 여기 세션 만드는건데, 왜 인증도 같이하는거지? 메서드로 분리해야겠다. v
    public SessionDTO createSession(String email, String password) {
        // 1. 일단 데이터베이스에서 유저테이블에서 입력받은 이메일 유저가 있는지 찾기 v
        // 1.1. 음 User 레퍼지토리 이용해서 이미 그 비교하는거 있으니까 그거 쓰면 될거같은데 v
        // 1.2  비밀번호 일치하는지도 추가하고 v
        // 1.3 컨트롤러에서 이메일이랑 비밀번호 넘겨 받아야지 v
        // 1.4 해당 양식이 필요하겠네 DTO 만들까? 일단 대충 받자 그냥 v - todo에 추가하자

        User user = authenticateUser(email, password);

        // 4. 근데 이메일과 비밀번호가 일치하면 이제 세션 만들어야지 v
        // 5. 세션 어떻게 만들지? v

        // 세션 인증키 조건
        // 1. 그냥 비밀번호라고 생각하면 복잡해야함 추측이 안되어야함.
        // 2. 완전히 랜덤해야함.
        // 3. 적당한 길이를 가지고 있어야함.
        // 4. 많은 경우의 수 들이 있어서 브루트포스로 때려맞추려고 해도 가능성이 매우 낮아야함.

        // 일단 대충 만들자 v
        // 세션 길이는 대충 한 최대 10자리 가진 정도 숫자로만 이루어진 문장 v
        // 그냥 숫자출력하는 랜덤 함수 들고와서 세션값이라고 넣으면 될듯 v

        Long number = new Random().nextLong(10000000000L);
        String sessionKey = String.valueOf(number);

        // 보니까 sessions 엔티티 만들어야 할듯 (테이블 만들어야할듯) v
        // 음 테이블 구조는
        // 기본키? id로 자동 증가 뭐 하고 - 나중에 수정해야지 v
        // 음 세션인증키 컬럼 만들고 v
        // 유효기간 컬럼 필요할듯 v
        // 외래 참조키로 유저 아디 남기고 v
        // 근데 이거 세션을 원래 서버 메모리나 Radis같은 곳에 저장해서 사용하는걸로 아는데
        // 지금은 어떻게 해야하지
        // 일단 데이터베이스에 넣고 추후에 개선해보자

        // 6. 세션 만들어진거 데이터베이스에 저장해야지
        // 세션 담는 엔티티 만들기 v
        Session session = new Session(sessionKey, user);
        // 세션 레포지토리 만들어야할듯. v
        // 데이터베이스 세션 저장해볼까 v
        Session result = sessionRepository.save(session);

        // 세션DTO 만들어서 넘기자. v
        return new SessionDTO(result.getSessionKey(), result.getExpiredAt());
    }

    // 세션 파괴하려면 순서는?
    // 세션키값의 유저를 찾는다.
    // 삭제한다.
    public boolean deleteSession(String sessionKey) {
        // 세션키 값의 유저를 찾는다.
        // 근데 어떤 값을 파라미터로 받아오지?
        // 세션키값? 아니면 DTO?
        // 근데 세션키값은 유니크 하지 않고, 그냥 session_id도 같이 받아서 그 아이디 기본키 값으로 찾아서
        // 추적하면 되지 않을까 싶은데
        // 기본키가 오토로 자동증가 하면 관리가 까다롭겠네
        // 일단 만들자

        // 원래는 여기에서 세션 널값을 체크 해야하는거 같은데, 컨트롤 부분에서 해버렸으니...
        // 수정 필요할듯

        System.out.println("DELETING SESSION: " + sessionKey);

         Session session = sessionRepository.findBySessionKey(sessionKey);

         if (session == null) {
             throw new InvalidParameterException("일치된 세션 없음");
         }

         sessionRepository.delete(session);
//        if (!sessionRepository.existsBySessionKey(sessionKey)) {
//            throw new IllegalArgumentException("인증된 세션이 없음.");
//        }

        // 어떤 값을 리턴해줘야 할까?
        // 음 그냥 성공 유무만 리턴해주면 될거 같은데?
        // 참일땐 세션 찾아서 삭제한거고
        // 거짓일땐 세션을 못찾거나 오류로 인해서 삭제를 못한거고
        // 근데 오류일떄는 오류를 던지는게 맞는거 같고.
        // 세션을 못찾아서 삭제에 실패했다고 넘기는게 맞나?
        // 리턴 값은 좀더 고민해보자.
        return true;
    }
}