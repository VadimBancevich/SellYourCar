package com.vb.api.service;

import com.vb.entities.User;

public interface IVerificationService {

    void verifyUser(String tokenValue);

    String createToken(User user);

    void sendVerificationEmail(User user);

}
