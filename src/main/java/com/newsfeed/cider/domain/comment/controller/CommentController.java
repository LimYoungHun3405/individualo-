package com.newsfeed.cider.domain.comment.controller;

import com.newsfeed.cider.common.model.SessionUser;
import com.newsfeed.cider.domain.comment.model.request.CommentRequestDto;
import com.newsfeed.cider.domain.comment.model.response.CommentResponseDto;
import com.newsfeed.cider.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/posts/{postId}/comments/{commentId}")
    public CommentResponseDto createComment(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @RequestBody CommentRequestDto dto
          ) {
        return commentService.createComment(sessionUser, postId, commentId, dto);
    }

    @GetMapping("/posts/{postId}/comments")
    public List<CommentResponseDto> getComments(@PathVariable Long postId) {
        return commentService.getComments(postId);
    }

    @PutMapping("/comments/{commentId}")
    public CommentResponseDto updateComment(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            @PathVariable Long commentId,
            @RequestBody CommentRequestDto dto) {
        return commentService.updateComment(sessionUser,commentId, dto );
    }

    @DeleteMapping("/comments/{commentId}")
    public String deleteComment(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            @PathVariable Long commentId
       ) {
        commentService.deleteComment(commentId,sessionUser);
        return "삭제 완료";
    }
}