package com.vb.services;

import com.vb.api.service.IMessagingService;
import com.vb.api.view.ChatMessageView;
import com.vb.utils.mappers.ChatMessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MessagingService implements IMessagingService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ChatMessageMapper chatMessageMapper;

    @Override
    public void sendToUsers(ChatMessageView message, Map<String, Object> headers) {
        messagingTemplate.convertAndSend("user/" + message.getFromUserId() + "/messages", message, headers);
        messagingTemplate.convertAndSend("user/" + message.getToUserId() + "/messages", message, headers);
    }

    @Override
    public void sendToUsers(ChatMessageView message) {
        sendToUsers(message, null);
    }

}
