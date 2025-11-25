package com.newsfeed.cider.domain.follow.model.response;

import com.newsfeed.cider.common.entity.Follow;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FollowResponse {
// - Properties
    private final Long followerId;
    private final Long followeeId;

// - Methods
    // - From Entity
    public static FollowResponse from(Follow follow) {
        return new FollowResponse(
                follow.getFollower().getProfileId(),
                follow.getFollowee().getProfileId()
        );
    }
}
