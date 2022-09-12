package com.innovation.newneekclone.service;

import com.innovation.newneekclone.dto.ResponseDto;
import com.innovation.newneekclone.entity.News;
import com.innovation.newneekclone.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;

    public ResponseDto<?> getAllNews() {
        return ResponseDto.success(newsRepository.findAllByOrderByDate());
    }

    @Transactional
    public ResponseDto<?> getNews(Long newsid) {
//        News news = isPresentNews(newsid);
//        if(news == null){
//            return ResponseDto.fail("NOT_FOUNDED", "존재하지 않는 게시글");
//        }
        News news = newsRepository.findById(newsid).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 게시글입니다.")
        );
        return ResponseDto.success(news);
    }

//    @Transactional(readOnly = true)
//    public News isPresentNews(Long newsid){
//        Optional<News> optionalNews = newsRepository.findById(newsid);
//        return optionalNews.orElse(null);
//    }
}