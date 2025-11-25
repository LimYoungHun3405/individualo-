package com.newsfeed.cider.common.util;

import com.newsfeed.cider.common.exception.CustomException;

import static com.newsfeed.cider.common.enums.ExceptionCode.ACCESS_DENIED;

public class AuthManager {

    // 로그인한 계정이 권한이 있는지 확인
    public static void validateAuthorization(Long loginId, Long profileId) {
        boolean isSameUser = profileId.equals(loginId);
        if (!isSameUser) {
            throw new CustomException(ACCESS_DENIED);
        }
    }
}
