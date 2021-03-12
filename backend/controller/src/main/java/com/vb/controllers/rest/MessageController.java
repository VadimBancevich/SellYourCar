package com.vb.controllers.rest;

import com.vb.api.service.IChatMessageService;
import com.vb.api.view.ChatMessageView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.v1.url}/messages")
public class MessageController {

    @Autowired
    private IChatMessageService chatMessageService;

    @PostMapping("/send")
    public void saveMessage(@RequestParam Long carId, @RequestParam String messageText) {
        chatMessageService.saveAndSendMessage(carId, messageText);
    }

    @GetMapping
    public List<ChatMessageView> getMessagesByChat(@RequestParam Long chatId,
                                                   @RequestParam(defaultValue = "0") Integer skip,
                                                   @RequestParam(defaultValue = "20") Integer size) {
        return chatMessageService.findViewByChatId(chatId, skip, size);
    }

}
