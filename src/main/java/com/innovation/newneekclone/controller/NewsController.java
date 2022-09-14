package com.innovation.newneekclone.controller;

import com.innovation.newneekclone.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    @GetMapping("/api/news")
    public ResponseEntity<?> getAllNews() {
        return newsService.getAllNews();
    }

    @GetMapping("/api/news/category/{category}")
    public ResponseEntity<?> getCategoryNews(@PathVariable String category){
        return newsService.getCategoryNews(category);
    }

    @GetMapping("/api/news/{news_id}")
    public ResponseEntity<?> getNews(@PathVariable Long news_id){
        return newsService.getNews(news_id);
    }

    @GetMapping("/api/news/search/{keyword}")
    public ResponseEntity<?> searchNews(@PathVariable String keyword){
        return newsService.searchNews(keyword);
    }
}
