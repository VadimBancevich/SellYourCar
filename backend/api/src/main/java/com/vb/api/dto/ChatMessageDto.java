package com.vb.api.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class ChatMessageDto extends ADto {

    private String content;
    private Long fromUserId;
    private Long toUserId;
    private Long chatId;
    private LocalDateTime dateTime;
    private boolean isRead;

}
