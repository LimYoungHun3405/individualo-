package com.newsfeed.cider.domain.comment.model.response;

import com.newsfeed.cider.common.entity.Comment;
import com.newsfeed.cider.common.entity.Post;
import com.newsfeed.cider.common.entity.Profile;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponseDto {
    private Long commentId;
    private Post post;
    private Profile profile;
    private Long parentId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static CommentResponseDto from(Comment comment) {
       return new CommentResponseDto(
               comment.getCommentId(),
               comment.getPost(),
               comment.getProfile(),
               comment.getParentId(),
               comment.getContent(),
               comment.getCreatedAt(),
               comment.getModifiedAt()

       );

    }
}