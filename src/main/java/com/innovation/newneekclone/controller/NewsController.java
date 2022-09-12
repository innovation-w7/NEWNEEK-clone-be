package com.innovation.newneekclone.controller;

import com.innovation.newneekclone.dto.ResponseDto;
import com.innovation.newneekclone.entity.News;
import com.innovation.newneekclone.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    @GetMapping("/api/news")
    public ResponseDto<?> getAllNews() {
        return newsService.getAllNews();
    }

    @GetMapping("/api/news/{newsid}")
    public ResponseDto<?> getNews(@PathVariable Long newsid){
        return newsService.getNews(newsid);
    }


}
