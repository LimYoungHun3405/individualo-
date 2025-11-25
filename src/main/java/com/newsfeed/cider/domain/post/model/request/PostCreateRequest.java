package com.newsfeed.cider.domain.post.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class PostCreateRequest {

    @NotBlank(message = "제목을 작성하지 않았습니다.")
    private String title;

    @NotBlank(message = "내용을 작성하지 않았습니다.")
    private String content;

    private Long communityId;
}
