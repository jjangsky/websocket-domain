package com.addict.jjangsky.chatservice.repositories;

import com.addict.jjangsky.chatservice.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByChatroomId(Long chatroomId);

    Boolean existsByChatroomIdAndCreatedAtAfter(Long id, LocalDateTime lastCheckedAt);
}
