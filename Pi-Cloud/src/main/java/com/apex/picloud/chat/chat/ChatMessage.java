package com.apex.picloud.chat.chat;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "ChatMessage")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String chatId;
    private String senderId;
    private String recipientId;
    private String content;
    private Date timestamp;
}
