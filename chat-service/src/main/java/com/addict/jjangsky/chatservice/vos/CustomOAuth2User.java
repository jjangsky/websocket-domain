package com.addict.jjangsky.chatservice.vos;

import com.addict.jjangsky.chatservice.entities.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class CustomOAuth2User implements OAuth2User {
    // 인증 객체 구현체 -> OAuth2User를 구현
    // 그대로 사용하는 것이 아닌 우리가 만든 `Member` 객체를 사용하기 위해 수정

    @Getter
    Member member;
    Map<String, Object> attributeMap;

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributeMap;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> member.getRole());
    }

    @Override
    public String getName() {
        return this.member.getNickName();
    }
}
