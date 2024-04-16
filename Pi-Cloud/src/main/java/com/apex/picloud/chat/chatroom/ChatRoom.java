package com.apex.picloud.chat.chatroom;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ChatRoom")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String chatId;
    private String senderId;
    private String recipientId;

}
