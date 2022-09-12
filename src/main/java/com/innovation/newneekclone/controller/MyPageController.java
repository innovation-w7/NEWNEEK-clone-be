package com.innovation.newneekclone.controller;

import com.innovation.newneekclone.dto.ProfileRequestDto;
import com.innovation.newneekclone.dto.ResponseDto;
import com.innovation.newneekclone.security.UserDetailsImpl;
import com.innovation.newneekclone.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MyPageController {
    private final MyPageService myPageService;

    @GetMapping("/api/auth/mypage/like")
    public ResponseDto<?> getMyLike(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return myPageService.getMyLike(userDetails);
    }

    @GetMapping("/api/auth/mypage/profile")
    public ResponseDto<?> getMyProfile(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return myPageService.getMyProfile(userDetails);
    }

    @PatchMapping("/api/auth/mypage/profile")
    public ResponseDto<?> changeMyProfile(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody ProfileRequestDto requestDto) {
       return myPageService.changeMyProfile(userDetails, requestDto);
    }

    @DeleteMapping("/api/auth/mypage/profile")
    public ResponseDto<?> deleteMyAccount(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody ProfileRequestDto requestDto) {
        return myPageService.deleteMyAccount(userDetails, requestDto);
    }
}
