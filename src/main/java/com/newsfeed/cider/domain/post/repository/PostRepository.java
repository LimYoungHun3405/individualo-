package com.newsfeed.cider.domain.post.repository;

import com.newsfeed.cider.common.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    // PostId가 일치하는 단일 Post 찾기
    Optional<Post> findByPostId(Long postId);
}
