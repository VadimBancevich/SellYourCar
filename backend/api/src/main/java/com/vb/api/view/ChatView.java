package com.vb.api.view;

import lombok.*;

import java.util.List;
import java.util.Map;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ChatView extends AView {

    private Long carId;
    private String chatName;
    private String imageUrl;
    private String lastMessageContent;
    private String lastMessageFromUserName;
    private Integer unreadCount;
    private Map<Long, String> userIdName;

    public ChatView(Long id, Long carId, String brandName, String modelName,
                    String generationName, String imageUrl,
                    String lastMessageContent,
                    String lastMessageFromUserName) {
        setId(id);
        this.carId = carId;
        this.chatName = String.format("%s %s %s", brandName, modelName, generationName);
        this.imageUrl = imageUrl;
        this.lastMessageContent = lastMessageContent;
        this.lastMessageFromUserName = lastMessageFromUserName;
    }

    public ChatView(Long id, Long carId, String brandName, String modelName, String generationName, Object imagesList,
                    String lastMessageContent, String lastMessageFromUserName) {
        setId(id);
        this.carId = carId;
        this.chatName = String.format("%s %s %s", brandName, modelName, generationName);
        List<String> images = (List<String>) imagesList;
        this.imageUrl = images.isEmpty() ? null : images.get(0);
        this.lastMessageContent = lastMessageContent;
        this.lastMessageFromUserName = lastMessageFromUserName;
    }

}
