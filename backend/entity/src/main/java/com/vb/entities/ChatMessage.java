package com.vb.entities;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Getter
@Setter
@Entity
@Table(name = "message")
public class ChatMessage extends AEntity {

    @Column(name = "content")
    private String content;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @Column(name = "is_read")
    private boolean isRead;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id", referencedColumnName = "id")
    private Chat chat;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_user_id", referencedColumnName = "id")
    private User fromUser;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_user_id", referencedColumnName = "id")
    private User toUser;

    public ChatMessage() {
        this.dateTime = LocalDateTime.now(ZoneOffset.UTC);
    }

}
