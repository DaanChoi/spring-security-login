package com.example.springsecuritylogin.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
// JPA Entity 클래스들이 BaseTimeEntity 상속할 경우 필드들(createdAt, lastModifiedAt)도 컬럼으로 인식하도록 합니다.
@EntityListeners(AuditingEntityListener.class)
// 자동으로 값 매핑 (Auditing) 기능 추가
public abstract class BaseTimeEntity {

    @CreatedDate
    // JPA Entity 최초로 생성되어 저장될 때, 시간이 자동으로 저장됩니다.
    private LocalDateTime createdData;
    @LastModifiedDate
    // JPA Entity 값이 변경될 때, 시간이 자동으로 저장됩니다.
    private LocalDateTime lastModifiedDate;

}
