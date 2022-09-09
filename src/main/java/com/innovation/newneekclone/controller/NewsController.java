package com.innovation.newneekclone.controller;

import com.innovation.newneekclone.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;


}
