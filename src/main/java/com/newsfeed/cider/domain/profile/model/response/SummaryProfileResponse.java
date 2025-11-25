package com.newsfeed.cider.domain.profile.model.response;

import com.newsfeed.cider.common.entity.Profile;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SummaryProfileResponse {
// - Properties
    private final Long profileId;
    private final String name;

// - Methods
    // - From Entity
    public static SummaryProfileResponse from(Profile profile) {
        return new SummaryProfileResponse(
                profile.getProfileId(),
                profile.getName()
        );
    }
}
