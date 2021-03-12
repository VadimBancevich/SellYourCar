package com.vb.api.dao;

import com.vb.entities.Car;
import com.vb.entities.Chat;
import com.vb.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@DataJpaTest
class IChatDaoTest {

    @Autowired
    private IChatDao chatDao;

    @Autowired
    private TestEntityManager em;

    @Autowired
    private RandomEntityFactory ref;

    @Test
    void findByUserIdsTest() {
        List<User> users = ref.createRandomEntity(User.class, 2);
        Car car = ref.createRandomEntity(Car.class);
        Chat chat = ref.createRandomEntity(Chat.class, false);
        chat.setUsers(users);
        chat.setCar(car);
        em.persistAndFlush(chat);
        Optional<Chat> testChat = chatDao.findByUserIds(users.get(0).getId(), users.get(1).getId(), car.getId());
        assertTrue(testChat.isPresent());
    }

    @Test
    void findByIdAndUsers_emailTest() {
        List<User> users = ref.createRandomEntity(User.class, 2);
        Chat chat = ref.createRandomEntity(Chat.class, false);
        chat.setUsers(users);
        chat.setCar(ref.createRandomEntity(Car.class));
        String userEmail = users.get(0).getEmail();
        em.persistAndFlush(chat);
        Optional<Chat> testChat = chatDao.findByIdAndUsers_email(chat.getId(), userEmail);
        assertTrue(testChat.isPresent());
    }

    @Test
    void findPrincipalChatsSortedByLastMessageProjTest() {
        //Not testable. H2 database cannot execute function JSON_EXTRACT
    }

    @Test
    void findChatWithUsersTest() {
        Chat chat = ref.createRandomEntity(Chat.class, false);
        chat.setCar(ref.createRandomEntity(Car.class));
        chat.setUsers(ref.createRandomEntity(User.class, 2));
        em.persistAndFlush(chat);

        Optional<Chat> testChat = chatDao.findChatWithUsers(chat.getId());
        assertTrue(testChat.isPresent());
        em.detach(testChat.get());
        assertNotNull(testChat.get().getUsers());
        assertFalse(testChat.get().getUsers().isEmpty());
    }

}