package com.vb.api.service;

import com.vb.api.view.ChatView;
import com.vb.entities.Car;
import com.vb.entities.Chat;
import com.vb.entities.User;

import java.util.List;
import java.util.Optional;

public interface IChatService {

    Chat findChatById(Long id);

    Chat findChatWithUsers(Long id);

    List<ChatView> findActualPrincipalChats(int skip, int size);

    Optional<Chat> findByUserIds(Long firstUserId, Long secondUserId, Long carId);

    Chat findPrincipalChatById(Long chatId);

    ChatView findPrincipalChatViewById(Long chatId);

    Chat createNewChat(List<User> users, Car car);

}
