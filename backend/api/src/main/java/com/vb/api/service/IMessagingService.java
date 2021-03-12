package com.vb.api.service;

import com.vb.api.view.ChatMessageView;
import org.springframework.lang.Nullable;

import java.util.Map;

public interface IMessagingService {

    void sendToUsers(ChatMessageView message, @Nullable Map<String, Object> headers);

    void sendToUsers(ChatMessageView message);

}
