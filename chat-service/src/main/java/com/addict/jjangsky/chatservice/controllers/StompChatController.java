package com.addict.jjangsky.chatservice.controllers;

import com.addict.jjangsky.chatservice.dtos.ChatMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.Map;

@Controller
@Slf4j
public class StompChatController {

    @MessageMapping("/chats/{chatroomId}") //  /pub/chats -> 발행으로 하면 `/chats` 으로 전달됨
    @SendTo("/sub/chats/{chatroomId}") // 구독자에게 메세지 전달
    public ChatMessage handleMessage(@AuthenticationPrincipal Principal principal,
                                     @DestinationVariable Long chatroomId,
                                     @Payload Map<String, String> payload) {

        return new ChatMessage(principal.getName(), payload.get("message"));
    }
}
