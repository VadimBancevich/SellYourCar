package com.vb.controllers.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    protected final RequestCache requestCache = new HttpSessionRequestCache();

    @Override
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String targetUrl;
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if (savedRequest != null) {
            targetUrl = savedRequest.getRedirectUrl();
        } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            targetUrl = "/admin";
        } else if (request.getHeader("Referer") != null) {
            String referer = request.getHeader("Referer");
            targetUrl = referer.contains("/login") ? "/" : referer;
        } else {
            targetUrl = "/";
        }
        return targetUrl;
    }

}
