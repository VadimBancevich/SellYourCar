package com.vb.services;

import com.vb.api.dao.IChatUserDao;
import com.vb.api.service.IChatUserService;
import com.vb.api.service.IUserService;
import com.vb.entities.ChatUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Service
@Transactional
public class ChatUserService implements IChatUserService {

    @Autowired
    private IChatUserDao chatUserDao;

    @Autowired
    private IUserService userService;

    @Override
    public ChatUser findPrincipalChatUserByChatId(Long chatId) {
        return chatUserDao.findByChatIdAndUserEmail(chatId, userService.getPrincipalEmail())
                .orElseThrow(() -> new EntityNotFoundException("No such entity"));
    }

}
