package com.vb.utils.mappers;

import com.vb.api.dto.ChatDto;
import com.vb.api.projections.ChatViewProjection;
import com.vb.api.service.IBrandService;
import com.vb.api.service.IGenerationService;
import com.vb.api.service.IModelService;
import com.vb.api.view.ChatView;
import com.vb.entities.AEntity;
import com.vb.entities.Chat;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.PersistenceException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ChatMapper extends AEntityDtoViewMapper<Chat, ChatDto, ChatView> {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private IBrandService brandService;
    @Autowired
    private IModelService modelService;
    @Autowired
    private IGenerationService generationService;
    @Autowired
    private ChatMessageMapper chatMessageMapper;
    @Autowired
    private CarMapper carMapper;

    public ChatMapper() {
        super(Chat.class, ChatDto.class, ChatView.class);
    }

    @PostConstruct
    private void setupMap() {
        modelMapper.createTypeMap(Chat.class, ChatDto.class).addMappings(mapping -> {
            mapping.skip(ChatDto::setUsersIds);
            mapping.skip(ChatDto::setChatMessageDto);
            mapping.skip((ChatDto::setCarDto));
        }).setPostConverter(toDtoConverter());
        modelMapper.createTypeMap(ChatDto.class, Chat.class).addMappings(mapping -> {
            mapping.skip(Chat::setUsers);
            mapping.skip(Chat::setMessages);
            mapping.skip(Chat::setCar);
        }).setPostConverter(toEntityConverter());
        modelMapper.createTypeMap(ChatViewProjection.class, ChatView.class)
                .setPostConverter(toViewConverterFromProjection());
    }


    public ChatView toView(ChatViewProjection projection) {
        return modelMapper.map(projection, ChatView.class);
    }

    public List<ChatView> toViews(List<ChatViewProjection> projections) {
        return projections.stream().map(this::toView).collect(Collectors.toList());
    }

    private Converter<ChatViewProjection, ChatView> toViewConverterFromProjection() {
        return context -> {
            ChatViewProjection source = context.getSource();
            ChatView destination = context.getDestination();
            String brandName = brandService.findDtoById(source.getBrandId()).getBrandName();
            String modelName = modelService.findDtoById(source.getModelId()).getModelName();
            String generationName = generationService.findDtoById(source.getGenerationId()).getGenerationName();
            destination.setChatName(String.format("%s %s %s", brandName, modelName, generationName));
            if (source.getFirstCarImg() != null) {
                destination.setImageUrl(source.getFirstCarImg().replace("\"", ""));
            }
            return destination;
        };
    }

    public ChatView mapSpecificFields(ChatViewProjection proj) {
        String brandName = brandService.findDtoById(proj.getBrandId()).getBrandName();
        String model = modelService.findDtoById(proj.getModelId()).getModelName();
        String generation = generationService.findDtoById(proj.getGenerationId()).getGenerationName();
        String chatName = String.format("%s %s %s", brandName, model, generation);
        return ChatView.builder()
                .carId(proj.getCarId()).chatName(chatName).imageUrl(proj.getFirstCarImg())
                .lastMessageContent(proj.getLastMessageContent())
                .lastMessageFromUserName(proj.getLastMessageFromUserName())
                .unreadCount(proj.getUnreadCount()).build();
    }

    @Override
    void mapSpecificFields(Chat source, ChatView destination) {
        destination.setLastMessageContent(source.getLastChatMessage().getContent());
        destination.setImageUrl(source.getCar().getImages().get(0));
        destination.setLastMessageFromUserName(source.getLastChatMessage().getFromUser().getName());
    }

    @Override
    void mapSpecificFields(Chat source, ChatDto destination) {
        try {
            destination.setUsersIds(
                    source.getUsers().stream().map(AEntity::getId).collect(Collectors.toList()));
        } catch (PersistenceException e) {
            destination.setUsersIds(null);
        }
        try {
            destination.setChatMessageDto(chatMessageMapper.toDto(
                    source.getMessages().stream().findFirst().orElse(null)));
        } catch (PersistenceException e) {
            destination.setChatMessageDto(null);
        }
        try {
            destination.setCarDto(carMapper.toDto(source.getCar()));
        } catch (PersistenceException e) {
            destination.setCarDto(null);
        }
    }

}
