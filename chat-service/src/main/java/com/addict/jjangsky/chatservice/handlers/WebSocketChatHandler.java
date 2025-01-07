package com.addict.jjangsky.chatservice.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class WebSocketChatHandler extends TextWebSocketHandler {
    /**
     * 원래는 WebScoketHandler를 구현해야 하지만 재정의할 메소드가 많아서
     * 스프링에서 제공하는 TextWebSocketHandler 사용
     */

    // 연결되어 있는 클라이언트 확인 Map
    final Map<String, WebSocketSession> webScoketSessionMap = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 웹소켓 클라이언트가 서버로 연결한 이후 동작
        log.info("{}", session.getId());

        // 세션 연결
        this.webScoketSessionMap.put(session.getId(), session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        // 메세지가 왔을 때 처리 로직
        log.info("{}, {}", session.getId(), message.getPayload());

        this.webScoketSessionMap.values().forEach(webSocketSession -> {
            try {
                webSocketSession.sendMessage(message);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // 서버에 접속해 있던 웹소켓 클라이언트가 연결을 끊었을 때
        log.info("{}",session.getId());

        this.webScoketSessionMap.remove(session.getId());
    }
}
