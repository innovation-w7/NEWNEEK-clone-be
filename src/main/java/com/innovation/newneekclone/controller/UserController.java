package com.innovation.newneekclone.controller;

import com.innovation.newneekclone.dto.ResponseDto;
import com.innovation.newneekclone.dto.UserClaimRequestDto;
import com.innovation.newneekclone.dto.UserLoginRequestDto;
import com.innovation.newneekclone.dto.UserSignupRequestDto;
import com.innovation.newneekclone.repository.UserRepository;
import com.innovation.newneekclone.security.jwt.JwtTokenProvider;
import com.innovation.newneekclone.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup") // 회원가입
    public ResponseDto<?> signup(@RequestBody @Valid UserSignupRequestDto userSignupRequestDto) {
        return userService.signup(userSignupRequestDto);
    }

    @PostMapping("/login") // 로그인
    public ResponseDto<?> login(@RequestBody @Valid UserLoginRequestDto userLoginRequestDto, HttpServletResponse response) {
        return userService.login(userLoginRequestDto,response);
    }

    @PostMapping("/claim") // 건의사항
    public ResponseDto<?> claim(@RequestBody UserClaimRequestDto userClaimRequestDto, HttpServletRequest request) {
        return userService.claim(userClaimRequestDto,request);
    }

}
