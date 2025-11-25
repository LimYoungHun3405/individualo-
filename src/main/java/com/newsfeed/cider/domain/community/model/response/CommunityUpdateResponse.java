package com.newsfeed.cider.domain.community.model.response;

import com.newsfeed.cider.common.entity.Community;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommunityUpdateResponse {
    private Long communityId;
    private String communityName;
    private String info;
    private LocalDateTime createdAt;

    public static CommunityUpdateResponse from(Community community) {
        return new CommunityUpdateResponse(
                community.getCommunityId(),
                community.getCommunityName(),
                community.getInfo(),
                community.getCreatedAt()
        );
    }
}
