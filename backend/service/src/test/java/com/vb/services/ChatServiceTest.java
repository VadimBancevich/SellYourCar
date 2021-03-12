package com.vb.services;

import com.vb.api.dao.*;
import com.vb.api.service.IChatService;
import com.vb.api.service.IUserService;
import com.vb.api.view.ChatView;
import com.vb.entities.Car;
import com.vb.entities.Chat;
import com.vb.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.vb.ApplicationTest.existEntityId;
import static com.vb.ApplicationTest.notExistEntityId;
import static org.junit.Assert.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class ChatServiceTest {

    @MockBean
    private IChatDao chatDao;
    @MockBean
    private IUserDao userDao;
    @MockBean
    private IBrandDao brandDao;
    @MockBean
    private IModelDao modelDao;
    @MockBean
    private IGenerationDao generationDao;
    @MockBean
    private IUserService userService;

    @Autowired
    private IChatService chatService;

    @Test
    void findChatByIdTest() {
        when(chatDao.findById(existEntityId)).thenReturn(Optional.of(new Chat()));
        when(chatDao.findById(notExistEntityId)).thenReturn(Optional.empty());

        Chat testChat = chatService.findChatById(existEntityId);

        assertNotNull(testChat);
        assertThrows(EntityNotFoundException.class, () -> chatService.findChatById(notExistEntityId));
    }

    @Test
    void findChatWithUsersTest() {
        Chat chat = new Chat();
        chat.setUsers(Arrays.asList(new User(), new User()));

        when(chatDao.findChatWithUsers(existEntityId)).thenReturn(Optional.of(chat));
        when(chatDao.findChatWithUsers(notExistEntityId)).thenReturn(Optional.empty());

        Chat testChat = chatService.findChatWithUsers(existEntityId);

        assertTrue(testChat.getUsers().size() > 0);
        assertThrows(EntityNotFoundException.class, () -> chatService.findChatWithUsers(notExistEntityId));
    }

    @Test
    void findActualPrincipalChatsTest() {
        String email = "email";
        Pageable pageable = PageRequest.of(0, 10);

        when(chatDao.findPrincipalChatsSortedByLastMessageProj(email, pageable)).thenReturn(new ArrayList<>());

        List<ChatView> testChatViews = chatService.findActualPrincipalChats(0, 10);
        assertNotNull(testChatViews);
    }

    @Test
    void createNewChatTest() {
        List<User> users = Arrays.asList(new User(), new User());
        Car car = new Car();

        Chat chat = new Chat();
        chat.setUsers(users);
        chat.setCar(car);

        when(chatDao.save(any(Chat.class))).thenReturn(chat);

        Chat testChat = chatService.createNewChat(users, car);

        assertNotNull(testChat);
        assertNotNull(testChat.getCar());
        assertEquals(2, testChat.getUsers().size());
    }

    @Test
    void findPrincipalChatByIdTest() {
        String principalEmail = "email";
        String anotherEmail = "anotherEmail";

        when(chatDao.findByIdAndUsers_email(existEntityId, principalEmail)).thenReturn(Optional.of(new Chat()));
        when(chatDao.findByIdAndUsers_email(notExistEntityId, principalEmail)).thenReturn(Optional.empty());
        when(chatDao.findByIdAndUsers_email(existEntityId, anotherEmail)).thenReturn(Optional.empty());
        when(userService.getPrincipalEmail()).thenReturn(principalEmail);

        Chat testChat = chatService.findPrincipalChatById(existEntityId);

        assertNotNull(testChat);
        assertThrows(EntityNotFoundException.class, () -> chatService.findPrincipalChatById(notExistEntityId));

        when(userService.getPrincipalEmail()).thenReturn(anotherEmail);

        assertThrows(EntityNotFoundException.class, () -> chatService.findPrincipalChatById(existEntityId));
    }

}