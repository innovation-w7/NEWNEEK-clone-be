package com.innovation.newneekclone.controller;

import com.innovation.newneekclone.dto.ProfileRequestDto;
import com.innovation.newneekclone.dto.ResponseDto;
import com.innovation.newneekclone.entity.User;
import com.innovation.newneekclone.security.UserDetailsImpl;
import com.innovation.newneekclone.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class MyPageController {
    private final MyPageService myPageService;

    @GetMapping("/api/auth/mypage/like")
    public ResponseDto<?> getMyLike(HttpServletRequest request) {
        return myPageService.getMyLike(request);
    }

    @GetMapping("/api/auth/mypage/profile")
    public ResponseDto<?> getMyProfile(HttpServletRequest request) {
        return myPageService.getMyProfile(request);
    }

    @PatchMapping("/api/auth/mypage/profile")
    public ResponseDto<?> changeMyProfile(HttpServletRequest request, @RequestBody ProfileRequestDto requestDto) {
       return myPageService.changeMyProfile(request, requestDto);
    }

    @DeleteMapping("/api/auth/mypage/profile")
    public ResponseDto<?> deleteMyAccount(HttpServletRequest request, @RequestBody ProfileRequestDto requestDto) {
        return myPageService.deleteMyAccount(request, requestDto);
    }
}
