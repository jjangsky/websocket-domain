package com.addict.jjangsky.chatservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    @Id
    Long id;

    String text;
    @JoinColumn(name="member_id")
    @ManyToOne
    Member member;

    @JoinColumn(name="chatroom_id")
    @ManyToOne
    Chatroom chatroom;

    LocalDateTime createdAt;
}
