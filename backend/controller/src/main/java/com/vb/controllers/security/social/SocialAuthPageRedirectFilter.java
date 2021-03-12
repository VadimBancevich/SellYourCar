package com.vb.controllers.security.social;

import org.springframework.security.web.savedrequest.HttpSessionRequestCache;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SocialAuthPageRedirectFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpReq = ((HttpServletRequest) request);
        HttpServletResponse httpResp = (HttpServletResponse) response;
        if (httpReq.getRequestURL().toString().contains("/social/auth/")) {
            HttpSessionRequestCache reqCache = new HttpSessionRequestCache();
            if (reqCache.getRequest(httpReq, httpResp) == null) {
                reqCache.saveRequest(httpReq, httpResp);
            }
        }
        chain.doFilter(request, response);
    }

}
