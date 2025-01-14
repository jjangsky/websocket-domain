package com.addict.jjangsky.chatservice.entities;

import jakarta.persistence.*;

@Entity
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
}
