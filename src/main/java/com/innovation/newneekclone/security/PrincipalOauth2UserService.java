package com.innovation.newneekclone.security;

import com.innovation.newneekclone.entity.User;
import com.innovation.newneekclone.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        String provider = userRequest.getClientRegistration().getRegistrationId();  //google
        String providerId = oAuth2User.getAttribute("sub");
        String nickname = provider+"_"+providerId; // 닉네임 임의 생성

        String uuid = UUID.randomUUID().toString().substring(0, 6);
        String password = bCryptPasswordEncoder.encode("password"+uuid);  // 비밀번호 임의 생성

        String email = oAuth2User.getAttribute("email");
        String role = "ROLE_USER"; // 일반 유저

        User user = userRepository.findByEmail(email).orElse(null);

        //DB에 없는 사용자라면 회원가입처리
        if(user == null){
            user = new User(
                    email,
                    password,
                    nickname,
                    false, // 구독 미신청
                    role,
                    provider,
                    providerId
            );
            userRepository.save(user);
        }

        return new UserDetailsImpl(user, oAuth2User.getAttributes());
    }
}
