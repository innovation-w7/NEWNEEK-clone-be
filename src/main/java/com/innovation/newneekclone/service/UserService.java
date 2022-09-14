package com.innovation.newneekclone.service;

<<<<<<< HEAD
import com.innovation.newneekclone.dto.*;
import com.innovation.newneekclone.entity.Claim;
import com.innovation.newneekclone.dto.response.ResponseDto;
import com.innovation.newneekclone.dto.request.UserLoginRequestDto;
import com.innovation.newneekclone.dto.request.UserSignupRequestDto;
import com.innovation.newneekclone.entity.Subscription;
import com.innovation.newneekclone.entity.User;
import com.innovation.newneekclone.repository.ClaimRepository;
import com.innovation.newneekclone.repository.SubscriptionRepository;
import com.innovation.newneekclone.repository.UserRepository;
import com.innovation.newneekclone.security.Authority;
import com.innovation.newneekclone.security.UserDetailsImpl;
import com.innovation.newneekclone.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final SubscriptionRepository subscriptionRepository;


    private final ClaimRepository claimRepository;

    private static final String ADMIN_PASSWORD="ADMIN1234";

    public ResponseDto signup(UserSignupRequestDto userSignupRequestDto) {

    public ResponseEntity<?> signup(UserSignupRequestDto userSignupRequestDto) {

        if (null != isPresentUser(userSignupRequestDto.getEmail())) {
            return ResponseEntity.badRequest().body(ResponseDto.fail("DUPLICATED_EMAIL", "가입된 이메일 입니다."));
        }
        if (!userSignupRequestDto.getPassword().equals(userSignupRequestDto.getPasswordConfirm())) {
            return ResponseEntity.badRequest().body(ResponseDto.fail("PASSWORDS_NOT_MATCHED", "비밀번호와 비밀번호 확인이 일치하지 않습니다."));
        }
        // 관리자 체크 후 비밀번호가 ADMIN1234이라면, 관리자로 회원가입
        if (userSignupRequestDto.getIsAdmin() && userSignupRequestDto.getPassword().equals(ADMIN_PASSWORD)) {
            User user = User.builder()
                    .email(userSignupRequestDto.getEmail())
                    .nickname(userSignupRequestDto.getNickname())
                    .password(passwordEncoder.encode(userSignupRequestDto.getPassword()))
                    .role(Authority.ROLE_ADMIN)
                    .isSubscribe(userSignupRequestDto.getIsSubscribe())
                    .build();
            userRepository.save(user);
            if (userSignupRequestDto.getIsSubscribe()) {
                Subscription subscription = new Subscription(
                        userSignupRequestDto.getEmail(),
                        userSignupRequestDto.getNickname(),
                        0L
                );
                subscriptionRepository.save(subscription);
            }
            return ResponseDto.success(user);
            // 그렇지 않을 경우 일반 회원으로 회원가입
        }
        else {
            User user = User.builder()
                    .email(userSignupRequestDto.getEmail())
                    .nickname(userSignupRequestDto.getNickname())
                    .password(passwordEncoder.encode(userSignupRequestDto.getPassword()))
                    .role(Authority.ROLE_USER)
                    .isSubscribe(userSignupRequestDto.getIsSubscribe())
                    .build();
            userRepository.save(user);
            if (userSignupRequestDto.getIsSubscribe()) {
                Subscription subscription = new Subscription(
                        userSignupRequestDto.getEmail(),
                        userSignupRequestDto.getNickname(),
                        0L
                );
                subscriptionRepository.save(subscription);
            }
            return ResponseDto.success(user);

        User user = User.builder()
                .email(userSignupRequestDto.getEmail())
                .nickname(userSignupRequestDto.getNickname())
                .password(passwordEncoder.encode(userSignupRequestDto.getPassword()))
                .role("ROLE_USER")
                .isSubscribe(userSignupRequestDto.getIsSubscribe())
                .build();

        userRepository.save(user);

        Subscription subscription = subscriptionRepository.findByEmail(user.getEmail());
        if (userSignupRequestDto.getIsSubscribe() && subscription == null) {
            subscription = new Subscription(
                    userSignupRequestDto.getEmail(),
                    userSignupRequestDto.getNickname(),
                    0L
            );
            subscriptionRepository.save(subscription);
        }



        return ResponseEntity.ok().body(ResponseDto.success(user));
    }

    public ResponseEntity<?> login(UserLoginRequestDto userLoginRequestDto, HttpServletResponse response) {
        User user = userRepository.findByEmail(userLoginRequestDto.getEmail()).orElse(null);

        if (user == null) {
            return ResponseEntity.badRequest().body(ResponseDto.fail("NO_USER", "아이디가 존재하지 않습니다."));
        }
        if (!passwordEncoder.matches(userLoginRequestDto.getPassword(), user.getPassword())) {
            return ResponseEntity.badRequest().body(ResponseDto.fail("PASSWORDS_NOT_MATCHED", "비밀번호가 일치하지 않습니다."));
        }

        String token = jwtTokenProvider.createToken(user);
        response.addHeader("access-token",token);
        return ResponseEntity.ok().body(ResponseDto.success(user));
    }


    public User isPresentUser(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.orElse(null);
    }

    public ResponseDto<?> claim(UserClaimRequestDto userClaimRequestDto,HttpServletRequest request) {
        Authentication authentication = jwtTokenProvider.getAuthentication(jwtTokenProvider.resolveToken(request));
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Claim claim = Claim.builder()
                .date(new Date())
                .title(userClaimRequestDto.getTitle())
                .content(userClaimRequestDto.getContent())
                .user(userDetails.getUser())
                .build();
        claimRepository.save(claim);
        ClaimResponseDto claimResponseDto = ClaimResponseDto.builder()
                    .id(claim.getId())
                    .userEmail(claim.getUser().getEmail())
                    .content(claim.getContent())
                    .title(claim.getTitle())
                    .build();
        return ResponseDto.success(claimResponseDto);
    }
}
