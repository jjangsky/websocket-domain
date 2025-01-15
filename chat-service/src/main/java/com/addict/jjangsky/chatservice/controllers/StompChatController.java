package com.addict.jjangsky.chatservice.controllers;

import com.addict.jjangsky.chatservice.dtos.ChatMessage;
import com.addict.jjangsky.chatservice.entities.Message;
import com.addict.jjangsky.chatservice.service.ChatService;
import com.addict.jjangsky.chatservice.vos.CustomOAuth2User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class StompChatController {

    private final ChatService chatService;

    // 서버에서 Stomp Message를 발행할 수 잇음
    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/chats/{chatroomId}") //  /pub/chats -> 발행으로 하면 `/chats` 으로 전달됨
    @SendTo("/sub/chats/{chatroomId}") // 구독자에게 메세지 전달
    public ChatMessage handleMessage(@AuthenticationPrincipal Principal principal,
                                     @DestinationVariable Long chatroomId,
                                     @Payload Map<String, String> payload) {

        CustomOAuth2User user = (CustomOAuth2User) ((OAuth2AuthenticationToken) principal).getPrincipal();
        Message message = chatService.saveMessage(user.getMember(), chatroomId, payload.get("message"));

        // 새로운 메세지가 발행된 채팅방 아이디 전달
        simpMessagingTemplate.convertAndSend("/sub/chats/news", chatroomId);
        return new ChatMessage(principal.getName(), payload.get("message"));
    }
}
