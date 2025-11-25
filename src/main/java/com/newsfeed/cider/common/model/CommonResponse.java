package com.newsfeed.cider.common.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CommonResponse<T> {

    private final int code;
    private final HttpStatus status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final T content;

    public CommonResponse(HttpStatus status, T content) {
        this.code = status.value();
        this.status = status;
        this.content = content;
    }
}
