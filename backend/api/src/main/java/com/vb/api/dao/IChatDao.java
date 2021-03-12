package com.vb.api.dao;

import com.vb.api.projections.ChatViewProjection;
import com.vb.api.view.ChatView;
import com.vb.entities.Chat;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IChatDao extends JpaRepository<Chat, Long> {

    String CHAT_VIEW_CONSTRUCTOR = "new com.vb.api.view.ChatView(c.id, car.id, b.brandName, m.modelName, g.generationName, car.images, lm.content, fu.name)";

    @Query("select c from Chat c join c.users u on u.id = :firstUserId join c.users uu on uu.id = :secondUserId where c.car.id = :carId")
    Optional<Chat> findByUserIds(Long firstUserId, Long secondUserId, Long carId);

    Optional<Chat> findByIdAndUsers_email(Long chatId, String email);

    @Query("select c.id as id, car.id as carId, car.brand.id as brandId, car.model.id as modelId, car.generation.id as generationId, function('json_extract_string',car.images, '$[0]') as firstCarImg, lm.content as lastMessageContent, fu.name as lastMessageFromUserName, cu.unreadCount as unreadCount from User u join u.chatUser cu join cu.chat c join c.lastChatMessage lm join lm.fromUser fu join c.car car where u.email = :email order by lm.id desc")
    List<ChatViewProjection> findPrincipalChatsSortedByLastMessageProj(String email, Pageable pageable);

    @Query("select " + CHAT_VIEW_CONSTRUCTOR + " from Chat c " +
            " join c.users u on u.email = :email " +
            " left join c.lastChatMessage lm " +
            " join lm.fromUser fu " +
            " join c.car car join car.brand b join car.model m join car.generation g " +
            " where c.id = :chatId ")
    ChatView findPrincipalChatById(String email, Long chatId);

    @Query("select c from Chat c join fetch c.users where c.id = :chatId")
    Optional<Chat> findChatWithUsers(Long chatId);

}
