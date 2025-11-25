package com.newsfeed.cider.domain.post.model.response;

import com.newsfeed.cider.common.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostUpdateResponse {

    private final Long id;
    private final String name;
    private final String title;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public PostUpdateResponse(Long id, String name, String title, String content, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    // Post를 PostUpdateResponse 만들어주는 정적 팩토리 메서드
    public static PostUpdateResponse from(Post post) {
        return new PostUpdateResponse(
                post.getPostId(),
                post.getProfile().getName(),
                post.getTitle(),
                post.getContent(),
                post.getCreatedAt(),
                post.getModifiedAt()
        );
    }
}
