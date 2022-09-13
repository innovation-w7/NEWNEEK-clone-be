package com.innovation.newneekclone.service;

import com.innovation.newneekclone.dto.*;
import com.innovation.newneekclone.entity.Claim;
import com.innovation.newneekclone.entity.Like;
import com.innovation.newneekclone.entity.News;
import com.innovation.newneekclone.entity.User;
import com.innovation.newneekclone.repository.*;
import com.innovation.newneekclone.security.UserDetailsImpl;
import com.innovation.newneekclone.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class MyPageService {

    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final ClaimRepository claimRepository;

    public ResponseDto<?> getMyLike(HttpServletRequest request) {
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
        return ResponseDto.success(newsList);
    }

    public ResponseDto<?> getMyProfile(HttpServletRequest request) {
        Authentication authentication = jwtTokenProvider.getAuthentication(jwtTokenProvider.resolveToken(request));
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        //User user = userDetails.getUser(); //유저 정보 받아오기
        return ResponseDto.success(
                ProfileResponseDto.builder()
                        .nickname(userDetails.getUser().getNickname()) // 유저의 닉네임,
                        .isSubscribe(userDetails.getUser().getIsSubscribe()).build() // 구독여부 받아오기. build()추가
        );
    }

    @Transactional
    public ResponseDto<?> changeMyProfile(HttpServletRequest request, ProfileRequestDto requestDto) {
        Authentication authentication = jwtTokenProvider.getAuthentication(jwtTokenProvider.resolveToken(request));
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userDetails.getUser(); //유저 정보 받아오기
        if (requestDto.getNickname() != null) {
            userDetails.getUser().updateNickname(requestDto.getNickname());
            userRepository.save(user);
            return ResponseDto.success("Nickname is Changed");
        } //닉네임 바꾸는 경우
        if (requestDto.getPassword() != null) {
            userDetails.getUser().updatePassword(requestDto.getPassword());
            userRepository.save(user);
            return ResponseDto.success("Password is Changed");
        } //비밀번호 바꾸는 경우 -> 패스위드 인코딩 확인하기
        if (requestDto.getIsSubscribe() != userDetails.getUser().getIsSubscribe()) {
            userDetails.getUser().updateIsSubcribe(requestDto.getIsSubscribe());
            userRepository.save(user);
            return ResponseDto.success("IsSubscribe is Changed");
        }//구독 여부 바꾸는 경우
        return ResponseDto.fail("NOT_CHANGED","Nothing has changed");
    }

    @Transactional
    public ResponseDto<?> deleteMyAccount(HttpServletRequest request, ProfileRequestDto requestDto) {
        Authentication authentication = jwtTokenProvider.getAuthentication(jwtTokenProvider.resolveToken(request));
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        //User user = userDetails.getUser(); //멤버 식별하기
        if (requestDto.getPassword().equals(userDetails.getUser().getPassword())) {  //비밀번호 확인하기 -> 패스워드 디코딩후 확인하게 수정하기
            userRepository.deleteById(userDetails.getUser().getId()); //리포지토리에서 유저 삭제하기
            return ResponseDto.success("Delete Success");
        }
        return ResponseDto.fail("NOT_MATCH","passwords Do Not Match");
    }

    public ResponseDto<?> getMyClaim(HttpServletRequest request) {
        Authentication authentication = jwtTokenProvider.getAuthentication(jwtTokenProvider.resolveToken(request));
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<Claim> myClaims= claimRepository.findByUser(userDetails.getUser());

        if(myClaims.isEmpty()){
            return ResponseDto.fail("NOTING_CLAIM","건의이력이 없습니다");
        }
        List<ClaimResponseDto> claims = new ArrayList<>();
        for(Claim claim:myClaims){
            claims.add(ClaimResponseDto.builder()
                    .id(claim.getId())
                    .date(claim.getDate())
                    .userEmail(claim.getUser().getEmail())
                    .content(claim.getContent())
                    .title(claim.getTitle())
                    .build());
        }
        return ResponseDto.success(claims);
    }
}