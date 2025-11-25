package com.newsfeed.cider.common.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "post")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("deleted_at IS NULL")
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId;                //게시글 고유 ID

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "profile_id", nullable = false)
    private Profile profile;            //게시글 작성 프로필 고유 ID

    @Column(nullable = false, length = 100)
    private String title;               //게시글 제목

    @Column(nullable = false)
    private String content;             //게시글 내용

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_id")
    private Community community;        //커뮤니티 (그룹)

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;    //삭제 여부 및 시점

    public Post(Profile profile, String content, String title, Community community) {
        this.profile = profile;
        this.content = content;
        this.title = title;
        this.community = community;
    }

    // Post 제목 수정
    public void updatePostTitle(String title) {
        this.title = title;
    }

    // Post 내용 수정
    public void updatePostContent(String content) {
        this.content = content;
    }

    public void softDelete() {
        this.deletedAt = LocalDateTime.now();
    }
}
