package com.example.springsecuritylogin.domain.Entity;

import com.example.springsecuritylogin.domain.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class RefreshToken extends BaseTimeEntity {
    @Id
    @Column(name = "refreshTokenIdx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // JPA 가 지원하는 PK 생성 전략 설정 (https://juntcom.tistory.com/156)
    private Long id;

    @Column(unique = true)
    private String refreshToken;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinColumn(name = "userIdx", updatable = false)
    private User user;


    @Builder
    public RefreshToken(Long id, String refreshToken, User user) {
        this.id = id;
        this.refreshToken = refreshToken;
        this.user = user;
        user.getRefreshTokens().add(this);
    }

}
