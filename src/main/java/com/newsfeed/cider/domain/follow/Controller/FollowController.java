package com.newsfeed.cider.domain.follow.Controller;

import com.newsfeed.cider.common.model.CommonResponse;
import com.newsfeed.cider.common.model.SessionUser;
import com.newsfeed.cider.domain.follow.Service.FollowService;
import com.newsfeed.cider.domain.follow.model.response.FollowResponse;
import com.newsfeed.cider.domain.profile.model.response.SummaryProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profiles")
public class FollowController {
// - Properties
    private final FollowService followService;

// - Methods
    // - Follow
    @PostMapping("/{followeeId}/follow")
    public ResponseEntity<CommonResponse<FollowResponse>> follow(
            @SessionAttribute(name = "loginUser") SessionUser loginUser,
            @PathVariable Long followeeId) {
        Long followerId = loginUser.getUserId();
        FollowResponse result = followService.follow(followerId, followeeId);

        CommonResponse<FollowResponse> response =
                new CommonResponse<>(HttpStatus.CREATED, result);

        return ResponseEntity.status(response.getStatus()).body(response);
    }
    // - UnFollow
    @DeleteMapping("/{followeeId}/follow")
    public ResponseEntity<CommonResponse<FollowResponse>> unfollow(
            @SessionAttribute(name = "longinUser") SessionUser loginUser,
            @PathVariable Long followeeId) {
        Long followerId = loginUser.getUserId();
        FollowResponse result = followService.unfollow(followerId, followeeId);

        CommonResponse<FollowResponse> response =
                new CommonResponse<>(HttpStatus.OK, result);

        return ResponseEntity.status(response.getStatus()).body(response);
    }
    // - Get Following List
    @GetMapping("/following")
    public ResponseEntity<CommonResponse<List<SummaryProfileResponse>>> getFollowingList(
            @SessionAttribute(name = "loginUser") SessionUser loginUser) {
        Long followerId = loginUser.getUserId();
        List<SummaryProfileResponse> result = followService.getFollowingList(followerId);

        CommonResponse<List<SummaryProfileResponse>> response =
                new CommonResponse<>(HttpStatus.OK, result);

        return ResponseEntity.status(response.getStatus()).body(response);
    }
    // - Get Follower List
    @GetMapping("/follower")
    public ResponseEntity<CommonResponse<List<SummaryProfileResponse>>> getFollowerList(
            @SessionAttribute(name = "loginUser") SessionUser loginUser) {
        Long followeeId = loginUser.getUserId();
        List<SummaryProfileResponse> result = followService.getFollowerList(followeeId);

        CommonResponse<List<SummaryProfileResponse>> response =
                new CommonResponse<>(HttpStatus.OK, result);

        return ResponseEntity.status(response.getStatus()).body(response);
    }
}