package com.innovation.newneekclone.controller;

import com.innovation.newneekclone.dto.ResponseDto;
import com.innovation.newneekclone.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/news/{newsId}")
    public ResponseDto<?> like(@PathVariable Long newsId, HttpServletRequest request) {
        return likeService.like(newsId,request);
    }


}
