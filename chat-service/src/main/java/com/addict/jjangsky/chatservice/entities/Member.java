package com.addict.jjangsky.chatservice.entities;

import com.nimbusds.openid.connect.sdk.claims.Gender;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Member {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    @Id
    Long id;

    String email;
    String nickName;
    String name;
    @Enumerated(EnumType.STRING)
    Gender gender;
    String phoneNumber;
    LocalDate birthDay;
    String role;
}
