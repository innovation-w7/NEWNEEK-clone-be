package com.innovation.newneekclone.service;

import com.innovation.newneekclone.dto.response.ResponseDto;
import com.innovation.newneekclone.entity.Like;
import com.innovation.newneekclone.entity.News;
import com.innovation.newneekclone.repository.LikeRepository;
import com.innovation.newneekclone.repository.NewsRepository;
import com.innovation.newneekclone.security.UserDetailsImpl;
import com.innovation.newneekclone.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final JwtTokenProvider jwtTokenProvider;
    private final LikeRepository likeRepository;
    private final NewsRepository newsRepository;
    public ResponseEntity<?> like(Long newsId, HttpServletRequest request) {
        Authentication authentication = jwtTokenProvider.getAuthentication(jwtTokenProvider.resolveToken(request));
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        News news = isPresentNews(newsId);
        Optional<Like> isDoneLike = likeRepository.findByNewsAndUser(news,userDetails.getUser());
            if(news==null){
                return ResponseEntity.badRequest().body(ResponseDto.fail("WRONG_ACCESS","뉴스가 없습니다"));
            }
            if (isDoneLike.isEmpty()) { // isDoneLike==null 하면 오류남
                Like like = Like.builder()
                        .user(userDetails.getUser())
                        .news(news)
                        .build();
                likeRepository.save(like);
                news.likeCount(1);
                newsRepository.save(news);
                return ResponseEntity.ok().body(ResponseDto.success("좋아요 등록."));

            }else {
                likeRepository.delete(isDoneLike.get());
                news.likeCount(-1);
                newsRepository.save(news);
                return ResponseEntity.ok().body(ResponseDto.success("좋아요 취소."));
            }
        }

    private News isPresentNews(Long newsId) {
        Optional<News> optionalNews =newsRepository.findById(newsId);
        return optionalNews.orElse(null);

    }
}

