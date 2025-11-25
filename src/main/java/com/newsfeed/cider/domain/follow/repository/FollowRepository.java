package com.newsfeed.cider.domain.follow.repository;

import com.newsfeed.cider.common.entity.Follow;
import com.newsfeed.cider.common.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    // - Find All By Follower
    List<Follow> findAllByFollower(Profile follower);
    // - Find All By Followee
    List<Follow> findAllByFollowee(Profile followee);
    // - Find By Follower And Followee
    Optional<Follow> findByFollowerAndFollowee(Profile follower, Profile followee);
    // - ExistsByFollowerAndFollowee
    boolean existsByFollowerAndFollowee(Profile follower, Profile followee);
}
