package com.kakao_tech.community.entity;

import com.kakao_tech.community.dto.UserDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT")
    private Integer id;

    @Column(unique = true, nullable = false, length = 10)
    private String nickname;

    @Column(unique = true, nullable = false, length = 320)
    private String email;

    @Column(unique = false, nullable = false, length = 60)
    private String password;

    @Column(unique = false, nullable = true, length = 255)
    private String profileUrl;

    protected User() {}

    public User(UserDTO.SignUpRequset signUpRequset) {
        this.nickname = signUpRequset.getNickname();
        this.email = signUpRequset.getEmail();
        this.password = signUpRequset.getPassword();
    }

    public User(String nickname, String email, String password) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
    }

    public User(String nickname, String email, String password, String profileUrl) {
        this(nickname, email, password);
        this.profileUrl = profileUrl;
    }
}
