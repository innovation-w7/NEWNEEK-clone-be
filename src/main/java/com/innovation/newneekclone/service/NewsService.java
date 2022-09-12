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
    public ResponseDto<?> getNews(Long news_id) {
        News news = newsRepository.findById(news_id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 게시글입니다.")
        );
        return ResponseDto.success(news);
    }
}