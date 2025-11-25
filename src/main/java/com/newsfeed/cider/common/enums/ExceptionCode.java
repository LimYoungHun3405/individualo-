package com.newsfeed.cider.common.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionCode {

    //프로필
    EXIST_EMAIL(HttpStatus.BAD_REQUEST, "존재하는 이메일입니다"),
    FORBIDDEN(HttpStatus.FORBIDDEN, "잘못된 접근입니다"),
    NOT_FOUND_PROFILE(HttpStatus.BAD_REQUEST, "없는 프로필입니다"),
    UN_AUTHORIZED(HttpStatus.UNAUTHORIZED, "이메일과 비밀번호가 일치하지 않습니다"),

    //게시글
    NOT_FOUND_POST(HttpStatus.BAD_REQUEST, "찾으시는 게시글이없습니다"),

    //댓글
    NOT_FOUND_COMMENT(HttpStatus.BAD_REQUEST, "댓글이 존재하지 않습니다"),

    //팔로우
    ALREADY_FOLLOW(HttpStatus.BAD_REQUEST, "이미 팔로우한 유저입니다."),
    SELF_FOLLOW_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "자기 자신은 팔로우할 수 없습니다."),
    NOT_FOUND_FOLLOW(HttpStatus.BAD_REQUEST, "팔로우 관계가 존재하지 않습니다"),

    //커뮤니티 그룹
    EXIST_COMMUNITY(HttpStatus.BAD_REQUEST, "존재하는 커뮤니티입니다"),
    NOT_FOUND_COMMUNITY(HttpStatus.BAD_REQUEST, "존재하지 않은 커뮤니티입니다"),

    //인증
    NOT_LOGGED_IN(HttpStatus.UNAUTHORIZED,  "로그인이 되어 있지 않습니다."),
    ACCESS_DENIED(HttpStatus.FORBIDDEN,  "해당 작업을 수행할 권한이 없습니다."),
    ALREADY_LOGGED_IN(HttpStatus.CONFLICT, "이미 로그인이 되어 있습니다."),
;
    private final HttpStatus status;
    private final String message;

    ExceptionCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }


}
