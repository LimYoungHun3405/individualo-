package com.newsfeed.cider.domain.comment.service;

import com.newsfeed.cider.common.entity.Comment;

import com.newsfeed.cider.common.entity.Post;
import com.newsfeed.cider.common.entity.Profile;
import com.newsfeed.cider.common.model.SessionUser;
import com.newsfeed.cider.domain.comment.model.request.CommentRequestDto;
import com.newsfeed.cider.domain.comment.model.response.CommentResponseDto;
import com.newsfeed.cider.domain.comment.repository.CommentRepository;
import com.newsfeed.cider.domain.post.repository.PostRepository;
import com.newsfeed.cider.domain.profile.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final ProfileRepository profileRepository;

    @Transactional
    public CommentResponseDto createComment(SessionUser sessionUser, Long postId, Long commentId, CommentRequestDto dto) {

        //1.요청을 entity객체로 만든다.
        //데이터타입  데이터명  =  new 데이터 타입
        Post post = postRepository.findByPostId(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시물이 존재하지 않습니다."));

        //profile parentId content
        Profile profile = profileRepository.findByProfileId(sessionUser.getUserId())
                .orElseThrow(()-> new IllegalArgumentException("프로필이 존재한지 않습니다."));

        Comment comment = new Comment(post, profile , commentId, dto.getContent());

        //2.저장한다.
        Comment savedComment =commentRepository.save(comment);

        //3.저장된 객체를 dto에 담는다.
        return  CommentResponseDto.from(savedComment);

    }
    @Transactional(readOnly = true)
    public List<CommentResponseDto> getComments(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시물이 존재하지 않습니다."));
        ;// 게시글을 찾아온다 postId기준으로

        return commentRepository.findByPost(post)
                .stream()
                .map(CommentResponseDto::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public CommentResponseDto updateComment(SessionUser sessionUser,Long commentId, CommentRequestDto dto) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(()
                ->new IllegalArgumentException("댓글 작성되지 않았습니다."));
        return CommentResponseDto.from(comment);
    }


    @Transactional
    public void deleteComment(Long commentId, SessionUser sessionUser) {
        Comment comment = getCommentByIdAndSessionUser(commentId, sessionUser);
        commentRepository.delete(comment);
    }

    private Comment getCommentByIdAndSessionUser(Long commentId, SessionUser sessionUser) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글이 존재하지 않습니다."));

        if (!comment.getProfile().getProfileId().equals(sessionUser.getUserId())) {
            throw new IllegalArgumentException("작성자만 수정/삭제할 수 있습니다.");
        }
        return comment;
    }
}