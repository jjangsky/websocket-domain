package com.addict.jjangsky.chatservice.controllers;

import com.addict.jjangsky.chatservice.entities.Chatroom;
import com.addict.jjangsky.chatservice.service.ChatService;
import com.addict.jjangsky.chatservice.vos.CustomOAuth2User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/chats")
public class ChatController {

    private final ChatService chatService;

    @PostMapping
    public Chatroom createChatroom(@AuthenticationPrincipal CustomOAuth2User user,
            @RequestParam String title){
        return chatService.createChatroom(user.getMember(), title);
    }

    @PostMapping("/{chatroomId}")
    public Boolean joinChatroom(@AuthenticationPrincipal CustomOAuth2User user,
                                @PathVariable Long chatroomId){
        return chatService.joinChatroom(user.getMember(), chatroomId);
    }

    @DeleteMapping("/{chatroomId}")
    public Boolean leaveChatroom(@AuthenticationPrincipal CustomOAuth2User user,
                                 @PathVariable Long chatroomId){
        return chatService.leaveChatroom(user.getMember(), chatroomId);
    }

    @GetMapping
    public List<Chatroom> getChatroomList(@AuthenticationPrincipal CustomOAuth2User user){
        return chatService.getChatroomList(user.getMember());
    }
}
