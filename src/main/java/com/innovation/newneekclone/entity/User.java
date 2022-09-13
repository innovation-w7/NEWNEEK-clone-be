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

    private String provider;    // oauth2를 이용할 경우 어떤 플랫폼을 이용하는지
    private String providerId;  // oauth2를 이용할 경우 아이디값


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

    public User(String email, String password, String nickname, Boolean isSubscribe, String role, String provider, String providerId) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.isSubscribe = isSubscribe;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
    }

    @Builder(builderClassName = "OAuth2Register", builderMethodName = "oauth2Register")


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
