package com.innovation.newneekclone.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "users")
@Getter
@NoArgsConstructor
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String nickname;
    @Column(nullable = false)
    private Boolean isSubscribe;

    public User(String email, String password, String nickname, Boolean isSubscribe) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.isSubscribe = isSubscribe;
    }
}
