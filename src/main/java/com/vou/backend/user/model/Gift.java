package com.vou.backend.user.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "gifts")
@Getter
@Setter
public class Gift {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sender_id")
    private Long senderId;

    @Column(name = "receiver_id")
    private Long receiverId;

    @Column(name = "token", unique = true)
    private String token;

    @Column(name = "type")
    private String type;

    @Column(name = "content")
    private long content;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "redeemed_at")
    private LocalDateTime redeemedAt;
}
