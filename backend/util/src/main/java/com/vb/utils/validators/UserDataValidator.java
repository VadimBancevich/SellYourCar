package com.vb.utils.validators;

import com.vb.api.dto.UserDto;
import com.vb.api.exceptions.ValidationException;

public class UserDataValidator {

    private UserDataValidator() {
    }

    private static final String VALIDATE_EMAIL_REGEX = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    private static final String VALIDATE_USERNAME_REGEX = "[a-zA-Z]{2,45}|[а-яА-Я]{2,45}";
    private static final String VALIDATE_USER_PASSWORD_REGEX = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]*)(?=.*[@#$%^&+=]*)(?=\\S+$).{6,20}";

    public static void validateUserData(UserDto userDto) {
        validateEmail(userDto.getEmail());
        validateUserPassword(userDto.getPassword());
        validateUserName(userDto.getName());
    }

    public static void validateEmail(String email) {
        if (email == null || !email.matches(VALIDATE_EMAIL_REGEX))
            throw new ValidationException("Not valid email");
    }

    public static void validateUserName(String userName) {
        if (userName == null || !userName.matches(VALIDATE_USERNAME_REGEX))
            throw new ValidationException("Not valid name");
    }

    public static void validateUserPassword(String password) {
        if (password == null || !password.matches(VALIDATE_USER_PASSWORD_REGEX))
            throw new ValidationException("Not valid password");
    }

    public static void equalPasswords(String password, String confirmPassword) {
        if (!password.equals(confirmPassword))
            throw new ValidationException("Passwords not equal");
    }

}