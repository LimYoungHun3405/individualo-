package com.newsfeed.cider.domain.community.controller;

import com.newsfeed.cider.common.model.CommonResponse;
import com.newsfeed.cider.domain.community.model.request.CommunityCreateRequest;
import com.newsfeed.cider.domain.community.model.request.CommunityUpdateRequest;
import com.newsfeed.cider.domain.community.model.response.CommunityCreateResponse;
import com.newsfeed.cider.domain.community.model.response.CommunityGetResponse;
import com.newsfeed.cider.domain.community.model.response.CommunityUpdateResponse;
import com.newsfeed.cider.domain.community.service.CommunityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/community")
public class CommunityController {

    private final CommunityService communityService;

    /**
     * 커뮤니티 그룹 생성
     * @param request CreateCommunityRequest (communityName, info)
     * @return CreateCommunityResponse (communityId, communityName, info, createdAt)
     */
    @PostMapping()
    public ResponseEntity<CommonResponse<CommunityCreateResponse>> create(@RequestBody @Valid CommunityCreateRequest request) {

        CommonResponse<CommunityCreateResponse> result = communityService.create(request);

        return ResponseEntity.status(result.getStatus()).body(result);
    }

    /**
     * 그룹 조회 페이징
     * @param page 페이지 번호
     * @param size 페이지 크기
     * @return PagedModel<GetCommunityResponse>> (communityId, communityName, info, createdAt)
     */
    @GetMapping()
    public ResponseEntity<CommonResponse<PagedModel<CommunityGetResponse>>> getCommunityPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        CommonResponse<PagedModel<CommunityGetResponse>> result = communityService.getCommunityPage(page, size);

        return ResponseEntity.status(result.getStatus()).body(result);
    }

    /**
     * 그룹 단건 조회
     * @param communityName 커뮤니티 그룹 이름
     * @return GetCommunityResponse (communityId, communityName, info, createdAt)
     */
    @GetMapping("/{communityName}")
    public ResponseEntity<CommonResponse<CommunityGetResponse>> getOneCommunity(@PathVariable String communityName) {

        CommonResponse<CommunityGetResponse> result = communityService.getOneCommunity(communityName);

        return ResponseEntity.status(result.getStatus()).body(result);
    }

    /**
     * 그룹 수정
     * @param communityName 커뮤니티 그룹 이름
     * @param request UpdateCommunityRequest (communityName, info)
     * @return UpdateCommunityResponse (communityId, communityName, info, createdAt)
     */
    @PutMapping("/{communityName}")
    public ResponseEntity<CommonResponse<CommunityUpdateResponse>> update(
            @PathVariable String communityName,
            @RequestBody CommunityUpdateRequest request) {

        CommonResponse<CommunityUpdateResponse> result = communityService.update(communityName, request);

        return ResponseEntity.status(result.getStatus()).body(result);
    }

    /**
     * 그룹 삭제
     * @param communityName 커뮤니티 그룹 이름
     * @return OK, null
     */
    @DeleteMapping("/{communityName}")
    public ResponseEntity<CommonResponse<Void>> delete(@PathVariable String communityName) {

        CommonResponse<Void> result = communityService.delete(communityName);

        return ResponseEntity.status(result.getStatus()).body(result);
    }
}
