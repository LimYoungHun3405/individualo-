package com.newsfeed.cider.domain.profile.service;


import com.newsfeed.cider.common.entity.Profile;
import com.newsfeed.cider.common.enums.ExceptionCode;
import com.newsfeed.cider.common.exception.CustomException;
import com.newsfeed.cider.common.model.SessionUser;
import com.newsfeed.cider.common.util.PasswordEncoder;
import com.newsfeed.cider.domain.profile.model.request.LoginRequest;
import com.newsfeed.cider.domain.profile.model.request.ProfileCreateRequest;
import com.newsfeed.cider.domain.profile.model.request.ProfileUpdateRequest;
import com.newsfeed.cider.domain.profile.model.response.ProfileCreateResponse;
import com.newsfeed.cider.domain.profile.model.response.ProfileReadResponse;
import com.newsfeed.cider.domain.profile.model.response.ProfileUpdateResponse;
import com.newsfeed.cider.domain.profile.repository.ProfileRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.newsfeed.cider.common.util.AuthManager.validateAuthorization;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Transactional
@Service
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final PasswordEncoder passwordEncoder;


    public ProfileCreateResponse createProfile(ProfileCreateRequest request){
        if (profileRepository.existsByEmail(request.getEmail())){
            throw new CustomException(ExceptionCode.EXIST_EMAIL);
        }

        Profile profile = new Profile(request.getProfilename(), request.getEmail(), passwordEncoder.encode(request.getPassword()));
        profileRepository.save(profile);

        return ProfileCreateResponse.from(profile);

    }


    public ProfileUpdateResponse updateProfile(long nowLoginProfileId, long profileId, ProfileUpdateRequest request) {
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_PROFILE));

        //isOwner(nowLoginProfileId, profile.getProfileId());
        validateAuthorization(nowLoginProfileId, profile.getProfileId());

        profile.updateProfileInfo(request.getProfilename(), request.getEmail());

        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            String encodedPassword = passwordEncoder.encode(request.getPassword());
            profile.updatePassword(encodedPassword);
        }

        profileRepository.save(profile);

        return ProfileUpdateResponse.from(profile);
    }



    public void deleteProfile(long nowLoginProfileId, long profileId){

        Profile profile =  profileRepository.findById(profileId).orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_PROFILE));

        validateAuthorization(nowLoginProfileId, profile.getProfileId());

        profileRepository.delete(profile);
    }


    @Transactional(readOnly = true)
    public ProfileReadResponse getProfile(long profileId){
        Profile profile = profileRepository.findById(profileId).orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_PROFILE));

        return ProfileReadResponse.from(profile);
    }

    @Transactional(readOnly = true)
    public SessionUser login(LoginRequest request){
        Profile profile = profileRepository.findByEmail(request.getEmail()).orElseThrow(() -> new CustomException(ExceptionCode.UN_AUTHORIZED));

        if(!passwordEncoder.matches(request.getPassword(), profile.getPassword())){
            throw new CustomException(ExceptionCode.UN_AUTHORIZED);
        }

        return new SessionUser(profile.getProfileId(), profile.getEmail());
    }

    /*void isOwner(long nowLoginProfileId, long profileId){
        if(nowLoginProfileId != profileId){
            throw new CustomException(ExceptionCode.FORBIDDEN);
        }
    }*/
}
