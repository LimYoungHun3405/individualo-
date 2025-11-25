package com.newsfeed.cider.domain.profile.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProfileCreateRequest {

    @Email(message = "이메일 형식이 아닙니다. 다시 확인해주세요")
    private String email;

    @NotBlank
    @Size(max = 4, message = "유저명은 4자리를 넘을 수 없습니다.")
    private String profilename;

    @NotBlank
    @Size(min = 6, message = "비밀번호가 너무 짧습니다.")
    private String password;

}