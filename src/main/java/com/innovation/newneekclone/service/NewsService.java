package com.innovation.newneekclone.service;

import com.innovation.newneekclone.dto.response.NewsResponseDto;
import com.innovation.newneekclone.dto.response.ResponseDto;
import com.innovation.newneekclone.entity.News;
import com.innovation.newneekclone.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;

    public ResponseEntity<?> getAllNews() {
        List<News> newsList = newsRepository.findAll();
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
        return ResponseEntity.ok().body(ResponseDto.success(newsListDto));
    }

    public ResponseEntity<?> getCategoryNews(String category){
        List<News> newsList = newsRepository.findAllByCategoryOrderByDateDesc(category);
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
        return ResponseEntity.ok().body(ResponseDto.success(newsListDto));
    }

    public ResponseEntity<?> getNews(Long news_id) {
        News news = newsRepository.findById(news_id).orElse(null);
        if (news == null) {
            return ResponseEntity.badRequest().body(ResponseDto.fail("NULL_ID", "기사가 존재하지 않습니다."));
        }
        return ResponseEntity.ok().body(ResponseDto.success(news));
    }

    public ResponseEntity<?> searchNews(String keyword) {
        List<News> newsList = newsRepository.findByContentContaining(keyword);
        newsList.addAll(newsRepository.findByTitleContaining(keyword));
        List<NewsResponseDto> newsListDto = new ArrayList<>();
        List<Long> idValue = new ArrayList<>();
        for (News news : newsList) {
            if (!idValue.contains(news.getId())) {
                idValue.add(news.getId());
                newsListDto.add(
                        NewsResponseDto.builder()
                                .id(news.getId())
                                .date(news.getDate())
                                .title(news.getTitle())
                                .category(news.getCategory())
                                .contentSum(news.getContentSum())
                                .build());
            }
        }
        return ResponseEntity.ok().body(ResponseDto.success(newsListDto));
    }
}