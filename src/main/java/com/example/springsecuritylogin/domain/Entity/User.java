package com.example.springsecuritylogin.domain.Entity;

import com.example.springsecuritylogin.domain.BaseTimeEntity;
import com.example.springsecuritylogin.domain.Role;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@ToString(exclude = "refreshTokens")
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

    @Column(length = 20, unique = true, nullable = false)
    private String uniqueId;

    @Column(length = 20, unique = true)
    private String nickname;

    @Column(length = 100, unique = true, nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    // Enum 타입 객체를 쓸때 활용, 반드시 String 타입으로 바꿔주고 DB 필드에 널어주어야 합니다.
    private Role role;

    // 연관 관계
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true) // FK가 없는 쪽에 mappedBy 사용을 추천
    private List<RefreshToken> refreshTokens = new ArrayList<>(); // 엔티티에 Null이 있는 것은 좋지 않기 때문에 ArrayList 인스턴스를 넣어 Null을 방지합니다.

    @Builder
    public User(Long id, String uniqueId, String nickname, String password, Role role) {
        this.id = id;
        this.uniqueId = uniqueId;
        this.nickname = nickname;
        this.password = password;
        this.role = role;
    }
}
