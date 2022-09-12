package com.innovation.newneekclone.service;

import com.innovation.newneekclone.dto.NewsResponseDto;
import com.innovation.newneekclone.dto.ResponseDto;
import com.innovation.newneekclone.entity.News;
import com.innovation.newneekclone.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;

    public ResponseDto<?> getAllNews() {
        List<News> newsList = newsRepository.findAllByOrderByDate();
        List<NewsResponseDto> newsListDto = new ArrayList<>();
        for (News news : newsList) {
            newsListDto.add(
                    NewsResponseDto.builder()
                            .id(news.getId())
                            .date(news.getDate())
                            .title(news.getTitle())
                            .category(news.getCategory())
                            .contentSum(news.getContentSum())
                            .build());
        }
        //news list 반환하기
        return ResponseDto.success(newsListDto);
    }

    @Transactional
    public ResponseDto<?> getNews(Long news_id) {
        News news = newsRepository.findById(news_id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 게시글입니다.")
        );
        return ResponseDto.success(news);
    }
}