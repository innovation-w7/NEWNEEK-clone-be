package com.innovation.newneekclone.test;

import com.innovation.newneekclone.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth/")
public class PostController {

    private final PostService postService;

    @PostMapping("/post")
    public ResponseDto<?> create(@RequestBody PostRequestDto requestDto, HttpServletRequest request) throws Exception {
       return postService.create(requestDto,request);
    }

}
