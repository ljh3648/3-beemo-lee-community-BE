package com.kakao_tech.community.entity;

import com.kakao_tech.community.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserTest {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("회원 생성 테스트")
    @Transactional
    void userCreateTest() {
//        given : 유저 한명의 정보를 준비한다.
        User user = new User("beemo", "beemo@example.com", "1234");

//        when : 실제로 데이터베이스에 저장한다.
        entityManager.persist(user);
        entityManager.flush();
        entityManager.clear();

//        then : 실제로 생성되었는지 검증한다.
        User result = entityManager.find(User.class, user.getId());
        assertThat(result).isNotNull();
        assertThat(result.getEmail()).isEqualTo(user.getEmail());
        assertThat(result.getNickname()).isEqualTo(user.getNickname());
    }

    @Test
    @DisplayName("One To Many 연관관계 테스트")
    @Transactional
    void userOneToManyTest() {
//        given : 유저 정보, 포스트 저장한다.
        User user = new User("beemo", "beemo@example.com", "1234");
        Post post1 = new Post("첫번째 게시글입니다.", "내용은 이렇습니다.", user);
        Post post2 = new Post("두번째 게시글입니다.", "내용은 이렇습니다.", user);
        user.addPost(post1); // 게시글 만들었기 때문에 동기화를 해야한다.
        user.addPost(post2);

        entityManager.persist(user); // User 클래스에서 Post 연관관계 설정에서 전이영속성을 설정하였기 때문에 추가된 post도 함께 영속성이 된다.

        post1.setBody("내용이 수정되었습니다.");
        user.removePost(post2); // post2를 삭제한다.
        entityManager.flush();

//        when : 양방향 관계인 user가 작성한 게시글을 조회한다.
//        데이터베이스에서 user.getId()에서 출력된 유저의 아이디를 찾는다.
        User find_user = entityManager.find(User.class, user.getId());
        System.out.println("User Id : " + find_user.getId());
        System.out.println("게시글 수 : " + find_user.getPosts().size());
//        find_user 객체에서 그 유저가 작성한 게시글들을 찾는다.
        for(Post p : find_user.getPosts()){
            System.out.println("Post ID : " + p.getId());
            System.out.println("제목     : " + p.getTitle());
            System.out.println("내용     : " + p.getBody());
            System.out.println("User ID : " + p.getUser().getId());
            System.out.println("작성자    : " + p.getUser().getNickname());
            System.out.println("이메일    : " + p.getUser().getEmail());
        }


//        then : 실제로 양방향 관계로 데이터를 확인 할 수 있는지 검증
    }

//    @Test
//    @Transactional
//    @Rollback(false)
//    void jpqlQueryTest() {
//        System.out.println("test==================================");
//        User user = new User("beemo", "beemo@example.com", "1234");
//        entityManager.persist(user);
////        TypedQuery<UserEntity> query = entityManager.createQuery("SELECT u FROM UserEntity u", UserEntity.class);
////        List<UserEntity> users = query.getResultList();
//        Query query = entityManager.createQuery("SELECT u.nickname FROM User u");
//        List<Object[]> result = query.getResultList();
//
//        System.out.println(result);
//        System.out.println("result==================================");
//    }
}