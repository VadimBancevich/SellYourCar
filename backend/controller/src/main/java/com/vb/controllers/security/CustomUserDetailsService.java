package com.vb.controllers.security;

import com.vb.api.dao.IUserDao;
import com.vb.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private IUserDao userDao;

    @Override
    @Transactional
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String email) {
        User userInDB = userDao.findUserByEmailWithRoles(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with email " + email + " not found"));

        return org.springframework.security.core.userdetails.User.builder()
                .username(userInDB.getEmail())
                .password(userInDB.getPassword())
                .authorities(userInDB.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                        .collect(Collectors.toList()))
                .build();
    }

}
