package com.addict.jjangsky.chatservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Chatroom {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatroom_id")
    @Id
    Long id;

    String title;

    @OneToMany(mappedBy = "chatroom")
    Set<MemberChatroomMapping> memberChatroomMappingSet;

    LocalDateTime createdAt;

    /**
     * MemberChatroomMapping의 값으 null인 경우를 고려하여(채팅방에 사람이 없음)
     * 메소드를 만들어서 처리함(null값 방지용)
     */
    public MemberChatroomMapping addMember(Member member){
        if(this.getMemberChatroomMappingSet() == null){
            this.memberChatroomMappingSet = new HashSet<>();
        }

        MemberChatroomMapping memberChatroomMapping = MemberChatroomMapping.builder()
                .member(member)
                .chatroom(this)
                .build();

        this.memberChatroomMappingSet.add(memberChatroomMapping);

        return memberChatroomMapping;
    }
}
