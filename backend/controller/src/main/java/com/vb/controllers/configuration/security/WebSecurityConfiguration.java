package com.vb.controllers.configuration.security;

import com.vb.controllers.security.CustomOAuthUserService;
import com.vb.controllers.security.CustomUserDetailsService;
import com.vb.controllers.security.social.SocialAuthPageRedirectFilter;
import com.vb.controllers.security.social.SocialAuthSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private CustomOAuthUserService customOAuthUserService;

    @Value("${api.v1.url}")
    private String apiV1Url;

    @Value("${origins.allowed}")
    private List<String> allowedOrigins;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(allowedOrigins);
        configuration.setAllowedMethods(Collections.singletonList("*"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Collections.singletonList(HttpHeaders.CONTENT_TYPE));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    private String[] apiV1Patterns(String... antPatterns) {
        return Stream.of(antPatterns).map(s -> apiV1Url + s).toArray(String[]::new);

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable().cors().and()
                .authorizeRequests()
                .antMatchers(apiV1Patterns("/brands/**", "/models/**", "/generations/**", "/auth/registration")).permitAll()
                .antMatchers("/{sw:swagger.*}/**", "/v3/**").permitAll()
                .antMatchers(HttpMethod.GET, apiV1Patterns("/cars/**", "/auth/logout")).permitAll()
                .antMatchers("/login/**", "/registration").anonymous()
                .antMatchers(apiV1Patterns("/admin/**")).hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                )
                .formLogin(formLogin -> formLogin
                        .loginProcessingUrl(apiV1Url + "/login")
                        .defaultSuccessUrl(apiV1Url + "/auth/me", true)
                        .failureHandler((request, response, e) -> response.setStatus(HttpStatus.UNAUTHORIZED.value()))
                        .usernameParameter("email")
                        .passwordParameter("password")
                )
                .logout(logout -> logout
                        .logoutUrl(apiV1Url + "/logout")
                        .logoutSuccessHandler((request, response, authentication) -> {
                            //No actions
                        })
                )
                .addFilterBefore(new SocialAuthPageRedirectFilter(), OAuth2AuthorizationRequestRedirectFilter.class)
                .oauth2Login(oauth2Login -> oauth2Login
                        .authorizationEndpoint().baseUri(apiV1Url + "/social/auth")
                        .and().successHandler(new SocialAuthSuccessHandler())
                        .userInfoEndpoint().oidcUserService(customOAuthUserService)
                );

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

}
