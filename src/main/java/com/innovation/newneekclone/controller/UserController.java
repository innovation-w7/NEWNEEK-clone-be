package com.innovation.newneekclone.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.innovation.newneekclone.config.GoogleConfigUtils;
import com.innovation.newneekclone.dto.ResponseDto;
import com.innovation.newneekclone.dto.UserLoginRequestDto;
import com.innovation.newneekclone.dto.UserSignupRequestDto;
import com.innovation.newneekclone.service.GoogleUserService;
import com.innovation.newneekclone.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;
    private final GoogleUserService googleUserService;
    private final GoogleConfigUtils googleConfigUtils;

    @PostMapping("/api/user/signup")
    public ResponseDto<?> signup(@RequestBody @Valid UserSignupRequestDto userSignupRequestDto) {
        return userService.signup(userSignupRequestDto);
    }

    @PostMapping("/api/user/login")
    public ResponseDto<?> login(@RequestBody @Valid UserLoginRequestDto userLoginRequestDto, HttpServletResponse response) {
        return userService.login(userLoginRequestDto,response);
    }

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
