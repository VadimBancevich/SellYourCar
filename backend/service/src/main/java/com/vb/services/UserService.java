package com.vb.services;

import com.vb.api.dao.IRoleDao;
import com.vb.api.dao.IUserDao;
import com.vb.api.dto.UserDto;
import com.vb.api.exceptions.UserException;
import com.vb.api.projections.UserIdAndName;
import com.vb.api.service.IUserService;
import com.vb.api.service.IVerificationService;
import com.vb.entities.Role;
import com.vb.entities.User;
import com.vb.utils.mappers.UserMapper;
import com.vb.utils.validators.UserDataValidator;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

@Service
@Transactional
public class UserService implements IUserService {

    @Autowired
    private IUserDao userDao;

    @Autowired
    private IRoleDao roleDao;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private IVerificationService verificationService;

    @Override
    public UserDto registration(UserDto userDto) {
        UserDataValidator.validateUserData(userDto);
        if (userDao.existsUserByEmail(userDto.getEmail()))
            throw new UserException("User with this email already exists");
        User user = userMapper.toEntity(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(new HashSet<>());
        user = userDao.save(user);
        UserDto newUser = userMapper.toDto(user);
        newUser.setPassword(userDto.getPassword());
        verificationService.sendVerificationEmail(user);
        return newUser;
    }

    @Override
    public Long findUserIdByEmail(String email) {
        return userDao.findUserIdByEmail(email).orElseThrow(() -> new UserException("User not found"));
    }

    @Override
    public User findUserByEmailOrCreateNew(OidcUser oidcUser) {
        return userDao.findUserByEmailWithRoles((oidcUser.getEmail())).orElseGet(() -> {
            User user = new User();
            user.setEmail(oidcUser.getEmail());
            user.setName(oidcUser.getFullName());
            user.setPassword(passwordEncoder.encode(RandomStringUtils.random(10, true, true)));
            user.setRoles(Collections.singleton(roleDao.findByRoleName("ROlE_USER")));
            return userDao.save(user);
        });
    }

    @Override
    public UserDto findUserByEmailWithRoles(String email) {
        User user = userDao.findUserByEmailWithRoles(email).orElseThrow(() -> new UserException("User not found"));
        UserDto userDto = userMapper.toDto(user);
        userDto.setRoles(user.getRoles().stream().map(Role::getRoleName).collect(Collectors.toList()));
        return userDto;
    }

    @Override
    public User findUserById(Long id) {
        return userDao.findById(id).orElseThrow(() -> new UserException("User with that id not found"));
    }

    @Override
    public User findByCarId(Long carId) {
        return userDao.findByCars_Id(carId)
                .orElseThrow(() -> new EntityNotFoundException("User not found by car id"));
    }

    @Override
    public User findByEmail(String email) {
        return userDao.findUserByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User with this email not found"));
    }

    @Override
    public User findPrincipal() {
        return findByEmail(getContext().getAuthentication().getName());
    }

    @Override
    public Map<Long, String> findUsersIdsAndNamesByIds(List<Long> usersIds) {
        return userDao.findIdAndNameByIdIn(usersIds).stream()
                .collect(Collectors.toMap(UserIdAndName::getId, UserIdAndName::getName));
    }

    @Override
    public String getPrincipalEmail() {
        if (!isAuthenticated()) {
            throw new UserException("User not authenticated");
        }
        return getContext().getAuthentication().getName();
    }

    @Override
    public boolean isAuthenticated() {
        return getContext().getAuthentication().isAuthenticated();
    }

}
