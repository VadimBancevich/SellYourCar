package com.vb.api.dao;

import com.vb.entities.ChatUser;
import com.vb.entities.ChatUserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface IChatUserDao extends JpaRepository<ChatUser, ChatUserId> {

    @Query("select cu from ChatUser cu where cu.chat.id = :chatId and cu.user.email = :email")
    Optional<ChatUser> findByChatIdAndUserEmail(Long chatId, String email);

}
