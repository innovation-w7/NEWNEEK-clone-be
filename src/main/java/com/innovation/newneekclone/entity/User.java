package com.innovation.newneekclone.entity;

import com.innovation.newneekclone.dto.UserSignupRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "users")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    @Column(nullable = false)
    private String role;

//    public User(String email, String password, String nickname, Boolean isSubscribe) { //String role
//        this.email = email;
//        this.password = password;
//        this.nickname = nickname;
//        this.isSubscribe = isSubscribe;
//        this.role = role;
//    }
}
