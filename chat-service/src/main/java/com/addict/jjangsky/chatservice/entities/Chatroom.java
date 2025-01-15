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
     * 테이블에 저장되는 속성은 아님(클래스에만 저장)
     * DB에 수시로 정보를 저장하고 확인하기 무리한 경우
     */
    @Transient
    Boolean hasNewMessage;

    public void setHasNewMessage(Boolean hasNewMessage){
        this.hasNewMessage = hasNewMessage;
    }


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
