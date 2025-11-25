package com.newsfeed.cider.common.util;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoder {

    /**
     * 비밀번호 암호화 기능
     * @param rawPassword 암호화할 대상
     * @return 암호화된 비밀번호
     */
    public String encode(String rawPassword) {
        return BCrypt.withDefaults().hashToString(BCrypt.MIN_COST, rawPassword.toCharArray());
    }

    /**
     * 데이터베이스에 저장된 비밀번호와 입력된 비밀번호 비교 기능
     * @param rawPassword 입력된 비밀번호
     * @param encodedPassword 암호화된 비밀번호
     * @return 일치하면 true, 불일치면 false
     */
    public boolean matches(String rawPassword, String encodedPassword) {
        BCrypt.Result result = BCrypt.verifyer().verify(rawPassword.toCharArray(), encodedPassword);
        return result.verified;
    }
}
