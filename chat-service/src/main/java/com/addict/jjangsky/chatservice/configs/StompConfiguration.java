package com.addict.jjangsky.chatservice.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@EnableWebSocketMessageBroker
@Configuration
public class StompConfiguration implements WebSocketMessageBrokerConfigurer {
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 서버가 어떠한 경로로 접근하는지 엔드포인트 지정
        registry.addEndpoint("/stomp/chats");
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 메세지 브로커의 역할을 하기위해 클라이언트에서 메세지 발행,
        // 클라이언트는 브로커로부터 전달되는 메시지를 받기 위해
        // 구독을 신청해야 하는데 해당 경로를 설정해 주는 곳

        registry.setApplicationDestinationPrefixes("/pub"); // 메세지 발행
        registry.enableSimpleBroker("/sub"); // 메세지 구독
    }
}
