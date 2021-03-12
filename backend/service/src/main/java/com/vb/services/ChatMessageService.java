package com.vb.services;

import com.vb.api.dao.IChatMessageDao;
import com.vb.api.exceptions.ChatMessageException;
import com.vb.api.service.*;
import com.vb.api.view.ChatMessageView;
import com.vb.entities.*;
import com.vb.services.pagination.LimitOffsetPage;
import com.vb.utils.mappers.ChatMessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

@Service
@Transactional
public class ChatMessageService implements IChatMessageService {

    @Autowired
    private IChatMessageDao chatMessageDao;
    @Autowired
    private ChatMessageMapper chatMessageMapper;
    @Autowired
    private ICarService carService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IChatService chatService;
    @Autowired
    private IMessagingService messagingService;
    @Autowired
    private IChatUserService chatUserService;

    @Override
    public ChatMessage saveMessage(Long chatId, String messageText) {
        if (messageText != null && messageText.isEmpty()) {
            throw new ChatMessageException("Message content can`t be empty");
        }
        Chat chat = chatService.findPrincipalChatById(chatId);
        List<User> users = chat.getUsers();
        String fromUserEmail = getContext().getAuthentication().getName();
        Optional<User> fromUser = users.stream().filter(user -> user.getEmail().equals(fromUserEmail)).findFirst();
        Optional<User> toUser = users.stream().filter(user -> !user.getEmail().equals(fromUserEmail)).findFirst();
        if (!fromUser.isPresent() || !toUser.isPresent()) {
            throw new EntityNotFoundException("asd");
        }
        ChatMessage message = new ChatMessage();
        message.setFromUser(fromUser.get());
        message.setToUser(toUser.get());
        message.setChat(chat);
        message.setContent(messageText);
        message.setDateTime(LocalDateTime.now(ZoneId.of(ZoneOffset.UTC.getId())));
        return chatMessageDao.save(message);
    }

    @Override
    public ChatMessage saveMessage(Long chatId, String messageText, String senderEmail) {
        Chat chat = chatService.findChatWithUsers(chatId);
        Optional<User> sender = chat.getUsers().stream().filter(user -> user.getEmail().equals(senderEmail)).findFirst();
        Optional<User> recipient = chat.getUsers().stream().filter(user -> !user.getEmail().equals(senderEmail)).findFirst();

        if (!sender.isPresent() || !recipient.isPresent()) {
            throw new EntityNotFoundException("Chat has not user(s)");
        }

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setContent(messageText);
        chatMessage.setDateTime(LocalDateTime.now(ZoneId.of(ZoneOffset.UTC.getId())));
        chatMessage.setChat(chat);
        chatMessage.setFromUser(sender.get());
        chatMessage.setToUser(recipient.get());
        chatMessage.setRead(false);
        return chatMessageDao.save(chatMessage);
    }

    @Override
    public void saveAndSendMessage(Long chatId, String messageText, String senderEmail) {
        ChatMessage chatMessage = saveMessage(chatId, messageText, senderEmail);
        messagingService.sendToUsers(chatMessageMapper.toView(chatMessage));
    }

    @Override
    public void saveAndSendMessage(Long carId, String messageText) {
        User toUser = userService.findByCarId(carId);
        User fromUser = userService.findPrincipal();
        Chat chat = chatService.findByUserIds(toUser.getId(), fromUser.getId(), carId)
                .orElseGet(() -> chatService.createNewChat(Arrays.asList(toUser, fromUser), carService.findById(carId)));
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setToUser(toUser);
        chatMessage.setFromUser(fromUser);
        chatMessage.setChat(chat);
        chatMessage.setContent(messageText);
        ChatMessageView messageView = chatMessageMapper.toView(chatMessageDao.save(chatMessage));
        messagingService.sendToUsers(messageView);
    }

    public void readMessageT(Long messageId, String principalEmail) {
        ChatMessage chatMessage = chatMessageDao.findById(messageId).orElseThrow(() -> new EntityNotFoundException(""));
        if (chatMessage.getToUser().getEmail().equals(principalEmail)) {
            chatMessage.setRead(true);
            ChatMessage save = chatMessageDao.save(chatMessage);
            messagingService.sendToUsers(chatMessageMapper.toView(save), Collections.singletonMap("updated", true));
        } else {
            throw new UnsupportedOperationException("Only recipient can read message");
        }
    }

    @Override
    public List<ChatMessage> findByChatId(Long chatId, int skip, int size) {
        if (size < 0 || size > 20) {
            size = 20;
        }
        LimitOffsetPage of = LimitOffsetPage.of(size, skip, Sort.by(AEntity_.ID).descending());
        chatService.findPrincipalChatById(chatId);
        return chatMessageDao.findByChat_Id(chatId, of);
    }

    @Override
    public List<ChatMessageView> findViewByChatId(Long chatId, int skip, int size) {
        List<ChatMessage> views = findByChatId(chatId, skip, size);
        return views.isEmpty() ? Collections.emptyList() : chatMessageMapper.toViewList(views);
    }

    @Override
    public List<ChatMessageView> findByChatIdFromFirstUnread(Long chatId) {
        ChatUser chatUser = chatUserService.findPrincipalChatUserByChatId(chatId);
        if (chatUser.getUnreadCount() > 0) {
            Long userId = userService.findUserIdByEmail(userService.getPrincipalEmail());
            return chatMessageMapper.toViewList(chatMessageDao.findFromFirstUnread(chatId, userId));
        } else {
            return chatMessageMapper.toViewList(findByChatId(chatId, 0, 20));
        }
    }

}
