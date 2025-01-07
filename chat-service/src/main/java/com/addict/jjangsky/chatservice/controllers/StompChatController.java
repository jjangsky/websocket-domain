package com.addict.jjangsky.chatservice.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
public class StompChatController {

    @MessageMapping("/chats") //  /pub/chats -> 발행으로 하면 `/chats` 으로 전달됨
    @SendTo("/sub/chats") // 구독자에게 메세지 전달
    public String handleMessage(@Payload String message){
        log.info("{}", message);
        return message;
    }
}
