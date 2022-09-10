package com.innovation.newneekclone.controller;

import com.innovation.newneekclone.dto.DetailPageSubscribeRequestDto;
import com.innovation.newneekclone.dto.MainPageSubscribeRequestDto;
import com.innovation.newneekclone.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping("/api/detail/subscribe")
    public void detailPageSubscribe(@RequestBody DetailPageSubscribeRequestDto requestDto) {
        subscriptionService.detailPageSubscribe(requestDto);
    }

    @PostMapping("/api/main/subscribe")
    public void mainPageSubscribe(@RequestBody MainPageSubscribeRequestDto requestDto) {
        subscriptionService.mainPageSubscribe(requestDto);
    }
}
