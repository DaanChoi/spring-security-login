package com.example.springsecuritylogin.domain.entity;

import com.example.springsecuritylogin.enums.Role;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@Entity
@Table(name = "User")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
//JPA 가 관리하는 객체입니다.
public class User extends BaseTimeEntity implements UserDetails {

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

    @Column
    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return uniqueId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
