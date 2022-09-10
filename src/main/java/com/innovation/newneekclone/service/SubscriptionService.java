package com.innovation.newneekclone.service;

import com.innovation.newneekclone.dto.DetailPageSubscribeRequestDto;
import com.innovation.newneekclone.dto.MainPageSubscribeRequestDto;
import com.innovation.newneekclone.entity.Subscription;
import com.innovation.newneekclone.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    public void detailPageSubscribe(DetailPageSubscribeRequestDto requestDto) {
        String email = requestDto.getEmail();
        checkSubscription(email);

        Subscription subscription = new Subscription(email, null, 0L);
        subscriptionRepository.save(subscription);
    }

    public void mainPageSubscribe(MainPageSubscribeRequestDto requestDto) {
        String email = requestDto.getEmail();
        String nickname = requestDto.getNickname();
        checkSubscription(email);

        Subscription subscription = new Subscription(email, nickname, 0L);
        subscriptionRepository.save(subscription);
    }

    private void checkSubscription(String email) {
        Subscription findSubscription = subscriptionRepository.findByEmail(email);
        if (findSubscription != null) {
            throw new IllegalArgumentException("이미 구독하셨네요!");
        }
    }
}
