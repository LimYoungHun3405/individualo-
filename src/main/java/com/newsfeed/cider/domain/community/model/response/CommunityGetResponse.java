package com.newsfeed.cider.domain.community.model.response;

import com.newsfeed.cider.common.entity.Community;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommunityGetResponse {
    private Long communityId;
    private String communityName;
    private String info;
    private LocalDateTime createdAt;

    public static CommunityGetResponse from(Community community) {
        return new CommunityGetResponse(
                community.getCommunityId(),
                community.getCommunityName(),
                community.getInfo(),
                community.getCreatedAt()
        );
    }
}
