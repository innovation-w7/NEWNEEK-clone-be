package com.innovation.newneekclone.service;

import com.innovation.newneekclone.dto.ResponseDto;
import com.innovation.newneekclone.entity.Like;
import com.innovation.newneekclone.entity.News;
import com.innovation.newneekclone.entity.User;
import com.innovation.newneekclone.repository.LikeRepository;
import com.innovation.newneekclone.repository.NewsRepository;
import com.innovation.newneekclone.repository.UserRepository;
import com.innovation.newneekclone.security.UserDetailsImpl;
import com.innovation.newneekclone.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final LikeRepository likeRepository;
    private final NewsRepository newsRepository;
    public ResponseDto<?> like(Long newsId, HttpServletRequest request) {
            News news = isPresentNews(newsId);
            Authentication authentication = jwtTokenProvider.getAuthentication(jwtTokenProvider.resolveToken(request));
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            Optional<Like> isDoneLike = likeRepository.findByNewsAndUser(news,userDetails.getUser());
            if(news==null){
                return ResponseDto.fail("WRONG_ACCESS","뉴스가 없습니다");
            }
            if (isDoneLike.isEmpty()) { // isDoneLike==null 하면 오류남
                Like like = Like.builder()
                        .user(userDetails.getUser())
                        .news(news)
                        .build();
                likeRepository.save(like);
                return ResponseDto.success("좋아요 등록.");

            }else {
                likeRepository.delete(isDoneLike.get());
                return ResponseDto.success("좋아요 취소.");
            }
        }

    private News isPresentNews(Long newsId) {
        Optional<News> optionalNews =newsRepository.findById(newsId);
        return optionalNews.orElse(null);

    }
}

