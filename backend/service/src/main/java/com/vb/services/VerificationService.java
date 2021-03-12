package com.vb.services;

import com.vb.api.dao.IRoleDao;
import com.vb.api.dao.IUserDao;
import com.vb.api.dao.IVerificationTokenDao;
import com.vb.api.exceptions.VerificationException;
import com.vb.api.service.IVerificationService;
import com.vb.entities.User;
import com.vb.entities.VerificationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
public class VerificationService implements IVerificationService {

    @Autowired
    private IVerificationTokenDao verificationTokenDao;

    @Autowired
    private IUserDao userDao;

    @Autowired
    private IRoleDao roleDao;

    @Autowired
    private JavaMailSender mailSender;

    @Value("${frontend.url}")
    private String frontendUrl;

    @Override
    public String createToken(User user) {
        VerificationToken token = new VerificationToken();
        token.setExpiryDate(LocalDateTime.now().plusDays(1));
        token.setUser(user);
        token.setTokenValue(UUID.randomUUID().toString());
        verificationTokenDao.save(token);
        return token.getTokenValue();
    }

    @Override
    public void verifyUser(String token) {
        VerificationToken verificationToken = verificationTokenDao.findByTokenValue(token)
                .orElseThrow(() -> new VerificationException("Token with that value not found"));
        if (verificationToken.getExpiryDate().isAfter(LocalDateTime.now())) {
            User user = verificationToken.getUser();
            user.getRoles().add(roleDao.findByRoleName("ROLE_USER"));
            userDao.save(user);
            verificationTokenDao.delete(verificationToken);
        } else {
            throw new VerificationException("You link is expired");
        }
    }

    @Async
    @Override
    public void sendVerificationEmail(User user) {
        String verificationToken = createToken(user);
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, false, "utf-8");
            message.setSubject("SellYourCar - Confirmation");
            message.setContent("Please, confirm your account, click on link below<br>"
                    + "<a href=\"" + frontendUrl + "/verify/" + verificationToken + "\">Click to confirm</a>", "text/html");
            messageHelper.setTo(user.getEmail());
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new VerificationException("Error sending verification email");
        }
    }

}
