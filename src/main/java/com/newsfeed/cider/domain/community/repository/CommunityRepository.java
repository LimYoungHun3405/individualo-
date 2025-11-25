package com.newsfeed.cider.domain.community.repository;

import com.newsfeed.cider.common.entity.Community;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommunityRepository extends JpaRepository<Community,Long> {

    boolean existsByCommunityName(String communityName);

    Page<Community> findAll(@NonNull Pageable pageable);

    Optional<Community> findByCommunityName(String communityName);

    void deleteByCommunityName(String communityName);
}
