package com.addict.jjangsky.chatservice.repositories;

import com.addict.jjangsky.chatservice.entities.Chatroom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatroomRepository extends JpaRepository<Chatroom, Long> {
}
