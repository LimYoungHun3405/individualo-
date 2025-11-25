package com.newsfeed.cider.domain.profile.controller;


import com.newsfeed.cider.common.enums.ExceptionCode;
import com.newsfeed.cider.common.exception.CustomException;
import com.newsfeed.cider.common.model.CommonResponse;
import com.newsfeed.cider.common.model.SessionUser;
import com.newsfeed.cider.domain.profile.model.request.LoginRequest;
import com.newsfeed.cider.domain.profile.model.request.ProfileCreateRequest;
import com.newsfeed.cider.domain.profile.model.request.ProfileUpdateRequest;
import com.newsfeed.cider.domain.profile.model.response.ProfileCreateResponse;
import com.newsfeed.cider.domain.profile.model.response.ProfileReadResponse;
import com.newsfeed.cider.domain.profile.model.response.ProfileUpdateResponse;
import com.newsfeed.cider.domain.profile.service.ProfileService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    //프로필 정보 보기
    @GetMapping("/profile/{profileId}")
    public ResponseEntity<CommonResponse<ProfileReadResponse>> getProfile(@PathVariable Long profileId){
        ProfileReadResponse response = profileService.getProfile(profileId);
        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<>(HttpStatus.OK, response));
    }

    //정보 수정
    @PutMapping("/profile/{profileId}")
    public ResponseEntity<CommonResponse<ProfileUpdateResponse>> updateProfile(@PathVariable Long profileId,
                                                                               @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
                                                                               @RequestBody ProfileUpdateRequest request){
        checkLogin(sessionUser);

        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<>(HttpStatus.OK, profileService.updateProfile(sessionUser.getUserId(), profileId, request)));
    }



    //회원가입
    @PostMapping("/signup")
    public ResponseEntity<CommonResponse<ProfileCreateResponse>> createProfile(@Valid @RequestBody ProfileCreateRequest request){
        ProfileCreateResponse response = profileService.createProfile(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CommonResponse<>(HttpStatus.CREATED, response));
    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity<CommonResponse<Void>> login(@RequestBody LoginRequest request, HttpSession session){
        SessionUser sessionUser = profileService.login(request);
        session.setAttribute("loginUser", sessionUser);

        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<>(HttpStatus.OK, null));

    }

    //로그아웃
    @PostMapping("/logout")
    public ResponseEntity<CommonResponse<Void>> logout(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            HttpSession session){

        if(sessionUser == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CommonResponse<>(HttpStatus.BAD_REQUEST, null));
        }

        session.invalidate();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new CommonResponse<>(HttpStatus.NO_CONTENT, null));

    }

    //회원탈퇴
    @DeleteMapping("/profile/{profileId}")
    public ResponseEntity<CommonResponse<Void>> deleteProfile(
            @PathVariable Long profileId, @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            HttpSession session){
        checkLogin(sessionUser);

        profileService.deleteProfile(sessionUser.getUserId(), profileId);
        session.invalidate();

        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponse<>(HttpStatus.OK, null));
    }

    private void checkLogin(SessionUser sessionUser) {
        if (sessionUser == null) {
            throw new CustomException(ExceptionCode.FORBIDDEN);
        }
    }



}
