package com.vb.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "chat")
public class Chat extends AEntity {

    @ManyToMany
    @JoinTable(name = "chat_user",
            joinColumns = @JoinColumn(name = "chat_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users;

    @OneToMany(mappedBy = "chat")
    private List<ChatMessage> messages;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id", referencedColumnName = "id", unique = true)
    private Car car;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "last_message_id", referencedColumnName = "id")
    private ChatMessage lastChatMessage;

    @OneToMany(mappedBy = "chat")
    private List<ChatUser> chatUser;

}