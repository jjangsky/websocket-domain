package com.addict.jjangsky.chatservice.repositories;

import com.addict.jjangsky.chatservice.entities.MemberChatroomMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberChatroomMappingRepository extends JpaRepository<MemberChatroomMapping, Long> {
    boolean existsByMemberIdAndChatroomId(Long id, Long chatroomId);

    void deleteByMemberIdAndChatroomId(Long id, Long chatroomId);

    List<MemberChatroomMapping> findAllByMemberId(Long id);
}
