package com.newsfeed.cider.domain.follow.Service;

import com.newsfeed.cider.common.entity.Follow;
import com.newsfeed.cider.common.entity.Profile;
import com.newsfeed.cider.common.enums.ExceptionCode;
import com.newsfeed.cider.common.exception.CustomException;
import com.newsfeed.cider.common.model.CommonResponse;
import com.newsfeed.cider.domain.follow.model.response.FollowResponse;
import com.newsfeed.cider.domain.follow.repository.FollowRepository;
import com.newsfeed.cider.domain.profile.model.response.SummaryProfileResponse;
import com.newsfeed.cider.domain.profile.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowService {
// - Properties
    private final FollowRepository followRepository;
    private final ProfileRepository profileRepository;

    // - Follow(Create)
    @Transactional
    public FollowResponse follow(Long followerId, Long followeeId) {
        // - Check Self Allow
        if (followerId.equals(followeeId)) {
            throw new CustomException(ExceptionCode.SELF_FOLLOW_NOT_ALLOWED);
        }
        // - Find Profile(Follower, Followee) By ID
        Profile follower = findProfileById(followerId);
        Profile followee = findProfileById(followeeId);
        // - Check Already Follow
        if (followRepository.existsByFollowerAndFollowee(follower, followee)) {
            throw new CustomException(ExceptionCode.ALREADY_FOLLOW);
        }
        // - Save New Follow
        Follow follow = new Follow(follower, followee);
        followRepository.save(follow);
        // - return
        return FollowResponse.from(follow);
    }
    // - UnFollow(Delete)
    @Transactional
    public FollowResponse unfollow(Long followerId, Long followeeId) {
        // - Find Profile(Follower, Followee) By ID
        Profile follower = findProfileById(followerId);
        Profile followee = findProfileById(followeeId);
        // - Find Follow, Check Not Found Follow
        Follow follow = followRepository.findByFollowerAndFollowee(follower, followee)
                .orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_FOLLOW));
        // - Delete Follow
        followRepository.delete(follow);
        // - Return
        return FollowResponse.from(follow);
    }
    // - GetFollowingList(Read)
    @Transactional(readOnly = true)
    public List<SummaryProfileResponse> getFollowingList(Long followerId) {
        // - Find Follower By ID
        Profile follower = findProfileById(followerId);
        // - Find All By Follower
        List<Follow> follows = followRepository.findAllByFollower(follower);
        // - Return
        return follows.stream()
                .map(f -> new SummaryProfileResponse(
                        f.getFollowee().getProfileId(),
                        f.getFollowee().getName()
                ))
                .toList();
    }
    // - GetFolloweeList(Read)
    @Transactional(readOnly = true)
    public List<SummaryProfileResponse> getFollowerList(Long followeeId) {
        // - Find Followee By ID
        Profile followee = findProfileById(followeeId);
        // - Find All By Followee
        List<Follow> follows = followRepository.findAllByFollowee(followee);
        // - Return
        return follows.stream()
                .map(f -> new SummaryProfileResponse(
                        f.getFollower().getProfileId(),
                        f.getFollower().getName()
                ))
                .toList();
    }

    // - Find Profile By ID
    private Profile findProfileById(Long profileId) {
        return profileRepository.findById(profileId)
                .orElseThrow( () -> new CustomException(ExceptionCode.NOT_FOUND_PROFILE));
    }
}
