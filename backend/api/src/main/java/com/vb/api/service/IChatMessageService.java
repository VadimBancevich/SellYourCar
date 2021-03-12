package com.vb.api.service;


import com.vb.api.view.ChatMessageView;
import com.vb.entities.ChatMessage;

import java.util.List;

public interface IChatMessageService {

    ChatMessage saveMessage(Long chatId, String messageText);

    ChatMessage saveMessage(Long chatId, String messageText, String senderEmail);

    void saveAndSendMessage(Long chatId, String messageText, String senderEmail);

    void saveAndSendMessage(Long carId, String messageText);

    List<ChatMessageView> findByChatIdFromFirstUnread(Long chatId);

    void readMessageT(Long messageId, String principalEmail);

    List<ChatMessage> findByChatId(Long chatId, int skip, int size);

    List<ChatMessageView> findViewByChatId(Long chatId, int skip, int size);

}
