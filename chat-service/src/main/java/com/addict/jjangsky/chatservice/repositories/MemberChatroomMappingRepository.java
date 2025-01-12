package com.addict.jjangsky.chatservice.repositories;

import com.addict.jjangsky.chatservice.entities.MemberChatroomMapping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberChatroomMappingRepository extends JpaRepository<MemberChatroomMapping, Long> {
}
