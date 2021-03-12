package com.vb.controllers.rest;

import com.vb.api.dto.ChatMessageDto;
import com.vb.api.service.IChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class MessagingController {

    @Autowired
    private IChatMessageService chatMessageService;

    @MessageMapping("/chat")
    public void saveAndSendMessage(ChatMessageDto messageDto, Principal principal) {
        chatMessageService.saveAndSendMessage(messageDto.getChatId(), messageDto.getContent(), principal.getName());
    }

    @MessageMapping("/read")
    public void readMessage(Long messageId, Principal principal) {
        chatMessageService.readMessageT(messageId, principal.getName());
    }

}
