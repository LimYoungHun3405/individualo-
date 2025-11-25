package com.newsfeed.cider.common.enums;

import lombok.Getter;

@Getter
public enum ValidCode { //Valid 오류 메시지 Enum

    //프로필

    //게시글
    NOT_BLANK_POST_TITLE("title", "NotBlank", "제목을 작성하지 않았습니다."),
    NOT_BLANK_POST_CONTENT("content", "NotBlank","내용을 작성하지 않았습니다."),

    //댓글

    //팔로우

    //그룹
    NOT_BLANK_COMMUNITY_NAME("communityName", "NotBlank", "커뮤니티 이름을 적어주세요");

    private final String field;
    private final String code;
    private final String message;

    ValidCode(String field, String code, String message) {
        this.field = field;
        this.code = code;
        this.message = message;
    }

    /**
     * 필드이름과 어노테이션 이름으로 메시지 얻기
     * @param field 필드 이름
     * @param code  어노테이션 이름
     * @return 메시지
     */
    public static String getMessage(String field, String code) {
        for (ValidCode valid : values()) {
            if (valid.field.equals(field) && valid.code.equals(code)) {
                return valid.message;
            }
        }
        return null;
    }

}

