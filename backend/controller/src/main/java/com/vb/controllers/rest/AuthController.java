package com.vb.controllers.rest;

import com.vb.api.dto.UserDto;
import com.vb.api.service.IUserService;
import com.vb.api.service.IVerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("${api.v1.url}/auth")
public class AuthController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IVerificationService verificationService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping(value = "/registration")
    public UserDto registration(@RequestBody UserDto userDto) {
        UserDto newUser = userService.registration(userDto);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(newUser.getEmail(), newUser.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        newUser.setPassword(null);
        return newUser;
    }

    @GetMapping("/verify/{token}")
    public void verifyUser(@PathVariable String token) {
        verificationService.verifyUser(token);
    }

    @GetMapping("/me")
    public UserDto getMe(Principal principal) {
        return userService.findUserByEmailWithRoles(principal.getName());
    }

}

