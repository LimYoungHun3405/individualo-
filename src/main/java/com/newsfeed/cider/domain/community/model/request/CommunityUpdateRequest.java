package com.newsfeed.cider.domain.community.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CommunityUpdateRequest {

    @NotBlank
    private String communityName;
    private String info;

    public CommunityUpdateRequest(String communityName, String info) {
        this.communityName = communityName;
        this.info = info;
    }
}
