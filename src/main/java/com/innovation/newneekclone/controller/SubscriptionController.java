package com.innovation.newneekclone.controller;

import com.innovation.newneekclone.dto.request.DetailPageSubscribeRequestDto;
import com.innovation.newneekclone.dto.request.MainPageSubscribeRequestDto;
import com.innovation.newneekclone.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping("/api/detail/subscribe")
    public ResponseEntity<?> detailPageSubscribe(@RequestBody DetailPageSubscribeRequestDto requestDto) {
        return subscriptionService.detailPageSubscribe(requestDto);
    }

    @PostMapping("/api/main/subscribe")
    public ResponseEntity<?> mainPageSubscribe(@RequestBody MainPageSubscribeRequestDto requestDto) {
        return subscriptionService.mainPageSubscribe(requestDto);
    }
}
