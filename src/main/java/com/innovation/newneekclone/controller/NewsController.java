package com.innovation.newneekclone.controller;

import com.innovation.newneekclone.dto.ResponseDto;
import com.innovation.newneekclone.entity.News;
import com.innovation.newneekclone.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    @GetMapping("/api/news")
    public ResponseDto<?> getAllNews() {
        return newsService.getAllNews();
    }

    @GetMapping("/api/news/category/{category}")
    public ResponseDto<?> getCategoryNews(@PathVariable String category){
        return newsService.getCategoryNews(category);
    }

    @GetMapping("/api/news/{news_id}")
    public ResponseDto<?> getNews(@PathVariable Long news_id){
        return newsService.getNews(news_id);
    }
}
