package com.newsfeed.cider.domain.profile.repository;

import com.newsfeed.cider.common.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<Profile>  findByProfileId(Long profileId);
}
