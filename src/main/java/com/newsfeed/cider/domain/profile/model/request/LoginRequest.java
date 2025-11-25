package com.newsfeed.cider.domain.profile.model.request;

import lombok.Getter;

@Getter
public class LoginRequest {

    private String email;
    private String password;
}