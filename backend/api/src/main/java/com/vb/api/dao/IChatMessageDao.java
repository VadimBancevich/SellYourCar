package com.vb.api.dao;

import com.vb.entities.ChatMessage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IChatMessageDao extends JpaRepository<ChatMessage, Long> {

    @Query("select cm from ChatMessage cm where cm.chat.id = :chatId and cm.id >= (select min(cm.id) from ChatMessage cm where cm.chat.id = :chatId and cm.isRead = false and cm.toUser.id = :principalId)")
    List<ChatMessage> findFromFirstUnread(Long chatId, Long principalId);

    List<ChatMessage> findByChat_Id(Long chatId, Pageable pageable);

}
