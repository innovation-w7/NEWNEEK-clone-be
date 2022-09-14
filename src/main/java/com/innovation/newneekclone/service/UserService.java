package com.innovation.newneekclone.service;

import com.innovation.newneekclone.dto.response.ResponseDto;
import com.innovation.newneekclone.dto.request.UserLoginRequestDto;
import com.innovation.newneekclone.dto.request.UserSignupRequestDto;
import com.innovation.newneekclone.entity.Subscription;
import com.innovation.newneekclone.entity.User;
import com.innovation.newneekclone.repository.SubscriptionRepository;
import com.innovation.newneekclone.repository.UserRepository;
import com.innovation.newneekclone.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final SubscriptionRepository subscriptionRepository;

    public ResponseEntity<?> signup(UserSignupRequestDto userSignupRequestDto) {
        if (null != isPresentUser(userSignupRequestDto.getEmail())) {
            return ResponseEntity.badRequest().body(ResponseDto.fail("DUPLICATED_EMAIL", "가입된 이메일 입니다."));
        }
        if (!userSignupRequestDto.getPassword().equals(userSignupRequestDto.getPasswordConfirm())) {
            return ResponseEntity.badRequest().body(ResponseDto.fail("PASSWORDS_NOT_MATCHED", "비밀번호와 비밀번호 확인이 일치하지 않습니다."));
        }

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

}
