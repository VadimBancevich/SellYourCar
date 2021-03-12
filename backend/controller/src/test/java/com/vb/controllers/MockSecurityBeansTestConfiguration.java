package com.vb.controllers;

import com.vb.controllers.security.CustomOAuthUserService;
import com.vb.controllers.security.CustomUserDetailsService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

@TestConfiguration
public class MockSecurityBeansTestConfiguration {

    @MockBean
    private CustomUserDetailsService userDetailsService;

    @MockBean
    private CustomOAuthUserService customOAuthUserService;

    @MockBean
    private ClientRegistrationRepository clientRegistrationRepository;

}
