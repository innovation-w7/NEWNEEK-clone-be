package com.innovation.newneekclone.service;

import com.innovation.newneekclone.dto.ResponseDto;
import com.innovation.newneekclone.entity.Like;
import com.innovation.newneekclone.entity.News;
import com.innovation.newneekclone.entity.User;
import com.innovation.newneekclone.repository.LikeRepository;
import com.innovation.newneekclone.repository.NewsRepository;
import com.innovation.newneekclone.repository.UserRepository;
import com.innovation.newneekclone.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
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
            String token = jwtTokenProvider.resolveToken(request);
            UserDetails userDetails = (UserDetails) jwtTokenProvider.getAuthentication(token).getPrincipal();
            Optional<User> user = userRepository.findByEmail(userDetails.getUsername());
            Optional<Like> isDoneLike = likeRepository.findByNewsIdAndUserId(newsId,user.get().getId());
            if(isPresentNews(newsId)==null){
                return ResponseDto.fail("WRONG_ACCESS","뉴스가 없습니다");
            }

            if (null != isDoneLike) {
                likeRepository.delete(isDoneLike.get());
                return ResponseDto.success("좋아요 취소.");
            }

            Like like = Like.builder()
                    .user(user.get())
                    .news(isPresentNews(newsId))
                    .build();

            likeRepository.save(like);

            return ResponseDto.success("좋아요 등록.");
        }

    private News isPresentNews(Long newsId) {
        Optional<News> optionalNews =newsRepository.findById(newsId);
        return optionalNews.orElse(null);

    }
}

