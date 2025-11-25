package com.newsfeed.cider.domain.profile.model.response;

import com.newsfeed.cider.common.entity.Profile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileCreateResponse {
    private Long id;
    private String email;
    private String profilename;
    private String password;

    public static ProfileCreateResponse from(Profile profile) {
        return new ProfileCreateResponse(
                profile.getProfileId(),
                profile.getName(),
                profile.getEmail(),
                profile.getPassword()
        );
    }

}
