package com.innovation.newneekclone.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.innovation.newneekclone.dto.ResponseDto;
import com.innovation.newneekclone.dto.KakaoUserInfoDto;
import com.innovation.newneekclone.dto.UserLoginRequestDto;
import com.innovation.newneekclone.dto.UserSignupRequestDto;
import com.innovation.newneekclone.service.KakaoUserService;
import com.innovation.newneekclone.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private final KakaoUserService kakaoUserService;

    @PostMapping("/user/signup") // 회원가입
    public ResponseDto<?> signup(@RequestBody @Valid UserSignupRequestDto userSignupRequestDto) {
        return userService.signup(userSignupRequestDto);
    }

    @PostMapping("/user/login") // 로그인
    public ResponseDto<?> login(@RequestBody @Valid UserLoginRequestDto userLoginRequestDto, HttpServletResponse response) {
        return userService.login(userLoginRequestDto,response);
    }

    @GetMapping("/user/kakao")
    public KakaoUserInfoDto kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        return kakaoUserService.kakaoLogin(code, response);
    }
}
