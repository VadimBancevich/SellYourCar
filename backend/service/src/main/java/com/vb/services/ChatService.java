package com.vb.services;

import com.vb.api.dao.IChatDao;
import com.vb.api.projections.ChatViewProjection;
import com.vb.api.service.IChatService;
import com.vb.api.service.IUserService;
import com.vb.api.view.ChatView;
import com.vb.entities.Car;
import com.vb.entities.Chat;
import com.vb.entities.User;
import com.vb.services.pagination.LimitOffsetPage;
import com.vb.utils.mappers.ChatMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ChatService implements IChatService {

    @Autowired
    private IChatDao chatDao;

    @Autowired
    private IUserService userService;

    @Autowired
    private ChatMapper chatMapper;

    @Override
    public Chat findChatById(Long id) {
        return chatDao.findById(id).orElseThrow(() -> new EntityNotFoundException("No such chat"));
    }

    @Override
    public Chat findChatWithUsers(Long id) {
        return chatDao.findChatWithUsers(id).orElseThrow(() -> new EntityNotFoundException("No such chat"));
    }

    @Override
    public List<ChatView> findActualPrincipalChats(int skip, int size) {
        if (skip < 0) {
            skip = 0;
        }
        if (size < 1 || size > 10) {
            size = 10;
        }

        List<ChatViewProjection> proj =
                chatDao.findPrincipalChatsSortedByLastMessageProj(userService.getPrincipalEmail(), LimitOffsetPage.of(size, skip));
        return chatMapper.toViews(proj);
    }

    @Override
    public Chat createNewChat(List<User> users, Car car) {
        Chat chat = new Chat();
        chat.setUsers(users);
        chat.setCar(car);
        return chatDao.save(chat);
    }

    @Override
    public Optional<Chat> findByUserIds(Long firstUserId, Long secondUserId, Long carId) {
        return chatDao.findByUserIds(firstUserId, secondUserId, carId);
    }

    @Override
    public Chat findPrincipalChatById(Long chatId) {
        return chatDao.findByIdAndUsers_email(chatId, userService.getPrincipalEmail())
                .orElseThrow(() -> new EntityNotFoundException("No chat for this user"));
    }

    @Override
    public ChatView findPrincipalChatViewById(Long chatId) {
        return chatDao.findPrincipalChatById(userService.getPrincipalEmail(), chatId);
    }

}
