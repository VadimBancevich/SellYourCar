package com.vb.controllers.security;

import com.vb.api.service.IUserService;
import com.vb.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class CustomOAuthUserService implements OAuth2UserService<OidcUserRequest, OidcUser> {

    @Autowired
    private IUserService userService;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) {
        final OidcUserService delegate = new OidcUserService();
        final OidcUser oidcUser = delegate.loadUser(userRequest);

        Set<GrantedAuthority> oidcUserAuthorities = new HashSet<>();

        User user = userService.findUserByEmailOrCreateNew(oidcUser);
        user.getRoles().forEach(role ->
                oidcUserAuthorities.add(new SimpleGrantedAuthority(role.getRoleName())));

        return new DefaultOidcUser(oidcUserAuthorities, oidcUser.getIdToken(), oidcUser.getUserInfo(), "email");
    }

}
