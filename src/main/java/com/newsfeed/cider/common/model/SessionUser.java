package com.newsfeed.cider.common.model;

import lombok.Getter;

@Getter
public class SessionUser {
    private final Long userId;
    private final String email;

    public SessionUser(Long userId, String email) {
        this.userId = userId;
        this.email = email;
    }
}
