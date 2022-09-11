package com.innovation.newneekclone.scheduler;

import com.innovation.newneekclone.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MailScheduler {

    private final SubscriptionRepository subscriptionRepository;

    @Scheduled(cron = "0 0 7 * * *") // 아침 7시
    public void sendNewsEmail() {


    }
}
