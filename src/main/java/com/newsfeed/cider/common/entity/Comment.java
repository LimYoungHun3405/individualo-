package com.newsfeed.cider.common.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "comment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;             //댓글 고유 ID

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;                  //댓글이 달린 게시물 ID

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "profile_id", nullable = false)
    private Profile profile;            //프로필 고유 ID

    @Column(name = "parent_id")
    private Long parentId;              //상위 댓글 ID

    @Column(nullable = false)
    private String content;             //댓글 내용

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;    //삭제 여부 및 시점

    public Comment(Post post, Profile profile, Long parentId, String content) {
        this.post = post;
        this.profile = profile;
        this.parentId = parentId;
        this.content = content;
    }

    public void softDelete() {
        this.deletedAt = LocalDateTime.now();
    }
}
