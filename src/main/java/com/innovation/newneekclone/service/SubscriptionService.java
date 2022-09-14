package com.innovation.newneekclone.service;

import com.innovation.newneekclone.dto.request.DetailPageSubscribeRequestDto;
import com.innovation.newneekclone.dto.request.MainPageSubscribeRequestDto;
import com.innovation.newneekclone.dto.response.ResponseDto;
import com.innovation.newneekclone.entity.Subscription;
import com.innovation.newneekclone.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    public ResponseEntity<?> detailPageSubscribe(DetailPageSubscribeRequestDto requestDto) {
        String email = requestDto.getEmail();

        Subscription findSubscription = subscriptionRepository.findByEmail(email);
        if (findSubscription != null) {
            return ResponseEntity.badRequest().body(ResponseDto.fail("SUBSCRIPTION_FAIL", "이미 구독하셨네요!"));
        }

        Subscription subscription = new Subscription(email, null, 0L);
        subscriptionRepository.save(subscription);
        return ResponseEntity.ok().body(ResponseDto.success("Subscription Success"));
    }

    public ResponseEntity<?> mainPageSubscribe(MainPageSubscribeRequestDto requestDto) {
        String email = requestDto.getEmail();
        String nickname = requestDto.getNickname();

        Subscription findSubscription = subscriptionRepository.findByEmail(email);
        if (findSubscription != null) {
            return ResponseEntity.badRequest().body(ResponseDto.fail("SUBSCRIPTION_FAIL", "이미 구독하셨네요!"));
        }

        Subscription subscription = new Subscription(email, nickname, 0L);
        subscriptionRepository.save(subscription);
        return ResponseEntity.ok().body(ResponseDto.success("Subscription Success"));
    }
}
