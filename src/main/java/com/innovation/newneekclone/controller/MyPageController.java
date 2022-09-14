package com.innovation.newneekclone.controller;

import com.innovation.newneekclone.dto.request.ProfileRequestDto;
import com.innovation.newneekclone.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class MyPageController {
    private final MyPageService myPageService;

    @GetMapping("/api/auth/mypage/like")
    public ResponseEntity<?> getMyLike(HttpServletRequest request) {
        return myPageService.getMyLike(request);
    }

    @GetMapping("/api/auth/mypage/profile")
    public ResponseEntity<?> getMyProfile(HttpServletRequest request) {
        return myPageService.getMyProfile(request);
    }

    @PatchMapping("/api/auth/mypage/profile")
    public ResponseEntity<?> changeMyProfile(HttpServletRequest request, @RequestBody ProfileRequestDto requestDto) {
       return myPageService.changeMyProfile(request, requestDto);
    }

    @DeleteMapping("/api/auth/mypage/profile")
    public ResponseEntity<?> deleteMyAccount(HttpServletRequest request, @RequestBody ProfileRequestDto requestDto) {
        return myPageService.deleteMyAccount(request, requestDto);
    }

    @GetMapping("/api/auth/mypage/claim")
    public ResponseDto<?> getMyClaim(HttpServletRequest request) {
        return myPageService.getMyClaim(request);
    }
}
