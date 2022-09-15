package com.innovation.newneekclone.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.innovation.newneekclone.dto.KakaoUserInfoDto;
import com.innovation.newneekclone.entity.Subscription;
import com.innovation.newneekclone.entity.User;
import com.innovation.newneekclone.repository.SubscriptionRepository;
import com.innovation.newneekclone.repository.UserRepository;
import com.innovation.newneekclone.security.UserDetailsImpl;
import com.innovation.newneekclone.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class KakaoUserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    private final SubscriptionRepository subscriptionRepository;

    private final JwtTokenProvider tokenProvider;

    public KakaoUserInfoDto kakaoLogin(String code, HttpServletResponse response) throws JsonProcessingException{
        String accessToken = getAccessToken(code);

        KakaoUserInfoDto kakaoUserInfo = getKakaoUserInfo(accessToken);

        User kakaoUser = registerKakaoUserIfNeed(kakaoUserInfo);

        Authentication authentication = forceLogin(kakaoUser);

        kakaoUsersAuthorizationInput(kakaoUser, authentication, response);
        return kakaoUserInfo;
    }

    private String getAccessToken(String code)throws JsonProcessingException{
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", "clientid"); //kakao developer에서 제공된 API키 넣기
        body.add("redirect_uri", "http://localhost:8080/api/user/kakao");
        body.add("code", code);

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(body, headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "http://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class);

        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        return jsonNode.get("access_token").asText();
    }

    private KakaoUserInfoDto getKakaoUserInfo(String accessToken) throws  JsonProcessingException{
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoUserInfoRequest,
                String.class
        );

        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        Long id = jsonNode.get("id").asLong();
        String email = jsonNode.get("kakao_account").get("email").asText();
        String nickname = jsonNode.get("properties").get("nickname").asText();
        System.out.println();
        return new KakaoUserInfoDto(id, nickname, email);
    }

    private User registerKakaoUserIfNeed(KakaoUserInfoDto kakaoUserInfo){
        String kakaoEmail = kakaoUserInfo.getEmail();
        User kakaoUser = userRepository.findByEmail(kakaoEmail).orElse(null);

         if(kakaoUser == null){
             String nickname = kakaoUserInfo.getNickname();
             String password = UUID.randomUUID().toString(); //카카오 로그인을 할 때에는 pw없음(카카오에서 안줌)
             //따라서 pw에 값을 집어넣기 위해서 대충 중복되는 아무값이나 적용하는 콛ㅡ
             String encodedPassword = passwordEncoder.encode(password);
             String role = "ROLE_USER";
             Boolean isSubscribe = false;
             Subscription subscription = subscriptionRepository.findByEmail(kakaoEmail).orElse(null);
             if(subscription != null){
                 isSubscribe = true;
             }
             kakaoUser = new User(kakaoEmail, nickname, encodedPassword, isSubscribe, role);
             userRepository.save(kakaoUser);
         }
         return kakaoUser;
    }
    private Authentication forceLogin(User kakaoUser) {
        UserDetails userDetails = new UserDetailsImpl(kakaoUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }

    private void kakaoUsersAuthorizationInput(User kakaoUser, Authentication authentication, HttpServletResponse response) {
        // response header에 token 추가
        UserDetailsImpl userDetailsImpl = ((UserDetailsImpl) authentication.getPrincipal());
        String token = tokenProvider.createToken(kakaoUser);
        response.addHeader("access-token", token);
    }

}
