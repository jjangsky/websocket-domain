package com.addict.jjangsky.chatservice.service;

import com.addict.jjangsky.chatservice.entities.Chatroom;
import com.addict.jjangsky.chatservice.entities.Member;
import com.addict.jjangsky.chatservice.entities.MemberChatroomMapping;
import com.addict.jjangsky.chatservice.entities.Message;
import com.addict.jjangsky.chatservice.repositories.ChatroomRepository;
import com.addict.jjangsky.chatservice.repositories.MemberChatroomMappingRepository;
import com.addict.jjangsky.chatservice.repositories.MessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatService {

    private final ChatroomRepository chatroomRepository;
    private final MemberChatroomMappingRepository memberChatroomMappingRepository;
    private final MessageRepository messageRepository;


    /**
     * 채팅방을 생성하는 서비스
     */
    @Transactional
    public Chatroom createChatroom(Member member, String title){
        // 채팅방 생성
        Chatroom chatroom = Chatroom.builder()
                .title(title)
                .createdAt(LocalDateTime.now())
                .build();

        chatroom = chatroomRepository.save(chatroom);

        // 만든 사람이 채팅방 참여
        MemberChatroomMapping memberChatroomMapping = chatroom.addMember(member);

        memberChatroomMapping = memberChatroomMappingRepository.save(memberChatroomMapping);

        return chatroom;
    }

    /**
     * 채팅방 참여 서비스
     */
    @Transactional
    public Boolean joinChatroom(Member member, Long newChatroomId, Long currentChatroomId){
        if(currentChatroomId != null){
            // 방을 옮겨가는 과정인 경우
            updateLastCheckedAt(member, currentChatroomId);
        }

        // 먼저 채팅방에 존재하는지
        if(memberChatroomMappingRepository.existsByMemberIdAndChatroomId(member.getId(), newChatroomId)){
            log.info("이미 참여한 채팅방 입니다.");
            return false;
        }
        Chatroom chatroom = chatroomRepository.findById(newChatroomId).get();


        MemberChatroomMapping memberChatroomMapping = MemberChatroomMapping.builder()
                .member(member)
                .chatroom(chatroom)
                .build();

        memberChatroomMapping = memberChatroomMappingRepository.save(memberChatroomMapping);

        return true;
    }

    private void updateLastCheckedAt(Member member, Long currentChatroomId) {
        MemberChatroomMapping memberChatroomMapping
                = memberChatroomMappingRepository.findAllByMemberIdAndChatroomId(member.getId(), currentChatroomId);
        memberChatroomMapping.updateLastCheckedAt();

        memberChatroomMappingRepository.save(memberChatroomMapping);
    }

    /**
     * 채팅방 나가는 서비스
     */
    @Transactional
    public Boolean leaveChatroom(Member member, Long chatroomId){
        if(memberChatroomMappingRepository.existsByMemberIdAndChatroomId(member.getId(), chatroomId)){
            log.info("참여하지 않은 방입니다.");
            return false;
        }

        memberChatroomMappingRepository.deleteByMemberIdAndChatroomId(member.getId(), chatroomId);

        return true;
    }

    /**
     * 채팅방 목록 조회 서비스
     */
    public List<Chatroom> getChatroomList(Member member){
        List<MemberChatroomMapping> memberChatroomMappingList
                = memberChatroomMappingRepository.findAllByMemberId(member.getId());

        /**
         * 채팅방 조회 시 신규 메세지 알림 여부를 적용해서 조회 처리
         * -> 메시지가 생성 된 것이 어떤 특정 일자(마지막으로 메세지 확인)를 넘었을 때 `hasNewMessage` 값 처리
         */
        return memberChatroomMappingList.stream()
                .map(memberChatroomMapping -> {
                    Chatroom chatroom = memberChatroomMapping.getChatroom();
                    chatroom.setHasNewMessage
                            (messageRepository.existsByChatroomIdAndCreatedAtAfter(chatroom.getId(), memberChatroomMapping.getLastCheckedAt()));
                    return chatroom;
                })
                .toList();
    }

    public Message saveMessage(Member member, Long chatroomId, String text){
        Chatroom chatroom = chatroomRepository.findById(chatroomId).get();

        Message message = Message.builder()
                .text(text)
                .member(member)
                .chatroom(chatroom)
                .createdAt(LocalDateTime.now())
                .build();

        return messageRepository.save(message);
    }

    public List<Message> getMessageList(Long chatroomId){
        return messageRepository.findAllByChatroomId(chatroomId);
    }

}
