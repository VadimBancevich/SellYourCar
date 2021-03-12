package com.vb.controllers.rest;


import com.vb.api.service.IChatService;
import com.vb.api.view.ChatView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.v1.url}/chats")
public class ChatController {

    @Autowired
    private IChatService chatService;

    @GetMapping
    public List<ChatView> getChats(@RequestParam(required = false, defaultValue = "0") Integer skip,
                                   @RequestParam(required = false, defaultValue = "10") Integer size) {
        return chatService.findActualPrincipalChats(skip, size);
    }

    @GetMapping("/{id}")
    public ChatView getChatById(@PathVariable Long id) {
        return chatService.findPrincipalChatViewById(id);
    }

}
