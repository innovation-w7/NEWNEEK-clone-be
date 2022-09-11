package com.innovation.newneekclone.test;

import com.innovation.newneekclone.dto.ResponseDto;
import com.innovation.newneekclone.entity.User;
import com.innovation.newneekclone.repository.UserRepository;
import com.innovation.newneekclone.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @Transactional
    public ResponseDto<?> create(PostRequestDto requestDto, HttpServletRequest request) throws Exception {
//        if (null == request.getHeader("access-token")) {
//            return ResponseDto.fail("NOT_LOGIN", "로그인 필요");
//        }
        Optional<User> user = userRepository.findByEmail(jwtTokenProvider.getEmail(jwtTokenProvider.resolveToken(request)));
        Post post = Post.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .build();
        postRepository.save(post);
        return ResponseDto.success(post.getTitle());
        }
}
