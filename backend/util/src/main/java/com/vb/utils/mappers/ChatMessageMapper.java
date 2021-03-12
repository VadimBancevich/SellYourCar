package com.vb.utils.mappers;

import com.vb.api.dto.ChatMessageDto;
import com.vb.api.service.IChatService;
import com.vb.api.service.IUserService;
import com.vb.api.view.ChatMessageView;
import com.vb.entities.ChatMessage;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ChatMessageMapper extends AEntityDtoViewMapper<ChatMessage, ChatMessageDto, ChatMessageView> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IUserService userService;

    @Autowired
    private IChatService chatService;

    public ChatMessageMapper() {
        super(ChatMessage.class, ChatMessageDto.class, ChatMessageView.class);
    }

    @PostConstruct
    private void setupMap() {
        modelMapper.createTypeMap(ChatMessage.class, ChatMessageDto.class)
                .addMappings(mapping -> {
                    mapping.skip(ChatMessageDto::setChatId);
                    mapping.skip(ChatMessageDto::setFromUserId);
                    mapping.skip(ChatMessageDto::setToUserId);
                }).setPostConverter(toDtoConverter());
        modelMapper.createTypeMap(ChatMessageDto.class, ChatMessage.class)
                .addMappings(mapping -> mapping.skip(ChatMessage::setId))
                .setPostConverter(toEntityConverter());
        modelMapper.createTypeMap(ChatMessage.class, ChatMessageView.class)
                .addMappings(mapping -> {
                    mapping.skip(ChatMessageView::setChatId);
                    mapping.skip(ChatMessageView::setFromUserId);
                    mapping.skip(ChatMessageView::setToUserId);
                    mapping.skip(ChatMessageView::setFromUserName);
                    mapping.skip(ChatMessageView::setToUserName);
                })
                .setPostConverter(toViewConverter());
    }


    @Override
    void mapSpecificFields(ChatMessage source, ChatMessageView destination) {
        destination.setChatId(source.getChat().getId());
        destination.setFromUserId(source.getFromUser().getId());
        destination.setToUserId(source.getToUser().getId());
    }

    @Override
    public ChatMessageView toView(ChatMessage entity) {
        ChatMessageView messageView = super.toView(entity);
        mergeChatMessageDtoWithUserName(Collections.singletonList(messageView));
        return messageView;
    }

    @Override
    public List<ChatMessageView> toViewList(List<ChatMessage> entities) {
        List<ChatMessageView> messages = entities.stream().map(super::toView).collect(Collectors.toList());
        mergeChatMessageDtoWithUserName(messages);
        return messages;
    }

    private void mergeChatMessageDtoWithUserName(List<ChatMessageView> messages) {
        List<Long> usersIds = Arrays.asList(messages.get(0).getFromUserId(), messages.get(0).getToUserId());

        Map<Long, String> userIdNameMap = userService.findUsersIdsAndNamesByIds(usersIds);
        messages.forEach(chatMessageView -> {
            chatMessageView.setFromUserName(userIdNameMap.get(chatMessageView.getFromUserId()));
            chatMessageView.setToUserName(userIdNameMap.get(chatMessageView.getToUserId()));
        });
    }

    @Override
    void mapSpecificFields(ChatMessage source, ChatMessageDto destination) {
        destination.setChatId(source.getChat().getId());
        destination.setFromUserId(source.getFromUser().getId());
        destination.setToUserId(source.getToUser().getId());
    }

    @Override
    void mapSpecificFields(ChatMessageDto source, ChatMessage destination) {
        destination.setToUser(userService.findUserById(source.getToUserId()));
        destination.setFromUser(userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()));
        destination.setChat(chatService.findChatById(destination.getId()));
    }

}
