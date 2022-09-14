package com.innovation.newneekclone.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.innovation.newneekclone.config.GoogleConfigUtils;
import com.innovation.newneekclone.dto.ResponseDto;
import com.innovation.newneekclone.dto.UserClaimRequestDto;
import com.innovation.newneekclone.dto.UserLoginRequestDto;
import com.innovation.newneekclone.dto.UserSignupRequestDto;
import com.innovation.newneekclone.service.GoogleUserService;
import com.innovation.newneekclone.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")

public class UserController {

    private final UserService userService;
    private final GoogleUserService googleUserService;
    private final GoogleConfigUtils googleConfigUtils;


    @PostMapping("/signup") // 회원가입
>>>>>>> 1549e83adab6f81f14e3f303ba3b07db5fdfcb33
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

    // 버튼 누르면 구글 로그인 폼 나옴
    @GetMapping(value = "/api/user/login/google")
    public ResponseEntity<Object> moveGoogleInitUrl() {
        String authUrl = googleConfigUtils.googleInitUrl();
        URI redirectUri;
        try {
            redirectUri = new URI(authUrl);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setLocation(redirectUri);
            return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return ResponseEntity.badRequest().build();
    }

    @GetMapping("login/oauth2/code/google")
    public ResponseEntity<?> redirectGoogleLogin(@RequestParam(value = "code") String authCode, HttpServletResponse response) throws JsonProcessingException {
        return googleUserService.googleLogin(authCode, response);
    }

}
