package com.newsfeed.cider.domain.post.service;

import com.newsfeed.cider.common.entity.Community;
import com.newsfeed.cider.common.entity.Post;
import com.newsfeed.cider.common.entity.Profile;
import com.newsfeed.cider.common.enums.ExceptionCode;
import com.newsfeed.cider.common.exception.CustomException;
import com.newsfeed.cider.domain.post.model.request.PostCreateRequest;
import com.newsfeed.cider.domain.post.model.request.PostUpdateRequest;
import com.newsfeed.cider.domain.post.model.response.PostCreateResponse;
import com.newsfeed.cider.domain.post.model.response.PostGetResponse;
import com.newsfeed.cider.domain.post.model.response.PostUpdateResponse;
import com.newsfeed.cider.domain.post.repository.PostRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.newsfeed.cider.common.enums.ExceptionCode.NOT_FOUND_POST;
import static com.newsfeed.cider.common.util.AuthManager.validateAuthorization;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    // Post 생성
    @Transactional
    public PostCreateResponse savePost(@Valid PostCreateRequest request, Long loginId) {

        Profile profile = getProfileById(loginId);

        Community community = null;
        if (request.getCommunityId() != null) {
            community = getCommunityById(request.getCommunityId());
        }

        Post post = new Post(
                profile,
                request.getTitle(),
                request.getContent(),
                community
        );

        Post savedPost = postRepository.save(post);
        return PostCreateResponse.from(savedPost);
    }

    // 전체 Post 조회 (페이징)
    @Transactional(readOnly = true)
    public Page<PostGetResponse> getAllPost(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("modifiedAt").descending());
        Page<Post> posts = postRepository.findAll(pageable);
        return posts.map(PostGetResponse::from);
    }

    // 단건 Post 조회
    @Transactional(readOnly = true)
    public PostGetResponse getOnePost(Long postId) {
        Post post = getPostById(postId);
        return PostGetResponse.from(post);
    }

    // Post 수정
    @Transactional
    public PostUpdateResponse updateService(@Valid PostUpdateRequest request, Long loginId, Long postId) {

        Post post = getPostById(postId);

        validateAuthorization(loginId, postId);

        // 제목 수정
        if (request.getTitle() != null) {
            post.updatePostTitle(request.getTitle());
        }

        // 내용 수정
        if (request.getContent() != null) {
            post.updatePostContent(request.getContent());
        }

        return PostUpdateResponse.from(post);
    }

    // Post 삭제
    @Transactional
    public void deletePost(Long loginId, Long postId) {
        Post post = postRepository.findByPostId(postId).orElseThrow(() -> new CustomException(NOT_FOUND_POST));
        validateAuthorization(loginId, postId);
        post.softDelete();
    }

    // postId가 일치하는 Post 가져오기
    // postID가 일치하는 Post가 없으면 예외 처리
    private Post getPostById(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new CustomException(NOT_FOUND_POST)
        );
        return post;
    }

    // 아직 구현되지 않은 메서드 , CommunityService에 구현 예정, 컴파일 에러 방지를 위해 선언만 해둠
    private Community getCommunityById(Long communityId) {
        return null;
    }

    // 아직 구현되지 않은 메서드 , ProfileService에 구현 예정, 컴파일 에러 방지를 위해 선언만 해둠
    private Profile getProfileById(Long loginUserId) {
        throw new CustomException(ExceptionCode.FORBIDDEN);
    }
}
