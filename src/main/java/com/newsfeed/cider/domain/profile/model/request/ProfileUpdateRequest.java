package com.newsfeed.cider.domain.profile.model.request;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProfileUpdateRequest {
    private String profilename;
    private String email;
    private String password;
}
