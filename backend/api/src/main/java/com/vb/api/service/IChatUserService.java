package com.vb.api.service;

import com.vb.entities.ChatUser;

public interface IChatUserService {

    ChatUser findPrincipalChatUserByChatId(Long chatId);

}
