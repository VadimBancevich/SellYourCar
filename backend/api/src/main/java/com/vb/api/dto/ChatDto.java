package com.vb.api.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ChatDto extends ADto {

    private List<Long> usersIds;
    private ChatMessageDto chatMessageDto;
    private CarDto carDto;
    private Integer unreadCount;

}
