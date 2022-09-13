package com.innovation.newneekclone.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscription_id")
    private Long id;

    @Column(nullable = false)
    private String email;
    private String nickname;
    @Column(nullable = false)
    private Long lastSentNewsId;

    public void updateLastSentNewsId() {
        this.lastSentNewsId++;
    }

    public Subscription(String email, String nickname, Long lastSentNewsId) {
        this.email = email;
        this.nickname = nickname;
        this.lastSentNewsId = lastSentNewsId;
    }

    public Subscription orElse(Object other) {
        return null;
    }
}
