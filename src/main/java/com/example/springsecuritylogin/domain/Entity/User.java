package com.example.springsecuritylogin.domain.Entity;

import com.example.springsecuritylogin.domain.BaseTimeEntity;
import com.example.springsecuritylogin.domain.Role;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
//JPA 가 관리하는 객체입니다.
public class User extends BaseTimeEntity {

    @Id
//    @Column(name = "member_id", columnDefinition = "BINARY(16)")
//    @GeneratedValue(strategy = GenerationType.IDENTITY) => 왜 에러가 남???.......ㅜㅜㅜㅜㅜㅜㅜㅜㅜ
    @Column(name = "userIdx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // JPA 가 지원하는 PK 생성 전략 설정 (https://juntcom.tistory.com/156)
    private Long id;

    @Column(length = 50, unique = true, nullable = false)
    private String email;

    @Column(length = 20, unique = true, nullable = false)
    private String nickname;

    @Column(length = 100, unique = true, nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    // Enum 타입 객체를 쓸때 활용, 반드시 String 타입으로 바꿔주고 DB 필드에 널어주어야 합니다.
    private Role role;

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Builder
    public User(Long id, String email, String nickname, String password, Role role) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.role = role;
    }
}
