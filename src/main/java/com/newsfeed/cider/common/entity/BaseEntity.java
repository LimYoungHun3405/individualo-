package com.newsfeed.cider.common.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass   //매핑정보만 상속받는 SuperClass
@EntityListeners(AuditingEntityListener.class)  // jpa 엔티티의 특정 이벤트가 발생했을 때, 이를 자동으로 감지하여 수행하도록 도와줌
public abstract class BaseEntity {  // 공용으로 사용할 속성들을 모아둔 클래스

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;
}