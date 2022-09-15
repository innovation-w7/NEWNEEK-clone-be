package com.innovation.newneekclone.service;

import com.innovation.newneekclone.dto.response.NewsResponseDto;
import com.innovation.newneekclone.dto.request.ProfileRequestDto;
import com.innovation.newneekclone.dto.response.ProfileResponseDto;
import com.innovation.newneekclone.dto.response.ResponseDto;
import com.innovation.newneekclone.entity.Like;
import com.innovation.newneekclone.entity.News;
import com.innovation.newneekclone.entity.User;
import com.innovation.newneekclone.repository.LikeRepository;
import com.innovation.newneekclone.repository.UserRepository;
import com.innovation.newneekclone.security.UserDetailsImpl;
import com.innovation.newneekclone.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class MyPageService {

    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public ResponseEntity<?> getMyLike(HttpServletRequest request) {
        Authentication authentication = jwtTokenProvider.getAuthentication(jwtTokenProvider.resolveToken(request));
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<Like> likeList = likeRepository.findAllByUser(userDetails.getUser());
        List<NewsResponseDto> newsList = new ArrayList<>();
        for (Like like : likeList) {
            News news = like.getNews();
            newsList.add(
                    NewsResponseDto.builder()
                            .id(news.getId())
                            .date(news.getDate())
                            .title(news.getTitle())
                            .category(news.getCategory())
                            .contentSum(news.getContentSum())
                            .build());
        }
        //news list 반환하기
        return ResponseEntity.ok().body(ResponseDto.success(newsList));
    }

    public ResponseEntity<?> getMyProfile(HttpServletRequest request) {
        Authentication authentication = jwtTokenProvider.getAuthentication(jwtTokenProvider.resolveToken(request));
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userDetails.getUser(); //유저 정보 받아오기
        return ResponseEntity.ok().body(
                ResponseDto.success(
                        ProfileResponseDto.builder()
                        .nickname(user.getNickname()) // 유저의 닉네임,
                        .isSubscribe(user.getIsSubscribe()) // 구독여부 받아오기
                        .build()
                )
        );

    }

    @Transactional
    public ResponseEntity<?> changeMyProfile(HttpServletRequest request, ProfileRequestDto requestDto) {
        Authentication authentication = jwtTokenProvider.getAuthentication(jwtTokenProvider.resolveToken(request));
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userDetails.getUser(); //유저 정보 받아오기

        if (requestDto.getNickname() != null) {
            user.updateNickname(requestDto.getNickname());
            userRepository.save(user);
            return ResponseEntity.ok().body(ResponseDto.success("Nickname Changed"));
        } //닉네임 바꾸는 경우
        if (requestDto.getPassword() != null) {
            user.updatePassword(requestDto.getPassword());
            userRepository.save(user);
            return ResponseEntity.ok().body(ResponseDto.success("Password Changed"));
        } //비밀번호 바꾸는 경우 -> 패스위드 인코딩 확인하기
        if (requestDto.getIsSubscribe() != user.getIsSubscribe()) {
            user.updateIsSubcribe(requestDto.getIsSubscribe());
            userRepository.save(user);
            return ResponseEntity.ok().body(ResponseDto.success("IsSubscribe Changed"));
        }//구독 여부 바꾸는 경우

        return ResponseEntity.ok().body(ResponseDto.fail("NOT_CHANGED","Nothing Changed"));
    }

    public ResponseEntity<?> deleteMyAccount(HttpServletRequest request) {
        Authentication authentication = jwtTokenProvider.getAuthentication(jwtTokenProvider.resolveToken(request));
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        userRepository.deleteById(userDetails.getUser().getId()); //리포지토리에서 유저 삭제하기
        return ResponseEntity.ok().body(ResponseDto.success("Delete Success"));
    }
}