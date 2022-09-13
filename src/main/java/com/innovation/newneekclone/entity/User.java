package com.innovation.newneekclone.entity;

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

    public User(String email, String password, String nickname, Boolean isSubscribe) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.isSubscribe = isSubscribe;
    }

    public User(String email, String password, String nickname, Boolean isSubscribe, String role){
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.isSubscribe = isSubscribe;
        this.role = role;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updatePassword(String password) {
        this.password = password; //패스워드 인코딩 확인하기
    }

    public void updateIsSubcribe(Boolean isSubscribe) {
        this.isSubscribe = isSubscribe;
    }

}
