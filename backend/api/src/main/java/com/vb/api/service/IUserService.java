package com.vb.api.service;

import com.vb.api.dto.UserDto;
import com.vb.entities.User;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.util.List;
import java.util.Map;

public interface IUserService {

    UserDto registration(UserDto userDto);

    Long findUserIdByEmail(String email);

    User findByEmail(String email);

    User findUserByEmailOrCreateNew(OidcUser oidcUser);

    UserDto findUserByEmailWithRoles(String email);

    User findUserById(Long id);

    User findByCarId(Long carId);

    User findPrincipal();

    Map<Long, String> findUsersIdsAndNamesByIds(List<Long> usersIds);

    String getPrincipalEmail();

    boolean isAuthenticated();

}
